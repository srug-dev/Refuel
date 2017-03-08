package com.srug.mobile.refuel.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.view.ui.UserListAdapter;
import com.srug.mobile.refuel.view.ui.VehicleListAdapter;

public class UserFragment extends Fragment {

    public static final String USER_FRAGMENT_TAG
            = "com.srug.mobile.refuel.view.USER_FRAGMENT_TAG";

    private Context mContext;
    private UserListAdapter mUserListAdapter;
    private VehicleListAdapter mVehicleListAdapter;
    private RecyclerView mRvUserList;
    private RecyclerView mRvVehicleList;

    public UserFragment() {
    }

    public void setAdapter(VehicleListAdapter adapter) {
        mVehicleListAdapter = adapter;
    }

    public void setAdapter(UserListAdapter adapter) {
        mUserListAdapter = adapter;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRvUserList = (RecyclerView) view.findViewById(R.id.rv_user_list);
        mRvUserList.setHasFixedSize(true);
        mRvUserList.setAdapter(mUserListAdapter);
        LinearLayoutManager userListLayoutManager = new LinearLayoutManager(mContext);
        mRvUserList.setLayoutManager(userListLayoutManager);

        mRvVehicleList = (RecyclerView) view.findViewById(R.id.rv_vehicle_list);
        mRvVehicleList.setHasFixedSize(true);
        mRvVehicleList.setAdapter(mVehicleListAdapter);
        LinearLayoutManager vehicleListLayoutManager = new LinearLayoutManager(mContext);
        mRvVehicleList.setLayoutManager(vehicleListLayoutManager);
    }
}
