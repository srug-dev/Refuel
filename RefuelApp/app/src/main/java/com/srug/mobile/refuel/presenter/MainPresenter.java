package com.srug.mobile.refuel.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.srug.mobile.refuel.MVP;
import com.srug.mobile.refuel.common.GenericAsyncTask;
import com.srug.mobile.refuel.common.GenericAsyncTaskOps;
import com.srug.mobile.refuel.model.mediator.DataMediator;
import com.srug.mobile.refuel.model.mediator.data.Refueling;
import com.srug.mobile.refuel.model.mediator.data.User;
import com.srug.mobile.refuel.model.mediator.data.Vehicle;
import com.srug.mobile.refuel.presenter.handler.RefuelingListHandler;
import com.srug.mobile.refuel.presenter.handler.UserListHandler;
import com.srug.mobile.refuel.presenter.handler.VehicleListHandler;
import com.srug.mobile.refuel.view.ui.RefuelingListAdapter;
import com.srug.mobile.refuel.view.ui.UserListAdapter;
import com.srug.mobile.refuel.view.ui.VehicleListAdapter;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class MainPresenter implements
        GenericAsyncTaskOps<Long, Void, List<Refueling>>,
        MVP.MainProvidedPresenterOps {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private DataMediator mDataMediator;
    private UserListAdapter mUserListAdapter;
    private UserListHandler mUserListHandler;
    private VehicleListAdapter mVehicleListAdapter;
    private VehicleListHandler mVehicleListHandler;
    private RefuelingListAdapter mRefuelingListAdapter;
    private RefuelingListHandler mRefuelingListHandler;

    private WeakReference<MVP.MainRequiredViewOps> mMainView;
    private GenericAsyncTask<Long, Void, List<Refueling>, MainPresenter> mAsyncTask;

    public MainPresenter() {
    }

    @Override
    public void onPreExecute() {
    }

    @Override
    public List<Refueling> doInBackground(Long... vehicleIds) {
        List<Refueling> refuelingList = mDataMediator.getRefuelings(vehicleIds[0]);
        Collections.sort(refuelingList);
        Collections.reverse(refuelingList);
        return refuelingList;
    }

    @Override
    public void onPostExecute(List<Refueling> refuelingList) {
        if (refuelingList != null) {
            mRefuelingListHandler.initializeList(refuelingList);
            mRefuelingListAdapter.setRefuelingList(mRefuelingListHandler.getRefuelingList());
        }
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
            Context mainContext = mMainView.get().getActivityContext();

            mDataMediator = new DataMediator(mainContext);

            mUserListHandler = new UserListHandler();
            mRefuelingListHandler = new RefuelingListHandler();
            mVehicleListHandler = new VehicleListHandler();

            mUserListAdapter = new UserListAdapter(mainContext);
            mRefuelingListAdapter = new RefuelingListAdapter(mainContext);
            mVehicleListAdapter = new VehicleListAdapter(mainContext);

            User preferredUser = mDataMediator.getPreferredUser();
            Vehicle preferredVehicle = mDataMediator.getPreferredVehicle();

            List<User> users = mDataMediator.getUsers();
            List<Vehicle> vehicles = mDataMediator.getVehicles(preferredUser.getId());

            mUserListHandler.initializeList(users);
            mVehicleListHandler.initializeList(vehicles);

            mUserListHandler.setSelectedUser(preferredUser.getId());
            mVehicleListHandler.setSelectedVehicle(preferredVehicle.getId(), preferredUser.getId());

            mUserListAdapter.setUserList(mUserListHandler.getUserList());
            mVehicleListAdapter.setVehicleList(mVehicleListHandler.getVehicleList());

            getRefuelingList(preferredVehicle.getId());
        }

        mMainView.get().setAdapter(mUserListAdapter);
        mMainView.get().setAdapter(mVehicleListAdapter);
        mMainView.get().setAdapter(mRefuelingListAdapter);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
    }

    private void getRefuelingList(Long vehicleId) {
        mAsyncTask = new GenericAsyncTask<>(this);
        mAsyncTask.execute(vehicleId);
    }

    @Override
    public void newRefueling() {
        new AsyncTask<Void, Void, Long>() {

            @Override
            protected Long doInBackground(Void... params) {
                VehicleViewModel vehicleViewModel = mVehicleListHandler.getSelectedVehicle();
                return vehicleViewModel != null ? vehicleViewModel.getId() : null;
            }

            @Override
            protected void onPostExecute(Long params) {
                if (params != null) {
                    mMainView.get().openNewRefueling(params);
                }
            }
        }.execute();
    }

    @Override
    public void reloadArchive() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                VehicleViewModel vehicleViewModel = mVehicleListHandler.getSelectedVehicle();
                if (vehicleViewModel == null) { return false; }

                getRefuelingList(vehicleViewModel.getId());
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    mRefuelingListAdapter.notifyDataSetChanged();
                    mMainView.get().stopRefreshing();
                }
            }
        }.execute();
    }
}
