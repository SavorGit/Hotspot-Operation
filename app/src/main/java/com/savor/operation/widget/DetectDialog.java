package com.savor.operation.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.api.utils.FileUtils;
import com.savor.operation.R;
import com.savor.operation.SavorApplication;
import com.savor.operation.adapter.ExeSpinnerAdapter;
import com.savor.operation.bean.DetectBean;
import com.savor.operation.bean.ExecutorInfoBean;
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.interfaces.MaintenanceCallBack;
import com.savor.operation.interfaces.RefuseCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.savor.operation.adapter.FixTaskListAdapter.REQUEST_CODE_IMAGE;
import static com.savor.operation.adapter.FixTaskListAdapter.TAKE_PHOTO_REQUEST;

/**
 * 自定义AlertDialog
 * Created by bushlee on 16/11/21.
 */
public class DetectDialog implements OnClickListener {
    private Context context;
    private Dialog dialog;

    private View view;

    private Display display;
    private LayoutInflater mInflater;
    private LinearLayout msg_la;
    private TextView submit;
    private TextView cancel;
    //private RefuseCallBack callBack;
    private MaintenanceCallBack callBack;

    private TextView tv_drawings;
    private TextView tv_acceptance;

    private ImageView iv_drawings;
    private ImageView iv_acceptance;

    private List<TaskDetailRepair> repair_list;
    private int currentTakePhonePos;
    private String currentImagePath;
    private Activity activity;
    private int currentpos;
    private  List<DetectBean> urls = new ArrayList<DetectBean>();
    private String hotel_id;
    private String currentType;

    public DetectDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        mInflater = LayoutInflater.from(context);
        //inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DetectDialog(Context context, List<TaskDetailRepair> repair_list , RefuseCallBack callBack){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        //this.callBack = callBack;
        builder();
    }

    public DetectDialog(Context context, String hotel_id, Activity activity, MaintenanceCallBack callBack){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.callBack = callBack;
        this.hotel_id = hotel_id;
        this.activity = activity;
        builder();
    }
    public DetectDialog builder() {
        view = LayoutInflater.from(context).inflate(R.layout.detect_dialog_layout, null);
        submit = (TextView) view.findViewById(R.id.submit);
        iv_drawings = (ImageView) view.findViewById(R.id.iv_drawings);
        iv_acceptance = (ImageView) view.findViewById(R.id.iv_acceptance);
        tv_drawings = (TextView) view.findViewById(R.id.tv_drawings);
        tv_acceptance = (TextView) view.findViewById(R.id.tv_acceptance);
        DetectBean obj = new DetectBean();
        urls.add(obj);
        urls.add(obj);
        if (dialog == null) {
            dialog = new Dialog(context, R.style.AlertDialogStyle);
        }
        dialog.setContentView(view);

        dialog.setCancelable(true);// 不可以用“返回键”取消
        setListeners();
        return this;
    }

    public void setListeners() {
        submit.setOnClickListener(this);
        iv_drawings.setOnClickListener(this);
        iv_acceptance.setOnClickListener(this);
        tv_drawings.setOnClickListener(this);
        tv_acceptance.setOnClickListener(this);


    }



    public void dismiss(){
        if(dialog != null){
            dialog.dismiss();
        }
    }

    public void show(){
        if(dialog != null){
            dialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_drawings:
            case R.id.tv_drawings:
                currentType = "1";
                takePhoto();
                break;
            case R.id.iv_acceptance:
            case R.id.tv_acceptance:
                currentType = "2";
                takePhoto();
                break;
            case R.id.submit:
                callBack.toTransform(urls);
                dismiss();
                break;
        }
    }

    private void takePhoto() {
//        if(TextUtils.isEmpty(repairInfo.getBox_id())) {
//            ShowMessage.showToast(context,"请选择版位");
//            return;
//        }
        new ChoosePicDialog(context, new ChoosePicDialog.OnTakePhotoBtnClickListener() {
            @Override
            public void onTakePhotoClick() {

                String cacheDir = ((SavorApplication) activity.getApplication()).imagePath;
                File cachePath = new File(cacheDir);
                if(!cachePath.exists()) {
                    cachePath.mkdirs();
                }
                currentImagePath = cacheDir+ File.separator+hotel_id+"_"+System.currentTimeMillis()+".jpg";
                File file = new File(currentImagePath);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                activity.startActivityForResult(intent, TAKE_PHOTO_REQUEST);
            }
        },
                new ChoosePicDialog.OnAlbumBtnClickListener() {
                    @Override
                    public void onAlbumBtnClick() {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activity.startActivityForResult(intent, REQUEST_CODE_IMAGE);
                    }
                }
        ).show();
    }

    /**
     * 更新图片路径
     */
    public void updateImagePath(String path) {


            String cachePath = ((SavorApplication)activity.getApplication()).imagePath;
            File dir = new File(cachePath);
            if(!dir.exists()) {
                dir.mkdirs();
            }

           // String box_id = currentExecutorInfoBean.getBox_id();
            long timeMillis = System.currentTimeMillis();
            String key = hotel_id+"_"+timeMillis+".jpg";
            String copyPath = dir.getAbsolutePath()+File.separator+key;

            File sFile = new File(path);
            FileUtils.copyFile(sFile, copyPath);
            //Glide.with(context).load(copyPath).into(iv_exce_pic1);

        if ("1".equals(currentType)) {
            if(!TextUtils.isEmpty(copyPath)) {
                iv_drawings.setVisibility(View.VISIBLE);
                Glide.with(context).load(copyPath).placeholder(R.drawable.kong_mrjz).into(iv_drawings);
                DetectBean detectBean = new DetectBean();
                detectBean.setType(currentType);
                detectBean.setImg(copyPath);
                urls.set(0,detectBean);
            }else {
                tv_drawings.setVisibility(View.GONE);
            }
        }else if ("2".equals(currentType)) {
            if(!TextUtils.isEmpty(copyPath)) {
                iv_acceptance.setVisibility(View.VISIBLE);
                Glide.with(context).load(copyPath).placeholder(R.drawable.kong_mrjz).into(iv_acceptance);
                DetectBean detectBean = new DetectBean();
                detectBean.setType(currentType);
                detectBean.setImg(copyPath);
                urls.set(1,detectBean);
            }else {
                tv_acceptance.setVisibility(View.GONE);
            }
        }
            //urls.set(currentTakePhonePos ,copyPath);

    }

    /**
     * 拍照以后更新图片路径到实体类中
     */
    public void updataPhotoPath() {
        if ("1".equals(currentType)) {
            if(!TextUtils.isEmpty(currentImagePath)) {
                iv_drawings.setVisibility(View.VISIBLE);
                Glide.with(context).load(currentImagePath).placeholder(R.drawable.kong_mrjz).into(iv_drawings);
                DetectBean detectBean = new DetectBean();
                detectBean.setType(currentType);
                detectBean.setImg(currentImagePath);
                urls.set(0,detectBean);
            }else {
                tv_drawings.setVisibility(View.GONE);
            }
        }else if ("2".equals(currentType)) {
            if(!TextUtils.isEmpty(currentImagePath)) {
                iv_acceptance.setVisibility(View.VISIBLE);
                Glide.with(context).load(currentImagePath).placeholder(R.drawable.kong_mrjz).into(iv_acceptance);
                DetectBean detectBean = new DetectBean();
                detectBean.setType(currentType);
                detectBean.setImg(currentImagePath);
                urls.set(1,detectBean);
            }else {
                tv_acceptance.setVisibility(View.GONE);
            }
        }
//
//        DetectBean detectBean = new DetectBean();
//        detectBean.setType(currentType);
//        detectBean.setImg(currentImagePath);
//        urls.add(detectBean);

    }
}
