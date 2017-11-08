package com.savor.operation.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.common.api.utils.FileUtils;
import com.common.api.utils.ShowMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.savor.operation.R;
import com.savor.operation.SavorApplication;
import com.savor.operation.adapter.FixTaskListAdapter;
import com.savor.operation.bean.BaseBoxInfo;
import com.savor.operation.bean.BoxInfo;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.RepairInfo;
import com.savor.operation.core.AppApi;
import com.savor.operation.enums.SearchHotelOpType;
import com.savor.operation.utils.ConstantValues;
import com.savor.operation.utils.OSSClientUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.savor.operation.adapter.FixTaskListAdapter.REQUEST_CODE_IMAGE;
import static com.savor.operation.adapter.FixTaskListAdapter.TAKE_PHOTO_REQUEST;

/**
 * 任务详情页面（公共页面）
 */
public class TaskActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_SEARCH = 0x1;
    public static final int RESULT_CODE_SEARCH = 0x2;
    private ListView mTaskLv;
    private View mHeadView;
    private ImageView mBackBtn;
    private PublishTaskActivity.TaskType actionType;
    private RelativeLayout mNumLayout;
    private TextView mAddTv;
    private TextView mReduceTv;
    private TextView mBoxNumTv;
    private RelativeLayout mSearchLayout;
    private Hotel hotel;
    private TextView mHotelNameTv;
    private TextView mRightTv;
    private EditText mContactEt;
    private EditText mPhoneEt;
    private TextView mAddressEt;
    private RadioGroup mEmergcyRG;
    private List<RepairInfo> boxList = new ArrayList<>();
    private FixTaskListAdapter mTaskAdapter;
    private List<BoxInfo> mBoxList;
    private ProgressBar mloadingPb;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        handleIntent();
        getViews();
        setViews();
        setListeners();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        actionType = (PublishTaskActivity.TaskType) intent.getSerializableExtra("type");
    }

    @Override
    public void getViews() {
        mloadingPb = (ProgressBar) findViewById(R.id.pb_loading);
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTaskLv = (ListView) findViewById(R.id.lv_task_list);
        mRightTv = (TextView) findViewById(R.id.tv_right);

        initHeaderView();

    }

    private void initHeaderView() {
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setText("发布");

        mHeadView = View.inflate(this, R.layout.header_view_task, null);
        mNumLayout = (RelativeLayout) mHeadView.findViewById(R.id.rl_num);
        mAddTv = (TextView) mHeadView.findViewById(R.id.tv_add);
        mReduceTv = (TextView) mHeadView.findViewById(R.id.tv_reduce);
        mBoxNumTv = (TextView) mHeadView.findViewById(R.id.tv_box_num);
        mHotelNameTv = (TextView) mHeadView.findViewById(R.id.tv_select_hotel);
        mSearchLayout = (RelativeLayout) mHeadView.findViewById(R.id.rl_select_hotel);
        mContactEt = (EditText) mHeadView.findViewById(R.id.et_contact);
        mPhoneEt = (EditText) mHeadView.findViewById(R.id.et_phone);
        mAddressEt = (EditText) mHeadView.findViewById(R.id.et_address);
        mEmergcyRG = (RadioGroup) mHeadView.findViewById(R.id.rg_emergcy);

        if (actionType == PublishTaskActivity.TaskType.INFO_CHECK || actionType == PublishTaskActivity.TaskType.NETWORK_REMOULD) {
            mNumLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public void setViews() {
        boxList.clear();

        mTaskAdapter = new FixTaskListAdapter(this);
        mTaskAdapter.setData(boxList);
        mTaskLv.setAdapter(mTaskAdapter);
        mTaskLv.addHeaderView(mHeadView);
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);
        mAddTv.setOnClickListener(this);
        mReduceTv.setOnClickListener(this);
        mSearchLayout.setOnClickListener(this);
        mRightTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String num;
        Intent intent;
        String hotelId = "";
        switch (v.getId()) {
            case R.id.tv_right:
                if (hotel != null) {
                    hotelId = hotel.getId();
                }

                // 校验必须的参数
                if (TextUtils.isEmpty(hotelId)) {
                    ShowMessage.showToast(this, "请选择酒楼");
                    return;
                }

                if(actionType == PublishTaskActivity.TaskType.FIX) {
                    // 校验如果某一个没选择版位不允许发布
                    List<RepairInfo> infos = mTaskAdapter.getData();
                    for(RepairInfo repairInfo : infos) {
                        String box_id = repairInfo.getBox_id();
                        if(TextUtils.isEmpty(box_id)) {
                            ShowMessage.showToast(this,"请选择版位");
                            return;
                        }
                    }
                }

                List<RepairInfo> data = mTaskAdapter.getData();
                if(data!=null&&data.size()>0) {
                    if(isHasUploadPic(data)) {
                        mloadingPb.setVisibility(View.VISIBLE);
                        uploadPic(data,0);
                    }else {
                        publish();
                    }
                }else {
                    publish();
                }

                break;
            case R.id.rl_select_hotel:
                intent = new Intent(this, SearchActivity.class);
                intent.putExtra("type", SearchHotelOpType.PUBLIS_TASK);
                startActivityForResult(intent, REQUEST_CODE_SEARCH);
                break;
            case R.id.tv_add:
                if (hotel != null) {
                    hotelId = hotel.getId();
                }
                if (TextUtils.isEmpty(hotelId)) {
                    ShowMessage.showToast(this, "请选择酒楼");
                    return;
                }

                num = mBoxNumTv.getText().toString();
                try {
                    int boxNum = Integer.valueOf(num);
                    boxNum += 1;
                    mBoxNumTv.setText(String.valueOf(boxNum));
                } catch (Exception e) {
                    mBoxNumTv.setText("0");
                }

                if(actionType == PublishTaskActivity.TaskType.FIX) {
                    RepairInfo repairInfo = new RepairInfo();
                    repairInfo.setBoxInfoList(mBoxList);
                    boxList.add(0, repairInfo);
                    mTaskAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_reduce:
                num = mBoxNumTv.getText().toString();
                try {
                    int boxNum = Integer.valueOf(num);
                    boxNum -= 1;
                    if (boxNum < 0) {
                        boxNum = 0;
                    }
                    mBoxNumTv.setText(String.valueOf(boxNum));
                } catch (Exception e) {
                    mBoxNumTv.setText("0");
                }
                if (boxList.size() > 0) {
                    boxList.remove(0);
                    mTaskAdapter.notifyDataSetChanged();
                } else {
                    boxList.clear();
                }
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }

    private void publish() {

        String address = mAddressEt.getText().toString();
        String contact = mContactEt.getText().toString();
        String phone = mPhoneEt.getText().toString();

        String hotelId = "";
        if (hotel != null) {
            hotelId = hotel.getId();
        }

        String publish_user_id = mSession.getLoginResponse().getUserid();

        int checkedRadioButtonId = mEmergcyRG.getCheckedRadioButtonId();
        String task_emerge = "";
        if (checkedRadioButtonId == R.id.rb_exigence) {
            task_emerge = "2";
        } else if (checkedRadioButtonId == R.id.rb_normal) {
            task_emerge = "3";
        }

        String repair_info = "";

        /**3，信息监测 4，网络改造 6，安装与验收 7，维修*/
        String task_type = "";
        switch (actionType) {
            case FIX:
                task_type = "7";
                List<BaseBoxInfo> boxInfos = new ArrayList<>();
                List<RepairInfo> data = mTaskAdapter.getData();
                if(data!=null&&data.size()>0) {
                    for(int i = 0;i<data.size();i++) {
                        RepairInfo repairInfo = data.get(i);
                        BaseBoxInfo boxInfo = new BaseBoxInfo();
                        boxInfo.setBox_id(repairInfo.getBox_id());
                        boxInfo.setFault_desc(repairInfo.getFault_desc());
                        boxInfo.setFault_img_url(repairInfo.getFault_img_url());
                        boxInfos.add(boxInfo);
                    }
                    Gson gson = new Gson();
                    repair_info = gson.toJson(boxInfos, new TypeToken<List<BaseBoxInfo>>() {
                    }.getType());
                }
                break;
            case INFO_CHECK:
                task_type = "3";
                break;
            case NETWORK_REMOULD:
                task_type = "4";
                break;
            case SETUP_AND_CHECK:
                task_type = "6";
                break;
        }

        String tv_nums = "";
        if (actionType == PublishTaskActivity.TaskType.SETUP_AND_CHECK || actionType == PublishTaskActivity.TaskType.FIX) {
            tv_nums = mBoxNumTv.getText().toString();
        }


        AppApi.publishTask(this, address, contact, hotelId, phone, publish_user_id, repair_info, task_emerge, task_type, tv_nums, this);
    }

    private void uploadPic(final List<RepairInfo> infos, final int startPos) {
        final RepairInfo repairInfo = infos.get(startPos);
        if(!TextUtils.isEmpty(repairInfo.getFault_img_url())) {
            final OSSClient ossClient = OSSClientUtil.getInstance().getOSSClient(this);
            String imagePath = repairInfo.getFault_img_url();
            File file = new File(imagePath);
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = simpleDateFormat.format(date);
            final String objectKey = "log/resource/operation/mobile/"+dateStr+"/"+file.getName();
            // 构造上传请求
            PutObjectRequest put = new PutObjectRequest(ConstantValues.BUCKET_NAME,objectKey , imagePath);
            try {
                ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                        String imageUrl = ossClient.presignPublicObjectURL(ConstantValues.BUCKET_NAME, objectKey);
                        repairInfo.setFault_img_url(imageUrl);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SEARCH && resultCode == RESULT_CODE_SEARCH) {
            if (data != null) {
                hotel = (Hotel) data.getSerializableExtra("hotel");
                if (hotel != null) {
                    String name = hotel.getName();
                    String id = hotel.getId();
                    if (!TextUtils.isEmpty(name)) {
                        mHotelNameTv.setText(name);
                    }

                    if (!TextUtils.isEmpty(id)) {
                        AppApi.getBoxList(this, id, this);
                    }
                }
            }
        } else if (requestCode == TAKE_PHOTO_REQUEST && resultCode == Activity.RESULT_OK) {
            mTaskAdapter.updataPhotoPath();
        }else if (requestCode == REQUEST_CODE_IMAGE&&resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);

            mTaskAdapter.updateImagePath(imagePath);
        }

    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_BOX_LIST_JSON:
                if (obj instanceof List) {
                    mBoxList = (List<BoxInfo>) obj;
                }
                break;
            case POST_PUBLISH_JSON:
                mloadingPb.setVisibility(View.GONE);
                ShowMessage.showToast(this, "发布成功");
                finish();
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method,obj);
        switch (method) {
            case POST_PUBLISH_JSON:
                mloadingPb.setVisibility(View.GONE);
                break;
        }
    }
}
