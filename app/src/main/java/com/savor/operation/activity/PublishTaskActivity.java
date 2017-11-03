package com.savor.operation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.savor.operation.R;

import java.io.Serializable;

public class PublishTaskActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private LinearLayout mSetupLayout;
    private LinearLayout mInfoCheckLayout;
    private LinearLayout mNetworkRemouldLayout;
    private LinearLayout mFixLayout;

    public enum TaskType implements Serializable{
        /**安装与验收*/
        SETUP_AND_CHECK,
        /**信息监测*/
        INFO_CHECK,
        /**网络改造*/
        NETWORK_REMOULD,
        /**维修*/
        FIX,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_task);

        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTitleTv = (TextView) findViewById(R.id.tv_center);

        mSetupLayout = (LinearLayout) findViewById(R.id.ll_setup);
        mInfoCheckLayout = (LinearLayout) findViewById(R.id.ll_info_check);
        mNetworkRemouldLayout = (LinearLayout) findViewById(R.id.ll_network_remould);
        mFixLayout = (LinearLayout) findViewById(R.id.ll_fix);
    }

    @Override
    public void setViews() {
        mTitleTv.setText("选择任务类型");
        mTitleTv.setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public void setListeners() {
        mSetupLayout.setOnClickListener(this);
        mInfoCheckLayout.setOnClickListener(this);
        mNetworkRemouldLayout.setOnClickListener(this);
        mFixLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_setup:
                // 安装与验收
                intent = new Intent(mContext,TaskActivity.class);
                intent.putExtra("type", TaskType.SETUP_AND_CHECK);
                mContext.startActivity(intent);
                break;
            case R.id.ll_info_check:
                // 信息监测
                intent = new Intent(mContext,TaskActivity.class);
                intent.putExtra("type", TaskType.INFO_CHECK);
                mContext.startActivity(intent);
                break;
            case R.id.ll_network_remould:
                // 网络改造
                intent = new Intent(mContext,TaskActivity.class);
                intent.putExtra("type", TaskType.NETWORK_REMOULD);
                mContext.startActivity(intent);
                break;
            case R.id.ll_fix:
                // 维修
                intent = new Intent(mContext,TaskActivity.class);
                intent.putExtra("type", TaskType.FIX);
                mContext.startActivity(intent);
                break;
        }
    }
}
