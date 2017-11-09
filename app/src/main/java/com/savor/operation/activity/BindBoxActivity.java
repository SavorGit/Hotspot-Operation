package com.savor.operation.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.ssdp.SSDPService;

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
    }

    @Override
    public void setViews() {
        mCenterTv.setText("绑定包间版位");
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);

    }

    @Override
    public void onSSDPReceivedListener(String address, String boxAddress, int hotelId, int roomId,String boxMac) {
        ShowMessage.showToast(this,"机顶盒mac："+boxMac);
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
}
