package com.savor.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.adapter.LoadingProgramAdsAdapter;
import com.savor.operation.bean.LoadingProgramAds;
import com.savor.operation.core.AppApi;

import java.util.List;

public class LoadingListActivity extends BaseActivity implements View.OnClickListener {

    private ListView mLoadingLv;
    private TextView mPeriodTv;
    private LoadingProgramAdsAdapter programAdsAdapter;
    private String pro_download_period;
    private ImageView mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_list);

        handleIntent();
        getViews();
        setViews();
        setListeners();
        getData();
    }

    public static void startLoadingListActivity(Context context,String pro_download_period) {
        Intent intent = new Intent(context,LoadingListActivity.class);
        intent.putExtra("pro_download_period",pro_download_period);
        context.startActivity(intent);
    }

    private void getData() {
        if(!TextUtils.isEmpty(pro_download_period)) {
            showLoadingLayout();
            AppApi.getLoadingProList(this,pro_download_period,this);
        }
    }

    private void handleIntent() {
        pro_download_period = getIntent().getStringExtra("pro_download_period");
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mLoadingLv = (ListView) findViewById(R.id.rlv_loading);

        initHeaderView();
    }

    private void initHeaderView() {
        View headerView = View.inflate(this, R.layout.header_view_loading_list,null);
        mPeriodTv = (TextView) headerView.findViewById(R.id.tv_period);
        mLoadingLv.addHeaderView(headerView);
    }

    @Override
    public void setViews() {
        programAdsAdapter = new LoadingProgramAdsAdapter(this);
        mLoadingLv.setAdapter(programAdsAdapter);
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_LOADING_PRO_JSON:
                hideLoadingLayout();
                if(obj instanceof List) {
                    List<LoadingProgramAds> programList = (List<LoadingProgramAds>) obj;
                    programAdsAdapter.setData(programList);
                }
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method, obj);
        switch (method) {
            case POST_LOADING_PRO_JSON:
                hideLoadingLayout();
                break;
        }
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
