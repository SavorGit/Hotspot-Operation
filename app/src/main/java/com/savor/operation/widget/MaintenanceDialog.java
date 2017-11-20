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
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.api.utils.FileUtils;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.SavorApplication;
import com.savor.operation.adapter.ExeSpinnerAdapter;
import com.savor.operation.adapter.SpinnerAdapter;
import com.savor.operation.bean.ExecutorInfoBean;
import com.savor.operation.bean.RepairInfo;
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.bean.UserBean;
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
public class MaintenanceDialog implements OnClickListener {
    private Context context;
    private Dialog dialog;
    private EditText ev_info;
    private View view;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private Display display;
    private LayoutInflater mInflater;
    private LinearLayout msg_la;
    private TextView submit;
    private TextView cancel;
    //private RefuseCallBack callBack;
    private MaintenanceCallBack callBack;
    private TextView tv_select_pic3;
    private TextView tv_select_pic2;
    private TextView tv_select_pic1;
    private ImageView iv_exce_pic3;
    private ImageView iv_exce_pic2;
    private ImageView iv_exce_pic1;
    private TextView del1;
    private TextView del2;
    private TextView del3;
    private RelativeLayout pic3;
    private RelativeLayout pic2;
    private RelativeLayout pic1;
    private List<TaskDetailRepair> repair_list;
    private int currentTakePhonePos;
    private String currentImagePath;
    private Activity activity;
    private List<ExecutorInfoBean> elist;
    private ExeSpinnerAdapter spinnerAdapter;
    private ExecutorInfoBean currentExecutorInfoBean;
    private int currentpos;
    private ImageView[] ims = {iv_exce_pic1,iv_exce_pic2,iv_exce_pic1};
    private  List<String> urls = new ArrayList<String>();
    private String box_id;
    private String state;
    public MaintenanceDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        mInflater = LayoutInflater.from(context);
        //inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MaintenanceDialog(Context context, List<TaskDetailRepair> repair_list , RefuseCallBack callBack){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        //this.callBack = callBack;
        builder();
    }

    public MaintenanceDialog(Context context, List<ExecutorInfoBean> elist , Activity activity,MaintenanceCallBack callBack){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.callBack = callBack;
        this.elist = elist;
        this.activity = activity;
        builder();
    }
    public MaintenanceDialog builder() {
        view = LayoutInflater.from(context).inflate(R.layout.maintenance_dialog_layout, null);
        ev_info = (EditText) view.findViewById(R.id.ev_info);
        submit = (TextView) view.findViewById(R.id.submit);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        pic1 = (RelativeLayout) view.findViewById(R.id.pic1);
        pic2 = (RelativeLayout) view.findViewById(R.id.pic2);
        pic3 = (RelativeLayout) view.findViewById(R.id.pic3);
        iv_exce_pic1 = (ImageView) view.findViewById(R.id.iv_exce_pic1);
        iv_exce_pic2 = (ImageView) view.findViewById(R.id.iv_exce_pic2);
        iv_exce_pic3 = (ImageView) view.findViewById(R.id.iv_exce_pic3);
        tv_select_pic1 = (TextView) view.findViewById(R.id.tv_select_pic1);
        tv_select_pic2 = (TextView) view.findViewById(R.id.tv_select_pic2);
        tv_select_pic3 = (TextView) view.findViewById(R.id.tv_select_pic3);
        del1 = (TextView) view.findViewById(R.id.del1);
        del2 = (TextView) view.findViewById(R.id.del2);
        del3 = (TextView) view.findViewById(R.id.del3);
        cancel = (TextView) view.findViewById(R.id.cancel);
        urls.add("");
        urls.add("");
        urls.add("");

        if (dialog == null) {
            dialog = new Dialog(context, R.style.AlertDialogStyle);
        }
        dialog.setContentView(view);

        dialog.setCancelable(true);// 不可以用“返回键”取消
//        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (
//                display.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));
        spinnerAdapter = new ExeSpinnerAdapter(context,elist);
        spinner.setAdapter(spinnerAdapter);
        setListeners();
        return this;
    }

    public void setListeners() {
        submit.setOnClickListener(this);
        iv_exce_pic1.setOnClickListener(this);
        iv_exce_pic2.setOnClickListener(this);
        iv_exce_pic3.setOnClickListener(this);
        tv_select_pic1.setOnClickListener(this);
        tv_select_pic2.setOnClickListener(this);
        tv_select_pic3.setOnClickListener(this);
        del1.setOnClickListener(this);
        del2.setOnClickListener(this);
        del3.setOnClickListener(this);
        cancel.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup arg0, int arg1) {
                               // TODO Auto-generated method stub
                              //获取变更后的选中项的ID
                              int radioButtonId = arg0.getCheckedRadioButtonId();
                              //根据ID获取RadioButton的实例
                                RadioButton rb = (RadioButton)view.findViewById(radioButtonId);
                 if ("已解决".equals(rb.getText().toString())) {
                     state = "1";
                 }else {
                     state = "2";
                 }
                              //  state = rb.getText().toString();
                               //更新文本内容，以符合选中项
                              //tv.setText("您的性别是：" + rb.getText());
                           }
         });

        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                currentExecutorInfoBean = (ExecutorInfoBean)spinnerAdapter.getItem(arg2);
                box_id = currentExecutorInfoBean.getBox_id();
               // currentpos = arg2;
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                currentExecutorInfoBean = (ExecutorInfoBean)spinnerAdapter.getItem(0);
                currentpos = 0;
            }
        });
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
            case R.id.iv_exce_pic1:
            case R.id.tv_select_pic1:
                currentTakePhonePos = 0;
                takePhoto(currentExecutorInfoBean,currentTakePhonePos);
                break;
            case R.id.iv_exce_pic2:
            case R.id.tv_select_pic2:
                currentTakePhonePos = 1;
                takePhoto(currentExecutorInfoBean,currentTakePhonePos);
                break;
            case R.id.iv_exce_pic3:
            case R.id.tv_select_pic3:
                currentTakePhonePos = 2;
                takePhoto(currentExecutorInfoBean,currentTakePhonePos);
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(state)) {
                    state = "2";
                }
                callBack.toMaintenance(box_id,ev_info.getText().toString(),state,urls);
                break;
            case R.id.cancel:
                dismiss();
                break;
            case R.id.del1:
                iv_exce_pic1.setVisibility(View.GONE);
                tv_select_pic1.setVisibility(View.VISIBLE);
                del1.setVisibility(View.GONE);
                urls.set(0,"");
                break;
            case R.id.del2:
                iv_exce_pic2.setVisibility(View.GONE);
                tv_select_pic2.setVisibility(View.VISIBLE);
                del2.setVisibility(View.GONE);
                urls.set(1,"");
                break;
            case R.id.del3:
                iv_exce_pic3.setVisibility(View.GONE);
                tv_select_pic3.setVisibility(View.VISIBLE);
                del3.setVisibility(View.GONE);
                urls.set(2,"");
                break;


        }
    }

    private void takePhoto(final ExecutorInfoBean repairInfo, final int position) {
//        if(TextUtils.isEmpty(repairInfo.getBox_id())) {
//            ShowMessage.showToast(context,"请选择版位");
//            return;
//        }
        new ChoosePicDialog(context, new ChoosePicDialog.OnTakePhotoBtnClickListener() {
            @Override
            public void onTakePhotoClick() {
                currentTakePhonePos = position;
                String cacheDir = ((SavorApplication) activity.getApplication()).imagePath;
                File cachePath = new File(cacheDir);
                if(!cachePath.exists()) {
                    cachePath.mkdirs();
                }
                currentImagePath = cacheDir+ File.separator+repairInfo.getBox_id()+"_"+System.currentTimeMillis()+".jpg";
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
                        currentTakePhonePos = position;
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activity.startActivityForResult(intent, REQUEST_CODE_IMAGE);
                    }
                }
        ).show();
    }

    /**
     * 更新图片路径
     */
    public void updateImagePath(String path) {
 //       if(elist!=null && elist.size()>=currentTakePhonePos) {

            String cachePath = ((SavorApplication)activity.getApplication()).imagePath;
            File dir = new File(cachePath);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            String box_id = currentExecutorInfoBean.getBox_id();
            long timeMillis = System.currentTimeMillis();
            String key = box_id+"_"+timeMillis+".jpg";
            String copyPath = dir.getAbsolutePath()+File.separator+key;

            File sFile = new File(path);
            FileUtils.copyFile(sFile, copyPath);
            //Glide.with(context).load(copyPath).into(iv_exce_pic1);

            switch(currentTakePhonePos){
                case 0:
                    if(!TextUtils.isEmpty(copyPath)) {


                        iv_exce_pic1.setVisibility(View.VISIBLE);
                        del1.setVisibility(View.VISIBLE);
                        Glide.with(context).load(copyPath).placeholder(R.drawable.kong_mrjz).into(iv_exce_pic1);
                    }else {
                        tv_select_pic1.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    if(!TextUtils.isEmpty(copyPath)) {
                        iv_exce_pic2.setVisibility(View.VISIBLE);
                        del2.setVisibility(View.VISIBLE);
                        Glide.with(context).load(copyPath).placeholder(R.drawable.kong_mrjz).into(iv_exce_pic2);
                    }else {
                        tv_select_pic2.setVisibility(View.GONE);
                    }
                    break;
                case 2:
                    if(!TextUtils.isEmpty(copyPath)) {
                        iv_exce_pic3.setVisibility(View.VISIBLE);
                        del3.setVisibility(View.VISIBLE);
                        Glide.with(context).load(copyPath).placeholder(R.drawable.kong_mrjz).into(iv_exce_pic3);
                    }else {
                        tv_select_pic3.setVisibility(View.GONE);
                    }
                    break;
            }
            urls.set(currentTakePhonePos ,copyPath);
//            if(!TextUtils.isEmpty(copyPath)) {
//                iv_exce_pic1.setVisibility(View.VISIBLE);
//                Glide.with(context).load(copyPath).into(iv_exce_pic1);
//            }else {
//                tv_select_pic1.setVisibility(View.GONE);
//            }
//            elist.get(currentTakePhonePos).setFault_img_url(copyPath);
//            notifyDataSetChanged();
//        }
    }

    /**
     * 拍照以后更新图片路径到实体类中
     */
    public void updataPhotoPath() {
//        if(elist!=null && elist.size()>0) {
//            mData.get(currentTakePhonePos).setFault_img_url(currentImagePath);
//            notifyDataSetChanged();
//            FileUtils.copyFile(sFile, copyPath);
//            Glide.with(context).load(copyPath).into(ims[currentpos]);
//            if(!TextUtils.isEmpty(currentImagePath)) {
//                iv_exce_pic1.setVisibility(View.VISIBLE);
//                Glide.with(context).load(currentImagePath).into(iv_exce_pic1);
//            }else {
//                tv_select_pic1.setVisibility(View.GONE);
//            }

            switch(currentTakePhonePos){
                case 0:
                    if(!TextUtils.isEmpty(currentImagePath)) {
                        del1.setVisibility(View.VISIBLE);
                        iv_exce_pic1.setVisibility(View.VISIBLE);
                        Glide.with(context).load(currentImagePath).placeholder(R.drawable.kong_mrjz).into(iv_exce_pic1);
                    }else {
                        tv_select_pic1.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    if(!TextUtils.isEmpty(currentImagePath)) {
                        iv_exce_pic2.setVisibility(View.VISIBLE);
                        del2.setVisibility(View.VISIBLE);
                        Glide.with(context).load(currentImagePath).placeholder(R.drawable.kong_mrjz).into(iv_exce_pic2);
                    }else {
                        tv_select_pic2.setVisibility(View.GONE);
                    }
                    break;
                case 2:
                    if(!TextUtils.isEmpty(currentImagePath)) {
                        iv_exce_pic3.setVisibility(View.VISIBLE);
                        del3.setVisibility(View.VISIBLE);
                        Glide.with(context).load(currentImagePath).placeholder(R.drawable.kong_mrjz).into(iv_exce_pic3);
                    }else {
                        tv_select_pic3.setVisibility(View.GONE);
                    }
                    break;
            }

            urls.set(currentTakePhonePos ,currentImagePath);
 //       }
    }
}
