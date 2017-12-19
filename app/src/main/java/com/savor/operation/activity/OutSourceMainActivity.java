package com.savor.operation.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.bean.FixBean;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.core.AppApi;
import com.savor.operation.utils.ActivitiesManager;
import com.savor.operation.widget.CommonDialog;

/**
 * 首页
 */
public class OutSourceMainActivity extends BaseActivity implements View.OnClickListener {

    private static final int NOTIFY_DOWNLOAD_FILE = 10001;
    private static final int UPDATE_LOCATION = 10002;
    private long exitTime;
    private TextView mSearchTv;
    private FixBean fixBean;
    private TextView mUserInfo;
    private NotificationManager manager;
    private int msg;
    private Notification notif;
    private TextView mLocationTv;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_LOCATION:
                    String currentLocation = mSession.getCurrentLocation();
                    if(!TextUtils.isEmpty(currentLocation)) {
                        mLocationTv.setText("当前位置："+currentLocation);
                    }else {
                        mLocationTv.setText("当前位置：获取失败");
                    }
                    startLocationTimer();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outsource_main);

        getViews();
        setViews();
        setListeners();
        startLocationTimer();
    }

    private void startLocationTimer() {
        mHandler.sendEmptyMessageDelayed(UPDATE_LOCATION,1000*10);
    }

    @Override
    public void getViews() {
        mSearchTv = (TextView) findViewById(R.id.tv_search);
        mLocationTv = (TextView) findViewById(R.id.tv_location);

        mUserInfo = (TextView) findViewById(R.id.tv_user);
    }

    @Override
    public void setViews() {
        LoginResponse loginResponse = mSession.getLoginResponse();
        if(loginResponse!=null) {
            String nickname = loginResponse.getNickname();
            mUserInfo.setText("当前登录用户："+nickname);
        }

        String currentLocation = mSession.getCurrentLocation();
        if(!TextUtils.isEmpty(currentLocation)) {
            mLocationTv.setText("当前位置："+currentLocation);
        }
    }

    @Override
    public void setListeners() {
        mSearchTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                new CommonDialog(this, "是否退出？", new CommonDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        mSession.setLoginResponse(null);
                        Intent intent = new Intent(OutSourceMainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }, new CommonDialog.OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                },"确定").show();
                break;
            case R.id.tv_search:
                Intent intent = new Intent(this,SearchHotelActivity.class);
                startActivityForResult(intent,SingleHotelPositionInfoAcitivty.REQUEST_CODE_SELECT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == SingleHotelPositionInfoAcitivty.RESULT_CODE_SELECT) {
            if(data!=null) {
                fixBean = (FixBean) data.getSerializableExtra("bean");
            }
        }
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(UPDATE_LOCATION);
        mHandler.removeCallbacksAndMessages(null);
    }
}
