package com.srug.mobile.refuel.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srug.mobile.refuel.R;

/**
 * Created by sergio on 8/02/17.
 */

public class RecordFragment extends Fragment {

    public static final String RECORD_FRAGMENT_TAG
            = "com.srug.mobile.refuel.view.RECORD_FRAGMENT_TAG";

    public RecordFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record, container, false);
    }
}
