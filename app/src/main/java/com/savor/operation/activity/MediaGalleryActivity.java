package com.savor.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.common.api.utils.FileUtils;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.SavorApplication;
import com.savor.operation.adapter.MediaAdapter;
import com.savor.operation.bean.MediaInfo;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.utils.ConstantValues;
import com.savor.operation.utils.MediaUtils;
import com.savor.operation.utils.OSSClientUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MediaGalleryActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener, AdapterView.OnItemClickListener {

    private List<MediaInfo> mVideoList = new ArrayList<>();
    private List<MediaInfo> mMediaList = new ArrayList<>();
    private GridView mMediasGv;
    private MediaAdapter mMediaListAdapter;
    private Context context;
    private String URL;
    private String checkUrl = "";
    /**加载本地图片完成*/
    public static final int LOAD_COMPLETE = 0x1;
    /**图片投屏*/
    public static final int PRO_IMAGE = 0x2;

    /**图片视频*/
    private static final int PROCESS_VIDEO = 0x11;

    private static final int HANDLE_OVER = 0x12;

    private static final int UPDATE_PROGRESS = 0x13;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case LOAD_COMPLETE:
                   // mProgressBar.setVisibility(View.GONE);
                    showMedia();
                    //initMediaList();
                    break;



            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        context = this;
        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getViews() {
        mMediasGv = (GridView) findViewById(R.id.gv_medias);
    }

    @Override
    public void setViews() {
        //MediaUtils.getMediaVideo(MediaGalleryActivity.this,mVideoList);

        mMediaListAdapter = new MediaAdapter(this);
        mMediasGv.setAdapter(mMediaListAdapter);
        loadMediaFiles();
    }

    @Override
    public void setListeners() {
        mMediasGv.setOnItemClickListener(this);
    }

    private void showMedia() {
        mMediaListAdapter.setData(mMediaList);
    }

    private void loadMediaFiles() {
        new Thread(){
            @Override
            public void run() {
                //MediaUtils.getMediaPhoto(MediaGalleryActivity.this,mImageList,photoMap);
                MediaUtils.getMediaVideo(MediaGalleryActivity.this,mVideoList);
                mMediaList.clear();
               // mMediaList.addAll(mImageList);
                mMediaList.addAll(mVideoList);
                Collections.sort(mMediaList, new Comparator<MediaInfo>() {
                    @Override
                    public int compare(MediaInfo o1, MediaInfo o2) {
                        return new Long(o1.getCreateTime()).compareTo(new Long(o2.getCreateTime()));
                    }
                });
                mHandler.sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MediaInfo mediaInfo = (MediaInfo) mMediaListAdapter.getItem(position);
        if (mediaInfo != null) {
            String assetpath = mediaInfo.getAssetpath();
            if (!TextUtils.isEmpty(assetpath)) {
                updateImagePath(assetpath);
            }
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
        String key = "FCD5D900B377"+"_"+timeMillis+".mp4";
        String copyPath = dir.getAbsolutePath()+File.separator+key;
        //String copyPath = dir.getAbsolutePath()+File.separator;

        File sFile = new File(path);
        FileUtils.copyFile(sFile, copyPath);
        URL = copyPath;
       // Glide.with(context).load(copyPath).into(iv);
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
                ShowMessage.showToast(MediaGalleryActivity.this,"投屏成功");
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
