package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.PositionDetailAdapter;
import com.savor.operation.bean.Hotel;

/**
 * 酒店版位详细信息
 */
public class HotelDetailInfoActivity extends BaseActivity {

    private Hotel mHotel;
    private View headerView;
    private TextView mHotelNameTv;
    private TextView mHotelAddressTv;
    private TextView mHotelBelongsTv;
    private TextView mIfImportantTv;
    private TextView mHotelLevelTv;
    private TextView mChangeDescTv;
    private TextView mInstallDateTv;
    private TextView mHotelStateTv;
    private TextView mHotelContantTv;
    private TextView mBoxTypeTv;
    private TextView mPreservePersonTv;
    private TextView mSpAddressTv;
    private TextView mSpMacTv;
    private TextView mPlaneTv;
    private TextView mTelePhoneTv;
    private TextView mOperationPersonTv;
    private TextView mHotelLocationTv;
    private TextView mHotelWifiNameTv;
    private TextView mProgramTv;
    private TextView mPositionInfoTv;
    private PullToRefreshListView mRefreshListView;
    private PositionDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail_info);

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
        mRefreshListView = (PullToRefreshListView) findViewById(R.id.wl_listview );

        headerView = View.inflate(this, R.layout.header_view_hotel_detail,null);
        mHotelNameTv = (TextView) headerView.findViewById(R.id.tv_hotel_name);
        mHotelAddressTv = (TextView) headerView.findViewById(R.id.tv_hotel_address);
        mHotelBelongsTv = (TextView) headerView.findViewById(R.id.tv_hotel_belongs);
        mIfImportantTv = (TextView) headerView.findViewById(R.id.tv_if_important);
        mHotelLevelTv = (TextView) headerView.findViewById(R.id.tv_hotel_level);
        mChangeDescTv = (TextView) headerView.findViewById(R.id.tv_change_desc);
        mInstallDateTv = (TextView) headerView.findViewById(R.id.tv_install_date);
        mHotelStateTv = (TextView) headerView.findViewById(R.id.tv_hotel_state);
        mHotelContantTv = (TextView) headerView.findViewById(R.id.tv_hotel_contant);
        mBoxTypeTv = (TextView) headerView.findViewById(R.id.tv_box_type);
        mPreservePersonTv = (TextView) headerView.findViewById(R.id.tv_preserve_person);
        mSpAddressTv = (TextView) headerView.findViewById(R.id.tv_sp_address);
        mSpMacTv = (TextView) headerView.findViewById(R.id.tv_sp_mac);
        mPlaneTv = (TextView) headerView.findViewById(R.id.tv_plane);
        mTelePhoneTv = (TextView) headerView.findViewById(R.id.tv_telephone);
        mOperationPersonTv = (TextView) headerView.findViewById(R.id.tv_operation_persion);
        mHotelLocationTv = (TextView) headerView.findViewById(R.id.tv_hotel_location);
        mHotelWifiNameTv = (TextView) headerView.findViewById(R.id.tv_hotel_wifiname);
        mProgramTv = (TextView) headerView.findViewById(R.id.tv_program);
        mPositionInfoTv = (TextView) headerView.findViewById(R.id.position_info);
    }

    @Override
    public void setViews() {

        mAdapter = new PositionDetailAdapter(this);
        mRefreshListView.setAdapter(mAdapter);
        mRefreshListView.getRefreshableView().addHeaderView(headerView);
    }

    @Override
    public void setListeners() {

    }
}
