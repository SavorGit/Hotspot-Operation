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
import com.savor.operation.adapter.PubProListAdapter;
import com.savor.operation.bean.LoadingProgramAds;
import com.savor.operation.bean.PubProgram;
import com.savor.operation.core.AppApi;

import java.io.Serializable;
import java.util.List;

public class LoadingListActivity extends BaseActivity implements View.OnClickListener {

    private ListView mLoadingLv;
    private TextView mPeriodTv;
    private LoadingProgramAdsAdapter programAdsAdapter;
    private String pro_download_period;
    private ImageView mBackBtn;
    private String ads_download_period;
    private Operationtype type;
    private String box_id;
    private String pro_period;
    private String ads_period;
    private TextView mTitleTv;

    public enum Operationtype implements Serializable{
        /**下载节目*/
        PUB_PRO_LIST,
        /**下载广告*/
        DOWNLOAD_ADS,
        /**发布内容*/
        DOWNLOAD_PRO,
    }

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
        if(type!=null) {
            switch (type) {
                case DOWNLOAD_ADS:
                    AppApi.getDownloadAds(this,box_id,ads_download_period,this);
                    break;
                case DOWNLOAD_PRO:
                    if(!TextUtils.isEmpty(pro_download_period)) {
                        showLoadingLayout();
                        AppApi.getLoadingProList(this,pro_download_period,this);
                    }
                    break;
                case PUB_PRO_LIST:
                    if(!TextUtils.isEmpty(box_id)) {
                        AppApi.getPubProList(this,box_id,this);
                    }
                    break;
            }

        }
    }

    private void handleIntent() {
        pro_download_period = getIntent().getStringExtra("pro_download_period");
        ads_download_period = getIntent().getStringExtra("ads_download_period");
        pro_period = getIntent().getStringExtra("pro_period");
        ads_period = getIntent().getStringExtra("ads_period");
        box_id = getIntent().getStringExtra("box_id");
        type = (Operationtype) getIntent().getSerializableExtra("type");
    }

    @Override
    public void getViews() {
        mTitleTv = (TextView) findViewById(R.id.tv_center);
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
        if(type!=null) {
            switch (type) {
                case PUB_PRO_LIST:
                    mTitleTv.setText("发布节目单");
                    mPeriodTv.setText("发布节目期号："+pro_period+"    "+"发布广告期号："+ads_period);
                    break;
                case DOWNLOAD_PRO:
                    mTitleTv.setText("节目下载列表");
                    mPeriodTv.setText("下载节目期号："+pro_download_period);
                    break;
                case DOWNLOAD_ADS:
                    mTitleTv.setText("广告下载列表");
                    mPeriodTv.setText("下载广告期号："+ads_download_period);
                    break;
            }
        }
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        hideLoadingLayout();
        switch (method) {
            case POST_DOWNLOAD_ADS_JSON:
            case POST_PUBLISH_LIST_JSON:_JSON:
                if(obj instanceof List) {
                    List<PubProgram> pubPrograms = (List<PubProgram>) obj;
                    PubProListAdapter pubProListAdapter = new PubProListAdapter(this);
                    pubProListAdapter.setData(pubPrograms);
                    mLoadingLv.setAdapter(pubProListAdapter);
                }
                break;
            case POST_LOADING_PRO_JSON:
                if(obj instanceof List) {
                    List<LoadingProgramAds> programList = (List<LoadingProgramAds>) obj;
                    programAdsAdapter = new LoadingProgramAdsAdapter(this);
                    mLoadingLv.setAdapter(programAdsAdapter);
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
