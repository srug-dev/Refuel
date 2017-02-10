package com.srug.mobile.refuel.model.mediator.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DataContract {

    public static final String CONTENT_AUTHORITY = "com.srug.mobile.refuel.dataprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String MIME_TYPE_DIR = "vnd.android.cursor.dir/";

    private static final String MIME_TYPE_ITEM = "vnd.android.cursor.item/";

    private DataContract() {
    }

    public static final class RefuelEntry implements BaseColumns {

        public static final String REFUEL_TABLE_NAME = "refuel_table";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(REFUEL_TABLE_NAME).build();

        public static final String CONTENT_ITEMS_TYPE
                = MIME_TYPE_DIR + CONTENT_AUTHORITY + "/" + REFUEL_TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE
                = MIME_TYPE_ITEM + CONTENT_AUTHORITY + "/" + REFUEL_TABLE_NAME;

        public static final String COLUMN_REFUEL_ID = "_id";
        public static final String COLUMN_VEHICLE_ID = "vehicle_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DISTANCE = "distance";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMNS_TO_DISPLAY[]
                = new String[] {
                COLUMN_REFUEL_ID,
                COLUMN_VEHICLE_ID,
                COLUMN_DATE,
                COLUMN_DISTANCE,
                COLUMN_PRICE,
                COLUMN_AMOUNT
        };

        public static Uri buildDataUri(long dataId) {
            return ContentUris.withAppendedId(CONTENT_URI, dataId);
        }
    }

    public static final class VehicleEntry implements BaseColumns {

        public static final String VEHICLE_TABLE_NAME = "vehicle_table";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(VEHICLE_TABLE_NAME).build();

        public static final String CONTENT_ITEMS_TYPE
                = MIME_TYPE_DIR + CONTENT_AUTHORITY + "/" + VEHICLE_TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE
                = MIME_TYPE_ITEM + CONTENT_AUTHORITY + "/" + VEHICLE_TABLE_NAME;

        public static final String COLUMN_VEHICLE_ID = "_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_MODEL = "model";
        public static final String COLUMN_PLATE = "plate";
        public static final String COLUMNS_TO_DISPLAY[]
                = new String[] {
                COLUMN_VEHICLE_ID,
                COLUMN_USER_ID,
                COLUMN_BRAND,
                COLUMN_MODEL,
                COLUMN_PLATE
        };

        public static Uri buildDataUri(long dataId) {
            return ContentUris.withAppendedId(CONTENT_URI, dataId);
        }
    }

    public static final class UserEntry implements BaseColumns {

        public static final String USER_TABLE_NAME = "user_table";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(USER_TABLE_NAME).build();

        public static final String CONTENT_ITEMS_TYPE
                = MIME_TYPE_DIR + CONTENT_AUTHORITY + "/" + USER_TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE
                = MIME_TYPE_ITEM + CONTENT_AUTHORITY + "/" + USER_TABLE_NAME;

        public static final String COLUMN_USER_ID = "_id";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMNS_TO_DISPLAY[]
                = new String[] {
                COLUMN_USER_ID,
                COLUMN_EMAIL
        };

        public static Uri buildDataUri(long dataId) {
            return ContentUris.withAppendedId(CONTENT_URI, dataId);
        }
    }
}
