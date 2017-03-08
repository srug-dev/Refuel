package com.srug.mobile.refuel.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.srug.mobile.refuel.MVP;
import com.srug.mobile.refuel.common.GenericAsyncTask;
import com.srug.mobile.refuel.common.GenericAsyncTaskOps;
import com.srug.mobile.refuel.model.mediator.DataMediator;
import com.srug.mobile.refuel.model.mediator.data.Refueling;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.srug.mobile.refuel.view.RefuelingActivity.EXTRA_REFUELING_ID;
import static com.srug.mobile.refuel.view.RefuelingActivity.EXTRA_VEHICLE_ID;

public class RefuelingPresenter implements
        GenericAsyncTaskOps<Long, Void, Refueling>,
        MVP.RefuelingProvidedPresenterOps {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private long mVehicleId;
    private RefuelingViewModel mCurrentRefueling;
    private DataMediator mDataMediator;

    private WeakReference<MVP.RefuelingRequiredViewOps> mMainView;
    private GenericAsyncTask<Long, Void, Refueling, RefuelingPresenter> mAsyncTask;

    public RefuelingPresenter() {
    }

    @Override
    public void onPreExecute() {
    }

    @Override
    public void onCreate(MVP.RefuelingRequiredViewOps view) {
    }

    @Override
    public void onConfiguration(MVP.RefuelingRequiredViewOps refuelingRequiredViewOps,
                                Bundle extras,
                                boolean firstTimeIn) {

        mMainView = new WeakReference<>(refuelingRequiredViewOps);

        if (firstTimeIn) {
            Context context = mMainView.get().getActivityContext();

            mDataMediator = new DataMediator(context);

            mVehicleId = extras.getLong(EXTRA_VEHICLE_ID);

            getRefueling(extras.getLong(EXTRA_REFUELING_ID));
        }

        mMainView.get().setRefueling(mCurrentRefueling);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
    }

    private void getRefueling(Long refuelingId) {
        mAsyncTask = new GenericAsyncTask<>(this);
        mAsyncTask.execute(refuelingId);
    }

    @Override
    public Refueling doInBackground(Long... params) {
        return mDataMediator.getRefueling(params[0]);
    }

    @Override
    public void onPostExecute(Refueling result) {
        if (mCurrentRefueling == null) {
            long refuelingId = UUID.randomUUID().getLeastSignificantBits();
            mCurrentRefueling = new RefuelingViewModel(refuelingId, mVehicleId);
            mCurrentRefueling.setDate(Calendar.getInstance().getTime());
        } else {
            mCurrentRefueling = new RefuelingViewModel(result.getId(), result.getVehicleId());
            mCurrentRefueling.setDate(result.getDate());
            mCurrentRefueling.setDistance(result.getDistance());
            mCurrentRefueling.setPrice(result.getPrice());
            mCurrentRefueling.setAmount(result.getAmount());
        }

        mMainView.get().setRefueling(mCurrentRefueling);
        mMainView.get().refreshRefueling();
    }

    @Override
    public void executeCalculate() {
        String message = "";
        if (mCurrentRefueling.getDistance() <= 0) {
            message = "Distance must be > 0 Km";
        } else if (mCurrentRefueling.getPrice() <= 0) {
            message = "Price must be > 0 €/l";
        } else if (mCurrentRefueling.getAmount() <= 0) {
            message = "Amount must be > 0 €";
        }

        if (message.isEmpty()) {
            mMainView.get().refreshRefueling();
        } else {
            mMainView.get().showToast(message);
        }
    }

    @Override
    public void executeSubmit() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                if (mCurrentRefueling == null) {
                    cancel(true);
                }
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                Refueling refueling = new Refueling(
                        mCurrentRefueling.getId(),
                        mCurrentRefueling.getVehicleId());
                refueling.setDate(mCurrentRefueling.getDate());
                refueling.setDistance(mCurrentRefueling.getDistance());
                refueling.setPrice(mCurrentRefueling.getPrice());
                refueling.setAmount(mCurrentRefueling.getAmount());
                mDataMediator.insertRefueling(refueling);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    mMainView.get().showToast("Refueling recorded");
                    mMainView.get().closeActivity(RESULT_OK);
                } else {
                    mMainView.get().showToast("Refueling failed");
                }
            }
        }.execute();
    }

    @Override
    public void updateRefuelingDate(Date newDate) {
        final Date date = newDate;
        new AsyncTask<Date, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                if (mCurrentRefueling == null) {
                    cancel(true);
                }
            }

            @Override
            protected Boolean doInBackground(Date... params) {
                Date date = params[0];
                mCurrentRefueling.setDate(date);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    mMainView.get().refreshRefueling();
                }
            }
        }.execute(date);
    }

    @Override
    public void updateRefuelingDistance(Double newDistance) {
        final Double distance = newDistance;
        new AsyncTask<Double, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                if (mCurrentRefueling == null) {
                    cancel(true);
                }
            }

            @Override
            protected Boolean doInBackground(Double... params) {
                Double distance = params[0];
                mCurrentRefueling.setDistance(distance);
                return true;
            }
        }.execute(distance);
    }

    @Override
    public void updateRefuelingPrice(Double newPrice) {
        final Double price = newPrice;
        new AsyncTask<Double, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                if (mCurrentRefueling == null) {
                    cancel(true);
                }
            }

            @Override
            protected Boolean doInBackground(Double... params) {
                Double price = params[0];
                mCurrentRefueling.setPrice(price);
                return true;
            }
        }.execute(price);
    }

    @Override
    public void updateRefuelingAmount(Double newAmount) {
        final Double amount = newAmount;
        new AsyncTask<Double, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                if (mCurrentRefueling == null) {
                    cancel(true);
                }
            }

            @Override
            protected Boolean doInBackground(Double... params) {
                Double amount = params[0];
                mCurrentRefueling.setAmount(amount);
                return true;
            }
        }.execute(amount);
    }
}
