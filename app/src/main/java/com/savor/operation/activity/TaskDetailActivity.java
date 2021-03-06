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

import com.common.api.utils.AppUtils;
import com.common.api.utils.ShowMessage;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.CompleteInstallRepairAdapter;
import com.savor.operation.adapter.CompleteRepairAdapter;
import com.savor.operation.adapter.InstallRepairAdapter;
import com.savor.operation.adapter.MaintenanceRepairAdapter;
import com.savor.operation.adapter.RepairAdapter;
import com.savor.operation.bean.ExecuteRepair;
import com.savor.operation.bean.MissionTaskListBean;
import com.savor.operation.bean.TaskDetail;
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.interfaces.RefuseCallBack;
import com.savor.operation.widget.RefuseDialog;
import com.savor.operation.widget.UpgradeDialog;

import java.util.List;

/**
 * 指派任务详情
 * Created by bushlee on 2017/11/12.
 */

public class TaskDetailActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener,RefuseCallBack {

    public static final int RESULT_CODE_BACK = 0x1;
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
    private TextView refuse_desc;
    private String  tnum;
    private MaintenanceRepairAdapter maintenanceRepairAdapter;
    private InstallRepairAdapter installRepairAdapter;
    private CompleteInstallRepairAdapter completeInstallRepairAdapter;
    private CompleteRepairAdapter completeRepairAdapter;
    private String task_type_id;
    private TextView appoint_exe_time;
    private TextView lead_install;
    private TextView city_in;
    private TextView  refuse_time;
    private RelativeLayout remark_la;
    private TextView  remarkView;
    private RelativeLayout real_tv_nums_la;
    private TextView tv_real_tv_nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_detail);
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
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.wl_listview);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_center = (TextView) findViewById(R.id.tv_center);
        btn_la = (RelativeLayout) findViewById(R.id.btn_la);;
        refused = (TextView) findViewById(R.id.refused);
        assign = (TextView) findViewById(R.id.assign);
        initHeaderView();


}

    private void initHeaderView() {
        View headerView = View.inflate(this, R.layout.mission_header_layout, null);

        plan_state = (TextView) headerView.findViewById(R.id.plan_state);
        level_state = (TextView) headerView.findViewById(R.id.level_state);
        screen_num = (TextView) headerView.findViewById(R.id.screen_num);
        mold = (TextView) headerView.findViewById(R.id.mold);
        hotel_name = (TextView) headerView.findViewById(R.id.hotel_name);
        add = (TextView) headerView.findViewById(R.id.add);
        release_time = (TextView) headerView.findViewById(R.id.release_time);
        execute_time = (TextView) headerView.findViewById(R.id.execute_time);
        complete_time = (TextView) headerView.findViewById(R.id.complete_time);
        contact = (TextView) headerView.findViewById(R.id.contact);
        call = (TextView) headerView.findViewById(R.id.call);
        refuse_desc = (TextView) headerView.findViewById(R.id.refuse_desc);
        appoint_exe_time = (TextView) headerView.findViewById(R.id.appoint_exe_time);
        lead_install = (TextView) headerView.findViewById(R.id.lead_install);
        city_in = (TextView) headerView.findViewById(R.id.city_in);
        refuse_time = (TextView) headerView.findViewById(R.id.refuse_time);
        remark_la =(RelativeLayout) headerView.findViewById(R.id.remark_la);
        remarkView = (TextView) headerView.findViewById(R.id.remark);
        real_tv_nums_la =(RelativeLayout) headerView.findViewById(R.id.real_tv_nums_la);
        tv_real_tv_nums = (TextView) headerView.findViewById(R.id.real_tv_nums);
        mPullRefreshListView.getRefreshableView().addHeaderView(headerView);
    }
    @Override
    public void setViews() {
        tv_center.setText("任务详情");
        repairAdapter = new RepairAdapter(context);
        mPullRefreshListView.setAdapter(repairAdapter);
    }

    @Override
    public void setListeners() {
        iv_left.setOnClickListener(this);
        refused.setOnClickListener(this);
        assign.setOnClickListener(this);
        call.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                setResult(RESULT_CODE_BACK);
                finish();
                break;
            case R.id.refused:
                initRefuse();
                break;
            case R.id.assign:
                if (taskDetail != null) {
                    if (AppUtils.isFastDoubleClick(1)) {
                        return;
                    }else {
                        Intent intentp = new Intent(context, AppointActivity.class);
                        intentp.putExtra("taskDetail",taskDetail);
                        startActivityForResult(intentp,10);
                    }

                }

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
                finish();
                break;
        }

    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        if (obj instanceof ResponseErrorMessage){
            ResponseErrorMessage errorMessage = (ResponseErrorMessage)obj;
            String statusCode = String.valueOf(errorMessage.getCode());
            String msg = errorMessage.getMessage();
            if (!TextUtils.isEmpty(msg)) {
                ShowMessage.showToast(context,errorMessage.getMessage());
            }
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
            int is_lead_install = 5;
            is_lead_install = taskDetail.getIs_lead_install();
            String stateId = taskDetail.getState_id();
            if ("0".equals(stateId)) {
                plan_state.setTextColor(context.getResources().getColor(R.color.green_2));
            }else if ("1".equals(stateId)) {
                plan_state.setTextColor(context.getResources().getColor(R.color.orange_1));
            }else if ("2".equals(stateId)) {
                plan_state.setTextColor(context.getResources().getColor(R.color.api_blue));
            }else if ("4".equals(stateId)) {
                plan_state.setTextColor(context.getResources().getColor(R.color.green_2));
            }else if ("5".equals(stateId)) {
                plan_state.setTextColor(context.getResources().getColor(R.color.red));
            }

            plan_state.setText(taskDetail.getState());
            city_in.setText("("+taskDetail.getRegion_name()+")");
            String refuseT = taskDetail.getRefuse_time();
            if (!TextUtils.isEmpty(refuseT)) {
                refuse_time.setVisibility(View.VISIBLE);
                refuse_time.setText("拒绝时间："+refuseT+"("+taskDetail.getAppoint_user()+")");
            }else {
                refuse_time.setVisibility(View.GONE);
            }

            String task_emerge_id = taskDetail.getTask_emerge_id();
            if ("2".equals(task_emerge_id)) {
                level_state.setVisibility(View.VISIBLE);
                level_state.setText(taskDetail.getTask_emerge());
            }else {
                level_state.setVisibility(View.GONE);
            }

            String tvNums = taskDetail.getTv_nums();
            if (!TextUtils.isEmpty(tvNums)) {
                screen_num.setVisibility(View.VISIBLE);
                screen_num.setText("版位数量 ："+taskDetail.getTv_nums());
            }else {
                screen_num.setVisibility(View.GONE);
            }


            String appoint_exe_timeString = taskDetail.getAppoint_exe_time();
            if (!TextUtils.isEmpty(appoint_exe_timeString)) {
                appoint_exe_time.setText("执行指派时间 ："+appoint_exe_timeString+"("+taskDetail.getExeuser()+")");
            }else {
                appoint_exe_time.setVisibility(View.GONE);
            }
            mold.setText(taskDetail.getTask_type_desc());
            hotel_name.setText(taskDetail.getHotel_name());
            String ad = taskDetail.getHotel_address();
            add.setText(ad);


            if ("5".equals(stateId)) {
                String refuse = taskDetail.getRefuse_desc();
                if (!TextUtils.isEmpty(refuse)) {
                    refuse_desc.setVisibility(View.VISIBLE);
                    refuse_desc.setText("拒绝原因："+refuse);
                }else {
                    refuse_desc.setText("拒绝原因：无");
                }
            }else {
                refuse_desc.setVisibility(View.GONE);
            }

            String remarkExe = taskDetail.getDesc();
            if (!TextUtils.isEmpty(remarkExe)) {
                remarkView.setText("备注:"+remarkExe);
            }else {
                remarkView.setText("备注:"+"无");
            }

            String r_tv_num = taskDetail.getReal_tv_nums();
            if (!TextUtils.isEmpty(r_tv_num)&&!"0".equals(r_tv_num)) {
                real_tv_nums_la.setVisibility(View.VISIBLE);
                tv_real_tv_nums.setText("实际安装数量:"+r_tv_num);
            }else {
                real_tv_nums_la.setVisibility(View.GONE);
                //remarkView.setText("备注:"+"无");
            }
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
                    call.setVisibility(View.GONE);
                }else {
                    call.setVisibility(View.VISIBLE);
                }
                contact.setVisibility(View.VISIBLE);
                contact.setText("联系人："+taskDetail.getHotel_linkman()+"    "+tnum);
            }else {
                if (TextUtils.isEmpty(tnum)) {
                    tnum = "";
                    call.setVisibility(View.GONE);
                }else {
                    contact.setVisibility(View.VISIBLE);
                    call.setVisibility(View.VISIBLE);
                    contact.setText("联系人：无"+"    "+tnum);
                }

                //contact.setText("联系人："+taskDetail.getHotel_linkman()+"    "+tnum);
            }


            String create_time = taskDetail.getCreate_time();
            if (!TextUtils.isEmpty(create_time)) {
                release_time.setVisibility(View.VISIBLE);
                release_time.setText("发布时间:"+create_time+"("+taskDetail.getPublish_user()+")");
            }else {
                //release_time.setVisibility(View.GONE);
                release_time.setVisibility(View.GONE);
            }

            String appoint_time = taskDetail.getAppoint_time();
            if (!TextUtils.isEmpty(appoint_time)) {
                execute_time.setVisibility(View.VISIBLE);
                execute_time.setText("指派时间:"+appoint_time+"("+taskDetail.getAppoint_user()+")");
            }else {
                execute_time.setVisibility(View.GONE);
            }

            String complete_timeStr = taskDetail.getComplete_time();
            if (!TextUtils.isEmpty(complete_timeStr)) {
                complete_time.setVisibility(View.VISIBLE);
                complete_time.setText("完成时间："+complete_timeStr+"("+taskDetail.getExeuser()+")");
            }else {
                complete_time.setVisibility(View.GONE);
            }

            if (!"1".equals(stateId)&&!"5".equals(stateId)) {
                if ("2".equals(task_type_id)) {
                    if (is_lead_install == 1) {
                        lead_install.setVisibility(View.VISIBLE);
                        lead_install.setText("带队安装：需要");
                    } else if (is_lead_install == 0) {
                        lead_install.setVisibility(View.VISIBLE);
                        lead_install.setText("带队安装：不需要");
                    } else {
                        lead_install.setVisibility(View.GONE);
                    }
                } else {
                    lead_install.setVisibility(View.GONE);
                }
            }else {
                lead_install.setVisibility(View.GONE);
            }


            List<TaskDetailRepair> repair_list = taskDetail.getRepair_list();
            List<ExecuteRepair> execute = taskDetail.getExecute();

            if (repair_list != null && repair_list.size()>0) {
                //screen_num.setText("版位数量 ："+repair_list.size());
                mPullRefreshListView.setVisibility(View.VISIBLE);
                if ("1".equals(task_type_id)) {//信息检测
                    repairAdapter = new RepairAdapter(context);
                    mPullRefreshListView.setAdapter(repairAdapter);
                    repairAdapter.setData(repair_list);
                    mPullRefreshListView.onLoadComplete(false,false);

                }else if ("2".equals(task_type_id)){//安装验收

                    installRepairAdapter = new InstallRepairAdapter(context);
                    mPullRefreshListView.setAdapter(installRepairAdapter);
                    installRepairAdapter.setData(repair_list);
                    mPullRefreshListView.onLoadComplete(false,false);

                }else if ("4".equals(task_type_id)) {//维修
                    maintenanceRepairAdapter = new MaintenanceRepairAdapter(context);
                    mPullRefreshListView.setAdapter(maintenanceRepairAdapter);
                    maintenanceRepairAdapter.setData(repair_list);
                    mPullRefreshListView.onLoadComplete(false,false);

                }else if ("8".equals(task_type_id)) {//网络改造
                    repairAdapter = new RepairAdapter(context);
                    mPullRefreshListView.setAdapter(repairAdapter);
                    repairAdapter.setData(repair_list);
                    mPullRefreshListView.onLoadComplete(false,false);
                }


            }else if(execute != null && execute.size()>0){
                mPullRefreshListView.setVisibility(View.VISIBLE);
                if ("1".equals(task_type_id)) {//信息检测
                    completeRepairAdapter = new CompleteRepairAdapter(context);
                    mPullRefreshListView.setAdapter(completeRepairAdapter);
                    completeRepairAdapter.setData(execute);
                    mPullRefreshListView.onLoadComplete(false,false);

                }else if ("2".equals(task_type_id)){//安装验收
                    completeInstallRepairAdapter = new CompleteInstallRepairAdapter(context);
                    mPullRefreshListView.setAdapter(completeInstallRepairAdapter);
                    completeInstallRepairAdapter.setData(execute);
                    mPullRefreshListView.onLoadComplete(false,false);

                }else if ("8".equals(task_type_id)) {//网络改造
                    completeRepairAdapter = new CompleteRepairAdapter(context);
                    mPullRefreshListView.setAdapter(completeRepairAdapter);
                    completeRepairAdapter.setData(execute);
                    mPullRefreshListView.onLoadComplete(false,false);
                }

            }else {
               // mPullRefreshListView.setVisibility(View.GONE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            finish();
        }
    }

}
