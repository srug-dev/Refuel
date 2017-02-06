package com.srug.mobile.refuel.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public abstract class GenericActivity<V, P,
        T extends PresenterOps<V>>
        extends FragmentActivity implements ContextView {

    protected static final String TAG = GenericActivity.class.getSimpleName();

    private final RetainedFragmentManager mRetainedFragmentManager
            = new RetainedFragmentManager(this.getFragmentManager(),
            TAG);

    private PresenterOps mPresenterInstance;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onCreate(Class<T> opsType, V view, Bundle extras) {
        try {
            handleConfiguration(opsType, view, extras);
        } catch (InstantiationException
                | IllegalAccessException e) {
            Log.d(TAG, "onCreate() " + e);
            throw new RuntimeException(e);
        }
    }

    public void handleConfiguration(Class<? extends PresenterOps> opsType,
                                    V view, Bundle extras)
            throws InstantiationException, IllegalAccessException {

        if (mRetainedFragmentManager.firstTimeIn()) {
            Log.d(TAG, "First time onCreate() call");
            initialize(opsType, view, extras);
        } else {
            Log.d(TAG, "Second or subsequent onCreate() call");
            mPresenterInstance = mRetainedFragmentManager.get(opsType.getSimpleName());
            if (mPresenterInstance == null) {
                initialize(opsType, view, extras);
            } else {
                mPresenterInstance.onConfiguration(view, extras, false);
            }
        }
    }

    private void initialize(Class<? extends PresenterOps> opsType,
                            V view, Bundle extras)
            throws InstantiationException, IllegalAccessException {
        mPresenterInstance = opsType.newInstance();
        mRetainedFragmentManager.put(opsType.getSimpleName(), mPresenterInstance);
        mPresenterInstance.onConfiguration(view, extras, true);
    }

    public P getPresenter() {
        return (P) mPresenterInstance;
    }

    public RetainedFragmentManager getRetainedFragmentManager() {
        return mRetainedFragmentManager;
    }

    public Context getActivityContext() {
        return this;
    }

    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
