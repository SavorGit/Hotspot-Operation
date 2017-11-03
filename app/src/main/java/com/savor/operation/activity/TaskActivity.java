package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.savor.operation.R;
import com.savor.operation.adapter.MaintainTaskAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务详情页面（公共页面）
 */
public class TaskActivity extends BaseActivity implements View.OnClickListener {

    private ListView mTaskLv;
    private View mHeadView;
    private ImageView mBackBtn;
    private PublishTaskActivity.TaskType actionType;
    private RelativeLayout mNumLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        handleIntent();
        getViews();
        setViews();
        setListeners();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        actionType = (PublishTaskActivity.TaskType) intent.getSerializableExtra("type");
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTaskLv = (ListView) findViewById(R.id.lv_task_list);

        initHeaderView();

    }

    private void initHeaderView() {
        mHeadView = View.inflate(this,R.layout.header_view_task,null);
        mNumLayout = (RelativeLayout) mHeadView.findViewById(R.id.rl_num);
        if(actionType == PublishTaskActivity.TaskType.INFO_CHECK||actionType == PublishTaskActivity.TaskType.NETWORK_REMOULD) {
            mNumLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setViews() {
        List<String> list = new ArrayList<>();
        for(int i = 0;i<10;i++) {
            list.add(i+"");
        }


        MaintainTaskAdapter mTaskAdapter = new MaintainTaskAdapter(this);
        mTaskLv.setAdapter(mTaskAdapter);
        mTaskLv.addHeaderView(mHeadView);
        if(actionType == PublishTaskActivity.TaskType.FIX) {
            mTaskAdapter.setData(list);
        }
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);
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
