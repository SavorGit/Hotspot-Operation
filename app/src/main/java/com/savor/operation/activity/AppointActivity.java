package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
    private TextView info_state;
    private TextView info_hotel_name;
    private TextView tv_num;
    private RadioGroup radioGroup;
    private PullToRefreshListView pullToRefreshListView;
    private TextView tv_center;
    private TextView time;
    private ImageView iv_left;
    private JobAdapter jobAdapter;
    private String times;
    private TextView exe_num;

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
        info_state = (TextView) findViewById(R.id.info_state);
        info_hotel_name = (TextView) findViewById(R.id.info_hotel_name);
        tv_num = (TextView) findViewById(R.id.tv_num);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.wl_listview);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        time = (TextView) findViewById(R.id.time);
        exe_num = (TextView) findViewById(R.id.exe_num);
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
                    if (tasks != null && tasks.size()>0) {
                        exe_num.setText("执行者数量"+tasks.size());
                        if (jobAdapter!= null && jobAdapter.getCount()>0) {
                            jobAdapter.clear();
                        }
                        jobAdapter.setData(tasks);
                    }

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
        String state = taskDetail.getTask_type_desc();
        String hotel_name = taskDetail.getHotel_address();
        String num = taskDetail.getTv_nums();

        if (!TextUtils.isEmpty(state)) {
            info_state.setVisibility(View.VISIBLE);
            info_state.setText(state);
        }else {
            info_state.setVisibility(View.GONE);
            //info_state.setText(state);
        }

        if (!TextUtils.isEmpty(hotel_name)) {
            info_hotel_name.setVisibility(View.VISIBLE);
            info_hotel_name.setText(hotel_name);
        }else {
            info_hotel_name.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(num)) {
            tv_num.setVisibility(View.VISIBLE);
            tv_num.setText("版位数量："+num+"个");
        }else {
            tv_num.setVisibility(View.GONE);
        }

    }
    private void getExeUserList(){
        AppApi.getExeUserList(context,times,"",taskDetail.getId(),this);
    }

    @Override
    public void appoint(ExeUserList itemVo) {
        AppApi.appointTask(context,times,mSession.getLoginResponse().getUserid(),itemVo.getUser_id(),taskDetail.getId(),this);
    }
}
