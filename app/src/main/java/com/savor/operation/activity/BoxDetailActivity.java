package com.savor.operation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.adapter.ProgramStatusAdapter;
import com.savor.operation.adapter.TvBoxFixHistoryAdapter;
import com.savor.operation.bean.BoxDetail;
import com.savor.operation.bean.FixHistoryResponse;
import com.savor.operation.bean.Program;
import com.savor.operation.core.AppApi;
import com.savor.operation.widget.LoadingDialog;

import java.util.List;

public class BoxDetailActivity extends BaseActivity implements View.OnClickListener {

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
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
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

        mProgramRlv.addHeaderView(headerView);
    }

    @Override
    public void setViews() {
        programStatusAdapter = new ProgramStatusAdapter(this);
        mProgramRlv.setAdapter(programStatusAdapter);
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }


    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_BOX_DETAIL_JSON:
                hideLoadingLayout();
                if(obj instanceof BoxDetail) {
                    BoxDetail boxDetail = (BoxDetail) obj;
                    initViews(boxDetail);
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
        mProgramPeriodTv.setText("节目期号："+pro_period);
        mAdsPeriodTv.setText("广告期号："+ads_period);

        List<Program> program_list = boxDetail.getProgram_list();
        programStatusAdapter.setData(program_list);

    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method, obj);
        switch (method) {
            case POST_BOX_DETAIL_JSON:
                hideLoadingLayout();
                break;
        }
    }
}
