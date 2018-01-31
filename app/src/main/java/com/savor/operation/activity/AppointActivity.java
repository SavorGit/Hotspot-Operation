package com.savor.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.widget.CommonDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by bushlee on 2017/11/13.
 */

public class AppointActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener,JobAdapter.Appoint,CommonDialog.OnConfirmListener {

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
    private String is_lead_install = "1";
    private TextView reflrsh;

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
        reflrsh = (TextView) findViewById(R.id.reflrsh);
        exe_num = (TextView) findViewById(R.id.exe_num);
    }

    @Override
    public void setViews() {
        tv_center.setText("指派人员");
//        jobAdapter = new JobAdapter(context,this);
//        pullToRefreshListView.setAdapter(jobAdapter);
        if (taskDetail != null) {
            String task_type_id = taskDetail.getTask_type_id();
            if ("2".equals(task_type_id) ) {
                radioGroup.setVisibility(View.VISIBLE);
            }else {
                radioGroup.setVisibility(View.GONE);
                is_lead_install = "";
            }
            exe_num.setVisibility(View.GONE);
        }

    }

    @Override
    public void setListeners() {
        time.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        reflrsh.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton)findViewById(radioButtonId);
                if ("带队安装".equals(rb.getText().toString())) {
                    is_lead_install = "1";
                }else {
                    is_lead_install = "0";
                }
                if (!TextUtils.isEmpty(times)) {
                    getExeUserList();
                }

                //  state = rb.getText().toString();
                //更新文本内容，以符合选中项
                //tv.setText("您的性别是：" + rb.getText());
            }
        });
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
            case R.id.reflrsh:
                if (!TextUtils.isEmpty(times)) {
                    getExeUserList();
                }
                break;
        }

    }

    private void testDatePicker() {
        TimePickerView timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                times = getTime(date);
                Date currentDate = new Date(System.currentTimeMillis());
                String ctimes =  getTime(currentDate);
                int compareTo = currentDate.compareTo(date);
                if(compareTo>=1&& !times.equals(ctimes)) {
                    time.setText("执行日期：");
                    ShowMessage.showToast(AppointActivity.this,"不能选择今天之前的日期");
                    if (jobAdapter != null) {
                        List<ExeUserList> data = jobAdapter.getData();
                        data.clear();
                        jobAdapter.setData(data,times);
                    }
                    times = "";
                    exe_num.setText("执行者数量"+0);
                }else {
                    time.setText("执行日期："+times);
                    getExeUserList();
                }
                //ShowMessage.showToast(SavorMainActivity.this,time);

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
                        exe_num.setVisibility(View.VISIBLE);
                        exe_num.setText("执行者数量"+tasks.size());
                        jobAdapter = new JobAdapter(context,this);
                        pullToRefreshListView.setAdapter(jobAdapter);

                        if (jobAdapter!= null && jobAdapter.getCount()>0) {
//                            List<ExeUserList> data = jobAdapter.getData();
//                            data.clear();
                            jobAdapter.clear();
                            jobAdapter.setData(tasks,times);
                        }else {
                            jobAdapter.setData(tasks,times);
                        }

                    }else {
                        exe_num.setVisibility(View.GONE);
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

    @Override
    public void onError(AppApi.Action method, Object obj) {


        switch (method) {
            case POST_EXE_USER_LIST_JSON:
                exe_num.setText("执行者数量"+0);
                break;
            case POST_APPOIN_TASK_JSON:
                // if(obj instanceof List) {
                if (obj instanceof ResponseErrorMessage){
                    ResponseErrorMessage errorMessage = (ResponseErrorMessage)obj;
                    String statusCode = String.valueOf(errorMessage.getCode());
                    ShowMessage.showToast(context,errorMessage.getMessage());
                }
                //  }
                break;
        }
    }
    private void initView(){
        if (taskDetail != null) {
            String state = taskDetail.getTask_type_desc();
            String hotel_name = taskDetail.getHotel_name();
            String num = taskDetail.getTv_nums();

            if (!TextUtils.isEmpty(state)) {
                info_state.setVisibility(View.VISIBLE);
                info_state.setText(state);
            }else {
                info_state.setVisibility(View.INVISIBLE);
                //info_state.setText(state);
            }

            if (!TextUtils.isEmpty(hotel_name)) {
                info_hotel_name.setVisibility(View.VISIBLE);
                info_hotel_name.setText(hotel_name);
            }else {
                info_hotel_name.setVisibility(View.INVISIBLE);
            }

            if (!TextUtils.isEmpty(num)) {
                tv_num.setVisibility(View.VISIBLE);
                tv_num.setText("版位数量："+num+"个");
            }else {
                tv_num.setVisibility(View.INVISIBLE);
            }
        }



    }
    private void getExeUserList(){
        if (jobAdapter != null) {
            jobAdapter.clear();
        }
        AppApi.getExeUserList(context,times,is_lead_install,taskDetail.getId(),this);
    }

    @Override
    public void appoint(ExeUserList itemVo) {

        final ExeUserList CitemVo = itemVo;

        new CommonDialog(this, "是否指派给"+itemVo.getUsername(), new CommonDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {
                AppApi.appointTask(context,times,mSession.getLoginResponse().getUserid(),CitemVo.getUser_id(),taskDetail.getId(),is_lead_install,AppointActivity.this);
            }
        }, new CommonDialog.OnCancelListener() {
            @Override
            public void onCancel() {

            }
        },"确定").show();


    }

    @Override
    public void onConfirm() {

    }
}
