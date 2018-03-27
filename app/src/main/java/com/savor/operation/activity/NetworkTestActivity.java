package com.savor.operation.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.savor.operation.R;
import com.savor.operation.adapter.NetworkTestPagerAdapter;
import com.savor.operation.fragment.NetworkTestFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络测评列表页
 *
 * @author hezd Create on 2018/02/28
 */
public class NetworkTestActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private PagerSlidingTabStrip mPagerTabs;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);

        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        mBackIv = (ImageView) findViewById(R.id.iv_left);
        mTitleTv = (TextView) findViewById(R.id.tv_center);
        mPagerTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    public void setViews() {
        mTitleTv.setText("信息监测列表");

        List<String> titleList = new ArrayList<>();
        titleList.add("未完成");
        titleList.add("已完成");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(NetworkTestFragment.newInstance(NetworkTestFragment.NetworkTestType.TYPE_UNCOMPLETE));
        fragmentList.add(NetworkTestFragment.newInstance(NetworkTestFragment.NetworkTestType.TYPE_COMPLETE));
        NetworkTestPagerAdapter networkTestPagerAdapter = new NetworkTestPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        mViewPager.setAdapter(networkTestPagerAdapter);
        mPagerTabs.setViewPager(mViewPager);

    }

    @Override
    public void setListeners() {
        mBackIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
