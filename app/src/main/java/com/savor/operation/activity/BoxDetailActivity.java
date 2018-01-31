package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.adapter.ProgramStatusAdapter;
import com.savor.operation.adapter.TvBoxFixHistoryAdapter;
import com.savor.operation.bean.BoxDetail;
import com.savor.operation.bean.BoxState;
import com.savor.operation.bean.FixHistoryResponse;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.OneKeyTestResponse;
import com.savor.operation.bean.Program;
import com.savor.operation.core.AppApi;
import com.savor.operation.widget.FixDialog;
import com.savor.operation.widget.OneKeyTestDialog;

import java.util.List;

public class BoxDetailActivity extends BaseActivity implements View.OnClickListener, OneKeyTestDialog.OnReTestBtnClickLisnter, OneKeyTestDialog.OnCancelBtnClickListener {

    private static final int MSG_ONKEY_TEST = 0x1;
    private ImageView mBackBtn;
    private ListView mProgramRlv;
    private String box_id;
    private TextView mBoxNameTv;
    private TextView mBoxMacTv;
    private TextView mLastHeartTimeTv;
    private TextView mLastLogTimeTv;
    private RecyclerView mRepairListRlv;
    private TextView mHistoryHintTv;
    private TextView mCurrentStatusTv;
    private TextView mProgramStatusTv;
    private TextView mAdvertStatusTv;
    private TextView mLookLoadingProgramTv;
    private TextView mLookLoadingAdvertTv;
    private TextView mContentListBtn;
    private TextView mProgramPeriodTv;
    private TextView mAdsPeriodTv;
    private ProgramStatusAdapter programStatusAdapter;
    private String box_name;
    private TextView mTitleTv;
    private BoxDetail boxDetail;
    private TextView mFixBtn;
    private Hotel hotel;
    private String box_mac;
    private TextView mOnkeyTestTv;
    private OneKeyTestDialog oneKeyTestDialog;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ONKEY_TEST:
                    oneKeyTest();
                    break;
            }
        }
    };
    private TextView mPubProgramBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_detail);

        handleIntent();
        getViews();
        setViews();
        setListeners();
        getData();
    }

    private void getData() {
        if(!TextUtils.isEmpty(box_id)) {
            showLoadingLayout();
            AppApi.getBoxDetail(this,box_id,this);
        }
    }

    private void handleIntent() {
        box_id = getIntent().getStringExtra("box_id");
        box_name = getIntent().getStringExtra("box_name");
        box_mac = getIntent().getStringExtra("box_mac");
        hotel = (Hotel) getIntent().getSerializableExtra("hotel");
    }

    @Override
    public void getViews() {
        mFixBtn = (TextView) findViewById(R.id.tv_fix);
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTitleTv = (TextView) findViewById(R.id.tv_center);
        mProgramRlv = (ListView) findViewById(R.id.rlv_program_status);

        initHeaderView();
    }

    private void initHeaderView() {
        View headerView = View.inflate(this, R.layout.header_view_box_detail,null);

        mBoxNameTv = (TextView) headerView.findViewById(R.id.tv_room_name);
        mBoxMacTv = (TextView)  headerView.findViewById(R.id.tv_box_mac);
        mLastHeartTimeTv = (TextView)  headerView.findViewById(R.id.tv_last_heart_time);
        mLastLogTimeTv = (TextView)  headerView.findViewById(R.id.tv_last_log_time);
        mRepairListRlv = (RecyclerView)  headerView.findViewById(R.id.rlv_fix_history);
        mHistoryHintTv = (TextView)  headerView.findViewById(R.id.tv_history_hint);

        mCurrentStatusTv = (TextView)  headerView.findViewById(R.id.tv_current_status);
        mProgramStatusTv = (TextView)  headerView.findViewById(R.id.tv_program_status);
        mAdvertStatusTv = (TextView)  headerView.findViewById(R.id.tv_advert_status);
        mLookLoadingProgramTv = (TextView)  headerView.findViewById(R.id.tv_loading_program);
        mLookLoadingAdvertTv = (TextView)  headerView.findViewById(R.id.tv_loading_advert);
        mContentListBtn = (TextView)  headerView.findViewById(R.id.tv_content_list);

        mProgramPeriodTv = (TextView)  headerView.findViewById(R.id.tv_pro_period);
        mAdsPeriodTv = (TextView)  headerView.findViewById(R.id.tv_ads_period);
        mOnkeyTestTv = (TextView) headerView.findViewById(R.id.tv_onekey_test);
        mPubProgramBtn = (TextView) headerView.findViewById(R.id.tv_content_list);

        mProgramRlv.addHeaderView(headerView);
    }

    @Override
    public void setViews() {
        if(!TextUtils.isEmpty(box_name)) {
            mTitleTv.setText(box_name);
        }
        programStatusAdapter = new ProgramStatusAdapter(this);
        mProgramRlv.setAdapter(programStatusAdapter);
    }

    @Override
    public void setListeners() {
        mPubProgramBtn.setOnClickListener(this);
        mOnkeyTestTv.setOnClickListener(this);
        mFixBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mLookLoadingProgramTv.setOnClickListener(this);
        mLookLoadingAdvertTv.setOnClickListener(this);
    }

    public void showOnkeTestDialog() {
        if(oneKeyTestDialog==null) {
            oneKeyTestDialog = new OneKeyTestDialog(this);
            oneKeyTestDialog.setOnReTestBtnClickLisnter(this);
            oneKeyTestDialog.setOnCancelBtnClickListener(this);
        }
        oneKeyTestDialog.reset();
        oneKeyTestDialog.show();
    }

    public void hideOnKeyTestDialog() {
        if(oneKeyTestDialog!=null&&oneKeyTestDialog.isShowing()) {
            oneKeyTestDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content_list:
                Intent intent = new Intent(this,LoadingListActivity.class);
                intent.putExtra("type",LoadingListActivity.Operationtype.PUB_PRO_LIST);
                intent.putExtra("pro_period",boxDetail.getPro_period());
                intent.putExtra("ads_period",boxDetail.getAds_period());
                intent.putExtra("box_id",box_id);
                startActivity(intent);
                break;
            case R.id.tv_onekey_test:
                showOnkeTestDialog();
                mHandler.sendEmptyMessageDelayed(MSG_ONKEY_TEST,500);
                break;
            case R.id.tv_fix:
                FixHistoryResponse fixHistoryResponse = new FixHistoryResponse();
                new FixDialog(this, new FixDialog.OnSubmitBtnClickListener() {
                    @Override
                    public void onSubmitClick(FixDialog.OperationType type, FixHistoryResponse fixHistoryResponse, FixDialog.FixState isResolve, List<String> damageDesc, BoxState boxState, String comment, Hotel hotel) {
                        StringBuilder sb = new StringBuilder();
                        for(int i = 0;i<damageDesc.size();i++) {
                            if(damageDesc.size() == 1 || i == damageDesc.size()-1) {
                                sb.append(damageDesc.get(i));
                            }else {
                                sb.append(damageDesc.get(i)+",");
                            }
                        }

                        int state = 0;
                        if(isResolve == FixDialog.FixState.RESOLVED) {
                            state = 1;
                        }else if(isResolve == FixDialog.FixState.UNRESOLVED) {
                            state = 2;
                        }

                        LoginResponse loginResponse = mSession.getLoginResponse();
                        String userid = loginResponse.getUserid();
                        AppApi.submitDamage(BoxDetailActivity.this,box_mac,
                                hotel.getId(),boxState.getId()+"",comment,sb.toString(),state+"", 2+"",userid,BoxDetailActivity.this);

                    }
                }, FixDialog.OperationType.TYPE_BOX, fixHistoryResponse, mSession.getDamageConfig(), hotel).show();
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_loading_program:
                LoadingListActivity.startLoadingListActivity(this,boxDetail.getPro_download_period());
                break;
            case R.id.tv_loading_advert:
                LoadingListActivity.startLoadingListActivity(this,boxDetail.getAds_download_period());
                break;
        }
    }

    private void oneKeyTest() {
        AppApi.oneKeyTest(this,box_id,this);
    }


    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_ONEKEY_TEST_JSON:
                if(obj instanceof OneKeyTestResponse) {
                    OneKeyTestResponse oneKeyTestResponse = (OneKeyTestResponse) obj;
                    oneKeyTestDialog.init(oneKeyTestResponse);
                }
                break;
            case POST_SUBMIT_DAMAGE_JSON:
                ShowMessage.showToast(BoxDetailActivity.this,"提交成功");
                getData();
                break;
            case POST_BOX_DETAIL_JSON:
                hideLoadingLayout();
                if(obj instanceof BoxDetail) {
                    boxDetail = (BoxDetail) obj;
                    initViews(boxDetail);
                    mProgramRlv.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgramRlv.setSelectionAfterHeaderView();
                            mProgramRlv.setSelection(0);
                        }
                    });
                }
                break;
        }
    }

    private void initViews(BoxDetail boxDetail) {
        /****包间、最后心跳、日志上传时间初始化***/
        String box_name = boxDetail.getBox_name();
        String box_mac = boxDetail.getBox_mac();
        String last_heart_time = boxDetail.getLast_heart_time();
        String log_upload_time = boxDetail.getLog_upload_time();
        List<FixHistoryResponse.PositionInfo.BoxInfoBean.RepaireInfo> repair_record = boxDetail.getRepair_record();

        mBoxNameTv.setText(getFormatStr(box_name));
        mBoxMacTv.setText(getFormatStr(box_mac));
        mLastHeartTimeTv.setText("最后心跳时间："+getFormatStr(last_heart_time));
        mLastLogTimeTv.setText("最后上传日志时间："+getFormatStr(log_upload_time));

        if(repair_record!=null&&repair_record.size()>0) {
            mHistoryHintTv.setText("维修记录：");
            TvBoxFixHistoryAdapter adapter = new TvBoxFixHistoryAdapter(mContext);
            LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
            mRepairListRlv.setLayoutManager(manager);
            mRepairListRlv.setAdapter(adapter);
            adapter.setData(repair_record);
        }else {
            mHistoryHintTv.setText("维修记录：无");
        }

        /*****当前状态初始化**/
        String loss_hours = boxDetail.getLoss_hours();
        String pro_period_state = boxDetail.getPro_period_state();
        String ads_period_state = boxDetail.getAds_period_state();
        mCurrentStatusTv.setText("当前状态："+getFormatStr(loss_hours));
        mProgramStatusTv.setText("节目状态："+getFormatStr(pro_period_state));
        mAdvertStatusTv.setText("广告状态："+getFormatStr(ads_period_state));

        /*****当前期号****/
        String pro_period = boxDetail.getPro_period();
        String ads_period = boxDetail.getAds_period();
        String pro_download_period = boxDetail.getPro_download_period();
        String ads_download_period = boxDetail.getAds_download_period();
        mLookLoadingProgramTv.setVisibility(TextUtils.isEmpty(pro_download_period)?View.GONE:View.VISIBLE);
        mLookLoadingAdvertTv.setVisibility(TextUtils.isEmpty(ads_download_period)?View.GONE:View.VISIBLE);

        mProgramPeriodTv.setText("节目期号："+getFormatStr(pro_period));
        mAdsPeriodTv.setText("广告期号："+getFormatStr(ads_period));

        List<Program> program_list = boxDetail.getProgram_list();
        programStatusAdapter.setData(program_list);

    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_ONEKEY_TEST_JSON:
                ShowMessage.showToast(this,"测试失败");
                hideOnKeyTestDialog();
                break;
            case POST_BOX_DETAIL_JSON:
                hideLoadingLayout();
                break;
        }
    }

    @Override
    public void onReTestBtnClick() {
        oneKeyTestDialog.reset();
        mHandler.sendEmptyMessageDelayed(MSG_ONKEY_TEST,500);
    }

    @Override
    public void onCancelBtnClick() {
        mHandler.removeMessages(MSG_ONKEY_TEST);
        mHandler.removeCallbacksAndMessages(null);
    }
}
