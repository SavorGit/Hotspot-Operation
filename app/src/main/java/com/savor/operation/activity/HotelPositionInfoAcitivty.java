package com.savor.operation.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.savor.operation.R;
import com.savor.operation.adapter.HotelPositionAdapter;
import com.savor.operation.bean.Hotel;

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
    private View mHeaderView;

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
        mHeaderView = ImageView.inflate(this, R.layout.header_view_positionlayout,null);
    }

    @Override
    public void setViews() {
        mHotelPositionAdapter = new HotelPositionAdapter(this);
        mPostionListView.setAdapter(mHotelPositionAdapter);
        mPostionListView.addHeaderView(mHeaderView);

        if(mHotel!=null) {
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText(mHotel.getName());
            mTitleTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            mTitleTv.getPaint().setAntiAlias(true);//抗锯齿
        }

        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setBackgroundResource(R.drawable.bg_edittext);
        mRightTv.setTextColor(getResources().getColor(R.color.black));
        mRightTv.setText(getString(R.string.refresh));

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRightTv.getLayoutParams();
        layoutParams.height = DensityUtil.dip2px(this,35);
        layoutParams.setMargins(0,0,DensityUtil.dip2px(this,15),0);
        mRightTv.setPadding(DensityUtil.dip2px(this,10),0,DensityUtil.dip2px(this,10),0);
    }

    @Override
    public void setListeners() {

    }
}
