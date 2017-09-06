package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.SpinnerAdapter;
import com.savor.operation.bean.ErrorReport;
import com.savor.operation.bean.ErrorReportBean;
import com.savor.operation.bean.UserBean;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/4.
 */

public class MaintenanceRecordActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener {
    private Context context;
    private Spinner spinner;
    private RelativeLayout back;
    private PullToRefreshListView mPullRefreshListView;
    private List<UserBean> list = new ArrayList<UserBean>();
    private SpinnerAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_record);
        context = this;
        getViews();
        setViews();
        setListeners();
        getData();
    }

    private void getData(){
        AppApi.getAllRepairUser(this,this);

    }
    @Override
    public void getViews() {
        spinner = (Spinner) findViewById(R.id.spinner);
        back = (RelativeLayout) findViewById(R.id.back);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {
        back.setOnClickListener(this);
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
       // mPullRefreshListView.onRefreshComplete();
        switch (method) {
            case POST_REPAIR_USER_JSON:
                if(obj instanceof List) {
                    List<UserBean> hotelList = (List<UserBean>) obj;
                    initSpinner(hotelList);
                }
                break;



        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {

        switch (method) {
            case POST_ERROR_REPORT_LIST_JSON:


                if (obj instanceof ResponseErrorMessage){
                    ResponseErrorMessage errorMessage = (ResponseErrorMessage)obj;
                    String statusCode = String.valueOf(errorMessage.getCode());
                    if (AppApi.ERROR_TIMEOUT.equals(statusCode)){
//                        mProgressLayout.loadFailure("数据加载超时");
//                        showRefreshHintAnimation("数据加载超时");
                    }else if (AppApi.ERROR_NETWORK_FAILED.equals(statusCode)){
//                        mProgressLayout.loadFailure("网络异常，点击重试");
//                        showRefreshHintAnimation("无法连接到网络,请检查网络设置");
                    }
                }


                break;




        }
    }

    private void initSpinner(List<UserBean> hotelList){
        if (hotelList != null && hotelList.size()>0) {
            spinnerAdapter = new SpinnerAdapter(context,hotelList);
            spinner.setAdapter(spinnerAdapter);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

        }
    }
}
