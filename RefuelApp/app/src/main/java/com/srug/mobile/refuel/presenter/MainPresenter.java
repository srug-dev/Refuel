package com.srug.mobile.refuel.presenter;

import android.os.Bundle;

import com.srug.mobile.refuel.MVP;
import com.srug.mobile.refuel.common.GenericAsyncTask;
import com.srug.mobile.refuel.common.GenericAsyncTaskOps;
import com.srug.mobile.refuel.model.mediator.DataMediator;
import com.srug.mobile.refuel.model.mediator.data.Refueling;
import com.srug.mobile.refuel.model.mediator.data.User;
import com.srug.mobile.refuel.model.mediator.data.Vehicle;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainPresenter implements
        GenericAsyncTaskOps<Long, Void, List<Refueling>>,
        MVP.MainProvidedPresenterOps {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private DataMediator mDataMediator;
    private WeakReference<MVP.MainRequiredViewOps> mMainView;
    private GenericAsyncTask<Long, Void, List<Refueling>, MainPresenter> mAsyncTask;

    private MainPresenter() {
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public List<Refueling> doInBackground(Long... vehicleIds) {
        return mDataMediator.getRefuelings(vehicleIds[0]);
    }

    @Override
    public void onPostExecute(List<Refueling> refuelingList) {
        // TODO
    }

    @Override
    public void onCreate(MVP.MainRequiredViewOps view) {
    }

    @Override
    public void onConfiguration(MVP.MainRequiredViewOps mainRequiredViewOps,
                                Bundle extras,
                                boolean firstTimeIn) {
        mMainView = new WeakReference<>(mainRequiredViewOps);
        if (firstTimeIn) {
            mDataMediator = new DataMediator(mMainView.get().getActivityContext());

            User preferredUser = mDataMediator.getPreferredUser();
            Vehicle preferredVehicle = mDataMediator.getPreferredVehicle();

            getRefuelingList(preferredVehicle.getId());
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
    }

    private void getRefuelingList(Long vehicleId) {
        mAsyncTask = new GenericAsyncTask<>(this);
        mAsyncTask.execute(vehicleId);
    }
}
