package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.bean.TaskDetail;
import com.savor.operation.core.ApiRequestListener;

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

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View view) {

    }
    private void initView(){
        info.setText(taskDetail.getTask_type_desc()+taskDetail.getHotel_address()+"版位数量："+taskDetail.getTv_nums()+"个");
    }
}
