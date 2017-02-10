package com.srug.mobile.refuel.model.mediator.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

public class DataProvider extends ContentProvider {

    protected static final String TAG = DataProvider.class.getSimpleName();

    private DataProviderImpl mImpl;

    @Override
    public boolean onCreate() {
        mImpl = new DataProviderImplSQLite(getContext());
        return mImpl.onCreate();
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return mImpl.getType(uri);
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues cvs) {
        return mImpl.insert(uri, cvs);
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] cvsArray) {
        return mImpl.bulkInsert(uri, cvsArray);
    }

    @Override
    public Cursor query(@NonNull Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        return mImpl.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri,
                      ContentValues cvs,
                      String selection,
                      String[] selectionArgs) {
        return mImpl.update(uri, cvs, selection, selectionArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return mImpl.delete(uri, selection, selectionArgs);
    }


}
