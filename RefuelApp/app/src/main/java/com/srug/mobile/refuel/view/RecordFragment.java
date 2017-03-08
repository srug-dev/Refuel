package com.srug.mobile.refuel.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.presenter.RefuelingViewModel;
import com.srug.mobile.refuel.utilities.DataFormatOperations;

import java.util.Calendar;
import java.util.Date;

import static com.srug.mobile.refuel.utilities.DataFormatOperations.DATE_FORMAT;

public class RecordFragment extends Fragment {

    private RecordFragmentListener mCallback;
    private EditText mEtDistance;
    private EditText mEtPrice;
    private EditText mEtAmount;
    private TextView mTvDate;
    private TextView mTvConsumption;
    private TextView mTvAverage;
    private Button mBtnChangeDate;
    private Button mBtnSubmit;
    private FloatingActionButton mBtnCalculate;
    private RefuelingViewModel mRefuelingViewModel;
    private Date mCurrentDate = Calendar.getInstance().getTime();
    private Double mCurrentDistance = 0.0;
    private Double mCurrentPrice = 0.0;
    private Double mCurrentAmount = 0.0;
    private Double mCurrentConsumption = 0.0;
    private Double mCurrentAverage = 0.0;

    public static final String RECORD_FRAGMENT_TAG
            = "com.srug.mobile.refuel.view.RECORD_FRAGMENT_TAG";

    public RecordFragment() {
    }

    public void setRefueling(RefuelingViewModel refuelingViewModel) {
        mRefuelingViewModel = refuelingViewModel;
    }

    public void refreshRefueling() {
        Double distance = mRefuelingViewModel.getDistance();
        if (mEtDistance != null && !mCurrentDistance.equals(distance)){
            mCurrentDistance = distance;
            mEtDistance.setText(String.valueOf(distance));
        }
        Double price = mRefuelingViewModel.getPrice();
        if (mEtPrice != null && !mCurrentPrice.equals(price)){
            mCurrentPrice = price;
            mEtPrice.setText(String.valueOf(price));
        }
        Double amount = mRefuelingViewModel.getAmount();
        if (mEtAmount != null && !mCurrentAmount.equals(amount)){
            mCurrentAmount = amount;
            mEtAmount.setText(String.valueOf(amount));
        }
        Date date = mRefuelingViewModel.getDate();
        if (mTvDate != null && !mCurrentDate.equals(date)){
            mCurrentDate = date;
            mTvDate.setText(DataFormatOperations.parseDateFormat(date, DATE_FORMAT));
        }
        Double consumption = mRefuelingViewModel.getConsumption();
        if (mTvConsumption != null && !mCurrentConsumption.equals(consumption)){
            mCurrentConsumption = consumption;
            mTvConsumption.setText(String.valueOf(consumption));
        }
        Double average = mRefuelingViewModel.getConsumptionAvg();
        if (mTvAverage != null && !mCurrentAverage.equals(average)){
            mCurrentAverage = average;
            mTvAverage.setText(String.valueOf(average));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof RecordFragmentListener) {
            mCallback = (RecordFragmentListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mEtDistance = (EditText)view.findViewById(R.id.et_record_distance);
        mEtPrice = (EditText)view.findViewById(R.id.et_record_price);
        mEtAmount = (EditText)view.findViewById(R.id.et_record_amount);
        mTvDate = (TextView)view.findViewById(R.id.tv_record_date);
        mTvConsumption = (TextView)view.findViewById(R.id.tv_record_consumption);
        mTvAverage = (TextView)view.findViewById(R.id.tv_record_avg_consumption);
        mBtnChangeDate = (Button)view.findViewById(R.id.btn_record_change_date);
        mBtnSubmit = (Button)view.findViewById(R.id.btn_record_submit);
        mBtnCalculate = (FloatingActionButton)view.findViewById(R.id.btn_record_calculate);
        mEtDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double distance = parseDoubleValue(mEtDistance.getText().toString());
                mCallback.onRefuelingDistanceChanged(distance);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double price = parseDoubleValue(mEtPrice.getText().toString());
                mCallback.onRefuelingPriceChanged(price);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double amount = parseDoubleValue(mEtAmount.getText().toString());
                mCallback.onRefuelingAmountChanged(amount);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mBtnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = mRefuelingViewModel.getDate();
                if (date == null) {
                    date = Calendar.getInstance().getTime();
                }
                openDatePickerDialog(view.getContext(), date);
            }
        });
        mBtnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onRefuelingCalculateCommand();
            }
        });
        mBtnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCallback.onRefuelingSubmitCommand();
            }
        });
    }

    private void openDatePickerDialog(Context context, Date currentDate) {
        final Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        mCallback.onRefuelingDateChanged(c.getTime());
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private static Double parseDoubleValue(String strValue) {
        try {
            return Double.parseDouble(strValue);
        } catch (Exception ex) {
            Log.d(RecordFragment.class.getName(), ex.getMessage());
            return 0.0;
        }
    }

    public interface RecordFragmentListener {
        void onRefuelingDateChanged(Date date);

        void onRefuelingDistanceChanged(Double distance);

        void onRefuelingPriceChanged(Double price);

        void onRefuelingAmountChanged(Double amount);

        void onRefuelingSubmitCommand();

        void onRefuelingCalculateCommand();
    }
}
