package com.srug.mobile.refuel.view;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.srug.mobile.refuel.MVP;
import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.common.GenericActivity;
import com.srug.mobile.refuel.presenter.RefuelingPresenter;
import com.srug.mobile.refuel.presenter.RefuelingViewModel;
import com.srug.mobile.refuel.utilities.ActivityUtilities;

import java.util.Date;

public class RefuelingActivity extends GenericActivity<MVP.RefuelingRequiredViewOps,
        MVP.RefuelingProvidedPresenterOps,
        RefuelingPresenter>
        implements MVP.RefuelingRequiredViewOps, RecordFragment.RecordFragmentListener {

    public static final String EXTRA_REFUELING_ID
            = "com.srug.mobile.refuel.view.RefuelingActivity.EXTRA_REFUELING_ID";
    public static final String EXTRA_VEHICLE_ID
            = "com.srug.mobile.refuel.view.RefuelingActivity.EXTRA_VEHICLE_ID";

    public static final int REQUEST_REFUELING_ACTIVITY = 100;

    public static final long NEW_REFUELING_ID = -1L;

    private RecordFragment mRecordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refueling);

        setupActionBar();
        initializeRecord();

        super.onCreate(RefuelingPresenter.class, this, getIntent().getExtras());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showCloseDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                showCloseDialog();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void setRefueling(RefuelingViewModel refueling) {
        mRecordFragment.setRefueling(refueling);
    }

    @Override
    public void refreshRefueling() {
        mRecordFragment.refreshRefueling();
    }

    @Override
    public void showToast(String message) {
        ActivityUtilities.showShortToast(this, message);
    }

    @Override
    public void closeActivity(int result) {
        setResult(result);
        finish();
    }

    @Override
    public void onRefuelingDateChanged(Date date) {
        getPresenter().updateRefuelingDate(date);
    }

    @Override
    public void onRefuelingDistanceChanged(Double distance) {
        getPresenter().updateRefuelingDistance(distance);
    }

    @Override
    public void onRefuelingPriceChanged(Double price) {
        getPresenter().updateRefuelingPrice(price);
    }

    @Override
    public void onRefuelingAmountChanged(Double amount) {
        getPresenter().updateRefuelingAmount(amount);
    }

    @Override
    public void onRefuelingSubmitCommand() {
        getPresenter().executeSubmit();
    }

    @Override
    public void onRefuelingCalculateCommand() {
        getPresenter().executeCalculate();
    }

    public static Intent makeIntent(Context context, long vehicleId, long refuelingId) {
        Intent intent = new Intent(context, RefuelingActivity.class);
        intent.putExtra(EXTRA_VEHICLE_ID, vehicleId);
        intent.putExtra(EXTRA_REFUELING_ID, refuelingId);
        return intent;
    }

    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.partial_customized_action_bar, null);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_action_bar_title);
        tvTitle.setText(R.string.action_bar_title_new_refueling);
        if (null != actionBar) {
            actionBar.setCustomView(v);
        }
    }

    private void initializeRecord() {
        mRecordFragment = (RecordFragment) getSupportFragmentManager().
                findFragmentByTag(RecordFragment.RECORD_FRAGMENT_TAG);
        if (mRecordFragment == null) {
            mRecordFragment = new RecordFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_record,
                            mRecordFragment,
                            RecordFragment.RECORD_FRAGMENT_TAG).commit();
        }
    }

    private void showCloseDialog() {
        Resources resources = getApplicationContext().getResources();
        ActivityUtilities.showYesCancelAlertDialog(
                RefuelingActivity.this,
                resources.getString(R.string.activity_refueling_close_dialog_title),
                resources.getString(R.string.activity_refueling_exit_without_saving_message),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        closeActivity(RESULT_CANCELED);
                    }
                });
    }
}
