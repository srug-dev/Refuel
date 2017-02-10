package com.srug.mobile.refuel.model.mediator.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public abstract class DataProviderImpl {

    public static final int USER_ITEM = 100;

    public static final int USER_ITEMS = 101;

    public static final int VEHICLE_ITEM = 200;

    public static final int VEHICLE_ITEMS = 201;

    public static final int REFUEL_ITEM = 300;

    public static final int REFUEL_ITEMS = 301;

    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    protected static final String TAG = DataProvider.class.getSimpleName();

    private static final String UNKNOWN_URI = "Unknown uri: ";

    protected Context mContext;

    public DataProviderImpl(Context context) {
        mContext = context;
    }

    protected static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(DataContract.CONTENT_AUTHORITY,
                DataContract.UserEntry.USER_TABLE_NAME,
                USER_ITEMS);
        matcher.addURI(DataContract.CONTENT_AUTHORITY,
                DataContract.UserEntry.USER_TABLE_NAME + "/#",
                USER_ITEM);

        matcher.addURI(DataContract.CONTENT_AUTHORITY,
                DataContract.VehicleEntry.VEHICLE_TABLE_NAME,
                VEHICLE_ITEMS);
        matcher.addURI(DataContract.CONTENT_AUTHORITY,
                DataContract.VehicleEntry.VEHICLE_TABLE_NAME + "/#",
                VEHICLE_ITEM);

        matcher.addURI(DataContract.CONTENT_AUTHORITY,
                DataContract.RefuelEntry.REFUEL_TABLE_NAME,
                REFUEL_ITEMS);
        matcher.addURI(DataContract.CONTENT_AUTHORITY,
                DataContract.RefuelEntry.REFUEL_TABLE_NAME + "/#",
                REFUEL_ITEM);

        return matcher;
    }

    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case USER_ITEM:
                return DataContract.UserEntry.CONTENT_ITEM_TYPE;
            case USER_ITEMS:
                return DataContract.UserEntry.CONTENT_ITEMS_TYPE;
            case VEHICLE_ITEM:
                return DataContract.VehicleEntry.CONTENT_ITEM_TYPE;
            case VEHICLE_ITEMS:
                return DataContract.VehicleEntry.CONTENT_ITEMS_TYPE;
            case REFUEL_ITEM:
                return DataContract.RefuelEntry.CONTENT_ITEMS_TYPE;
            case REFUEL_ITEMS:
                return DataContract.RefuelEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }
    }

    public Uri insert(Uri uri, ContentValues cvs) {
        Uri returnUri;

        switch (URI_MATCHER.match(uri)) {
            case USER_ITEM:
                returnUri = insertUserEntry(uri, cvs);
                break;
            case VEHICLE_ITEM:
                returnUri = insertVehicleEntry(uri, cvs);
                break;
            case REFUEL_ITEM:
                returnUri = insertRefuelEntry(uri, cvs);
                break;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }

        mContext.getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    protected abstract Uri insertUserEntry(Uri uri, ContentValues cvs);

    protected abstract Uri insertVehicleEntry(Uri uri, ContentValues cvs);

    protected abstract Uri insertRefuelEntry(Uri uri, ContentValues cvs);

    public int bulkInsert(Uri uri, ContentValues[] cvsArray) {
        int returnCount;
        switch (URI_MATCHER.match(uri)) {
            case USER_ITEMS:
                returnCount = bulkInsertUsers(uri, cvsArray);
                break;
            case VEHICLE_ITEMS:
                returnCount = bulkInsertVehicles(uri, cvsArray);
                break;
            case REFUEL_ITEMS:
                returnCount = bulkInsertRefuels(uri, cvsArray);
                break;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }

        if (returnCount > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }

        return returnCount;
    }

    protected abstract int bulkInsertUsers(Uri uri, ContentValues[] cvsArray);

    protected abstract int bulkInsertVehicles(Uri uri, ContentValues[] cvsArray);

    protected abstract int bulkInsertRefuels(Uri uri, ContentValues[] cvsArray);

    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)) {
            case USER_ITEM:
                cursor = queryUser(uri, projection, selection, selectionArgs, sortOrder);
                break;
            case USER_ITEMS:
                cursor = queryUserEntries(uri, projection, selection, selectionArgs, sortOrder);
                break;
            case VEHICLE_ITEM:
                cursor = queryVehicle(uri,projection,selection,selectionArgs, sortOrder);
                break;
            case VEHICLE_ITEMS:
                cursor = queryVehicleEntries(uri,projection,selection, selectionArgs,sortOrder);
                break;
            case REFUEL_ITEM:
                cursor = queryRefuel(uri,projection,selection,selectionArgs,sortOrder);
                break;
            case REFUEL_ITEMS:
                cursor = queryRefuelEntries(uri,projection,selection,selectionArgs,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }

        cursor.setNotificationUri(mContext.getContentResolver(), uri);
        return cursor;
    }

    protected abstract Cursor queryUser(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    protected abstract Cursor queryUserEntries(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    protected abstract Cursor queryVehicle(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    protected abstract Cursor queryVehicleEntries(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    protected abstract Cursor queryRefuel(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    protected abstract Cursor queryRefuelEntries(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    public int update(Uri uri, ContentValues cvs, String selection, String[] selectionArgs) {
        int recsUpdated;
        switch (URI_MATCHER.match(uri)) {
            case USER_ITEM:
                recsUpdated = updateUser(uri, cvs, selection, selectionArgs);
                break;
            case USER_ITEMS:
                recsUpdated = updateUserEntries(uri, cvs, selection, selectionArgs);
                break;
            case VEHICLE_ITEM:
                recsUpdated = updateVehicle(uri, cvs, selection, selectionArgs);
                break;
            case VEHICLE_ITEMS:
                recsUpdated = updateVehicleEntries(uri, cvs, selection, selectionArgs);
                break;
            case REFUEL_ITEM:
                recsUpdated = updateRefuel(uri, cvs, selection, selectionArgs);
                break;
            case REFUEL_ITEMS:
                recsUpdated = updateRefuelEntries(uri, cvs, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }

        if (recsUpdated > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }

        return recsUpdated;
    }

    protected abstract int updateUser(Uri uri, ContentValues cvs, String selection, String[] selectionArgs);

    protected abstract int updateUserEntries(Uri uri, ContentValues cvs, String selection, String[] selectionArgs);

    protected abstract int updateVehicle(Uri uri, ContentValues cvs, String selection, String[] selectionArgs);

    protected abstract int updateVehicleEntries(Uri uri, ContentValues cvs, String selection, String[] selectionArgs);

    protected abstract int updateRefuel(Uri uri, ContentValues cvs, String selection, String[] selectionArgs);

    protected abstract int updateRefuelEntries(Uri uri, ContentValues cvs, String selection, String[] selectionArgs);

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int recsDeleted;
        switch (URI_MATCHER.match(uri)) {
            case USER_ITEM:
                recsDeleted = deleteUser(uri, selection, selectionArgs);
                break;
            case USER_ITEMS:
                recsDeleted = deleteUserEntries(uri, selection, selectionArgs);
                break;
            case VEHICLE_ITEM:
                recsDeleted = deleteVehicle(uri, selection, selectionArgs);
                break;
            case VEHICLE_ITEMS:
                recsDeleted = deleteVehicleEntries(uri, selection, selectionArgs);
                break;
            case REFUEL_ITEM:
                recsDeleted = deleteRefuel(uri, selection, selectionArgs);
                break;
            case REFUEL_ITEMS:
                recsDeleted = deleteRefuelEntries(uri, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }

        if (recsDeleted > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }

        return recsDeleted;
    }

    protected abstract int deleteUser(Uri uri, String selection, String[] selectionArgs);

    protected abstract int deleteUserEntries(Uri uri, String selection, String[] selectionArgs);

    protected abstract int deleteVehicle(Uri uri, String selection, String[] selectionArgs);

    protected abstract int deleteVehicleEntries(Uri uri, String selection, String[] selectionArgs);

    protected abstract int deleteRefuel(Uri uri, String selection, String[] selectionArgs);

    protected abstract int deleteRefuelEntries(Uri uri, String selection, String[] selectionArgs);

    public boolean onCreate() { return true; }
}
