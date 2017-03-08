package com.srug.mobile.refuel.view.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.presenter.VehicleViewModel;

import java.util.ArrayList;
import java.util.List;

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder> {

    private Context mContext;
    private List<VehicleViewModel> mVehicleList = new ArrayList<>();

    public VehicleListAdapter(Context context) {
        mContext = context;
    }

    public void setVehicleList(List<VehicleViewModel> vehicleList) {
        mVehicleList = vehicleList;
    }

    @Override
    public VehicleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle, parent, false);
        return new VehicleListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VehicleListAdapter.ViewHolder holder, int position) {
        VehicleViewModel vehicle = mVehicleList.get(position);
        holder.mLlItemVehicle.setTag(R.id.ll_item_vehicle, position);
        holder.mTvVehicleName.setText(vehicle.toString());
    }

    @Override
    public int getItemCount() {
        return mVehicleList.size();
    }

    public interface VehicleListListener {
        void onSelectedItem(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLlItemVehicle;
        private TextView mTvVehicleName;

        public ViewHolder(View itemView) {
            super(itemView);
            mLlItemVehicle = (LinearLayout) itemView.findViewById(R.id.ll_item_vehicle);
            mTvVehicleName = (TextView) itemView.findViewById(R.id.tv_vehicle_name);
        }
    }
}
