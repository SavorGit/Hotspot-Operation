package com.savor.operation;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.common.api.utils.AppUtils;
import com.common.api.utils.LogUtils;
import com.google.gson.Gson;
import com.savor.operation.activity.AbnormalityInfoActivity;
import com.savor.operation.activity.LoginActivity;
import com.savor.operation.activity.SavorMainActivity;
import com.savor.operation.activity.SeekTaskDetailActivity;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.PushErrorBean;
import com.savor.operation.bean.PushTaskBean;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.Session;
import com.savor.operation.utils.ActivitiesManager;
import com.savor.operation.utils.LocationService;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.io.File;
import java.util.Map;

/**
 * 全局application
 * Created by hezd on 2016/12/13.
 */

public class SavorApplication extends Application implements ApiRequestListener {

    private static SavorApplication mInstance;
    public String imagePath;
    public LocationService locationService;

    public static SavorApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Debug.startMethodTracing("operation");

        // 设置异常捕获处理类
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        Session.get(this);
        mInstance = this;

        //initUmengPush();
        initCacheFile();
        locationService = new LocationService(getApplicationContext());
//        Debug.stopMethodTracing();
    }

//    private void initUmengPush() {
//        LogUtils.d("savor:push initUmengPush");
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        LogUtils.d("savor:push deviceToken="+mPushAgent.getRegistrationId());
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token
//                LogUtils.d("savor:push deviceToken="+deviceToken);
//                Session session =  Session.get(getApplicationContext());
//                LoginResponse loginResponse = session.getLoginResponse();
//                if(!TextUtils.isEmpty(deviceToken)&&loginResponse!=null&&!TextUtils.isEmpty(loginResponse.getUserid())) {
//                    AppApi.uploadDeviceToken(SavorApplication.this,deviceToken,loginResponse.getUserid(),SavorApplication.this);
//                }
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                LogUtils.d("savor:push register failed ,message="+s);
//            }
//        });
//
//        /*
//         *
//         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
//         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
//         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
//         * */
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
//                // 点击收到推送，友盟埋点
//                Map<String,String> custom = msg.extra;
//                String type = custom.get("type");
//                String params = custom.get("params");
//                if("1".equals(type)) {
//                    PushErrorBean pushErrorBean = new Gson().fromJson(params, PushErrorBean.class);
//                    Session session = Session.get(context);
//                    LoginResponse loginResponse = session.getLoginResponse();
//                    boolean isRunning = ActivitiesManager.getInstance().contains(SavorMainActivity.class);
//                    if(loginResponse!=null) {
//                        if(!isRunning) {
//                            Intent intent = new Intent(context, SavorMainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                        Intent intent = new Intent(context, AbnormalityInfoActivity.class);
//                        intent.putExtra("error_id",pushErrorBean.getError_id());
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }else {
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
//                }else if("2".equals(type)) {
//                    PushTaskBean pushTaskBean = new Gson().fromJson(params, PushTaskBean.class);
//                    Session session = Session.get(context);
//                    LoginResponse loginResponse = session.getLoginResponse();
//                    boolean isRunning = ActivitiesManager.getInstance().contains(SavorMainActivity.class);
//                    if(loginResponse!=null) {
//                        if(!isRunning) {
//                            Intent intent = new Intent(context, SavorMainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                        Intent intent = new Intent(context, SeekTaskDetailActivity.class);
//                        intent.putExtra("id",pushTaskBean.getTask_id());
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }else {
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
//                }
//
//
//            }
//        };
//
//        UmengMessageHandler messageHandler = new UmengMessageHandler(){
//            @Override
//            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
//                super.dealWithNotificationMessage(context, uMessage);
//            }
//        };
////        mPushAgent.setMessageHandler(messageHandler);
////        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
////        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
////        mPushAgent.setNotificationClickHandler(notificationClickHandler);
////        mPushAgent.setDebugMode(false);
//    }


    private void initCacheFile() {
        String cachePath = AppUtils.getSDCardPath()+"savor"+ File.separator;
        imagePath = cachePath+File.separator+"operations"+File.separator+"cache";
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_UPLOAD_DEVICETOKEN_JSON:

                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_UPLOAD_DEVICETOKEN_JSON:

                break;
        }
    }

    @Override
    public void onNetworkFailed(AppApi.Action method) {

    }
}