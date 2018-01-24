package com.savor.operation.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.common.api.http.callback.FileDownProgress;
import com.common.api.utils.AppUtils;
import com.common.api.utils.DensityUtil;
import com.common.api.utils.FileUtils;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.adapter.ActionListAdapter;
import com.savor.operation.bean.ActionListItem;
import com.savor.operation.bean.City;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.RoleInfo;
import com.savor.operation.bean.SkillList;
import com.savor.operation.bean.TaskNum;
import com.savor.operation.bean.UpgradeInfo;
import com.savor.operation.core.AppApi;
import com.savor.operation.enums.FunctionType;
import com.savor.operation.utils.STIDUtil;
import com.savor.operation.utils.log.ActionType;
import com.savor.operation.widget.CommonDialog;
import com.savor.operation.widget.UpgradeDialog;
import com.savor.operation.widget.decoration.SpacesItemDecoration;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.savor.operation.activity.CityListActivity.REQUEST_CODE_CITY;
import static com.savor.operation.activity.CityListActivity.RESULT_CODE_CITY;

public class SavorMainActivity extends BaseActivity implements View.OnClickListener {

    private static final int GRID_ROW_COUNT = 2;
    private TextView mCityTv;
    private TextView mSearchTv;
    private RecyclerView mItemRlv;
    private TextView mUserInfoTv;
    private TextView mExitBtn;
    private UpgradeInfo upGradeInfo;
    private UpgradeDialog mUpgradeDialog;
    private NotificationManager manager;
    private Notification notif;
    private final int NOTIFY_DOWNLOAD_FILE=10001;
    /**发布者*/
    public static final List<ActionListItem> PUBLISH_TEMS = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.PUBLISH_TASK,0));
            add(new ActionListItem(FunctionType.PUB_TASK_LIST,0));
            add(new ActionListItem(FunctionType.SYSTEM_STATUS,0));
            add(new ActionListItem(FunctionType.EXCEPTION_REPORT,0));
            add(new ActionListItem(FunctionType.FIX_HISTORY,0));
        }
    };
    /**
     * 执行者
     */
    public static final List<ActionListItem> PERFORM_ITEMS = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.MY_TASK,0));
            add(new ActionListItem(FunctionType.SYSTEM_STATUS,0));
            add(new ActionListItem(FunctionType.EXCEPTION_REPORT,0));
            add(new ActionListItem(FunctionType.FIX_HISTORY,0));
            add(new ActionListItem(FunctionType.BIND_BOX,0));
        }
    };
    /**
     * 指派者
     */
    public static final List<ActionListItem> APPOINTER_ITEMS = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.APPOINT_TASK_LIST,0));
            add(new ActionListItem(FunctionType.SYSTEM_STATUS,0));
            add(new ActionListItem(FunctionType.EXCEPTION_REPORT,0));
            add(new ActionListItem(FunctionType.FIX_HISTORY,0));
        }
    };

    /**
     * 查看者
     */
    public static final List<ActionListItem> LOOK_ITEMS = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.TASK_LIST,0));
            add(new ActionListItem(FunctionType.SYSTEM_STATUS,0));
            add(new ActionListItem(FunctionType.EXCEPTION_REPORT,0));
            add(new ActionListItem(FunctionType.FIX_HISTORY,0));
        }
    };

    /**
     * 外包
     */
    public static final List<ActionListItem> OUTSOURCE = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.UPDATE_CHANGE_IMAGE,0));
        }
    };

    /**
     * 外包
     */
    public static final List<ActionListItem> CHECKHOTEL = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.CHECK_HOTEL,0));
        }
    };
    private ActionListAdapter mAdapter;
    private SpacesItemDecoration spacesItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savor_main);

        getViews();
        setViews();
        setListeners();
        upgrade();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoginResponse loginResponse = mSession.getLoginResponse();
        SkillList skill_list = loginResponse.getSkill_list();
        if(skill_list!=null) {
            RoleInfo role_info = skill_list.getRole_info();
            String id = role_info.getId();
            if("1".equals(id)) {
                mAdapter.setData(PUBLISH_TEMS);
            }else if("2".equals(id)) {
                mAdapter.setData(APPOINTER_ITEMS);
                getData();
            }else if("3".equals(id)) {
                mAdapter.setData(PERFORM_ITEMS);
                getData();
            }else if("4".equals(id)) {
                mAdapter.setData(LOOK_ITEMS);
            }else if("5".equals(id)) {
                mAdapter.setData(OUTSOURCE);

                mItemRlv.removeItemDecoration(spacesItemDecoration);
            }else if("6".equals(id)) {
                mAdapter.setData(CHECKHOTEL);

                mItemRlv.removeItemDecoration(spacesItemDecoration);
            }
        }
    }

    private void getData() {
        LoginResponse loginResponse = mSession.getLoginResponse();
        SkillList skill_list = loginResponse.getSkill_list();
        String userid = loginResponse.getUserid();
        List<City> manage_city = skill_list.getManage_city();
        String id = getCityId(manage_city);
        AppApi.getTaskNum(this,id,userid,this);
    }

    private String getCityId(List<City> manage_city) {
        for(City city: manage_city) {
            if(city.isSelect()) {
                return city.getId();
            }
        }
        return null;
    }

    @Override
    public void getViews() {
        mCityTv = (TextView) findViewById(R.id.tv_city);
        mSearchTv = (TextView) findViewById(R.id.tv_search);
        mItemRlv = (RecyclerView) findViewById(R.id.rlv_items);
        mUserInfoTv = (TextView) findViewById(R.id.tv_user_info);
        mExitBtn = (TextView) findViewById(R.id.tv_exit);
    }

    @Override
    public void setViews() {
        // 初始化城市信息
        LoginResponse loginResponse = mSession.getLoginResponse();
        SkillList skill_list = loginResponse.getSkill_list();
        List<City> manage_city = skill_list.getManage_city();
        if(manage_city!=null&&manage_city.size()>0) {
            City city = getSelectCity(manage_city);
            if(manage_city.size()>1) {
                Drawable drawable = getResources().getDrawable(R.drawable.ico_arraw_down_normal);
                mCityTv.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
                mCityTv.setOnClickListener(this);
            }else {
                mCityTv.setOnClickListener(null);
                mCityTv.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }
            mCityTv.setText(city.getRegion_name());
        }else {
            mCityTv.setOnClickListener(null);
            mCityTv.setCompoundDrawables(null,null,null,null);
        }

        // 用户信息
        String nickname = loginResponse.getNickname();
        String appVersion = AppUtils.getAppVersion(this);
        mUserInfoTv.setText("运维端"+appVersion+"--登录账号："+nickname);

        // 功能列表
        GridLayoutManager manager = new GridLayoutManager(this,GRID_ROW_COUNT);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mAdapter = new ActionListAdapter(this);
        mItemRlv.setLayoutManager(manager);
        mItemRlv.setAdapter(mAdapter);
        //添加ItemDecoration，item之间的间隔
        int leftRight = DensityUtil.dip2px(this,2);
        int topBottom = DensityUtil.dip2px(this,2);
        spacesItemDecoration = new SpacesItemDecoration(leftRight, topBottom, getResources().getColor(R.color.grid_item_divider));
        mItemRlv.addItemDecoration(spacesItemDecoration);


    }

    private City getSelectCity(List<City> manage_city) {
        for(City city : manage_city) {
            if(city.isSelect())
                return city;
        }
        return null;
    }

    @Override
    public void setListeners() {
        mExitBtn.setOnClickListener(this);
        mSearchTv.setOnClickListener(this);
//        mCityTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_city:
                intent = new Intent(this,CityListActivity.class);
                startActivityForResult(intent,REQUEST_CODE_CITY);
                break;
            case R.id.tv_search:
                intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_exit:
                new CommonDialog(this, "是否要注销当前登录账号", new CommonDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        mSession.setLoginResponse(null);
                        mSession.setAccount(null);
                        Intent intent = new Intent(SavorMainActivity.this, LoginActivity.class);
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CITY&&resultCode == RESULT_CODE_CITY) {
            LoginResponse loginResponse = mSession.getLoginResponse();
            SkillList skill_list = loginResponse.getSkill_list();
            if(skill_list!=null) {
                List<City> manage_city = skill_list.getManage_city();
                for(City city:manage_city) {
                    if(city.isSelect()) {
                        mCityTv.setText(city.getRegion_name());
                        break;
                    }
                }
            }
        }
    }

    int msg = 0;
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_TASK_NUM_JSON:
                if(obj instanceof TaskNum) {
                    TaskNum taskNum = (TaskNum) obj;
                    String nums = taskNum.getNums();
                    int num = 0;
                    try {
                        num = Integer.valueOf(nums);
                    }catch (Exception e){}
                    List<ActionListItem> data = mAdapter.getData();
                    for(ActionListItem item : data) {
                        FunctionType type = item.getType();
                        if(type == FunctionType.MY_TASK||type==FunctionType.APPOINT_TASK_LIST) {
                            item.setNum(num);
                            break;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
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
            AppApi.downApp(mContext,apkUrl, SavorMainActivity.this);
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

    @Override
    public void onError(AppApi.Action method, Object obj) {

    }
}
