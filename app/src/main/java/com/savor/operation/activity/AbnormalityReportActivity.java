package com.savor.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.ErrorReportListAdapter;
import com.savor.operation.bean.ErrorReport;
import com.savor.operation.bean.ErrorReportBean;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/4.
 */

public class AbnormalityReportActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener {
    private Context context;
    private RelativeLayout back;
    private PullToRefreshListView mPullRefreshListView;
    private ErrorReportListAdapter mAdapter;
    private int pageSize = 15;
    private String id = "0";
    private ErrorReport errorReport;
    private List<ErrorReportBean> list = new ArrayList<ErrorReportBean>();
    private boolean isUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormality_record);
        context = this;
        getViews();
        setViews();
        setListeners();
        getData();

    }

    private void getData(){
        AppApi.getErrorReportList(this,id,pageSize,this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

        }
    }

    @Override
    public void getViews() {
        back = (RelativeLayout) findViewById(R.id.back);
        mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.listview);
    }

    @Override
    public void setViews() {
        mAdapter = new ErrorReportListAdapter(context);
        mPullRefreshListView.setAdapter(mAdapter);
    }

    PullToRefreshBase.OnRefreshListener onRefreshListener = new PullToRefreshBase.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            id = "0";
            isUp = true;
           // istop = true;
            getData();
        }
    };

    PullToRefreshBase.OnLastItemVisibleListener onLastItemVisibleListener = new PullToRefreshBase.OnLastItemVisibleListener() {
        @Override
        public void onLastItemVisible() {
            isUp = false;
           // istop = false;
            getData();
        }
    };
    @Override
    public void setListeners() {
        back.setOnClickListener(this);
        mPullRefreshListView.setOnRefreshListener(onRefreshListener);
        mPullRefreshListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
        mPullRefreshListView.onLoadComplete(true,false);
        mPullRefreshListView.setOnItemClickListener(itemClickListener);
    }
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        mPullRefreshListView.onRefreshComplete();
        switch (method) {
            case POST_ERROR_REPORT_LIST_JSON:
                if(obj instanceof ErrorReport) {
                    errorReport= (ErrorReport) obj;
                    if (errorReport != null) {
                        List<ErrorReportBean> mList =  errorReport.getList();
                        handleVodList(mList);
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

    private void handleVodList(List<ErrorReportBean> mList){

        if (mList != null && mList.size() > 0) {
            if (isUp) {
                list.clear();
                mAdapter.clear();
                mPullRefreshListView.onLoadComplete(true,false);

            }else {
                mPullRefreshListView.onLoadComplete(true,false);
            }
            id = mList.get(mList.size()-1).getId();
            list.addAll(mList);
            mAdapter.setData(list);
            int haveNext = 0;
            haveNext =  errorReport.getIsNextPage();

            if (haveNext==0) {
                mPullRefreshListView.onLoadComplete(false,false);
            }else {
                mPullRefreshListView.onLoadComplete(true,false);
            }
        }else {
            if (list != null && list.size()>0  ) {
                mAdapter.clear();
            }
//            mProgressLayout.loadSuccess();
//            empty_la.setVisibility(View.VISIBLE);
            mPullRefreshListView.setVisibility(View.GONE);

            mPullRefreshListView.onLoadComplete(false,true);
        }



    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ErrorReportBean item = (ErrorReportBean)parent.getItemAtPosition(position);
            if (item!=null){
                Intent intent = new Intent();
                intent.putExtra("errorreport",item);
                intent.setClass(AbnormalityReportActivity.this,AbnormalityInfoActivity.class);
                startActivity(intent);
            }
        }
    };

}
