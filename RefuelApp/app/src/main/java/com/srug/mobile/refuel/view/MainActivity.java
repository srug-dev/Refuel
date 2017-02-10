package com.srug.mobile.refuel.view;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.srug.mobile.refuel.MVP;
import com.srug.mobile.refuel.R;
import com.srug.mobile.refuel.common.GenericActivity;
import com.srug.mobile.refuel.presenter.MainPresenter;

public class MainActivity extends GenericActivity<MVP.MainRequiredViewOps,
        MVP.MainProvidedPresenterOps,
        MainPresenter>
        implements MVP.MainRequiredViewOps {

    private DrawerLayout mDrawerLayout;
    private ArchiveFragment mArchiveFragment;
    private RecordFragment mRecordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        initializeDrawer();
        //initializeArchive();
        initializeRecord();

        super.onCreate(MainPresenter.class, this, getIntent().getExtras());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                switchDrawerLayout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.partial_customized_action_bar, null);
        if (null != actionBar) {
            actionBar.setCustomView(v);
        }
    }

    private void initializeDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.activity_main_drawer_layout_opening,
                R.string.activity_main_drawer_layout_closing) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initializeArchive() {
        mArchiveFragment = (ArchiveFragment) getSupportFragmentManager().
                findFragmentByTag(ArchiveFragment.ARCHIVE_FRAGMENT_TAG);
        if (mArchiveFragment == null) {
            mArchiveFragment = new ArchiveFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_archive,
                            mArchiveFragment,
                            ArchiveFragment.ARCHIVE_FRAGMENT_TAG).commit();
        }
    }

    private void initializeRecord() {
        mRecordFragment = (RecordFragment) getSupportFragmentManager().
                findFragmentByTag(RecordFragment.RECORD_FRAGMENT_TAG);
        if (mRecordFragment == null) {
            mRecordFragment = new RecordFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_archive,
                            mRecordFragment,
                            RecordFragment.RECORD_FRAGMENT_TAG).commit();
        }
    }

    private void switchDrawerLayout() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
