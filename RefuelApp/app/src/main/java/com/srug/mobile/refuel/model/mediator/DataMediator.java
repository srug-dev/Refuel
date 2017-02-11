package com.srug.mobile.refuel.model.mediator;

import android.content.Context;

import com.srug.mobile.refuel.model.mediator.data.Refueling;
import com.srug.mobile.refuel.model.mediator.data.User;
import com.srug.mobile.refuel.model.mediator.data.Vehicle;
import com.srug.mobile.refuel.model.mediator.provider.DataCache;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DataMediator {

    private WeakReference<Context> mContext;
    private DataCache mDataCache;

    public DataMediator(Context context) {
        mContext = new WeakReference<Context>(context);
        mDataCache = new DataCache(mContext.get());
    }

    // Users
    public ArrayList<User> getUsers() {
        return mDataCache.getUsers();
    }

    public User getUser(Long userId) {
        return mDataCache.getUser(userId);
    }

    public void insertUser(User user) {
        mDataCache.insertUser(user);
    }

    public void updateUser(User user) {
        mDataCache.updateUser(user);
    }

    public void deleteUser(Long userId) {
        mDataCache.deleteUser(userId);
    }

    // Vehicles
    public ArrayList<Vehicle> getVehicles(Long userId) {
        return mDataCache.getVehicles(userId);
    }

    public Vehicle getVehicle(Long vehicleId) {
        return mDataCache.getVehicle(vehicleId);
    }

    public void insertVehicle(Vehicle vehicle) {
        mDataCache.insertVehicle(vehicle);
    }

    public void updateVehicle(Vehicle vehicle) {
        mDataCache.updateVehicle(vehicle);
    }

    public void deleteVehicle(Long vehicleId) {
        mDataCache.deleteVehicle(vehicleId);
    }

    // Refuelings
    public ArrayList<Refueling> getRefuelings(Long vehicleId) {
        return mDataCache.getRefuelings(vehicleId);
    }

    public Refueling getRefueling(Long refuelingId) {
        return mDataCache.getRefueling(refuelingId);
    }

    public void insertRefueling(Refueling refueling) {
        mDataCache.insertRefueling(refueling);
    }

    public void updateRefueling(Refueling refueling) {
        mDataCache.updateRefueling(refueling);
    }

    public void deleteRefueling(Long refuelingId) {
        mDataCache.deleteRefueling(refuelingId);
    }
}
