package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.common.api.utils.ShowMessage;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.JobAdapter;
import com.savor.operation.bean.ExeUserList;
import com.savor.operation.bean.Task;
import com.savor.operation.bean.TaskDetail;
import com.savor.operation.bean.TaskInfoListBean;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by bushlee on 2017/11/13.
 */

public class AppointActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener,JobAdapter.Appoint {

    private TaskDetail taskDetail;
    private Context context;
    private TextView info;
    private RadioGroup radioGroup;
    private PullToRefreshListView pullToRefreshListView;
    private TextView tv_center;
    private TextView time;
    private ImageView iv_left;
    private JobAdapter jobAdapter;
    private String times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoint_layout);
        context = this;
        getDetail();
        getViews();
        setViews();
        setListeners();

        initView();
    }

    private void getDetail(){
        taskDetail = (TaskDetail) getIntent().getSerializableExtra("taskDetail");
    }


    @Override
    public void getViews() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        info = (TextView) findViewById(R.id.info);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.wl_listview);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        time = (TextView) findViewById(R.id.time);
    }

    @Override
    public void setViews() {
        tv_center.setText("指派人员");
        jobAdapter = new JobAdapter(context,this);
        pullToRefreshListView.setAdapter(jobAdapter);
        String task_type_id = taskDetail.getTask_type_id();
        if ("2".equals(task_type_id) ) {
            radioGroup.setVisibility(View.VISIBLE);
        }else {
            radioGroup.setVisibility(View.GONE);
        }
    }

    @Override
    public void setListeners() {
        time.setOnClickListener(this);
        iv_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.time:
                testDatePicker();
                break;
            case R.id.iv_left:
                finish();
                break;
        }

    }

    private void testDatePicker() {
        TimePickerView timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                times = getTime(date);
                //ShowMessage.showToast(SavorMainActivity.this,time);
                time.setText("执行日期："+times);
                getExeUserList();
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).isCenterLabel(false).build();
        timePickerView.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_EXE_USER_LIST_JSON:
                if(obj instanceof List) {
                    List<ExeUserList> tasks = (List<ExeUserList>) obj;
                    if (jobAdapter!= null && jobAdapter.getCount()>0) {
                        jobAdapter.clear();
                    }
                    jobAdapter.setData(tasks);
                }
                break;
            case POST_APPOIN_TASK_JSON:
               // if(obj instanceof List) {
                    finish();
               ShowMessage.showToast(context,"指派成功");
              //  }
                break;
        }
    }
    private void initView(){
        info.setText(taskDetail.getTask_type_desc()+taskDetail.getHotel_address()+"版位数量："+taskDetail.getTv_nums()+"个");
    }
    private void getExeUserList(){
        AppApi.getExeUserList(context,times,"",taskDetail.getId(),this);
    }

    @Override
    public void appoint(ExeUserList itemVo) {
        AppApi.appointTask(context,times,mSession.getLoginResponse().getUserid(),itemVo.getUser_id(),taskDetail.getId(),this);
    }
}
