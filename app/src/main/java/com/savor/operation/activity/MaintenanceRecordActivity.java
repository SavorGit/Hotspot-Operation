package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshExpandableListView;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.ErrorReportListAdapter;
import com.savor.operation.adapter.MaintenanceRecordAdapter;
import com.savor.operation.adapter.SpinnerAdapter;
import com.savor.operation.bean.ErrorReport;
import com.savor.operation.bean.ErrorReportBean;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.RepairRecordList;
import com.savor.operation.bean.RepairRecordListBean;
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
    private List<UserBean> userlist = new ArrayList<UserBean>();
    private SpinnerAdapter spinnerAdapter;
    private MaintenanceRecordAdapter mAdapter;
    private int page = 1;
    private String id = "0";
    private ErrorReport errorReport;
    private List<RepairRecordListBean> list = new ArrayList<RepairRecordListBean>();
    private boolean isUp = true;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_record);
        context = this;
        getViews();
        setViews();
        setListeners();
        getData();
        getRepairRecordList();
    }

    private void getData(){
        AppApi.getAllRepairUser(this,this);

    }

    private void getRepairRecordList(){
        AppApi.getRepairRecordList(this,userid,page,this);
    }
    @Override
    public void getViews() {
        LoginResponse loginResponse = mSession.getLoginResponse();
        userid = loginResponse.getUserid();
        spinner = (Spinner) findViewById(R.id.spinner);
        back = (RelativeLayout) findViewById(R.id.back);
        mPullRefreshListView  = (PullToRefreshListView)findViewById(R.id.listview);
    }

    @Override
    public void setViews() {
        mAdapter = new MaintenanceRecordAdapter(context);
        mPullRefreshListView.setAdapter(mAdapter);
    }

    @Override
    public void setListeners() {
        back.setOnClickListener(this);
        mPullRefreshListView.setOnRefreshListener(onRefreshListener);
        mPullRefreshListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
        mPullRefreshListView.onLoadComplete(true,false);
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        mPullRefreshListView.onRefreshComplete();
        switch (method) {
            case POST_REPAIR_USER_JSON:
                if(obj instanceof List) {
                    List<UserBean> hotelList = (List<UserBean>) obj;
                    initSpinner(hotelList);
                }
                break;
            case POST_REPAIR_RECORD_LIST_JSON:
                if(obj instanceof RepairRecordList) {
                    RepairRecordList repairRecordList = (RepairRecordList) obj;
                    if (repairRecordList != null) {
                        List<RepairRecordListBean> list = repairRecordList.getList();
                        handleVodList(list);
                    }
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
    private void handleVodList(List<RepairRecordListBean> mList) {

        if (mList != null && mList.size() > 0) {
            if (isUp) {
                list.clear();
                mAdapter.clear();
                mPullRefreshListView.onLoadComplete(true, false);

            } else {
                mPullRefreshListView.onLoadComplete(true, false);
            }
            // id = mList.get(mList.size()-1).getId();
            page++;
            list.addAll(mList);
            mAdapter.setData(list,isUp);
            //int haveNext = 0;
           // haveNext = errorReport.getIsNextPage();

            if (mList.size() < 15) {
                mPullRefreshListView.onLoadComplete(false, false);
            } else {
                mPullRefreshListView.onLoadComplete(true, false);
            }
        } else {
            if (list != null && list.size() > 0) {
                mAdapter.clear();
            }
//            mProgressLayout.loadSuccess();
//            empty_la.setVisibility(View.VISIBLE);
//            mPullRefreshListView.setVisibility(View.GONE);

            mPullRefreshListView.onLoadComplete(false, true);
        }

    }

    PullToRefreshBase.OnRefreshListener onRefreshListener = new PullToRefreshBase.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            page = 1;
            isUp = true;
            // istop = true;
            getRepairRecordList();
        }
    };

    PullToRefreshBase.OnLastItemVisibleListener onLastItemVisibleListener = new PullToRefreshBase.OnLastItemVisibleListener() {
        @Override
        public void onLastItemVisible() {
            isUp = false;
            // istop = false;
            getRepairRecordList();
        }
    };
        @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

        }
    }
}
