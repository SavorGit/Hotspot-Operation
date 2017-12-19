package com.savor.operation.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.adapter.SingleHotelPositionAdapter;
import com.savor.operation.bean.DamageConfig;
import com.savor.operation.bean.FixBean;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.PositionListInfo;
import com.savor.operation.core.AppApi;
import com.savor.operation.utils.ConstantValues;
import com.savor.operation.utils.OSSClientUtil;
import com.savor.operation.widget.SingleFixDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.savor.operation.widget.SingleFixDialog.REQUEST_CODE_IMAGE;


/**
 * 酒楼版位信息
 */
public class SingleHotelPositionInfoAcitivty extends BaseActivity implements  View.OnClickListener, SingleHotelPositionAdapter.OnFixBtnClickListener, SingleHotelPositionAdapter.OnSignBtnClickListener {

    private static final int TYPE_FIX = 0x1;
    private static final int TYPE_SIGN = 0x2;
    private ListView mPostionListView;
    private ImageView mBackBtn;
    private TextView mTitleTv;
    private SingleHotelPositionAdapter mSingleHotelPositionAdapter;
    private Hotel mHotel;
    private TextView mSpVersionTv;
    private TextView mLastSpVersionTv;
    private TextView mLastXintiao;
    private ImageView mSpState;
    private ImageView mLastXintiaoIV;
    private TextView mPositionDesc;
    private DamageConfig damageConfig;
    private PositionListInfo positionListInfo;
    private TextView mFixHintTv;
    private RecyclerView mSpHistoryRlv;
    private FixBean fixBean;
    public static int RESULT_CODE_SELECT = 100;
    public static int REQUEST_CODE_SELECT = 101;
    private SingleFixDialog singleFixDialog;
    private int currentOType;
    private String cacheImagePath;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_position_info_acitivty);

        handleIntent();
        getViews();
        setViews();
        setListeners();

        getData();
        getDamageInfo();

    }

    private void getDamageInfo() {
        AppApi.getSingleDamageConfig(this,this);
    }

    private void getData() {
        AppApi.getPositionList(this,mHotel.getId(),this);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        mHotel = (Hotel) intent.getSerializableExtra("hotel");
    }

    @Override
    public void getViews() {
        fixBean = new FixBean();

        View headerView = View.inflate(this,R.layout.header_view_position_list,null);
        mPositionDesc = (TextView) headerView.findViewById(R.id.tv_position_desc);
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTitleTv = (TextView) findViewById(R.id.tv_center);
        mPostionListView = (ListView) findViewById(R.id.lv_hotel_position_list);
        mPostionListView.addHeaderView(headerView);

        damageConfig = mSession.getDamageConfig();
    }

    @Override
    public void setViews() {
        mBackBtn.setOnClickListener(this);
        mSingleHotelPositionAdapter = new SingleHotelPositionAdapter(this);
        mPostionListView.setAdapter(mSingleHotelPositionAdapter);

        if(mHotel!=null) {
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText(mHotel.getName());
            mTitleTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            mTitleTv.getPaint().setAntiAlias(true);//抗锯齿
        }


    }

    @Override
    public void setListeners() {
        mTitleTv.setOnClickListener(this);
        mSingleHotelPositionAdapter.setOnFixBtnClickListener(this);
        mSingleHotelPositionAdapter.setOnSignBtnClickListener(this);
//        mPostionListView.setOnItemClickListener(this);
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_SINGLE_SUBMIT_DAMAGE_JSON:
                if(currentOType == TYPE_FIX) {
                    ShowMessage.showToast(this,"提交成功");
                    singleFixDialog.loadFinish();
                    singleFixDialog.dismiss();
                }else {
                    ShowMessage.showToast(this,"签到成功");
                }
                getData();
                break;
            case POST_POSITION_LIST_JSON:
                if(obj instanceof PositionListInfo) {
                    mPostionListView.setVisibility(View.VISIBLE);
                    positionListInfo = (PositionListInfo) obj;
                    if(positionListInfo!=null) {
                        PositionListInfo.PositionInfo list = positionListInfo.getList();
                        if(list!=null) {
                            String banwei = list.getBanwei();
                            mPositionDesc.setText(banwei);
                            List<PositionListInfo.PositionInfo.BoxInfoBean> box_info = list.getBox_info();
                            mSingleHotelPositionAdapter.setData(box_info);
                        }
                    }
                }
                break;
            case POST_SINGLE_DAMAGE_CONFIG_JSON:
                if(obj instanceof DamageConfig) {
                    damageConfig = (DamageConfig) obj;
                    List<DamageConfig.DamageInfo> list = damageConfig.getList();
                    if(list!=null&&list.size()>0) {
                        mSession.setSingleDamageConfig(damageConfig);
                    }
                }
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method, obj);
        switch (method) {
            case POST_SUBMIT_DAMAGE_JSON:
                if(currentOType == TYPE_FIX) {
//                    if(!TextUtils.isEmpty(cacheImagePath)) {
//                        File file = new File(cacheImagePath);
//                        file.delete();
//                    }
                    ShowMessage.showToast(this,"提交失败");
                    singleFixDialog.loadFinish();
                    singleFixDialog.dismiss();
                }else {
                    ShowMessage.showToast(this,"签到失败");
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_center:
                Intent intent = new Intent(this,SingleHotelMacInfoActivity.class);
                intent.putExtra("hotel",mHotel);
                startActivity(intent);
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }

    @Override
    public void onFixBtnClick(final PositionListInfo.PositionInfo.BoxInfoBean boxInfoBean) {
        currentOType = TYPE_FIX;
        singleFixDialog = new SingleFixDialog(this, new SingleFixDialog.OnSubmitBtnClickListener() {
            @Override
            public void onSubmitClick(SingleFixDialog.OperationType type, PositionListInfo.PositionInfo.BoxInfoBean fixHistoryResponse, final SingleFixDialog.FixState isResolve, final List<String> damageDesc, final String comment, final Hotel hotel, String imagePath) {
                singleFixDialog.startLoading();
                final OSSClient ossClient = OSSClientUtil.getInstance().getOSSClient(SingleHotelPositionInfoAcitivty.this);
                cacheImagePath = imagePath;
                if(TextUtils.isEmpty(imagePath)) {
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0;i<damageDesc.size();i++) {
                        if(damageDesc.size() == 1 || i == damageDesc.size()-1) {
                            sb.append(damageDesc.get(i));
                        }else {
                            sb.append(damageDesc.get(i)+",");
                        }
                    }
                    AppApi.submitSingleDamage(SingleHotelPositionInfoAcitivty.this,boxInfoBean.getBid(),hotel.getId(),
                            comment,"",sb.toString(),"2",isResolve== SingleFixDialog.FixState.RESOLVED?"1":"2",
                            mSession.getLoginResponse().getUserid(),mSession.getCurrentLocation(),SingleHotelPositionInfoAcitivty.this);
                    return;
                }

                File file = new File(imagePath);
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String dateStr = simpleDateFormat.format(date);

                final String objectKey = "log/resource/standalone/mobile/"+dateStr+"/"+file.getName();
                // 构造上传请求
                PutObjectRequest put = new PutObjectRequest(ConstantValues.BUCKET_NAME,objectKey , imagePath);
                try {
                    ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                        @Override
                        public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    submit(isResolve, damageDesc, comment, hotel, ossClient, objectKey, boxInfoBean);
                                }
                            });

                        }

                        @Override
                        public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ShowMessage.showToast(SingleHotelPositionInfoAcitivty.this,"上传图片失败");
                                    singleFixDialog.loadFinish();
                                }
                            });
                        }
                    });

                }catch (Exception e){}
            }
        }, SingleFixDialog.OperationType.TYPE_BOX,boxInfoBean,mSession.getSingleDamageConfig(),mHotel);
        singleFixDialog.show();
    }

    private void submit(SingleFixDialog.FixState isResolve, List<String> damageDesc, String comment, Hotel hotel, OSSClient ossClient, String objectKey, PositionListInfo.PositionInfo.BoxInfoBean boxInfoBean) {
        String url = ossClient.presignPublicObjectURL(ConstantValues.BUCKET_NAME, objectKey);

        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<damageDesc.size();i++) {
            if(damageDesc.size() == 1 || i == damageDesc.size()-1) {
                sb.append(damageDesc.get(i));
            }else {
                sb.append(damageDesc.get(i)+",");
            }
        }
        AppApi.submitSingleDamage(SingleHotelPositionInfoAcitivty.this,boxInfoBean.getBid(),hotel.getId(),
                comment,url,sb.toString(),"2",isResolve== SingleFixDialog.FixState.RESOLVED?"1":"2",
                mSession.getLoginResponse().getUserid(),mSession.getCurrentLocation(),SingleHotelPositionInfoAcitivty.this);
    }

    @Override
    public void onSignBtnClick(PositionListInfo.PositionInfo.BoxInfoBean boxInfoBean) {
        currentOType = TYPE_SIGN;
        AppApi.submitSingleDamage(SingleHotelPositionInfoAcitivty.this,boxInfoBean.getBid(),mHotel.getId(),
                "","","","1","",
                mSession.getLoginResponse().getUserid(),mSession.getCurrentLocation(),SingleHotelPositionInfoAcitivty.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == REQUEST_CODE_IMAGE&&resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            singleFixDialog.updateImage(imagePath);
        }
    }
}
