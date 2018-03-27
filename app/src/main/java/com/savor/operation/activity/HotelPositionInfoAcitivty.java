package com.savor.operation.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.adapter.HotelPositionAdapter;
import com.savor.operation.adapter.TvBoxFixHistoryAdapter;
import com.savor.operation.bean.BoxState;
import com.savor.operation.bean.DamageConfig;
import com.savor.operation.bean.FixHistoryResponse;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.PositionListInfo;
import com.savor.operation.core.AppApi;
import com.savor.operation.widget.FixDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒楼版位信息
 */
public class HotelPositionInfoAcitivty extends BaseActivity implements  View.OnClickListener, HotelPositionAdapter.OnItemClickListener {

    private ListView mPostionListView;
    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mRightTv;
    private HotelPositionAdapter mHotelPositionAdapter;
    private Hotel mHotel;
    private View mHeaderView;
    private TextView mSpVersionTv;
    private TextView mLastSpVersionTv;
    private TextView mLastXintiao;
    private ImageView mSpState;
    private ImageView mLastXintiaoIV;
    private TextView mPositionDesc;
    private DamageConfig damageConfig;
    private Button mFixSpBtn;
    private FixHistoryResponse fixHistoryResponse;
    private TextView mFixHintTv;
    private RecyclerView mSpHistoryRlv;
    private TvBoxFixHistoryAdapter mSpHistoryAdapter;
    private ImageView mRightIv;
    private TextView mBoxInnerIpTv;
    private TextView mBoxOutIpTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_info_acitivty);

        handleIntent();
        getViews();
        setViews();
        setListeners();

        getData();
        getDamageInfo();
        getstateConf();
    }
    private void getstateConf() {
        AppApi.getStateConfig(this,this);
    }
    private void getDamageInfo() {
        AppApi.getDamageConfig(this,this);
    }

    private void getData() {
        AppApi.getFixHistory(this,mHotel.getId(),this);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        mHotel = (Hotel) intent.getSerializableExtra("hotel");
    }

    @Override
    public void getViews() {
        mRightIv = (ImageView) findViewById(R.id.iv_right);
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTitleTv = (TextView) findViewById(R.id.tv_center);
        mRightTv = (TextView) findViewById(R.id.tv_right);
        mPostionListView = (ListView) findViewById(R.id.lv_hotel_position_list);

        mHeaderView = ImageView.inflate(this, R.layout.header_view_positionlayout,null);
        mBoxInnerIpTv = (TextView) mHeaderView.findViewById(R.id.tv_box_inner_ip);
        mBoxOutIpTv = (TextView) mHeaderView.findViewById(R.id.tv_box_out_ip);
        mSpVersionTv = (TextView) mHeaderView.findViewById(R.id.tv_sp_version);
        mLastSpVersionTv = (TextView) mHeaderView.findViewById(R.id.tv_last_sp_version);
        mLastXintiao = (TextView) mHeaderView.findViewById(R.id.tv_last_xintiao);
        mSpState = (ImageView) mHeaderView.findViewById(R.id.iv_sp_state);
        mLastXintiaoIV = (ImageView) mHeaderView.findViewById(R.id.iv_last_xintiao);
        mPositionDesc = (TextView) mHeaderView.findViewById(R.id.tv_position_desc);
        mFixSpBtn = (Button) mHeaderView.findViewById(R.id.btn_fix_sp);
        mFixHintTv = (TextView) mHeaderView.findViewById(R.id.tv_fix_hint);
        mSpHistoryRlv = (RecyclerView) mHeaderView.findViewById(R.id.rlv_history);

        damageConfig = mSession.getDamageConfig();
    }

    @Override
    public void setViews() {
        mBackBtn.setOnClickListener(this);
        mHotelPositionAdapter = new HotelPositionAdapter(this);
        mPostionListView.setAdapter(mHotelPositionAdapter);
        mPostionListView.addHeaderView(mHeaderView);

        if(mHotel!=null) {
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText(mHotel.getName());
            mTitleTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            mTitleTv.getPaint().setAntiAlias(true);//抗锯齿
        }

//        mRightTv.setVisibility(View.VISIBLE);
//        mRightTv.setBackgroundResource(R.drawable.bg_edittext);
//        mRightTv.setTextColor(getResources().getColor(R.color.black));
//        mRightTv.setText(getString(R.string.refresh));
//
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRightTv.getLayoutParams();
//        layoutParams.height = DensityUtil.dip2px(this,35);
//        layoutParams.setMargins(0,0,DensityUtil.dip2px(this,15),0);
//        mRightTv.setPadding(DensityUtil.dip2px(this,10),0,DensityUtil.dip2px(this,10),0);

        mRightIv.setVisibility(View.VISIBLE);
        mRightIv.setImageResource(R.drawable.ico_refresh_noword);

        mSpHistoryAdapter = new TvBoxFixHistoryAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mSpHistoryRlv.setLayoutManager(manager);
        mSpHistoryRlv.setAdapter(mSpHistoryAdapter);

    }

    @Override
    public void setListeners() {
        mTitleTv.setOnClickListener(this);
        mRightIv.setOnClickListener(this);
        mHotelPositionAdapter.setOnItemCLickListener(this);
//        mHotelPositionAdapter.setOnFixBtnClickListener(this);
        mFixSpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FixDialog(HotelPositionInfoAcitivty.this, new FixDialog.OnSubmitBtnClickListener() {
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
                        AppApi.submitDamage(HotelPositionInfoAcitivty.this,fixHistoryResponse.getList().getVersion().getSmall_mac(),
                                hotel.getId(),boxState.getId()+"",comment,sb.toString(),state+"", 1+"",userid,HotelPositionInfoAcitivty.this);

                    }
                }, FixDialog.OperationType.TYPE_SMALL, fixHistoryResponse, damageConfig, mHotel).show();
            }
        });
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_BOX_STATECONFIG_JSON:
                if(obj instanceof ArrayList) {
                    ArrayList<BoxState> boxStateList = (ArrayList<BoxState>) obj;
                    mSession.setBoxStateList(boxStateList);
                }
                break;
            case POST_SUBMIT_DAMAGE_JSON:
                ShowMessage.showToast(HotelPositionInfoAcitivty.this,"提交成功");
                getData();
                break;
            case POST_FIX_HISTORY_JSON:
                if(obj instanceof FixHistoryResponse) {
                    mPostionListView.setVisibility(View.VISIBLE);
                    fixHistoryResponse = (FixHistoryResponse) obj;
                    initSmallPlatfromInfo(fixHistoryResponse);
                }
                break;
            case POST_DAMAGE_CONFIG_JSON:
                if(obj instanceof DamageConfig) {
                    damageConfig = (DamageConfig) obj;
                    List<DamageConfig.DamageInfo> list = damageConfig.getList();
                    if(list!=null&&list.size()>0) {
                        mSession.setDamageConfig(damageConfig);
                    }
                }
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method, obj);
        switch (method) {
            case POST_FIX_HISTORY_JSON:
                mPostionListView.setVisibility(View.GONE);
                break;
        }
    }

    private void initSmallPlatfromInfo(FixHistoryResponse fixHistoryResponse) {
        FixHistoryResponse.PositionInfo list = fixHistoryResponse.getList();
        if (list != null) {
            FixHistoryResponse.PositionInfo.VersionBean version = list.getVersion();
            if (version != null) {
                String new_small = version.getNew_small();
                mSpVersionTv.setText("发布小平台版本号：" + new_small);

                FixHistoryResponse.PositionInfo.VersionBean.LastSmallBean last_small = version.getLast_small();
                int last_small_state = last_small.getLast_small_state();
                String last_small_pla = last_small.getLast_small_pla();
                if (last_small_state == 1) {
                    mSpState.setImageResource(R.drawable.cirlce_green);
                } else {
                    mSpState.setImageResource(R.drawable.cirlce_red);
                }
                mLastSpVersionTv.setText("最后小平台版本号：" + last_small_pla);

                FixHistoryResponse.PositionInfo.VersionBean.LastHeartTimeBean last_heart_time = version.getLast_heart_time();
                int lstate = last_heart_time.getLstate();
                String ltime = last_heart_time.getLtime();
                mLastXintiao.setText("最后小平台心跳时间：" + ltime);

                String pla_inner_ip = version.getPla_inner_ip();
                String pla_out_ip = version.getPla_out_ip();
                if(!TextUtils.isEmpty(pla_inner_ip)) {
                    mBoxInnerIpTv.setText("小平台内网IP："+pla_inner_ip);
                }else {
                    mBoxInnerIpTv.setText("小平台内网IP：无");
                }

                if(!TextUtils.isEmpty(pla_out_ip)) {
                    mBoxOutIpTv.setText("小平台外网IP："+pla_out_ip);
                }else {
                    mBoxOutIpTv.setText("小平台外网IP：无");
                }

                if (lstate == 1) {
                    mLastXintiaoIV.setImageResource(R.drawable.cirlce_green);
                } else {
                    mLastXintiaoIV.setImageResource(R.drawable.cirlce_red);
                }

                List<FixHistoryResponse.PositionInfo.BoxInfoBean.RepaireInfo> repair_record = version.getRepair_record();
                if(repair_record==null||repair_record.size()==0) {
                    mFixHintTv.setVisibility(View.INVISIBLE);
                }else {
                    mFixHintTv.setVisibility(View.VISIBLE);
                    mSpHistoryAdapter.setData(repair_record);
                }
            }

            String banwei = list.getBanwei();
            mPositionDesc.setText(banwei);
            List<FixHistoryResponse.PositionInfo.BoxInfoBean> box_info = list.getBox_info();
            mHotelPositionAdapter.setData(box_info);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_center:
                Intent intent = new Intent(this,HotelMacInfoActivity.class);
                intent.putExtra("hotel",mHotel);
                startActivity(intent);
                break;
            case R.id.iv_right:
                ShowMessage.showToast(this,"刷新数据");
                getData();
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }


    @Override
    public void onItemClick(FixHistoryResponse.PositionInfo.BoxInfoBean boxInfoBean, int position) {
        Intent intent = new Intent(this,BoxDetailActivity.class);
        intent.putExtra("box_id",boxInfoBean.getBox_id());
        intent.putExtra("box_name",boxInfoBean.getBoxname());
        intent.putExtra("box_mac",boxInfoBean.getMac());
        intent.putExtra("hotel",mHotel);
        startActivity(intent);
    }
}
