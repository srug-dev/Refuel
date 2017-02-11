package com.srug.mobile.refuel.model.mediator.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class DataProviderImplSQLite extends DataProviderImpl {

    public static final String OR = " OR ";
    private static SQLiteDataEntryHelper mDataEntryHelper;

    public DataProviderImplSQLite(Context context) {
        super(context);
    }

    public boolean onCreate() {
        mDataEntryHelper = new SQLiteDataEntryHelper(mContext);
        return true;
    }

    public DataProviderImplSQLite open() throws SQLException {
        if (mDataEntryHelper == null) {
            mDataEntryHelper = new SQLiteDataEntryHelper(mContext);
        }
        mDataEntryHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDataEntryHelper.close();
    }

    @Override
    protected Uri insertUserEntry(Uri uri, ContentValues cvs) {
        final SQLiteDatabase db = mDataEntryHelper.getWritableDatabase();

        long id = db.insert(DataContract.UserEntry.USER_TABLE_NAME, null, cvs);

        //Check if the key are the least significant UUID bits (that's it starts with -)
        if (id != -1) {
            return DataContract.UserEntry.buildDataUri(id);
        } else {
            throw new RuntimeException("Failed to insert row into " + uri);
        }
    }

    @Override
    protected Uri insertVehicleEntry(Uri uri, ContentValues cvs) {
        final SQLiteDatabase db = mDataEntryHelper.getWritableDatabase();

        long id = db.insert(DataContract.VehicleEntry.VEHICLE_TABLE_NAME, null, cvs);

        //Check if the key are the least significant UUID bits (that's it starts with -)
        if (id != -1) {
            return DataContract.VehicleEntry.buildDataUri(id);
        } else {
            throw new RuntimeException("Failed to insert row into " + uri);
        }
    }

    @Override
    protected Uri insertRefuelEntry(Uri uri, ContentValues cvs) {
        final SQLiteDatabase db = mDataEntryHelper.getWritableDatabase();

        long id = db.insert(DataContract.RefuelingEntry.REFUELING_TABLE_NAME, null, cvs);

        //Check if the key are the least significant UUID bits (that's it starts with -)
        if (id != -1) {
            return DataContract.RefuelingEntry.buildDataUri(id);
        } else {
            throw new RuntimeException("Failed to insert row into " + uri);
        }
    }

    @Override
    protected int bulkInsertUserEntries(Uri uri, ContentValues[] cvsArray) {
        final SQLiteDatabase db = mDataEntryHelper.getWritableDatabase();

        int returnCount = 0;
        db.beginTransaction();
        try {
            for (ContentValues cvs : cvsArray) {
                final long id = db.insert(DataContract.UserEntry.USER_TABLE_NAME, null, cvs);
                if (id < 0) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return returnCount;
    }

    @Override
    protected int bulkInsertVehicleEntries(Uri uri, ContentValues[] cvsArray) {
        final SQLiteDatabase db = mDataEntryHelper.getWritableDatabase();

        int returnCount = 0;
        db.beginTransaction();
        try {
            for (ContentValues cvs : cvsArray) {
                final long id = db.insert(DataContract.VehicleEntry.VEHICLE_TABLE_NAME, null, cvs);
                if (id < 0) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return returnCount;
    }

    @Override
    protected int bulkInsertRefuelingEntries(Uri uri, ContentValues[] cvsArray) {
        final SQLiteDatabase db = mDataEntryHelper.getWritableDatabase();

        int returnCount = 0;
        db.beginTransaction();
        try {
            for (ContentValues cvs : cvsArray) {
                final long id = db.insert(DataContract.RefuelingEntry.REFUELING_TABLE_NAME, null, cvs);
                if (id < 0) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return returnCount;
    }

    @Override
    protected Cursor queryUser(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mDataEntryHelper.getReadableDatabase().query(
                DataContract.UserEntry.USER_TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    protected Cursor queryUserEntries(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mDataEntryHelper.getReadableDatabase().query(
                DataContract.UserEntry.USER_TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    protected Cursor queryVehicle(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mDataEntryHelper.getReadableDatabase().query(
                DataContract.VehicleEntry.VEHICLE_TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    protected Cursor queryVehicleEntries(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mDataEntryHelper.getReadableDatabase().query(
                DataContract.VehicleEntry.VEHICLE_TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    protected Cursor queryRefuel(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mDataEntryHelper.getReadableDatabase().query(
                DataContract.RefuelingEntry.REFUELING_TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    protected Cursor queryRefuelEntries(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mDataEntryHelper.getReadableDatabase().query(
                DataContract.RefuelingEntry.REFUELING_TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    protected int updateUser(Uri uri, ContentValues cvs, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().update(
                DataContract.UserEntry.USER_TABLE_NAME,
                cvs,
                currentSelection,
                selectionArgs);
    }

    @Override
    protected int updateUserEntries(Uri uri, ContentValues cvs, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().update(
                DataContract.UserEntry.USER_TABLE_NAME,
                cvs,
                currentSelection,
                selectionArgs
        );
    }

    @Override
    protected int updateVehicle(Uri uri, ContentValues cvs, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().update(
                DataContract.VehicleEntry.VEHICLE_TABLE_NAME,
                cvs,
                currentSelection,
                selectionArgs);
    }

    @Override
    protected int updateVehicleEntries(Uri uri, ContentValues cvs, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().update(
                DataContract.VehicleEntry.VEHICLE_TABLE_NAME,
                cvs,
                currentSelection,
                selectionArgs
        );
    }

    @Override
    protected int updateRefuel(Uri uri, ContentValues cvs, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().update(
                DataContract.RefuelingEntry.REFUELING_TABLE_NAME,
                cvs,
                currentSelection,
                selectionArgs);
    }

    @Override
    protected int updateRefuelEntries(Uri uri, ContentValues cvs, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().update(
                DataContract.RefuelingEntry.REFUELING_TABLE_NAME,
                cvs,
                currentSelection,
                selectionArgs
        );
    }

    @Override
    protected int deleteUser(Uri uri, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().delete(
                DataContract.UserEntry.USER_TABLE_NAME,
                currentSelection,
                selectionArgs);
    }

    @Override
    protected int deleteUserEntries(Uri uri, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().delete(
                DataContract.UserEntry.USER_TABLE_NAME,
                currentSelection,
                selectionArgs
        );
    }

    @Override
    protected int deleteVehicle(Uri uri, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().delete(
                DataContract.VehicleEntry.VEHICLE_TABLE_NAME,
                currentSelection,
                selectionArgs);
    }

    @Override
    protected int deleteVehicleEntries(Uri uri, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().delete(
                DataContract.VehicleEntry.VEHICLE_TABLE_NAME,
                currentSelection,
                selectionArgs
        );
    }

    @Override
    protected int deleteRefuel(Uri uri, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().delete(
                DataContract.RefuelingEntry.REFUELING_TABLE_NAME,
                currentSelection,
                selectionArgs);
    }

    @Override
    protected int deleteRefuelEntries(Uri uri, String selection, String[] selectionArgs) {
        String currentSelection = addSelectionArgs(selection, selectionArgs, OR);
        return mDataEntryHelper.getWritableDatabase().delete(
                DataContract.RefuelingEntry.REFUELING_TABLE_NAME,
                currentSelection,
                selectionArgs
        );
    }

    private String addSelectionArgs(String selection,
                                    String[] selectionArgs,
                                    String operation) {
        if (selection == null || selectionArgs == null) {
            return null;
        } else {
            String selectionResult = "";

            for (int i = 0; i < selectionArgs.length - 1; i++) {
                selectionResult += (selection + " = ? " + operation + " ");
            }
            selectionResult += (selection + " = ?");

            Log.d(TAG, "selection = " + selectionResult + " selectionArgs = ");
            for (String args : selectionArgs) {
                Log.d(TAG, args + " ");
            }
            return selectionResult;
        }
    }

    public class SQLiteDataEntryHelper extends SQLiteOpenHelper {

        private static final String SQLITE_DB_NAME = "srug_refuel_db";

        private static final int DATABASE_VERSION = 1;
        private static final String AUTOINCREMENT = "AUTOINCREMENT";
        private static final String INTEGER = "INTEGER";
        private static final String NOT_NULL = "NOT NULL";
        private static final String NUMERIC = "NUMERIC";
        private static final String REAL = "REAL";
        private static final String TEXT = "TEXT";

        private final String mCreateTableUsers
                = "CREATE TABLE " + DataContract.UserEntry.USER_TABLE_NAME + " ("
                + DataContract.UserEntry.COLUMN_USER_ID + " " + INTEGER
                + " PRIMARY KEY " + AUTOINCREMENT + ", "
                + DataContract.UserEntry.COLUMN_EMAIL + " " + TEXT + ")";

        private final String mCreateTableVehicles
                = "CREATE TABLE " + DataContract.VehicleEntry.VEHICLE_TABLE_NAME + "("
                + DataContract.VehicleEntry.COLUMN_VEHICLE_ID + " " + INTEGER
                + " PRIMARY KEY " + AUTOINCREMENT + ", "
                + DataContract.VehicleEntry.COLUMN_USER_ID + " " + INTEGER + " " + NOT_NULL +  ", "
                + DataContract.VehicleEntry.COLUMN_BRAND + " " + TEXT + ", "
                + DataContract.VehicleEntry.COLUMN_MODEL + " " + TEXT + ", "
                + DataContract.VehicleEntry.COLUMN_PLATE + " " + TEXT + ", "
                + "FOREIGN KEY("
                + DataContract.VehicleEntry.COLUMN_USER_ID + ")"
                + " REFERENCES "
                + DataContract.UserEntry.USER_TABLE_NAME
                + "(" + DataContract.UserEntry.COLUMN_USER_ID + ") )";

        private final String mCreateTableRefuel
                = "CREATE TABLE " + DataContract.RefuelingEntry.REFUELING_TABLE_NAME + "("
                + DataContract.RefuelingEntry.COLUMN_REFUELING_ID + " " + INTEGER
                + " PRIMARY KEY " + AUTOINCREMENT + ", "
                + DataContract.RefuelingEntry.COLUMN_VEHICLE_ID + " " + INTEGER + " " + NOT_NULL +  ", "
                + DataContract.RefuelingEntry.COLUMN_DATE + " " + NUMERIC + " " + NOT_NULL +  ", "
                + DataContract.RefuelingEntry.COLUMN_DISTANCE + " " + REAL + " " + NOT_NULL +  ", "
                + DataContract.RefuelingEntry.COLUMN_PRICE + " " + REAL + " " + NOT_NULL +  ", "
                + DataContract.RefuelingEntry.COLUMN_AMOUNT + " " + REAL + " " + NOT_NULL +  ", "
                + "FOREIGN KEY("
                + DataContract.RefuelingEntry.COLUMN_VEHICLE_ID + ")"
                + " REFERENCES "
                + DataContract.VehicleEntry.VEHICLE_TABLE_NAME
                + "(" + DataContract.VehicleEntry.COLUMN_VEHICLE_ID + ") )";

        public SQLiteDataEntryHelper(Context context) {
            super(context, SQLITE_DB_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(mCreateTableUsers);
            db.execSQL(mCreateTableVehicles);
            db.execSQL(mCreateTableRefuel);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // N/A
        }
    }
}
