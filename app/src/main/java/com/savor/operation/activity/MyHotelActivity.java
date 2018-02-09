package com.savor.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.ErrorReportInfoAdapter;
import com.savor.operation.adapter.MyHotelAdapter;
import com.savor.operation.bean.City;
import com.savor.operation.bean.ErrorDetail;
import com.savor.operation.bean.ErrorDetailBean;
import com.savor.operation.bean.ErrorReportBean;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.HotelHeart;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.MyHotel;
import com.savor.operation.bean.MyHotelBean;
import com.savor.operation.bean.MytaskHotel;
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
    private PullToRefreshListView mPullRefreshListView;
    private MyHotelAdapter mAdapter;
    private int pageSize = 15;
    private int pageNum = 1;
    private boolean isUp = true;
    private ErrorDetail errorDetail;
    private String publish_user_id;
    private PubUser pubUser;
    private PubUserBean currentPubUser;
    public static final int RESULT_CODE_USER = 500;
    public static final int REQUEST_CODE_USER = 501;
    private TextView tv_name;
    private MyHotel myHotel;
    private MytaskHotel list;
    private List<MyHotelBean> hotel = new ArrayList<MyHotelBean>();
    private TextView tv_hotel_count;
    private TextView tv_hotel_normal;
    private TextView tv_hotel_freeze;

    private TextView tv_normal_box;
    private TextView tv_exe_box;
    private TextView tv_black_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hotel);
        context = this;
        //getIntentData();
        getViews();
        setViews();
        setListeners();
        getUserData();
        getMytaskHotel();
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
    private void getMytaskHotel(){
        AppApi.getMytaskHotel(this,pageNum+"",pageSize+"",publish_user_id,this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_name:
                intent = new Intent(this,PubUserListActivity.class);
                intent.putExtra("PubUser",pubUser);
                startActivityForResult(intent,REQUEST_CODE_USER);
                break;


        }
    }

    @Override
    public void getViews() {
        info = (TextView) findViewById(R.id.info);
        back = (RelativeLayout) findViewById(R.id.back);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_hotel_count = (TextView) findViewById(R.id.tv_hotel_count);
        tv_hotel_normal = (TextView) findViewById(R.id.tv_hotel_normal);
        tv_hotel_freeze = (TextView) findViewById(R.id.tv_hotel_freeze);
        tv_normal_box = (TextView) findViewById(R.id.tv_normal_box);
        tv_exe_box = (TextView) findViewById(R.id.tv_exe_box);
        tv_black_list = (TextView) findViewById(R.id.tv_black_list);
        mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.listview);
    }

    @Override
    public void setViews() {
        LoginResponse loginResponse = mSession.getLoginResponse();
        if (loginResponse != null) {
            publish_user_id = loginResponse.getUserid();
            //publish_user_id = "147";
            tv_name.setText(loginResponse.getUsername());
        }

        mAdapter = new MyHotelAdapter(context);
        mPullRefreshListView.setAdapter(mAdapter);
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        mPullRefreshListView.onRefreshComplete();
        switch (method) {
            case POST_MY_HOTEL_JSON:
                if(obj instanceof MyHotel) {
                    myHotel= (MyHotel) obj;
                    if (myHotel != null) {
                        list = myHotel.getList();
                        if (list != null) {
                            handleVodList(list);
                        }
                        //List<ErrorDetailBean> mList =  errorDetail.getList();

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

    private void initHeader(HotelHeart heart){
        if (heart != null) {
            int hotel_all_nums = heart.getHotel_all_nums();
            int hotel_all_normal_nums = heart.getHotel_all_normal_nums();
            int hotel_all_freeze_nums = heart.getHotel_all_freeze_nums();
            int box_normal_num = heart.getBox_normal_num();
            int box_not_normal_num = heart.getBox_not_normal_num();
            int black_box_num = heart.getBlack_box_num();
            tv_hotel_count.setText(""+hotel_all_nums);
            tv_hotel_normal.setText("正常"+hotel_all_normal_nums);
            tv_hotel_freeze.setText("冻结"+hotel_all_freeze_nums);
            tv_normal_box.setText("正常"+box_normal_num);
            tv_exe_box.setText("异常"+box_not_normal_num);
            tv_black_list.setText("黑名单"+black_box_num);

           String remark;
        }
    }

    private void handleVodList(MytaskHotel list){
        List<MyHotelBean> mList = list.getHotel();
        initHeader(list.getHeart());
        if (mList != null && mList.size() > 0) {
            pageNum++;
            if (isUp) {


                hotel.clear();
                mAdapter.clear();
                mPullRefreshListView.onLoadComplete(true,false);

            }else {
                mPullRefreshListView.onLoadComplete(true,false);
            }
            hotel.addAll(mList);
            mAdapter.setData(hotel);
            int haveNext = 0;
            haveNext =  list.getIsNextPage();

            if (haveNext==0) {
                mPullRefreshListView.onLoadComplete(false,false);
            }else {
                mPullRefreshListView.onLoadComplete(true,false);
            }
        }else {
            if (hotel != null && hotel.size()>0  ) {
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
            getMytaskHotel();
            // istop = true;
            //getData();
        }
    };

    PullToRefreshBase.OnLastItemVisibleListener onLastItemVisibleListener = new PullToRefreshBase.OnLastItemVisibleListener() {
        @Override
        public void onLastItemVisible() {
            isUp = false;
            getMytaskHotel();
            // istop = false;
           // getData();
        }
    };

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            MyHotelBean item = (MyHotelBean)parent.getItemAtPosition(position);
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
       if(resultCode == RESULT_CODE_USER) {
            currentPubUser = mSession.getPubUserBean();
            publish_user_id = currentPubUser.getPublish_user_id();
            tv_name.setText(currentPubUser.getRemark());
            pageNum = 1;
            isUp = true;
            getMytaskHotel();
       }
    }
}
