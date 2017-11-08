package com.savor.operation.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.savor.operation.R;
import com.savor.operation.ssdp.SSDPService;

public class BindBoxActivity extends BaseActivity implements SSDPService.OnSSDPReceivedListener {

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
        Intent intent = new Intent("com.savor.operation.ssdp.action.SERVICE");
        bindService(intent,mConn,BIND_AUTO_CREATE);
    }

    @Override
    public void getViews() {

    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onSSDPReceivedListener(String address, String boxAddress, int hotelId, int roomId) {
        
    }
}
