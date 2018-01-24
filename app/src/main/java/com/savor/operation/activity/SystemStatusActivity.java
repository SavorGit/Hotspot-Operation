package com.savor.operation.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.common.api.utils.ShowMessage;
import com.gxz.PagerSlidingTabStrip;
import com.savor.operation.R;
import com.savor.operation.adapter.IndexInfoAdapter;
import com.savor.operation.adapter.SystemStatusPagerAdapter;
import com.savor.operation.bean.IndexInfo;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.SystemStatusCity;
import com.savor.operation.core.AppApi;
import com.savor.operation.fragment.SystemStatusFragment;

import java.util.ArrayList;
import java.util.List;

public class SystemStatusActivity extends BaseFragmentActivity implements View.OnClickListener {

//    private RecyclerView mInfoLv;
//    private IndexInfoAdapter mAdapter;
    private TextView mRemarkTv;
    private TextView mTitletv;
    private TextView mRightTv;
    private ImageView mBackBtn;
    private ProgressBar mLoadingPb;
    private LinearLayout mContentLayout;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_status);

        getViews();
        setViews();
        setListeners();
        getData();
    }

    private void getData() {
        showLoadingLayout();
        AppApi.getCityList(this,this);
    }

    @Override
    public void getViews() {
        mContentLayout = (LinearLayout) findViewById(R.id.ll_content);
        mLoadingPb = (ProgressBar) findViewById(R.id.pb_loading);

        mBackBtn = (ImageView) findViewById(R.id.iv_left);

//        mInfoLv = (RecyclerView) findViewById(R.id.rlv_info);
        mRemarkTv = (TextView) findViewById(R.id.tv_reamrk);

        mTitletv = (TextView) findViewById(R.id.tv_center);
        mRightTv = (TextView) findViewById(R.id.tv_right);

        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    public void setViews() {
//        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        mInfoLv.setLayoutManager(manager);
//        mAdapter = new IndexInfoAdapter(this);
//        mInfoLv.setAdapter(mAdapter);
//        mInfoLv.setItemAnimator(new DefaultItemAnimator());

        mTitletv.setText("系统状态");
//        mRightTv.setVisibility(View.VISIBLE);
//        mRightTv.setBackgroundResource(R.drawable.bg_edittext);
//        mRightTv.setText("刷新");
        int padding10 = DensityUtil.dip2px(this, 10);
        int padding5 = DensityUtil.dip2px(this, 5);
        mRightTv.setPadding(padding10,0,padding10,0);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRightTv.getLayoutParams();
        layoutParams.setMargins(padding10,padding10,padding10,padding10);

    }

    @Override
    public void setListeners() {
        mRightTv.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_CITY_LIST_JSON:
                hideLoadingLayout();
                if(obj instanceof List) {
                    List<SystemStatusCity> cityList = (List<SystemStatusCity>) obj;

                    List<Fragment> fragmentList = new ArrayList<>();
                    for(int i = 0;i<cityList.size();i++) {
                        fragmentList.add(SystemStatusFragment.newInstance(cityList.get(i).getId()));
                    }

                    SystemStatusPagerAdapter mPagerAdapter = new SystemStatusPagerAdapter(getSupportFragmentManager(),fragmentList,cityList);
                    mViewPager.setAdapter(mPagerAdapter);
                    pagerSlidingTabStrip.setViewPager(mViewPager);
                }
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method, obj);
        switch (method) {
            case POST_INDEX_JSON:
                mLoadingPb.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_right:
                getData();
                break;
        }
    }

    @Override
    public void showLoadingLayout() {
        mLoadingPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingLayout() {
        mLoadingPb.setVisibility(View.GONE);
    }
}
