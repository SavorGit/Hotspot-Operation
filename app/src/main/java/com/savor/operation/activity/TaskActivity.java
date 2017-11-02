package com.savor.operation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTaskLv = (ListView) findViewById(R.id.lv_task_list);

        initHeaderView();

    }

    private void initHeaderView() {
        mHeadView = View.inflate(this,R.layout.header_view_task,null);

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
        mTaskAdapter.setData(list);
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
