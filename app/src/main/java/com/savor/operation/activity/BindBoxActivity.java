package com.savor.operation.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.AppUtils;
import com.common.api.utils.DensityUtil;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.adapter.BindBoxAdapter;
import com.savor.operation.bean.BindBoxList;
import com.savor.operation.bean.BindBoxListBean;
import com.savor.operation.core.AppApi;
import com.savor.operation.ssdp.SSDPService;
import com.savor.operation.utils.WifiUtil;

import java.util.List;

public class BindBoxActivity extends BaseActivity implements SSDPService.OnSSDPReceivedListener, View.OnClickListener {

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SSDPService ssdpService = ((SSDPService.SSDPBinder) service).getService();
            ssdpService.setOnSSDPReceivedListener(BindBoxActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Intent mSSDPBindIntent;
    private ImageView mBackBtn;
    private TextView mCenterTv;
    private TextView mRightTv;
    private ProgressBar mLoadingPb;
    private ListView mBindBoxListView;
    private TextView mHoltelTv;
    private TextView mWifiTv;
    private TextView mBoxMacTv;
    private ListView mBindBoxRlv;
    private View heaerView;
    private BindBoxAdapter mBindAdapter;
    private BindBoxList boxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_box);

        getViews();
        setViews();
        setListeners();

        bindSSDPService();
    }

    private void bindSSDPService() {
        mSSDPBindIntent = new Intent(this,SSDPService.class);
        mSSDPBindIntent.setAction("com.savor.operation.ssdp.action.SERVICE");
        bindService(mSSDPBindIntent,mConn,BIND_AUTO_CREATE);
        startService(mSSDPBindIntent);
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mCenterTv = (TextView) findViewById(R.id.tv_center);
        mRightTv = (TextView) findViewById(R.id.tv_right);

        mLoadingPb = (ProgressBar) findViewById(R.id.pb_loading);
        mBindBoxListView = (ListView) findViewById(R.id.lv_bind_box);

        initHeaderView();
    }

    private void initHeaderView() {
        heaerView = View.inflate(this, R.layout.header_view_bind_box,null);
        mHoltelTv = (TextView) heaerView.findViewById(R.id.tv_hotel);
        mWifiTv = (TextView) heaerView.findViewById(R.id.tv_wifi);
        mBoxMacTv = (TextView) heaerView.findViewById(R.id.tv_box_mac);

        mBindBoxRlv = (ListView) findViewById(R.id.lv_bind_box);
    }

    @Override
    public void setViews() {
        mCenterTv.setText("绑定包间版位");

        mLoadingPb.setVisibility(View.VISIBLE);

        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setBackgroundResource(R.drawable.bg_edittext);
        mRightTv.setText("刷新");
        int padding10 = DensityUtil.dip2px(this, 10);
        mRightTv.setPadding(padding10,0,padding10,0);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRightTv.getLayoutParams();
        layoutParams.setMargins(padding10,padding10,padding10,padding10);

        mBindBoxListView.addHeaderView(heaerView);

        mBindAdapter = new BindBoxAdapter(this);
        mBindBoxListView.setAdapter(mBindAdapter);
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);

        mRightTv.setOnClickListener(this);
    }

    @Override
    public void onSSDPReceivedListener(String address, String boxAddress, final int hotelId, final int roomId, final String boxMac) {
        mBindBoxListView.post(new Runnable() {
            @Override
            public void run() {
                mBoxMacTv.setText("当前机顶盒MAC："+boxMac);
                String wifiName = WifiUtil.getWifiName(BindBoxActivity.this);
                mWifiTv.setText("当前Wifi："+wifiName);
                AppApi.getRoomBoxList(BindBoxActivity.this,String.valueOf(hotelId),String.valueOf(roomId),BindBoxActivity.this);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
        stopService(mSSDPBindIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_ROOM_BOX_JSON:
                if(obj instanceof BindBoxList) {
                    boxList = (BindBoxList) obj;
                    if(boxList!=null) {
                        String hotel_name = boxList.getHotel_name();
                        mHoltelTv.setText("当前酒楼："+hotel_name);
                    }
                    List<BindBoxListBean> list = boxList.getList();
                    mBindAdapter.setData(list);
                    mLoadingPb.setVisibility(View.GONE);
                }
                break;
        }
    }
}
