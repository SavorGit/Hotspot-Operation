package com.savor.operation;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.common.api.utils.AppUtils;
import com.common.api.utils.LogUtils;
import com.savor.operation.activity.MainActivity;
import com.savor.operation.core.Session;
import com.savor.operation.utils.ActivitiesManager;
import com.savor.operation.utils.RecordUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.io.File;
import java.util.Map;

/**
 * Created by hezd on 2016/12/13.
 */

public class SavorApplication extends MultiDexApplication {

    private static SavorApplication mInstance;


    public static SavorApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 设置异常捕获处理类
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        Session.get(this);
        mInstance = this;
        MobclickAgent.openActivityDurationTrack(false);

        initUmengPush();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initUmengPush() {
        LogUtils.d("savor:push initUmengPush");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        LogUtils.d("savor:push deviceToken="+mPushAgent.getRegistrationId());
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtils.d("savor:push deviceToken="+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.d("savor:push register failed ,message="+s);
            }
        });

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                // 点击收到推送，友盟埋点
                RecordUtils.onEvent(getApplicationContext(),R.string.click_notification);
                Map<String,String> custom = msg.extra;
                boolean isRunning = ActivitiesManager.getInstance().contains(MainActivity.class);
                String type = custom.get("type");
                String params = custom.get("params");


            }
        };

        UmengMessageHandler messageHandler = new UmengMessageHandler(){
            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                super.dealWithNotificationMessage(context, uMessage);
                // 收到推送
                RecordUtils.onEvent(getApplicationContext(),R.string.receive_notification);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        mPushAgent.setDebugMode(false);
    }

}
