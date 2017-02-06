package com.srug.mobile.refuel.common;

import android.os.Bundle;

public interface PresenterOps<V> {

    void onCreate(V view);

    void onConfiguration(V view,
                         Bundle extras,
                         boolean firstTimeIn);

    void onDestroy(boolean isChangingConfiguration);
}
