package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.savor.operation.R;
import com.savor.operation.adapter.CategoryPagerAdapter;
import com.savor.operation.core.Session;
import com.savor.operation.fragment.AppointMissionFragment;
import com.savor.operation.fragment.MissionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 指派者任务列表
 * Created by bushlee on 2017/11/5.
 */

public class AppointMissionListActivity extends AppCompatActivity
        implements View.OnClickListener{

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private PagerSlidingTabStrip mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    private Session mSession;
    private Context mContext;
    private View mShadeLayer;
    private RelativeLayout mContentLayout;
    private ImageView mShareBtn;
    private CoordinatorLayout mParentLayout;
    private TextView mCategoryNameLabel;
    private CategoryPagerAdapter mPagerAdapter;
    private TextView tv_center;
    private ImageView iv_left;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_main);
        mSession = Session.get(this);
        mContext = this;
        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }


    public void getViews() {
        mShadeLayer = findViewById(R.id.shade_layer);
        mContentLayout = (RelativeLayout) findViewById(R.id.include);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTabLayout = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        mParentLayout = (CoordinatorLayout) findViewById(R.id.parent_layout);
        mCategoryNameLabel = (TextView) findViewById(R.id.tv_category_name);

    }

    public void setViews() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        mActionBarDrawerToggle.syncState();
        //getVersionCode()
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        tv_center.setText("任务列表");
        initIndicator();
    }
    private void initIndicator() {

        mTitleList.add("全部");
        mTitleList.add("待指派");
        mTitleList.add("待处理");
        mTitleList.add("已完成");

        mFragmentList.add(AppointMissionFragment.getInstance(0));
        mFragmentList.add(AppointMissionFragment.getInstance(1));
        mFragmentList.add(AppointMissionFragment.getInstance(2));
        mFragmentList.add(AppointMissionFragment.getInstance(4));


//        Bundle bundle = new Bundle();
//        bundle.putString("title", "专题");
        mPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setData(mFragmentList,mTitleList);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(4);
    }

    private void setListeners(){
        iv_left.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(mTitleList.contains("投屏")) {
                   // prePosition = position;
                }
                if(mTitleList !=null&& mTitleList.size()>position) {
//                    String cname = mTitleList.get(position);
//                    HashMap<String,String> hashMap = new HashMap<>();
//                    hashMap.put(getString(R.string.home_sliding_category),cname);
//                    RecordUtils.onEvent(HotspotMainActivity.this,getString(R.string.home_sliding_category),hashMap);
                }

                Fragment fragment = mFragmentList.get(position);
               // mShareBtn.setVisibility(View.GONE);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
