package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.JobAdapter;
import com.savor.operation.bean.ExeUserList;
import com.savor.operation.bean.Task;
import com.savor.operation.bean.TaskDetail;
import com.savor.operation.bean.TaskInfoListBean;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;

import java.util.List;

/**
 * Created by bushlee on 2017/11/13.
 */

public class AppointActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener {

    private TaskDetail taskDetail;
    private Context context;
    private TextView info;
    private RadioGroup radioGroup;
    private PullToRefreshListView pullToRefreshListView;
    private TextView tv_center;
    private ImageView iv_left;
    private JobAdapter jobAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoint_layout);
        context = this;
        getViews();
        setViews();
        setListeners();
        getDetail();
        initView();
    }

    private void getDetail(){
        taskDetail = (TaskDetail) getIntent().getSerializableExtra("voditem");
    }


    @Override
    public void getViews() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        info = (TextView) findViewById(R.id.info);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.wl_listview);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }

    @Override
    public void setViews() {
        jobAdapter = new JobAdapter(context);
        pullToRefreshListView.setAdapter(jobAdapter);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_EXE_USER_LIST_JSON:
                if(obj instanceof List) {
                    List<ExeUserList> tasks = (List<ExeUserList>) obj;
                    jobAdapter.setData(tasks);
                }
                break;
        }
    }
    private void initView(){
        info.setText(taskDetail.getTask_type_desc()+taskDetail.getHotel_address()+"版位数量："+taskDetail.getTv_nums()+"个");
    }
    private void getExeUserList(){
        AppApi.getExeUserList(context,"","",taskDetail.getId(),this);
    }
}
