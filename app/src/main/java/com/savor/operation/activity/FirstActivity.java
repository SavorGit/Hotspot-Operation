package com.savor.operation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.common.api.utils.FileUtils;
import com.common.api.utils.ShowMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.savor.operation.R;
import com.savor.operation.SavorApplication;
import com.savor.operation.bean.ExecutorInfo;
import com.savor.operation.bean.SmallPlatformByGetIp;
import com.savor.operation.bean.TaskDetail;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.utils.ConstantValues;
import com.savor.operation.utils.OSSClientUtil;
import com.savor.operation.utils.WifiUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.savor.operation.activity.TaskDetailActivity.RESULT_CODE_BACK;
import static com.savor.operation.adapter.FixTaskListAdapter.REQUEST_CODE_IMAGE;

public class FirstActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener {
    private Context context;
    private String URL;
    private String checkUrl = "";
    private TextView pic;
    private ImageView iv;
    private TextView video;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WifiManager.WIFI_STATE_ENABLED:
                    String wifiName = WifiUtil.getWifiName(FirstActivity.this);
//                    initSSDP();
//                    getIp();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
//                    stopSSDPService();
//                    reset();
                    break;
//                case MSG_CHECK_SSDP:
//                    if(mHotelId==0 ) {
//                        stopSSDPService();
//                    }
//                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_4g_layout);
        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        pic = (TextView) findViewById(R.id.pic);
        iv = (ImageView) findViewById(R.id.iv);
        video = (TextView) findViewById(R.id.video);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {
        pic.setOnClickListener(this);
        video.setOnClickListener(this);
    }

    private void ChoosePicDialog(){
        //currentTakePhonePos = position;
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE&&resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            updateImagePath(imagePath);
//            if ("1".equals(task_type_id)) {//信息检测
//                checkDialog.updateImagePath(imagePath);
//            }else if ("2".equals(task_type_id)){//安装验收
//                installDialog.updateImagePath(imagePath);
//            }else if ("4".equals(task_type_id)) {//维修
//                maintenanceDialog.updateImagePath(imagePath);
//            }else if ("8".equals(task_type_id)) {//网络改造
//                detectDialog.updateImagePath(imagePath);
//            }

        }
    }


    public void updateImagePath(String path) {
        //       if(elist!=null && elist.size()>=currentTakePhonePos) {

        String cachePath = ((SavorApplication)getApplication()).imagePath;
        File dir = new File(cachePath);
        if(!dir.exists()) {
            dir.mkdirs();
        }

       // String box_id = currentExecutorInfoBean.getBox_id();
        long timeMillis = System.currentTimeMillis();
        String key = "FCD5D900B377"+"_"+timeMillis+".jpg";
        String copyPath = dir.getAbsolutePath()+File.separator+key;
        //String copyPath = dir.getAbsolutePath()+File.separator;

        File sFile = new File(path);
        FileUtils.copyFile(sFile, copyPath);
        URL = copyPath;
        Glide.with(context).load(copyPath).into(iv);
        hotelCheckUploadPic(URL);

    }

    private void hotelCheckUploadPic(final String URL) {
        final String mURL = URL;
        if(!TextUtils.isEmpty(mURL)) {
            final OSSClient ossClient = OSSClientUtil.getInstance().getOSSClient(this);
            String imagePath = mURL;
            File file = new File(imagePath);
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = simpleDateFormat.format(date);
            final String objectKey = "log/resource/operation/mobile/hotel/"+dateStr+"/"+file.getName();
            // 构造上传请求
            PutObjectRequest put = new PutObjectRequest(ConstantValues.BUCKET_NAME,objectKey , imagePath);
            try {
                ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                        String imageUrll = ossClient.presignPublicObjectURL(ConstantValues.BUCKET_NAME, objectKey);
                        checkUrl = imageUrll;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                checkPublish(checkUrl);
                            }
                        });

                    }

                    @Override
                    public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                        checkUrl = "";
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                checkPublish(checkUrl);
                            }
                        });
                    }
                });

            }catch (Exception e) {}
        }else {
            //hideLoadingLayout();
            ShowMessage.showToast(this,"请上传图片");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
//                setResult(RESULT_CODE_BACK);
//                finish();
                break;
            case R.id.pic:
                ChoosePicDialog();

                break;
            case R.id.video:
                Intent intent = new Intent();
                intent.setClass(FirstActivity.this,MediaGalleryActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void checkPublish( String url) {
//        real_tv_nums = "";
//        List<String> ulist = new ArrayList<String>();
//        ulist.add(url);
//        Gson gson = new Gson();
//        String  repair_info = gson.toJson(ulist, new TypeToken<List<String>>() {
//        }.getType());
        AppApi.Forscreen(context, url ,this);


    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        hideLoadingLayout();
        switch (method){
            case POST_FORSCREEN_JSON:
                ShowMessage.showToast(FirstActivity.this,"投屏成功");
                break;



        }

    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
      //  hideLoadingLayout();

        switch (method){
            case GET_IP_JSON:
                break;
            case POST_REFUSE_TASK_JSON:
//                if (refuseDialog != null) {
//                    refuseDialog.dismiss();
//                }
                break;
            case POST_FORSCREEN_JSON:
                if (obj instanceof ResponseErrorMessage){
                    ResponseErrorMessage errorMessage = (ResponseErrorMessage)obj;
                    String statusCode = String.valueOf(errorMessage.getCode());
                    String msg = errorMessage.getMessage();
                    if (!TextUtils.isEmpty(msg)) {
                        ShowMessage.showToast(context,errorMessage.getMessage());
                    }else {
                        ShowMessage.showToast(context,"操作失败");
                    }

                }else {
                    ShowMessage.showToast(context,"操作失败");
                }
                break;
        }
    }
}
