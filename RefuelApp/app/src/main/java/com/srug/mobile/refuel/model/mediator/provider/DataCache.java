package com.srug.mobile.refuel.model.mediator.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.srug.mobile.refuel.model.mediator.data.Refueling;
import com.srug.mobile.refuel.model.mediator.data.User;
import com.srug.mobile.refuel.model.mediator.data.Vehicle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataCache {

    private static final String EQUALS = " = ";

    private WeakReference<Context> mContext;

    public DataCache(Context context) {
        mContext = new WeakReference<>(context);
    }

    // Users
    public User getUser(Long userId) {
        final String rowId = "" + DataContract.UserEntry.COLUMN_USER_ID + EQUALS
                + "'" + userId + "'";
        try (Cursor cursor = mContext.get().getContentResolver().query(
                DataContract.UserEntry.CONTENT_URI, null, rowId, null, null)) {
            if (null != cursor && cursor.moveToFirst()) {
                return getUser(cursor);
            } else {
                return null;
            }
        }
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Cursor cursor = mContext.get().getContentResolver().query(
                DataContract.UserEntry.CONTENT_URI, null, null, null, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    User user = getUser(cursor);
                    users.add(user);
                }
            }
        } catch (Exception ex) {
            Log.d("DataCache.class", ex.getMessage());
        }
        return users;
    }

    public void insertUser(User user) {
        mContext.get()
                .getContentResolver()
                .insert(DataContract.UserEntry.CONTENT_URI,
                        getContentValues(user));
    }

    public void updateUser(User user) {
        ContentValues userContentValues = getContentValues(user);
        mContext.get().getContentResolver()
                .update(DataContract.UserEntry.CONTENT_URI,
                        userContentValues,
                        DataContract.UserEntry.COLUMN_USER_ID,
                        new String[]{user.getId().toString()});
    }

    public void deleteUser(Long userId) {
        try {
            // delete user's vehicles
            List<Vehicle> vehicleList = getVehicles(userId);
            for (Vehicle vehicle : vehicleList) {
                deleteVehicle(vehicle.getId());
            }

            // delete user
            mContext.get().getContentResolver()
                    .delete(DataContract.UserEntry.CONTENT_URI,
                            DataContract.UserEntry.COLUMN_USER_ID,
                            new String[]{userId.toString()});
        } catch (Exception ex) {
            Log.d(DataCache.class.getName(), ex.getMessage());
        }
    }

    // Vehicles
    public Vehicle getVehicle(Long vehicleId) {
        final String rowId = "" + DataContract.VehicleEntry.COLUMN_VEHICLE_ID + EQUALS
                + "'" + vehicleId + "'";
        try (Cursor cursor = mContext.get().getContentResolver().query(
                DataContract.VehicleEntry.CONTENT_URI, null, rowId, null, null)) {
            if (null != cursor && cursor.moveToFirst()) {
                return getVehicle(cursor);
            } else {
                return null;
            }
        }
    }

    public ArrayList<Vehicle> getVehicles(Long userId) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        try (Cursor cursor = mContext.get().getContentResolver().query(
                DataContract.VehicleEntry.CONTENT_URI, null, null, null, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Vehicle vehicle = getVehicle(cursor);
                    if (vehicle.getUserId().equals(userId)) {
                        vehicles.add(vehicle);
                    }
                }
            }
        } catch (Exception ex) {
            Log.d("DataCache.class", ex.getMessage());
        }
        return vehicles;
    }

    public void insertVehicle(Vehicle vehicle) {
        mContext.get()
                .getContentResolver()
                .insert(DataContract.VehicleEntry.CONTENT_URI,
                        getContentValues(vehicle));
    }

    public void updateVehicle(Vehicle vehicle) {
        ContentValues vehicleContentValues = getContentValues(vehicle);
        mContext.get().getContentResolver()
                .update(DataContract.VehicleEntry.CONTENT_URI,
                        vehicleContentValues,
                        DataContract.VehicleEntry.COLUMN_VEHICLE_ID,
                        new String[]{vehicle.getId().toString()});
    }

    public void deleteVehicle(Long vehicleId) {
        try {
            // delete vehicle's refuelings
            List<Refueling> refuelingList = getRefuelings(vehicleId);
            for(Refueling refueling : refuelingList) {
                deleteRefueling(refueling.getId());
            }

            // delete vehicle
            mContext.get().getContentResolver()
                    .delete(DataContract.VehicleEntry.CONTENT_URI,
                            DataContract.VehicleEntry.COLUMN_VEHICLE_ID,
                            new String[]{vehicleId.toString()});
        } catch (Exception ex) {
            Log.d(DataCache.class.getName(), ex.getMessage());
        }
    }

    // Refuelings
    public Refueling getRefueling(Long refuelingId) {
        final String rowId = "" + DataContract.RefuelingEntry.COLUMN_REFUELING_ID + EQUALS
                + "'" + refuelingId + "'";
        try (Cursor cursor = mContext.get().getContentResolver().query(
                DataContract.RefuelingEntry.CONTENT_URI, null, rowId, null, null)) {
            if (null != cursor && cursor.moveToFirst()) {
                return getRefueling(cursor);
            } else {
                return null;
            }
        }
    }

    public ArrayList<Refueling> getRefuelings(Long vehicleId) {
        ArrayList<Refueling> refuelings = new ArrayList<>();
        try (Cursor cursor = mContext.get().getContentResolver().query(
                DataContract.RefuelingEntry.CONTENT_URI, null, null, null, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Refueling refueling = getRefueling(cursor);
                    if (refueling.getVehicleId().equals(vehicleId)) {
                        refuelings.add(refueling);
                    }
                }
            }
        } catch (Exception ex) {
            Log.d("DataCache.class", ex.getMessage());
        }
        return refuelings;
    }

    public void insertRefueling(Refueling refueling) {
        mContext.get()
                .getContentResolver()
                .insert(DataContract.RefuelingEntry.CONTENT_URI,
                        getContentValues(refueling));
    }

    public void updateRefueling(Refueling refueling) {
        ContentValues refuelingContentValues = getContentValues(refueling);
        mContext.get().getContentResolver()
                .update(DataContract.RefuelingEntry.CONTENT_URI,
                        refuelingContentValues,
                        DataContract.RefuelingEntry.COLUMN_REFUELING_ID,
                        new String[]{refueling.getId().toString()});
    }

    public void deleteRefueling(Long refuelingId) {
        try {
            mContext.get().getContentResolver()
                    .delete(DataContract.RefuelingEntry.CONTENT_URI,
                            DataContract.RefuelingEntry.COLUMN_REFUELING_ID,
                            new String[]{refuelingId.toString()});
        } catch (Exception ex) {
            Log.d(DataCache.class.getName(), ex.getMessage());
        }
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(DataContract.UserEntry.COLUMN_USER_ID, user.getId());
        values.put(DataContract.UserEntry.COLUMN_EMAIL, user.getEmail());

        return values;
    }

    private ContentValues getContentValues(Vehicle vehicle) {
        ContentValues values = new ContentValues();
        values.put(DataContract.VehicleEntry.COLUMN_VEHICLE_ID, vehicle.getId());
        values.put(DataContract.VehicleEntry.COLUMN_USER_ID, vehicle.getUserId());
        values.put(DataContract.VehicleEntry.COLUMN_BRAND, vehicle.getBrand());
        values.put(DataContract.VehicleEntry.COLUMN_MODEL, vehicle.getModel());
        values.put(DataContract.VehicleEntry.COLUMN_PLATE, vehicle.getPlate());

        return values;
    }

    private ContentValues getContentValues(Refueling refueling) {
        ContentValues values = new ContentValues();
        values.put(DataContract.RefuelingEntry.COLUMN_REFUELING_ID, refueling.getId());
        values.put(DataContract.RefuelingEntry.COLUMN_VEHICLE_ID, refueling.getVehicleId());
        values.put(DataContract.RefuelingEntry.COLUMN_DATE, refueling.getDate().getTime());
        values.put(DataContract.RefuelingEntry.COLUMN_DISTANCE, refueling.getDistance());
        values.put(DataContract.RefuelingEntry.COLUMN_PRICE, refueling.getPrice());
        values.put(DataContract.RefuelingEntry.COLUMN_AMOUNT, refueling.getAmount());

        return values;
    }

    private User getUser(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                DataContract.UserEntry.COLUMN_USER_ID));
        String email = cursor.getString(cursor.getColumnIndexOrThrow(
                DataContract.UserEntry.COLUMN_EMAIL));

        User user = new User(id);
        user.setEmail(email);

        return user;
    }

    private Vehicle getVehicle(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                DataContract.VehicleEntry.COLUMN_VEHICLE_ID));
        long userId = cursor.getLong(cursor.getColumnIndexOrThrow(
                DataContract.VehicleEntry.COLUMN_USER_ID));
        String brand = cursor.getString(cursor.getColumnIndexOrThrow(
                DataContract.VehicleEntry.COLUMN_BRAND));
        String model = cursor.getString(cursor.getColumnIndexOrThrow(
                DataContract.VehicleEntry.COLUMN_MODEL));
        String plate = cursor.getString(cursor.getColumnIndexOrThrow(
                DataContract.VehicleEntry.COLUMN_PLATE));

        Vehicle vehicle = new Vehicle(id, userId);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setPlate(plate);

        return vehicle;
    }

    private Refueling getRefueling(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                DataContract.RefuelingEntry.COLUMN_REFUELING_ID));
        long vehicleId = cursor.getLong(cursor.getColumnIndexOrThrow(
                DataContract.RefuelingEntry.COLUMN_VEHICLE_ID));
        long date = cursor.getLong(cursor.getColumnIndexOrThrow(
                DataContract.RefuelingEntry.COLUMN_DATE));
        Double distance = cursor.getDouble(cursor.getColumnIndexOrThrow(
                DataContract.RefuelingEntry.COLUMN_DISTANCE));
        Double price = cursor.getDouble(cursor.getColumnIndexOrThrow(
                DataContract.RefuelingEntry.COLUMN_PRICE));
        Double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(
                DataContract.RefuelingEntry.COLUMN_AMOUNT));

        Refueling refueling = new Refueling(id, vehicleId);
        refueling.setDate(new Date(date));
        refueling.setDistance(distance);
        refueling.setPrice(price);
        refueling.setAmount(amount);

        return refueling;
    }
}
