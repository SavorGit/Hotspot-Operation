package com.savor.operation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.savor.operation.R;

public class PublishTaskActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private LinearLayout mSetupLayout;
    private LinearLayout mInfoCheckLayout;
    private LinearLayout mNetworkRemouldLayout;
    private LinearLayout mFixLayout;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_setup:
                // 安装与验收
                break;
            case R.id.ll_info_check:
                // 信息监测
                break;
            case R.id.ll_network_remould:
                // 网络改造
                break;
            case R.id.ll_fix:
                // 维修
                break;
        }
    }
}
