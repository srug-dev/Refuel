package com.srug.mobile.refuel.view.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.presenter.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private Context mContext;
    List<UserViewModel> mUserList = new ArrayList<>();

    public UserListAdapter(Context context) {
        mContext = context;
    }

    public void setUserList(List<UserViewModel> userList) {
        mUserList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserViewModel viewModel = mUserList.get(position);
        holder.mUserName.setText(viewModel.toString());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public interface UserListListener {
        void onSelectedItem(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLinearLayout;
        private TextView mUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.ll_item_user);
            mUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
        }
    }
}
