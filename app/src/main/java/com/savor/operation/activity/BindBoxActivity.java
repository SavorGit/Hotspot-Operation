package com.savor.operation.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.adapter.BindBoxAdapter;
import com.savor.operation.bean.BindBoxList;
import com.savor.operation.bean.BindBoxListBean;
import com.savor.operation.bean.BindBoxResponse;
import com.savor.operation.core.AppApi;
import com.savor.operation.receiver.NetworkConnectChangedReceiver;
import com.savor.operation.ssdp.SSDPService;
import com.savor.operation.utils.WifiUtil;
import com.savor.operation.widget.CommonDialog;

import java.util.List;

/**
 * 绑定版位
 * @author hezd
 */
public class BindBoxActivity extends BaseActivity implements SSDPService.OnSSDPReceivedListener, View.OnClickListener, BindBoxAdapter.OnBindBtnClickListener {

    private static final int MSG_CHECK_SSDP = 100;
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
    private int mHotelId;
    private int mRoomId;
    private String mBoxMAc;
    private RelativeLayout mLoadFailedLayout;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WifiManager.WIFI_STATE_ENABLED:
                    String wifiName = WifiUtil.getWifiName(BindBoxActivity.this);
                    mWifiTv.setText("当前Wifi："+wifiName);
                    mLoadFailedLayout.setVisibility(View.GONE);
                    initSSDP();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    stopSSDPService();
                    reset();
                    break;
                case MSG_CHECK_SSDP:
                    if(mRoomId == 0||mHotelId==0 || TextUtils.isEmpty(mBoxMAc)) {
                        showLoadFailedLayout();
                        stopSSDPService();
                    }
                    break;
            }
        }
    };
    private BindBoxListBean bindBoxListBean;

    private void reset() {
        mHoltelTv.setText("当前酒楼：");
        mWifiTv.setText("当前Wifi：");
        mBoxMacTv.setText("当前机顶盒Mac：");
        if(boxList!=null) {
            List<BindBoxListBean> data = mBindAdapter.getData();
            data.clear();
            mBindAdapter.notifyDataSetChanged();
        }
    }

    private NetworkConnectChangedReceiver mChangedReceiver;

    private void showLoadFailedLayout() {
        mLoadingPb.setVisibility(View.GONE);
        mLoadFailedLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_box);

        getViews();
        setViews();
        setListeners();

    }

    private void initSSDP() {
//        if(WifiUtil.checkWifiState(this)) {
            mLoadingPb.setVisibility(View.VISIBLE);
            bindSSDPService();
            checkSSDPDelayed();
//        }else {
//            ShowMessage.showToast(this,"请连接wifi后继续操作");
//        }
    }

    private void checkSSDPDelayed() {
        mHandler.sendEmptyMessageDelayed(MSG_CHECK_SSDP,10*1000);
    }

    private void bindSSDPService() {
        mSSDPBindIntent = new Intent(this,SSDPService.class);
        mSSDPBindIntent.setAction("com.savor.operation.ssdp.action.SERVICE");
        bindService(mSSDPBindIntent,mConn,BIND_AUTO_CREATE);
        startService(mSSDPBindIntent);
    }

    public void registerNetWorkReceiver() {
        if(mChangedReceiver==null)
            mChangedReceiver = new NetworkConnectChangedReceiver(mHandler);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver(mChangedReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reset();
        registerNetWorkReceiver();
        if(!WifiUtil.checkWifiState(this)) {
            ShowMessage.showToast(this,"请连接酒楼Wifi后继续操作");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mChangedReceiver!=null) {
            unregisterReceiver(mChangedReceiver);
        }
        stopSSDPService();
    }

    @Override
    public void getViews() {
        mLoadFailedLayout = (RelativeLayout) findViewById(R.id.rl_load_failed);
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

        mBindAdapter.setOnBindBtnClickListener(this);
    }

    @Override
    public void onSSDPReceivedListener(String address, String boxAddress, final int hotelId, final int roomId, final String boxMac) {
        this.mHotelId = hotelId;
        this.mRoomId = roomId;
        this.mBoxMAc = boxMac;

        mHandler.removeMessages(MSG_CHECK_SSDP);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mLoadFailedLayout.setVisibility(View.GONE);
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
        stopSSDPService();
    }

    private void stopSSDPService() {
        try {
            if(mConn!=null) {
                unbindService(mConn);
            }
            if(mSSDPBindIntent!=null) {
                stopService(mSSDPBindIntent);
                mSSDPBindIntent = null;
            }
        }catch (Exception e){}

        mRoomId = 0;
        mHotelId = 0;
        mBoxMAc = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                if(!WifiUtil.checkWifiState(this)) {
                    ShowMessage.showToast(this,"请连接酒楼Wifi后继续操作");
                    return;
                }
                mLoadFailedLayout.setVisibility(View.GONE);
                stopSSDPService();
                reset();
                mLoadingPb.setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initSSDP();
                    }
                },1000);
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_BIND_BOX_JSON:
                if(obj instanceof BindBoxResponse) {
                    BindBoxResponse bindBoxResponse = (BindBoxResponse) obj;
                    int type = bindBoxResponse.getType();
                    if("1".equals(type)) {
                        ShowMessage.showToast(this,"绑定成功");
                        AppApi.bindBox(this,String.valueOf(mHotelId),bindBoxListBean.getBox_id(),bindBoxListBean.getBox_mac(),String.valueOf(mRoomId),this);
                    }else if("2".equals(type)) {
                        String err_msg = bindBoxResponse.getErr_msg();
                        ShowMessage.showToast(this,err_msg);
                    }
                }
                break;
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

    @Override
    public void onBindBtnClick(final BindBoxListBean bindBoxListBean) {
        new CommonDialog(this, "是否需要绑定？", new CommonDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {
                BindBoxActivity.this.bindBoxListBean = bindBoxListBean;
                AppApi.bindBox(BindBoxActivity.this,String.valueOf(mHotelId),bindBoxListBean.getBox_id(),bindBoxListBean.getBox_mac(),String.valueOf(mRoomId),BindBoxActivity.this);
            }
        }, new CommonDialog.OnCancelListener() {
            @Override
            public void onCancel() {

            }
        },"确定").show();

    }


}
