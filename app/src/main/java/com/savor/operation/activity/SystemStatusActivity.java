package com.savor.operation.activity;

import android.os.Bundle;
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
import com.savor.operation.R;
import com.savor.operation.adapter.IndexInfoAdapter;
import com.savor.operation.bean.IndexInfo;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.core.AppApi;

import java.util.List;

public class SystemStatusActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mInfoLv;
    private IndexInfoAdapter mAdapter;
    private TextView mRemarkTv;
    private TextView mTitletv;
    private TextView mRightTv;
    private ImageView mBackBtn;
    private ProgressBar mLoadingPb;
    private LinearLayout mContentLayout;

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
        mLoadingPb.setVisibility(View.VISIBLE);
        AppApi.getIndexInfo(this,this);
    }

    @Override
    public void getViews() {
        mContentLayout = (LinearLayout) findViewById(R.id.ll_content);
        mLoadingPb = (ProgressBar) findViewById(R.id.pb_loading);

        mBackBtn = (ImageView) findViewById(R.id.iv_left);

        mInfoLv = (RecyclerView) findViewById(R.id.rlv_info);
        mRemarkTv = (TextView) findViewById(R.id.tv_reamrk);

        mTitletv = (TextView) findViewById(R.id.tv_center);
        mRightTv = (TextView) findViewById(R.id.tv_right);
    }

    @Override
    public void setViews() {
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mInfoLv.setLayoutManager(manager);
        mAdapter = new IndexInfoAdapter(this);
        mInfoLv.setAdapter(mAdapter);
        mInfoLv.setItemAnimator(new DefaultItemAnimator());

        mTitletv.setText("系统状态");
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setBackgroundResource(R.drawable.bg_edittext);
        mRightTv.setText("刷新");
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
            case POST_INDEX_JSON:
                if(isFinishing())
                    return;
                if(obj instanceof IndexInfo) {

                    mLoadingPb.setVisibility(View.GONE);
                    mContentLayout.setVisibility(View.VISIBLE);

                    ShowMessage.showToast(this,"数据获取成功");
                    IndexInfo indexInfo = (IndexInfo) obj;
                    List<String> list = indexInfo.getList();
                    mAdapter.setData(list);
                    String remark = indexInfo.getRemark();
                    mRemarkTv.setText(remark);
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


}
