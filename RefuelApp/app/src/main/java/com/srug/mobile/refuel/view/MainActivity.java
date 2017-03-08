package com.srug.mobile.refuel.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.srug.mobile.refuel.MVP;
import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.common.GenericActivity;
import com.srug.mobile.refuel.presenter.MainPresenter;
import com.srug.mobile.refuel.utilities.ActivityUtilities;
import com.srug.mobile.refuel.view.ui.RefuelingListAdapter;
import com.srug.mobile.refuel.view.ui.UserListAdapter;
import com.srug.mobile.refuel.view.ui.VehicleListAdapter;

public class MainActivity extends GenericActivity<MVP.MainRequiredViewOps,
        MVP.MainProvidedPresenterOps,
        MainPresenter>
        implements MVP.MainRequiredViewOps, ArchiveFragment.ArchiveFragmentListener {

    private DrawerLayout mDrawerLayout;
    private ArchiveFragment mArchiveFragment;
    private UserFragment mUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        initializeDrawer();
        initializeArchive();

        super.onCreate(MainPresenter.class, this, getIntent().getExtras());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RefuelingActivity.REQUEST_REFUELING_ACTIVITY:
                    getPresenter().reloadArchive();
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                switchDrawerLayout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTryToRefreshRefuelingList() {
        getPresenter().reloadArchive();
    }

    @Override
    public void setAdapter(UserListAdapter userListAdapter) {
        mUserFragment.setAdapter(userListAdapter);
    }

    @Override
    public void setAdapter(VehicleListAdapter vehicleListAdapter) {
        mUserFragment.setAdapter(vehicleListAdapter);
    }

    @Override
    public void setAdapter(RefuelingListAdapter refuelingListAdapter) {
        mArchiveFragment.setAdapter(refuelingListAdapter);
    }

    @Override
    public void showToast(String message) {
        ActivityUtilities.showShortToast(this, message);
    }

    @Override
    public void openNewRefueling(long vehicleId) {
        startActivityForResult(
                RefuelingActivity.makeIntent(
                        getApplicationContext(),
                        vehicleId,
                        RefuelingActivity.NEW_REFUELING_ID),
                RefuelingActivity.REQUEST_REFUELING_ACTIVITY);
    }

    @Override
    public void stopRefreshing() {
        mArchiveFragment.stopRefreshing();
    }

    public void selectUser(View view) {
        // TODO
    }

    public void selectVehicle(View view) {
        // TODO
    }

    public void addRefueling(View view) {
        getPresenter().newRefueling();
    }

    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.partial_customized_action_bar, null);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_action_bar_title);
        tvTitle.setText(R.string.action_bar_title_refueling_archive);
        if (null != actionBar) {
            actionBar.setCustomView(v);
        }
    }

    private void initializeDrawer() {
        mUserFragment = (UserFragment) getSupportFragmentManager().
                findFragmentByTag(UserFragment.USER_FRAGMENT_TAG);
        if (mUserFragment == null) {
            mUserFragment = new UserFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_drawer_container,
                            mUserFragment,
                            UserFragment.USER_FRAGMENT_TAG).commit();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.activity_main_drawer_layout_opening,
                R.string.activity_main_drawer_layout_closing) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initializeArchive() {
        mArchiveFragment = (ArchiveFragment) getSupportFragmentManager().
                findFragmentByTag(ArchiveFragment.ARCHIVE_FRAGMENT_TAG);
        if (mArchiveFragment == null) {
            mArchiveFragment = new ArchiveFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_archive,
                            mArchiveFragment,
                            ArchiveFragment.ARCHIVE_FRAGMENT_TAG).commit();
        }
    }

    private void switchDrawerLayout() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
