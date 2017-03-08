package com.srug.mobile.refuel.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.view.ui.RefuelingListAdapter;

public class ArchiveFragment extends Fragment {

    public static final String ARCHIVE_FRAGMENT_TAG
            = "com.srug.mobile.refuel.view.ARCHIVE_FRAGMENT_TAG";

    private ArchiveFragmentListener mCallback;
    private Context mContext;
    private RefuelingListAdapter mAdapter;
    private RecyclerView mRvRefuelingList;
    private SwipeRefreshLayout mSwipeToRefreshRecords;

    public ArchiveFragment() {
    }

    public void setAdapter(RefuelingListAdapter adapter) {
        mAdapter = adapter;
    }

    public void stopRefreshing() {
        if (mSwipeToRefreshRecords != null && mSwipeToRefreshRecords.isRefreshing()) {
            mSwipeToRefreshRecords.setRefreshing(false);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
        if (activity instanceof ArchiveFragmentListener) {
            mCallback = (ArchiveFragmentListener) activity;
        }
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
        return inflater.inflate(R.layout.fragment_archive, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSwipeToRefreshRecords = (SwipeRefreshLayout) getActivity().findViewById(
                R.id.swipe_refresh_records);
        mRvRefuelingList = (RecyclerView) view.findViewById(R.id.rv_records_list);
        mRvRefuelingList.setHasFixedSize(true);
        mRvRefuelingList.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRvRefuelingList.setLayoutManager(layoutManager);
        mSwipeToRefreshRecords.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCallback != null) {
                    mCallback.onTryToRefreshRefuelingList();
                }
            }
        });
    }

    public interface ArchiveFragmentListener {
        void onTryToRefreshRefuelingList();
    }
}
