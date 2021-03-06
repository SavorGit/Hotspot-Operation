package com.savor.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.ErrorReportInfoAdapter;
import com.savor.operation.adapter.ErrorReportListAdapter;
import com.savor.operation.bean.ErrorDetail;
import com.savor.operation.bean.ErrorDetailBean;
import com.savor.operation.bean.ErrorReport;
import com.savor.operation.bean.ErrorReportBean;
import com.savor.operation.bean.Hotel;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/4.
 */

public class AbnormalityInfoActivity extends BaseActivity implements View.OnClickListener{
    private Context context;
    private RelativeLayout back;
    private ErrorReportBean item;
    private TextView info;
    private TextView time;
    private PullToRefreshListView mPullRefreshListView;
    private ErrorReportInfoAdapter mAdapter;
    private int pageSize = 15;
    private int pageNum = 1;
    private String error_id = "0";
    private String detail_id = "0";
    private boolean isUp = true;
    private List<ErrorDetailBean> list = new ArrayList<ErrorDetailBean>();
    private ErrorDetail errorDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormality_info);
        context = this;
        getIntentData();
        getViews();
        setViews();
        setListeners();
        getData();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if (intent != null) {
            error_id = intent.getStringExtra("error_id");
        }
    }

    private void getData(){
        AppApi.getErrorDetail(this,pageNum+"",error_id,pageSize,this);

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
        info = (TextView) findViewById(R.id.info);
        time = (TextView) findViewById(R.id.time);
        back = (RelativeLayout) findViewById(R.id.back);
        mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.listview);
    }

    @Override
    public void setViews() {

//        if (item != null) {
//            info.setText(item.getInfo());
//            time.setText(item.getDate());
//            error_id = item.getId();
//        }
        mAdapter = new ErrorReportInfoAdapter(context);
        mPullRefreshListView.setAdapter(mAdapter);
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        mPullRefreshListView.onRefreshComplete();
        switch (method) {
            case POST_ERROR_REPORT_DETAIL_JSON:
                if(obj instanceof ErrorDetail) {
                    errorDetail= (ErrorDetail) obj;
                    if (errorDetail != null) {
                        List<ErrorDetailBean> mList =  errorDetail.getList();
                        handleVodList(mList);
                    }
                }
                break;



        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method,obj);
        mPullRefreshListView.onRefreshComplete();
        mPullRefreshListView.onLoadComplete(false,true);
        switch (method) {
            case POST_ERROR_REPORT_LIST_JSON:


//                if (obj instanceof ResponseErrorMessage){
//                    ResponseErrorMessage errorMessage = (ResponseErrorMessage)obj;
//                    String statusCode = String.valueOf(errorMessage.getCode());
//                    if (AppApi.ERROR_TIMEOUT.equals(statusCode)){
////                        mProgressLayout.loadFailure("数据加载超时");
////                        showRefreshHintAnimation("数据加载超时");
//                    }else if (AppApi.ERROR_NETWORK_FAILED.equals(statusCode)){
////                        mProgressLayout.loadFailure("网络异常，点击重试");
////                        showRefreshHintAnimation("无法连接到网络,请检查网络设置");
//                    }
//                }


                break;




        }
    }
    @Override
    public void setListeners() {
        back.setOnClickListener(this);
        mPullRefreshListView.setOnRefreshListener(onRefreshListener);
        mPullRefreshListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
        mPullRefreshListView.setOnItemClickListener(itemClickListener);
        mPullRefreshListView.onLoadComplete(true,false);
    }


    private void handleVodList(List<ErrorDetailBean> mList){

        if (mList != null && mList.size() > 0) {
            pageNum++;
            if (isUp) {
                info.setText(errorDetail.getInfo());
                time.setText(errorDetail.getDate());
                list.clear();
                mAdapter.clear();
                mPullRefreshListView.onLoadComplete(true,false);

            }else {
                mPullRefreshListView.onLoadComplete(true,false);
            }
            detail_id = mList.get(mList.size()-1).getDetail_id();
            list.addAll(mList);
            mAdapter.setData(list);
            int haveNext = 0;
            haveNext =  errorDetail.getIsNextPage();

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

    PullToRefreshBase.OnRefreshListener onRefreshListener = new PullToRefreshBase.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            detail_id = "0";
            pageNum = 1;
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

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ErrorDetailBean item = (ErrorDetailBean)parent.getItemAtPosition(position);
            Hotel hotel = new Hotel();
            hotel.setId(item.getHotel_id());
            hotel.setName(item.getHotel_name());
            if (item!=null){
                Intent intent = new Intent();
                intent.putExtra("hotel",hotel);
                intent.setClass(AbnormalityInfoActivity.this,HotelPositionInfoAcitivty.class);
                startActivity(intent);
            }
        }
    };
}
