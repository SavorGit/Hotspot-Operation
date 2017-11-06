package com.savor.operation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.savor.operation.R;
import com.savor.operation.adapter.TaskListAdapter;
import com.savor.operation.bean.Task;
import com.savor.operation.core.AppApi;
import com.savor.operation.widget.decoration.SpacesItemDecoration;

import java.io.Serializable;
import java.util.List;

public class PublishTaskActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private RecyclerView mTaskListView;
    private TaskListAdapter mAdapter;

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
        getTaskList();
    }

    private void getTaskList() {
        AppApi.getTaskList(this,this);
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTitleTv = (TextView) findViewById(R.id.tv_center);

        mTaskListView = (RecyclerView) findViewById(R.id.rlv_task_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mTaskListView.setLayoutManager(manager);

        mAdapter = new TaskListAdapter(this);
        mTaskListView.setAdapter(mAdapter);

        //添加ItemDecoration，item之间的间隔
        int leftRight = DensityUtil.dip2px(this,2);
        int topBottom = DensityUtil.dip2px(this,2);

        mTaskListView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, getResources().getColor(R.color.grid_item_divider)));
    }

    @Override
    public void setViews() {
        mTitleTv.setText("选择任务类型");
        mTitleTv.setTextColor(getResources().getColor(R.color.white));

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

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_TASK_TYPE_JSON:
                if(obj instanceof List) {
                    List<Task> tasks = (List<Task>) obj;
                    mAdapter.setData(tasks);
                }
                break;
        }
    }
}
