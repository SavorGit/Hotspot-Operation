package com.savor.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.CompleteInstallRepairAdapter;
import com.savor.operation.adapter.CompleteRepairAdapter;
import com.savor.operation.adapter.RepairAdapter;
import com.savor.operation.bean.ExecuteRepair;
import com.savor.operation.bean.TaskDetail;
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.interfaces.RefuseCallBack;
import com.savor.operation.widget.RefuseDialog;

import java.util.List;

/**
 * 已完成任务详情
 * Created by bushlee on 2017/11/12.
 */

public class CompletedSeekTaskDetailActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener,RefuseCallBack {

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
    private TextView refused;
    private TextView assign;
    private TextView call;
    private String  tnum;
    private CompleteInstallRepairAdapter completeInstallRepairAdapter;
    private CompleteRepairAdapter completeRepairAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seek_mission_detail);
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
        refused = (TextView) findViewById(R.id.refused);
        assign = (TextView) findViewById(R.id.assign);
        call = (TextView) findViewById(R.id.call);
    }

    @Override
    public void setViews() {
        tv_center.setText("任务详情");
    }

    @Override
    public void setListeners() {
        iv_left.setOnClickListener(this);
        refused.setOnClickListener(this);
        call.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.refused:
                initRefuse();
                break;
            case R.id.call:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + tnum);
                intent.setData(data);
                startActivity(intent);
                break;
        }
    }

    private void getData(){
        AppApi.taskDetail(context,id,this);
    }
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method){
            case POST_TASK_DETAIL_JSON:
                mPullRefreshListView.onRefreshComplete();
                if (obj instanceof TaskDetail){
                    taskDetail = (TaskDetail)obj;
                    initView();
                }
                break;
            case POST_REFUSE_TASK_JSON:
                if (refuseDialog != null) {
                    refuseDialog.dismiss();
                }
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
           plan_state.setText(taskDetail.getState()+"("+taskDetail.getRegion_name()+")");
            String task_emerge_id = taskDetail.getTask_emerge_id();
            if ("2".equals(task_emerge_id)) {
                level_state.setVisibility(View.VISIBLE);
                level_state.setText(taskDetail.getTask_emerge());
            }else {
                level_state.setVisibility(View.INVISIBLE);
            }

            String tvNums = taskDetail.getTv_nums();
            if (!TextUtils.isEmpty(tvNums)) {
                screen_num.setVisibility(View.VISIBLE);
                screen_num.setText("版位数量 ："+taskDetail.getTv_nums());
            }else {
                screen_num.setVisibility(View.GONE);
                screen_num.setText("");
            }
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

            tnum = taskDetail.getHotel_linkman_tel();
            String linkman = taskDetail.getHotel_linkman();

            if (!TextUtils.isEmpty(linkman)) {
                if (TextUtils.isEmpty(tnum)) {
                    tnum = "";
                }
                contact.setVisibility(View.VISIBLE);
                contact.setText("联系人："+taskDetail.getHotel_linkman()+"    "+tnum);
            }else {
                contact.setVisibility(View.GONE);
                //contact.setText("联系人："+taskDetail.getHotel_linkman()+"    "+tnum);
            }

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

            List<TaskDetailRepair> repair_list = taskDetail.getRepair_list();
            List<ExecuteRepair> execute = taskDetail.getExecute();
            if (repair_list != null && repair_list.size()>0) {
                screen_num.setText("版位数量 ："+repair_list.size());
                repairAdapter = new RepairAdapter(context);
                mPullRefreshListView.setAdapter(repairAdapter);
                repairAdapter.setData(repair_list);
                mPullRefreshListView.onLoadComplete(false,false);
            }

        }

    }

    private void initRefuse(){
        refuseDialog = new RefuseDialog(
                mContext,
                this
        );
        refuseDialog.show();
    }
    @Override
    public void toRefuse(String info) {
        AppApi.refuseTask(context,info,mSession.getLoginResponse().getUserid(),id,this);
    }
}
