package com.srug.mobile.refuel.common;

import android.os.AsyncTask;

public class GenericAsyncTask<P,
        G,
        R,
        O extends GenericAsyncTaskOps<P, G, R>>
        extends AsyncTask<P, G, R> {

    protected O mOps;

    public GenericAsyncTask(O ops) {
        mOps = ops;
    }

    protected void onPreExecute() {
        mOps.onPreExecute();
    }

    @SuppressWarnings("unchecked")
    protected R doInBackground(P... params) {
        return mOps.doInBackground(params);
    }

    protected void onPostExecute(R result) {
        mOps.onPostExecute(result);
    }
}

