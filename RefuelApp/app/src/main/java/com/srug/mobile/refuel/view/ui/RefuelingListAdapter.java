package com.srug.mobile.refuel.view.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.presenter.RefuelingViewModel;
import com.srug.mobile.refuel.utilities.DataFormatOperations;

import java.util.ArrayList;
import java.util.List;

import static com.srug.mobile.refuel.utilities.DataFormatOperations.DATE_FORMAT;

public class RefuelingListAdapter extends RecyclerView.Adapter<RefuelingListAdapter.ViewHolder> {

    private Context mContext;
    private List<RefuelingViewModel> mRefuelingList = new ArrayList<>();

    public RefuelingListAdapter(Context context) {
        mContext = context;
    }

    public void setRefuelingList(List<RefuelingViewModel> refuelingList) {
        mRefuelingList = refuelingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_refueling, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RefuelingViewModel refueling = mRefuelingList.get(position);
        holder.mLlItemRefueling.setTag(R.id.ll_item_refueling, position);
        int rowColor = position%2 == 0 ? R.color.white : R.color.gray_200;
        holder.mLlItemRefueling.setBackgroundColor(mContext.getResources().getColor(rowColor));
        String date = DataFormatOperations.parseDateFormat(
                refueling.getDate(),
                DATE_FORMAT);
        String consumptionAvg = String.valueOf(refueling.getConsumptionAvg());
        holder.mDate.setText(date);
        holder.mConsumptionAvg.setText(consumptionAvg);
        if (mContext.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            String distance = String.valueOf(refueling.getDistance());
            String price = String.valueOf(refueling.getPrice());
            String consumption = String.valueOf(refueling.getConsumption());
            holder.mDistance.setText(distance);
            holder.mPrice.setText(price);
            holder.mConsumption.setText(consumption);
        }
    }

    @Override
    public int getItemCount() {
        return mRefuelingList.size();
    }

    public interface RefuelingListListener {
        void onSelectedItem(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mDate;
        private TextView mDistance;
        private TextView mPrice;
        private TextView mConsumption;
        private TextView mConsumptionAvg;
        private LinearLayout mLlItemRefueling;

        public ViewHolder(View itemView) {
            super(itemView);

            mDate = (TextView) itemView.findViewById(R.id.tv_refueling_date);
            mConsumptionAvg = (TextView) itemView.findViewById(R.id.tv_refueling_consumption_avg);
            mLlItemRefueling = (LinearLayout) itemView.findViewById(R.id.ll_item_refueling);
            if (itemView.getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_LANDSCAPE) {
                mDistance = (TextView) itemView.findViewById(R.id.tv_refueling_distance);
                mPrice = (TextView) itemView.findViewById(R.id.tv_refueling_price);
                mConsumption = (TextView) itemView.findViewById(R.id.tv_refueling_consumption);
            }
        }
    }
}
