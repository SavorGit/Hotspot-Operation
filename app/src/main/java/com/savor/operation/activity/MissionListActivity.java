package com.savor.operation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gxz.PagerSlidingTabStrip;
import com.savor.operation.R;

/**
 * Created by bushlee on 2017/11/5.
 */

public class MissionListActivity extends AppCompatActivity
        implements View.OnClickListener{

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private PagerSlidingTabStrip mTabLayout;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_main);
    }

    @Override
    public void onClick(View v) {

    }
}
