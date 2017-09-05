package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.adapter.HotelPositionAdapter;
import com.savor.operation.bean.Hotel;
import com.savor.operation.widget.DividerItemDecoration;

/**
 * 酒楼版位信息
 */
public class HotelPositionInfoAcitivty extends BaseActivity {

    private ListView mPostionListView;
    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mRightTv;
    private HotelPositionAdapter mHotelPositionAdapter;
    private Hotel mHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_info_acitivty);

        handleIntent();
        getViews();
        setViews();
        setListeners();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        mHotel = (Hotel) intent.getSerializableExtra("hotel");
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTitleTv = (TextView) findViewById(R.id.tv_center);
        mRightTv = (TextView) findViewById(R.id.tv_right);
        mPostionListView = (ListView) findViewById(R.id.lv_hotel_position_list);
    }

    @Override
    public void setViews() {
        mHotelPositionAdapter = new HotelPositionAdapter(this);
        mPostionListView.setAdapter(mHotelPositionAdapter);
    }

    @Override
    public void setListeners() {

    }
}
