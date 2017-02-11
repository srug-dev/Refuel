package com.srug.mobile.refuel.model.mediator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.srug.mobile.refuel.constants.DefaultValues;
import com.srug.mobile.refuel.constants.Preferences;
import com.srug.mobile.refuel.model.mediator.data.Refueling;
import com.srug.mobile.refuel.model.mediator.data.User;
import com.srug.mobile.refuel.model.mediator.data.Vehicle;
import com.srug.mobile.refuel.model.mediator.provider.DataCache;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.UUID;

public class DataMediator {

    private WeakReference<Context> mContext;
    private DataCache mDataCache;

    public DataMediator(Context context) {
        mContext = new WeakReference<Context>(context);
        mDataCache = new DataCache(mContext.get());
    }

    // Preferred Values
    public User getPreferredUser() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext.get());
        Long preferredUserId = preferences.getLong(
                Preferences.PREF_USER_ID,
                DefaultValues.UNDEFINED_USER_ID);
        if (preferredUserId.equals(DefaultValues.UNDEFINED_USER_ID)) {
            User defaultUser = new User(UUID.randomUUID().getLeastSignificantBits());
            preferredUserId = defaultUser.getId();
            setPreferredUser(preferredUserId);
        }
        return getUser(preferredUserId);
    }

    public void setPreferredUser(Long userId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.get());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(Preferences.PREF_USER_ID, userId);
        editor.commit();
    }

    public Vehicle getPreferredVehicle() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext.get());
        Long preferredVehicleId = preferences.getLong(
                Preferences.PREF_VEHICLE_ID,
                DefaultValues.UNDEFINED_VEHICLE_ID);
        if (preferredVehicleId.equals(DefaultValues.UNDEFINED_USER_ID)) {
            User preferredUser = getPreferredUser();
            Vehicle defaultVehicle = new Vehicle(
                    UUID.randomUUID().getLeastSignificantBits(),
                    preferredUser.getId());
            preferredVehicleId = defaultVehicle.getId();
            setPreferredVehicle(preferredVehicleId);
        }
        return getVehicle(preferredVehicleId);
    }

    public void setPreferredVehicle(Long vehicleId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.get());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(Preferences.PREF_VEHICLE_ID, vehicleId);
        editor.commit();
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
