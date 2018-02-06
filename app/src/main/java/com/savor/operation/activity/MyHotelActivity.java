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
import com.savor.operation.bean.City;
import com.savor.operation.bean.ErrorDetail;
import com.savor.operation.bean.ErrorDetailBean;
import com.savor.operation.bean.ErrorReportBean;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.PubUser;
import com.savor.operation.bean.PubUserBean;
import com.savor.operation.bean.SkillList;
import com.savor.operation.core.AppApi;

import java.util.ArrayList;
import java.util.List;

import static com.savor.operation.activity.CityListActivity.REQUEST_CODE_CITY;
import static com.savor.operation.activity.CityListActivity.RESULT_CODE_CITY;

/**
 * Created by bushlee on 2017/9/4.
 */

public class MyHotelActivity extends BaseActivity implements View.OnClickListener{
    private Context context;
    private RelativeLayout back;
    private ErrorReportBean item;
    private TextView info;
    private TextView time;
    private PullToRefreshListView mPullRefreshListView;
    private ErrorReportInfoAdapter mAdapter;
    private int pageSize = 15;
    private int pageNum = 1;
    private boolean isUp = true;
    private List<ErrorDetailBean> list = new ArrayList<ErrorDetailBean>();
    private ErrorDetail errorDetail;
    private String publish_user_id;
    private PubUser pubUser;
    public static final int RESULT_CODE_USER = 500;
    public static final int REQUEST_CODE_USER = 501;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormality_info);
        context = this;
        //getIntentData();
        getViews();
        setViews();
        setListeners();
        getUserData();
    }

//    private void getIntentData(){
//        Intent intent = getIntent();
//        if (intent != null) {
//            error_id = intent.getStringExtra("error_id");
//        }
//    }

    private void getUserData(){
        AppApi.getPubUser(this,publish_user_id,this);

    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_name:
                intent = new Intent(this,CityListActivity.class);
                intent.putExtra("PubUser",pubUser);
                startActivityForResult(intent,REQUEST_CODE_USER);
                break;


        }
    }

    @Override
    public void getViews() {
        info = (TextView) findViewById(R.id.info);
        time = (TextView) findViewById(R.id.time);
        back = (RelativeLayout) findViewById(R.id.back);
        tv_name = (TextView) findViewById(R.id.tv_name);
        mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.listview);
    }

    @Override
    public void setViews() {
        LoginResponse loginResponse = mSession.getLoginResponse();
        if (loginResponse != null) {
            publish_user_id = loginResponse.getUserid();
            tv_name.setText(loginResponse.getUsername());
        }

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
            case POST_PUB_USER_JSON:
                if(obj instanceof PubUser) {
                    pubUser= (PubUser) obj;
                    if (pubUser != null) {
                        List<PubUserBean> list = pubUser.getList();
                        if (list != null && list.size()>0) {
                            
                        }
//                        List<ErrorDetailBean> mList =  errorDetail.getList();
//                        handleVodList(mList);
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

                break;




        }
    }
    @Override
    public void setListeners() {
        back.setOnClickListener(this);
        tv_name.setOnClickListener(this);
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

            pageNum = 1;
            isUp = true;
            // istop = true;
            //getData();
        }
    };

    PullToRefreshBase.OnLastItemVisibleListener onLastItemVisibleListener = new PullToRefreshBase.OnLastItemVisibleListener() {
        @Override
        public void onLastItemVisible() {
            isUp = false;
            // istop = false;
           // getData();
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
                intent.setClass(MyHotelActivity.this,HotelPositionInfoAcitivty.class);
                startActivity(intent);
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_CODE_USER&&resultCode == REQUEST_CODE_USER) {


        }
    }
}
