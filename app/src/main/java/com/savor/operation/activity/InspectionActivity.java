package com.savor.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.InspectionAdapter;
import com.savor.operation.adapter.MaintenanceRecordAdapter;
import com.savor.operation.adapter.SpinnerAdapter;
import com.savor.operation.bean.ErrorReport;
import com.savor.operation.bean.ErrorReportBean;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.MyInspect;
import com.savor.operation.bean.MyInspectResult;
import com.savor.operation.bean.RepairRecord;
import com.savor.operation.bean.RepairRecordList;
import com.savor.operation.bean.RepairRecordListBean;
import com.savor.operation.bean.UserBean;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**巡检酒楼
 * Created by bushlee on 2017/9/4.
 */

public class InspectionActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener {
    private Context context;
    //private Spinner spinner;
    private RelativeLayout back;
    private TextView title;
    private PullToRefreshListView mPullRefreshListView;
    private List<UserBean> userlist = new ArrayList<UserBean>();
    private UserBean currentUser;
   // private SpinnerAdapter spinnerAdapter;
    private InspectionAdapter mAdapter;
    private int page = 1;
    private String id = "0";
    private ErrorReport errorReport;
    private List<MyInspect> list = new ArrayList<MyInspect>();
    private boolean isUp = true;
    private String userid;
    private String pageSize = "15";

    private RepairRecord repairRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        context = this;
        getViews();
        setViews();
        setListeners();
        getMyInspect();
    }



    private void getMyInspect(){
        AppApi.getMyInspect(this,page+"",pageSize,userid,this);
    }
    @Override
    public void getViews() {
        LoginResponse loginResponse = mSession.getLoginResponse();
        userid = loginResponse.getUserid();
        currentUser = new UserBean();
        currentUser.setUserid(userid);
        currentUser.setUsername(loginResponse.getUsername());
        //spinner = (Spinner) findViewById(R.id.spinner);
        back = (RelativeLayout) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        mPullRefreshListView  = (PullToRefreshListView)findViewById(R.id.listview);
    }

    @Override
    public void setViews() {
        mAdapter = new InspectionAdapter(context);
        mPullRefreshListView.setAdapter(mAdapter);
    }

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
            case POST_INSPECTOR_JSON:
                if(obj instanceof MyInspectResult) {
                    MyInspectResult myInspectResult = (MyInspectResult) obj;
                    if (myInspectResult != null) {

                           //List<MyInspect> list = repairRecord.getList();
                           handleVodList(myInspectResult);



                    }
                }
                break;



        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {

        switch (method) {
            case POST_INSPECTOR_JSON:

                mPullRefreshListView.onRefreshComplete();
                mPullRefreshListView.onLoadComplete(false, true);
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


    private void handleVodList( MyInspectResult myInspectResult) {

        if (myInspectResult != null) {
            int count = myInspectResult.getCount();
            title.setText("我的巡检酒楼（"+count+"）");
            List<MyInspect> mList = myInspectResult.getList();
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
                mAdapter.setData(list);
                int isNextPage = 0;
                isNextPage = myInspectResult.getIsNextPage();

                if (isNextPage == 0) {
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


                mPullRefreshListView.onLoadComplete(false, false);
            }
        }


    }

    PullToRefreshBase.OnRefreshListener onRefreshListener = new PullToRefreshBase.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            page = 1;
            isUp = true;
            // istop = true;
            getMyInspect();
        }
    };

    PullToRefreshBase.OnLastItemVisibleListener onLastItemVisibleListener = new PullToRefreshBase.OnLastItemVisibleListener() {
        @Override
        public void onLastItemVisible() {
            isUp = false;
            // istop = false;
            getMyInspect();
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

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            MyInspect item = (MyInspect)parent.getItemAtPosition(position);
            if (item!=null){
                Hotel hotel = new Hotel();
                hotel.setId(item.getHotel_id());
                hotel.setName(item.getHotel_name());
                Intent intent = new Intent();
                intent.putExtra("hotel",hotel);
                intent.setClass(InspectionActivity.this,HotelPositionInfoAcitivty.class);
                startActivity(intent);
            }
        }
    };
}
