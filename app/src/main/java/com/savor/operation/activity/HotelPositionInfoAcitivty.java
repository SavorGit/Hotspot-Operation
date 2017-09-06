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
import com.savor.operation.bean.FixHistoryResponse;
import com.savor.operation.bean.Hotel;
import com.savor.operation.core.AppApi;

import java.util.List;

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
    private TextView mSpVersionTv;
    private TextView mLastSpVersionTv;
    private TextView mLastXintiao;
    private ImageView mSpState;
    private ImageView mLastXintiaoIV;
    private TextView mPositionDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_info_acitivty);

        handleIntent();
        getViews();
        setViews();
        setListeners();

        getData();
    }

    private void getData() {
        AppApi.getFixHistory(this,mHotel.getId(),this);
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

        mSpVersionTv = (TextView) mHeaderView.findViewById(R.id.tv_sp_version);
        mLastSpVersionTv = (TextView) mHeaderView.findViewById(R.id.tv_last_sp_version);
        mLastXintiao = (TextView) mHeaderView.findViewById(R.id.tv_last_xintiao);
        mSpState = (ImageView) mHeaderView.findViewById(R.id.iv_sp_state);
        mLastXintiaoIV = (ImageView) mHeaderView.findViewById(R.id.iv_last_xintiao);
        mPositionDesc = (TextView) mHeaderView.findViewById(R.id.tv_position_desc);
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

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_FIX_HISTORY_JSON:
                if(obj instanceof FixHistoryResponse) {
                    FixHistoryResponse fixHistoryResponse = (FixHistoryResponse) obj;
                    initSmallPlatfromInfo(fixHistoryResponse);
                }
                break;
        }
    }

    private void initSmallPlatfromInfo(FixHistoryResponse fixHistoryResponse) {
        FixHistoryResponse.ListBean list = fixHistoryResponse.getList();
        if (list != null) {
            FixHistoryResponse.ListBean.VersionBean version = list.getVersion();
            if (version != null) {
                String new_small = version.getNew_small();
                mSpVersionTv.setText("发布小平台版本号：" + new_small);

                FixHistoryResponse.ListBean.VersionBean.LastSmallBean last_small = version.getLast_small();
                int last_small_state = last_small.getLast_small_state();
                String last_small_pla = last_small.getLast_small_pla();
                if (last_small_state == 1) {
                    mSpState.setImageResource(R.drawable.cirlce_green);
                } else {
                    mSpState.setImageResource(R.drawable.cirlce_red);
                }
                mLastSpVersionTv.setText("最后小平台版本号：" + last_small_pla);

                FixHistoryResponse.ListBean.VersionBean.LastHeartTimeBean last_heart_time = version.getLast_heart_time();
                int lstate = last_heart_time.getLstate();
                String ltime = last_heart_time.getLtime();
                mLastXintiao.setText("最后小平台心跳时间：" + ltime);
                if (lstate == 1) {
                    mLastXintiaoIV.setImageResource(R.drawable.cirlce_green);
                } else {
                    mLastXintiaoIV.setImageResource(R.drawable.cirlce_red);
                }
            }

            String banwei = list.getBanwei();
            mPositionDesc.setText(banwei);
            List<FixHistoryResponse.ListBean.BoxInfoBean> box_info = list.getBox_info();
            mHotelPositionAdapter.setData(box_info);
        }
    }
}
