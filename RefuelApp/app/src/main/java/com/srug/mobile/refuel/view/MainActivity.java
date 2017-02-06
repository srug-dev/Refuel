package com.srug.mobile.refuel.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.srug.mobile.refuel.MVP;
import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.common.GenericActivity;
import com.srug.mobile.refuel.presenter.MainPresenter;

public class MainActivity extends GenericActivity<MVP.MainRequiredViewOps,
        MVP.MainProvidedPresenterOps,
        MainPresenter>
        implements MVP.MainRequiredViewOps {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(MainPresenter.class, this, getIntent().getExtras());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
