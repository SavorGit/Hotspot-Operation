package com.savor.operation.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
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
import com.common.api.widget.ScrollListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.savor.operation.R;
import com.savor.operation.SavorApplication;
import com.savor.operation.adapter.FixTaskListAdapter;
import com.savor.operation.bean.BaseBoxInfo;
import com.savor.operation.bean.BoxInfo;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.RepairInfo;
import com.savor.operation.bean.Task;
import com.savor.operation.core.AppApi;
import com.savor.operation.enums.SearchHotelOpType;
import com.savor.operation.utils.ConstantValues;
import com.savor.operation.utils.OSSClientUtil;
import com.savor.operation.widget.LoadingDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.ViewGroup.FOCUS_AFTER_DESCENDANTS;
import static com.savor.operation.adapter.FixTaskListAdapter.REQUEST_CODE_IMAGE;
import static com.savor.operation.adapter.FixTaskListAdapter.TAKE_PHOTO_REQUEST;

/**
 * 任务详情页面（公共页面）
 */
public class TaskActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_SEARCH = 0x1;
    public static final int RESULT_CODE_SEARCH = 0x2;
    private static final int MESSAGE_UPLOAD_ERROR = 0x3;
    private static final int MESSAGE_UPLOAD_SUCCESS = 0x4;
    private static final int MESSAGE_PUBLISH = 0x5;
    private ScrollListView mTaskLv;
//    private View mHeadView;
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
    private EditText mAddressEt;
    private RadioGroup mEmergcyRG;
    private List<RepairInfo> boxList = new ArrayList<>();
    private FixTaskListAdapter mTaskAdapter;
    private List<BoxInfo> mBoxList;
    private TextView mTitleTv;
    private Task mTask;
    private int nextPos;
    /**总进度，需要上传几张图片*/
    private int imageCount;
    /**当前上传进度(包括成功和失败)，已经上传了几张*/
    private int uploadCount;
    /**当前上传进度(只包括成功)，已经上传了几张*/
    private int uploadSuccessCount;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_UPLOAD_SUCCESS:
                    uploadSuccessCount++;
                case MESSAGE_UPLOAD_ERROR:
                    uploadCount++;
                    loadingDialog.updateProgress("正在上传图片："+uploadSuccessCount+"/"+imageCount);
                    if(uploadCount == imageCount) {
                        mHandler.sendEmptyMessageDelayed(MESSAGE_PUBLISH,500);
                    }
                    break;
                case MESSAGE_PUBLISH:
                    publish();
                    break;
            }
        }
    };
    private LoadingDialog loadingDialog;
    private EditText mRemarkEt;

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
        mTask = (Task) intent.getSerializableExtra("task");
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTaskLv = (ScrollListView) findViewById(R.id.lv_task_list);
        mRightTv = (TextView) findViewById(R.id.tv_right);
        mTitleTv = (TextView) findViewById(R.id.tv_center);

        initHeaderView();

    }

    private void initHeaderView() {
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setText("发布");

//        mHeadView = View.inflate(this, R.layout.header_view_task, null);
        mNumLayout = (RelativeLayout) findViewById(R.id.rl_num);
        mAddTv = (TextView) findViewById(R.id.tv_add);
        mReduceTv = (TextView) findViewById(R.id.tv_reduce);
        mBoxNumTv = (TextView) findViewById(R.id.tv_box_num);
        mHotelNameTv = (TextView) findViewById(R.id.tv_select_hotel);
        mSearchLayout = (RelativeLayout) findViewById(R.id.rl_select_hotel);
        mContactEt = (EditText)findViewById(R.id.et_contact);
        mPhoneEt = (EditText) findViewById(R.id.et_phone);
        mAddressEt = (EditText) findViewById(R.id.et_address);
        mEmergcyRG = (RadioGroup) findViewById(R.id.rg_emergcy);
        mRemarkEt = (EditText) findViewById(R.id.et_remark);
        if (actionType == PublishTaskActivity.TaskType.INFO_CHECK || actionType == PublishTaskActivity.TaskType.NETWORK_REMOULD) {
            mNumLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public void setViews() {

        if(mTask!=null) {
            String type_name = mTask.getType_name();
            if(!TextUtils.isEmpty(type_name)) {
                mTitleTv.setText(type_name);
            }
        }

        mTaskAdapter = new FixTaskListAdapter(this);
        mTaskAdapter.setData(boxList);
        mTaskLv.setAdapter(mTaskAdapter);
//        mTaskLv.addHeaderView(mHeadView);

        mTaskLv.setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
//        initEditText(mContactEt);
//        initEditText(mPhoneEt);
//        initEditText(mAddressEt);
//        initEditText(mRemarkEt);
        resetBox();
    }

    private void initEditText(EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((ViewGroup) v.getParent())
                        .setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                return false;
            }
        });
    }

    private void resetBox() {
        mBoxNumTv.setText("1");

        boxList.clear();
        if(actionType == PublishTaskActivity.TaskType.FIX) {
            RepairInfo repairInfo = new RepairInfo();
            repairInfo.setBoxInfoList(mBoxList);
            boxList.add(0, repairInfo);
            mTaskAdapter.notifyDataSetChanged();
        }
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
                        String fault_desc = repairInfo.getFault_desc();

                        if(TextUtils.isEmpty(box_id)) {
                            ShowMessage.showToast(this,"请选择版位");
                            return;
                        }

                    }

                    for(RepairInfo repairInfo : infos) {
                        String box_id = repairInfo.getBox_id();
                        String fault_desc = repairInfo.getFault_desc();

                        if(TextUtils.isEmpty(fault_desc)) {
                            ShowMessage.showToast(this,"请填写故障现象");
                            return;
                        }

                    }

                }

                List<RepairInfo> data = mTaskAdapter.getData();
                nextPos = 0;
                if(data!=null&&data.size()>0) {
                    if(isHasUploadPic(data)) {
                        imageCount = getImageCount(data);
                        loadingDialog = new LoadingDialog(this,"正在上传图片：0/"+imageCount);
                        loadingDialog.show();
                        uploadPic(data);
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
                    // 如果当前是非安装验收类型，数量达到最大不可在操作
                    if(actionType != PublishTaskActivity.TaskType.SETUP_AND_CHECK) {
                        if(boxNum>=mBoxList.size()) {
                            ShowMessage.showToast(this,"不能大于该酒楼总版位数量");
                            return;
                        }
                    }
                    boxNum += 1;
                    mBoxNumTv.setText(String.valueOf(boxNum));
                } catch (Exception e) {
                    mBoxNumTv.setText("0");
                }

                if(actionType == PublishTaskActivity.TaskType.FIX) {
                    RepairInfo repairInfo = new RepairInfo();
                    repairInfo.setBoxInfoList(mBoxList);
                    boxList.add(repairInfo);
                    mTaskAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_reduce:
                num = mBoxNumTv.getText().toString();
                try {
                    int boxNum = Integer.valueOf(num);
                    boxNum -= 1;
                    if (boxNum < 1) {
                        boxNum = 1;
                    }
                    mBoxNumTv.setText(String.valueOf(boxNum));
                } catch (Exception e) {
                    mBoxNumTv.setText("0");
                }
                if (boxList.size() > 1) {
                    boxList.remove(boxList.size()-1);
                    mTaskAdapter.notifyDataSetChanged();
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
                task_type = "4";
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
                task_type = "1";
                break;
            case NETWORK_REMOULD:
                task_type = "8";
                break;
            case SETUP_AND_CHECK:
                task_type = "2";
                break;
        }

        String tv_nums = "";
        if (actionType == PublishTaskActivity.TaskType.SETUP_AND_CHECK || actionType == PublishTaskActivity.TaskType.FIX) {
            tv_nums = mBoxNumTv.getText().toString();
        }

        String desc = mRemarkEt.getText().toString();

        AppApi.publishTask(this, address, contact,desc, hotelId, phone, publish_user_id, repair_info, task_emerge, task_type, tv_nums, this);
    }

    private void uploadPic(final List<RepairInfo> infos) {
//        final RepairInfo repairInfo = infos.get(startPos);
        for(final RepairInfo repairInfo: infos) {
            String fault_img_url = repairInfo.getFault_img_url();
            if(!TextUtils.isEmpty(fault_img_url)) {
                final OSSClient ossClient = OSSClientUtil.getInstance().getOSSClient(this);
                String imagePath = repairInfo.getFault_img_url();
                File file = new File(imagePath);
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String dateStr = simpleDateFormat.format(date);
                final String objectKey = "log/resource/operation/mobile/box/"+dateStr+"/"+file.getName();
                //            // 构造上传请求
                PutObjectRequest put = new PutObjectRequest(ConstantValues.BUCKET_NAME,objectKey , imagePath);
                try {
                    ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                        @Override
                        public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                            String imageUrl = ossClient.presignPublicObjectURL(ConstantValues.BUCKET_NAME, objectKey);
                            repairInfo.setFault_img_url(imageUrl);
                            mHandler.sendEmptyMessage(MESSAGE_UPLOAD_SUCCESS);
                        }

                        @Override
                        public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                            mHandler.sendEmptyMessage(MESSAGE_UPLOAD_ERROR);
                        }
                    });
                }catch (Exception e) {}
            }
        }

//        if(!TextUtils.isEmpty(repairInfo.getFault_img_url())) {
//
//        }

//        if(!TextUtils.isEmpty(repairInfo.getFault_img_url())) {
//            final OSSClient ossClient = OSSClientUtil.getInstance().getOSSClient(this);
//            String imagePath = repairInfo.getFault_img_url();
//            File file = new File(imagePath);
//            Date date = new Date(System.currentTimeMillis());
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//            String dateStr = simpleDateFormat.format(date);
//            final String objectKey = "log/resource/operation/mobile/box/"+dateStr+"/"+file.getName();
//            // 构造上传请求
//            PutObjectRequest put = new PutObjectRequest(ConstantValues.BUCKET_NAME,objectKey , imagePath);
//            try {
//                ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//                    @Override
//                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
//                        String imageUrl = ossClient.presignPublicObjectURL(ConstantValues.BUCKET_NAME, objectKey);
//                        repairInfo.setFault_img_url(imageUrl);
//                        nextPos = startPos+1;
//                        if(nextPos <infos.size()) {
//                            uploadPic(infos, nextPos);
//                        }else {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    publish();
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
//                        repairInfo.setFault_img_url("");
//                        int nextPos = startPos+1;
//                        if(nextPos<infos.size()) {
//                            uploadPic(infos,nextPos);
//                        }else {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    publish();
//                                }
//                            });
//                        }
//                    }
//                });
//
//            }catch (Exception e) {}
//        }else {
//            nextPos = startPos+1;
//            if(nextPos<infos.size()) {
//                uploadPic(infos, nextPos);
//            }else {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        publish();
//                    }
//                });
//            }
//        }
    }

    private int getImageCount(List<RepairInfo> infos) {
        int count = 0;
        for(int i = 0;i<infos.size();i++) {
            RepairInfo repairInfo = infos.get(i);
            if(repairInfo!=null&&!TextUtils.isEmpty(repairInfo.getFault_img_url())) {
                count++;
            }
        }
        return count;
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
                    String contractor = hotel.getContractor();
                    String mobile = hotel.getMobile();
                    String addr = hotel.getAddr();

                    if (!TextUtils.isEmpty(name)) {
                        mHotelNameTv.setText(name);
                    }else {
                        mHotelNameTv.setText(R.string.content_empty);
                    }

                    mContactEt.setText(getFormatStr(contractor));
                    mPhoneEt.setText(getFormatStr(mobile));
                    mAddressEt.setText(getFormatStr(addr));

                    resetBox();

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
                    if(boxList!=null) {
                        for(RepairInfo repairInfo:boxList) {
                            repairInfo.setBoxInfoList(mBoxList);
                        }
                        mTaskAdapter.notifyDataSetChanged();
                    }
                }

                break;
            case POST_PUBLISH_JSON:
                if(loadingDialog!=null&&loadingDialog.isShowing())
                    loadingDialog.dismiss();
                ShowMessage.showToast(this, "发布成功");
                finish();
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_PUBLISH_JSON:
                loadingDialog.dismiss();
                break;
        }
    }
}
