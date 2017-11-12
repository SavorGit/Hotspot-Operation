package com.savor.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.bean.MissionTaskListBean;
import com.savor.operation.bean.TaskDetail;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;

import java.util.List;

/**
 * 通用任务详情
 * Created by bushlee on 2017/11/12.
 */

public class TaskDetailActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener {

    private Context context;
    private PullToRefreshListView mPullRefreshListView;
    private String id;
    public TextView plan_state;
    public TextView level_state;
    public TextView screen_num;
    public TextView mold;
    public TextView hotel_name;
    public TextView release_execute_time;
    public TextView release_time;
    public TextView execute_time;
    public TextView complete_time;
    private TaskDetail taskDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_detail);
        context = this;
        handleIntent();
        getViews();
        setViews();
        setListeners();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }


    @Override
    public void getViews() {
        plan_state = (TextView) findViewById(R.id.plan_state);
        level_state = (TextView) findViewById(R.id.level_state);
        screen_num = (TextView) findViewById(R.id.screen_num);
        mold = (TextView) findViewById(R.id.mold);
        hotel_name = (TextView) findViewById(R.id.hotel_name);
        release_execute_time = (TextView) findViewById(R.id.release_execute_time);
        release_time = (TextView) findViewById(R.id.release_time);
        execute_time = (TextView) findViewById(R.id.execute_time);
        complete_time = (TextView) findViewById(R.id.complete_time);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.wl_listview);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {

    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method){
            case POST_VIEW_TASK_LIST_JSON:
                mPullRefreshListView.onRefreshComplete();
                if (obj instanceof TaskDetail){
                    taskDetail = (TaskDetail)obj;
                    initView();
                }


                break;
        }

    }

    private void initView(){
        if (taskDetail != null){
           plan_state.setText(taskDetail.getState()+"("+taskDetail.getRegion_name()+")");
            String task_emerge_id = taskDetail.getTask_emerge_id();
            if ("2".equals(task_emerge_id)) {
                level_state.setVisibility(View.VISIBLE);
                level_state.setText(taskDetail.getTask_emerge());
            }else {
                level_state.setVisibility(View.INVISIBLE);
            }

            screen_num.setText("版位数量 ："+taskDetail.getTv_nums());
            mold.setText(taskDetail.getTask_type_desc());
            hotel_name.setText(taskDetail.getHotel_name());
//            String appoint_exe_time = taskDetail.getAppoint_exe_time();
//            if (!TextUtils.isEmpty(appoint_exe_time)) {
//                release_execute_time.setVisibility(View.VISIBLE);
//                release_execute_time.setText("执行指派时间:"+appoint_exe_time+"("+taskDetail.getExeuser()+")");
//            }else {
                release_execute_time.setVisibility(View.GONE);
                release_execute_time.setText("");
 //           }


            String create_time = taskDetail.getCreate_time();
            if (!TextUtils.isEmpty(create_time)) {
                release_time.setVisibility(View.VISIBLE);
                release_time.setText("发布时间:"+create_time+"("+taskDetail.getPublish_user()+")");
            }else {
                release_time.setVisibility(View.GONE);
                release_time.setText("");
            }

            String appoint_time = taskDetail.getAppoint_time();
            if (!TextUtils.isEmpty(appoint_time)) {
                execute_time.setVisibility(View.VISIBLE);
                execute_time.setText("指派时间:"+appoint_time+"("+taskDetail.getAppoint_user()+")");
            }else {
                execute_time.setVisibility(View.GONE);
                execute_time.setText("");
            }

            String complete_timeStr = taskDetail.getComplete_time();
            if (!TextUtils.isEmpty(complete_timeStr)) {
                complete_time.setVisibility(View.VISIBLE);
                complete_time.setText("完成时间"+complete_time+"("+taskDetail.getExeuser()+")");
            }else {
                complete_time.setVisibility(View.GONE);
                complete_time.setText("");
            }
        }

    }
}
