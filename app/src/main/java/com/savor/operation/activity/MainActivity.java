package com.savor.operation.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.common.api.http.callback.FileDownProgress;
import com.common.api.utils.AppUtils;
import com.common.api.utils.FileUtils;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.adapter.IndexInfoAdapter;
import com.savor.operation.bean.IndexInfo;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.UpgradeInfo;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.utils.ActivitiesManager;
import com.savor.operation.utils.STIDUtil;
import com.savor.operation.widget.CommonDialog;
import com.savor.operation.widget.UpgradeDialog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mSearchTv;
    private TextView mFixHistoryTv;
    private TextView mExceptionReprotTv;
    private TextView mRemarkTv;
    private RecyclerView mInfoLv;
    private IndexInfoAdapter mAdapter;
    private TextView mVersionTv;
    private TextView mUserNmaeTv;
    private Button mExitBtn;
    private TextView mRefreshTv;
    private UpgradeInfo upGradeInfo;
    private UpgradeDialog mUpgradeDialog;
    private NotificationManager manager;
    private Notification notif;
    private final int NOTIFY_DOWNLOAD_FILE=10001;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();
        setViews();
        setListeners();
        getData();
        upgrade();
    }

    private void getData() {
        AppApi.getIndexInfo(this,this);
    }

    @Override
    public void getViews() {
        mRefreshTv = (TextView) findViewById(R.id.tv_refresh);
        mExitBtn = (Button) findViewById(R.id.btn_exit);
        mUserNmaeTv = (TextView) findViewById(R.id.tv_username);
        mVersionTv = (TextView) findViewById(R.id.tv_version);
        mInfoLv = (RecyclerView) findViewById(R.id.rlv_info);
        mRemarkTv = (TextView) findViewById(R.id.tv_reamrk);
        mSearchTv = (TextView) findViewById(R.id.tv_search);
        mFixHistoryTv = (TextView) findViewById(R.id.tv_fix_history);
        mExceptionReprotTv = (TextView) findViewById(R.id.tv_exception_report);
    }

    @Override
    public void setViews() {
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mInfoLv.setLayoutManager(manager);

        mAdapter = new IndexInfoAdapter(this);
        mInfoLv.setAdapter(mAdapter);
        mInfoLv.setItemAnimator(new DefaultItemAnimator());

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            mVersionTv.setText("运维端v"+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setListeners() {
        mRefreshTv.setOnClickListener(this);
        mExitBtn.setOnClickListener(this);
        mSearchTv.setOnClickListener(this);
        mFixHistoryTv.setOnClickListener(this);
        mExceptionReprotTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_refresh:
                ShowMessage.showToast(this,"刷新数据");
                getData();
                break;
            case R.id.btn_exit:
                new CommonDialog(this, "是否要注销当前登录账号", new CommonDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        mSession.setLoginResponse(null);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
                intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_fix_history:
                intent = new Intent(this,MaintenanceRecordActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_exception_report:
                intent = new Intent(this,AbnormalityReportActivity.class);
                startActivity(intent);
                break;
        }
    }

    int msg = 0;
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_INDEX_JSON:
                if(isFinishing())
                    return;
                if(obj instanceof IndexInfo) {
                    ShowMessage.showToast(this,"数据获取成功");
                    IndexInfo indexInfo = (IndexInfo) obj;
                    List<String> list = indexInfo.getList();
                    mAdapter.setData(list);
                    String remark = indexInfo.getRemark();
                    mRemarkTv.setText(remark);
                    LoginResponse loginResponse = mSession.getLoginResponse();
                    mUserNmaeTv.setText("登录账号："+loginResponse.getNickname());
                }
                break;
            case POST_UPGRADE_JSON:
                if(isFinishing())
                    return;
                if (obj instanceof UpgradeInfo) {
                    upGradeInfo = (UpgradeInfo) obj;
                    if (upGradeInfo != null) {
                        setUpGrade();
                    }
                }
                break;
            case TEST_DOWNLOAD_JSON:
                if(isFinishing())
                    return;
                if (obj instanceof FileDownProgress){
                    FileDownProgress fs = (FileDownProgress) obj;
                    long now = fs.getNow();
                    long total = fs.getTotal();
                    if ((int)(((float)now / (float)total)* 100)-msg>=5) {
                        msg = (int) (((float)now / (float)total)* 100);
                        notif.contentView.setTextViewText(R.id.content_view_text1,
                                (Integer) msg + "%");
                        notif.contentView.setProgressBar(R.id.content_view_progress,
                                100, (Integer) msg, false);
                        manager.notify(NOTIFY_DOWNLOAD_FILE, notif);
                    }

                }else if (obj instanceof File) {
                    mSession.setApkDownloading(false);
                    File f = (File) obj;
                    byte[] fRead;
                    String md5Value=null;
                    try {
                        fRead = FileUtils.readFileToByteArray(f);
                        md5Value= AppUtils.getMD5(fRead);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //比较本地文件版本是否与服务器文件一致，如果一致则启动安装
                    if (md5Value!=null&&md5Value.equals(upGradeInfo.getMd5())){
                        if (manager!=null){
                            manager.cancel(NOTIFY_DOWNLOAD_FILE);
                        }
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setDataAndType(Uri.parse("file://" + f.getAbsolutePath()),
                                "application/vnd.android.package-archive");
                        startActivity(i);
                    }else {
                        if (manager!=null){
                            manager.cancel(NOTIFY_DOWNLOAD_FILE);
                        }
                        ShowMessage.showToast(mContext,"下载失败");
                        setUpGrade();
                    }

                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ShowMessage.showToast(this,getString(R.string.confirm_exit_app));
                    exitTime = System.currentTimeMillis();
                } else {
                    exitApp();
                }
        }
        return true;
    }

    private void exitApp() {
        ActivitiesManager.getInstance().popAllActivities();
        Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {

        switch (method) {

            case POST_UPGRADE_JSON:
                if(obj instanceof ResponseErrorMessage) {
                    ResponseErrorMessage message = (ResponseErrorMessage) obj;
                    int code = message.getCode();
                    String msg = message.getMessage();
//                    if (!ismuteUp){
//                        if (!TextUtils.isEmpty(msg)){
//                            showToast(msg);
//                        }
//                    }
                }
                break;


        }
    }
    private void upgrade(){
        AppApi.Upgrade(mContext,this,mSession.getVersionCode());
    }



    private void setUpGrade(){
        String upgradeUrl = upGradeInfo.getOss_addr();
        //String upgradeUrl = "http://a5.pc6.com/pc6_soure/2016-2/com.huiche360.huiche_8.apk";

        if (!TextUtils.isEmpty(upgradeUrl)) {
            if (STIDUtil.needUpdate(mSession, upGradeInfo)) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(getString(R.string.home_update),"ensure");
                String[] content = upGradeInfo.getRemark();
                if (upGradeInfo.getUpdate_type() == 1) {
                    mUpgradeDialog = new UpgradeDialog(
                            mContext,
                            TextUtils.isEmpty(upGradeInfo.getVersion_code()+"")?"":"新版本：V"+upGradeInfo.getVersion_code(),
                            content,
                            this.getString(R.string.confirm),
                            forUpdateListener
                    );
                    mUpgradeDialog.show();
                }else {
                    mUpgradeDialog = new UpgradeDialog(
                            mContext,
                            TextUtils.isEmpty(upGradeInfo.getVersion_code()+"")?"":"新版本：V"+upGradeInfo.getVersion_code(),
                            content,
                            this.getString(R.string.cancel),
                            this.getString(R.string.confirm),
                            cancelListener,
                            forUpdateListener
                    );
                    mUpgradeDialog.show();
                }


            }else{
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(getString(R.string.home_update),"cancel");

//                if (!ismuteUp){
//                    ShowMessage.showToast(mContext, "当前为最新版本");
//                }

            }
        }else {
//            if (!ismuteUp){
//                ShowMessage.showToast(mContext, "当前为最新版本");
//            }
        }


    }


    private View.OnClickListener forUpdateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mUpgradeDialog.dismiss();
            downLoadApk(upGradeInfo.getOss_addr());
            // downLoadApk("http://download.savorx.cn/app-xiaomi-debug.apk");
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mUpgradeDialog.dismiss();
        }
    };
    protected void downLoadApk(String apkUrl) {
        if (!mSession.isApkDownloading()){
            mSession.setApkDownloading(true);
            // 下载apk包
            initNotification();
            AppApi.downApp(mContext,apkUrl, MainActivity.this);
        }else{
            ShowMessage.showToast(mContext,"下载中,请稍候");
        }
    }
    /**
     * 初始化Notification
     */
    private void initNotification() {
        manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notif = new Notification();
        notif.icon = R.drawable.ic_launcher;
        notif.tickerText = "下载通知";
        // 通知栏显示所用到的布局文件
        notif.contentView = new RemoteViews(mContext.getPackageName(),
                R.layout.download_content_view);
        notif.contentIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(
                mContext.getPackageName()+".debug"), PendingIntent.FLAG_CANCEL_CURRENT);
        // notif.defaults = Notification.DEFAULT_ALL;
        manager.notify(NOTIFY_DOWNLOAD_FILE, notif);
    }

}
