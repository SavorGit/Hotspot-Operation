package com.savor.operation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.common.api.utils.ShowMessage;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.savor.operation.R;
import com.savor.operation.adapter.RepairAdapter;
import com.savor.operation.bean.BaseBoxInfo;
import com.savor.operation.bean.ExecutorInfo;
import com.savor.operation.bean.ExecutorInfoBean;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.RepairInfo;
import com.savor.operation.bean.TaskDetail;
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.interfaces.MaintenanceCallBack;
import com.savor.operation.interfaces.RefuseCallBack;
import com.savor.operation.utils.ConstantValues;
import com.savor.operation.utils.OSSClientUtil;
import com.savor.operation.widget.MaintenanceDialog;
import com.savor.operation.widget.RefuseDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.savor.operation.adapter.FixTaskListAdapter.REQUEST_CODE_IMAGE;
import static com.savor.operation.adapter.FixTaskListAdapter.TAKE_PHOTO_REQUEST;

/**
 * 执行者任务详情
 * Created by bushlee on 2017/11/12.
 */

public class ExeTaskDetailActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener,MaintenanceCallBack {

    private Context context;
    private PullToRefreshListView mPullRefreshListView;
    private String id;
    private TextView plan_state;
    private TextView level_state;
    private TextView screen_num;
    private TextView mold;
    private TextView hotel_name;
    private TextView add;
    private TextView release_time;
    private TextView execute_time;
    private TextView complete_time;
    private TextView contact;
    private TextView tv_center;
    private ImageView iv_left;
    private TaskDetail taskDetail;
    private RepairAdapter repairAdapter;
    private RefuseDialog refuseDialog;
    private RelativeLayout btn_la;
    private TextView assign;
    private List<TaskDetailRepair> repair_list;
    private String task_type_id;
    private ExecutorInfo executorInfo;
    private List<ExecutorInfoBean> elist;
    private MaintenanceDialog maintenanceDialog;
    //private String  urls ="";
    private String box_id;
    private String state;
    private String remark;
    private Handler mHandler = new Handler();
    private  List<String> urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exe_mission_detail);
        context = this;
        handleIntent();
        getViews();
        setViews();
        setListeners();
        getData();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }


    @Override
    public void getViews() {
        plan_state = (TextView) findViewById(R.id.plan_state);
        level_state = (TextView) findViewById(R.id.level_state);
        screen_num = (TextView) findViewById(R.id.screen_num);
        mold = (TextView) findViewById(R.id.mold);
        hotel_name = (TextView) findViewById(R.id.hotel_name);
        add = (TextView) findViewById(R.id.add);
        release_time = (TextView) findViewById(R.id.release_time);
        execute_time = (TextView) findViewById(R.id.execute_time);
        complete_time = (TextView) findViewById(R.id.complete_time);
        contact = (TextView) findViewById(R.id.contact);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.wl_listview);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        btn_la = (RelativeLayout) findViewById(R.id.btn_la);;
        assign = (TextView) findViewById(R.id.assign);
    }

    @Override
    public void setViews() {
        tv_center.setText("任务详情");
    }

    @Override
    public void setListeners() {
        iv_left.setOnClickListener(this);
        assign.setOnClickListener(this);

    }
   // maintenance
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.assign:
                //initRefuse();
                maintenance();
                break;
        }
    }

    private void getData(){
        AppApi.taskDetail(context,id,this);
    }
    private void getExecutorInfo(){
        AppApi.getExecutorInfo(context,task_type_id,id,mSession.getLoginResponse().getUserid(),this);
    }
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method){
            case POST_TASK_DETAIL_JSON:
                mPullRefreshListView.onRefreshComplete();
                if (obj instanceof TaskDetail){
                    taskDetail = (TaskDetail)obj;
                    initView();
                    getExecutorInfo();
                }
                break;
            case POST_REFUSE_TASK_JSON:
                if (refuseDialog != null) {
                    refuseDialog.dismiss();
                }
                break;
            case POST_EXE_INFO_JSON:
                if (obj instanceof ExecutorInfo){
                    executorInfo = (ExecutorInfo)obj;
                    if (executorInfo != null) {
                        elist = executorInfo.getList();
                    }
                }

                break;
            case POST_REPORT_MISSION_JSON:
                finish();
                break;


        }

    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        if (obj instanceof ResponseErrorMessage){
            ResponseErrorMessage errorMessage = (ResponseErrorMessage)obj;
            String statusCode = String.valueOf(errorMessage.getCode());
            ShowMessage.showToast(context,errorMessage.getMessage());
        }
        switch (method){
            case POST_REFUSE_TASK_JSON:
                if (refuseDialog != null) {
                    refuseDialog.dismiss();
                }

                break;
        }
    }
    private void initView(){
        if (taskDetail != null){
            task_type_id = taskDetail.getTask_type_id();
            plan_state.setText(taskDetail.getState()+"("+taskDetail.getRegion_name()+")");
            String task_emerge_id = taskDetail.getTask_emerge_id();
            if ("2".equals(task_emerge_id)) {
                level_state.setVisibility(View.VISIBLE);
                level_state.setText(taskDetail.getTask_emerge());
            }else {
                level_state.setVisibility(View.INVISIBLE);
            }

            screen_num.setText("版位数量 ："+taskDetail.getTv_nums());
            mold.setText(taskDetail.getTask_type_desc());
            hotel_name.setText(taskDetail.getHotel_name());
            add.setText(taskDetail.getHotel_address());
//            String appoint_exe_time = taskDetail.getAppoint_exe_time();
//            if (!TextUtils.isEmpty(appoint_exe_time)) {
//                release_execute_time.setVisibility(View.VISIBLE);
//                release_execute_time.setText("执行指派时间:"+appoint_exe_time+"("+taskDetail.getExeuser()+")");
//            }else {
//                release_execute_time.setVisibility(View.GONE);
//                release_execute_time.setText("");
 //           }

            contact.setText("联系人："+taskDetail.getHotel_linkman());
            String create_time = taskDetail.getCreate_time();
            if (!TextUtils.isEmpty(create_time)) {
                release_time.setVisibility(View.VISIBLE);
                release_time.setText("发布时间:"+create_time+"("+taskDetail.getPublish_user()+")");
            }else {
                release_time.setVisibility(View.GONE);
                release_time.setText("");
            }

            String appoint_time = taskDetail.getAppoint_time();
            if (!TextUtils.isEmpty(appoint_time)) {
                execute_time.setVisibility(View.VISIBLE);
                execute_time.setText("指派时间:"+appoint_time+"("+taskDetail.getAppoint_user()+")");
            }else {
                execute_time.setVisibility(View.GONE);
                execute_time.setText("");
            }

            String complete_timeStr = taskDetail.getComplete_time();
            if (!TextUtils.isEmpty(complete_timeStr)) {
                complete_time.setVisibility(View.VISIBLE);
                complete_time.setText("完成时间"+complete_timeStr+"("+taskDetail.getExeuser()+")");
            }else {
                complete_time.setVisibility(View.GONE);
                complete_time.setText("");
            }

               repair_list = taskDetail.getRepair_list();
            if (repair_list != null && repair_list.size()>0) {
                screen_num.setText("版位数量 ："+repair_list.size());
                repairAdapter = new RepairAdapter(context);
                mPullRefreshListView.setAdapter(repairAdapter);
                repairAdapter.setData(repair_list);
                mPullRefreshListView.onLoadComplete(false,false);
            }

        }

    }

    private void maintenance(){
        maintenanceDialog = new MaintenanceDialog(
                mContext,
                elist,
                ExeTaskDetailActivity.this,
                this
        );
        maintenanceDialog.show();
    }

    @Override
    public void toMaintenance(String box,String re,String st, List<String> urlss) {
        urls.clear();
        box_id = box;
        state = st;
        remark = re;
        uploadPic(urlss, 0);
    }

    private void publish() {
        Gson gson = new Gson();
        String  repair_info = gson.toJson(urls, new TypeToken<List<String>>() {
        }.getType());
        AppApi.reportMission(context, box_id ,remark, repair_info,state
                ,id,taskDetail.getTask_type_id(),mSession.getLoginResponse().getUserid(),this);
    }
    @Override
    public void toInstallation() {

    }

    @Override
    public void toTransform() {

    }

    @Override
    public void toDetect() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == TAKE_PHOTO_REQUEST && resultCode == Activity.RESULT_OK) {
             maintenanceDialog.updataPhotoPath();
        }else if (requestCode == REQUEST_CODE_IMAGE&&resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);

             maintenanceDialog.updateImagePath(imagePath);
        }

    }

    private void uploadPic(final List<String> infos, final int startPos) {
        final String url = infos.get(startPos);
        if(!TextUtils.isEmpty(url)) {
            final OSSClient ossClient = OSSClientUtil.getInstance().getOSSClient(this);
            String imagePath = url;
            File file = new File(imagePath);
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = simpleDateFormat.format(date);
            final String objectKey = "log/resource/operation/mobile/box/"+dateStr+"/"+file.getName();
            // 构造上传请求
            PutObjectRequest put = new PutObjectRequest(ConstantValues.BUCKET_NAME,objectKey , imagePath);
            try {
                ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                        String imageUrl = ossClient.presignPublicObjectURL(ConstantValues.BUCKET_NAME, objectKey);
                        urls.add(imageUrl);
                       // urls = urls+imageUrl+",";
                        //repairInfo.setFault_img_url(imageUrl);

                        int nextPos = startPos+1;
                        if(nextPos<infos.size()) {
                            uploadPic(infos,nextPos);
                        }else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    publish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                        int nextPos = startPos+1;
                        if(nextPos<infos.size()) {
                            uploadPic(infos,nextPos);
                        }else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    publish();
                                }
                            });
                        }
                    }
                });

            }catch (Exception e) {}
        }
    }

    private boolean isHasUploadPic(List<RepairInfo> infos) {
        for(int i = 0;i<infos.size();i++) {
            RepairInfo repairInfo = infos.get(i);
            if(!TextUtils.isEmpty(repairInfo.getFault_img_url())) {
                return true;
            }
        }
        return false;
    }

}
