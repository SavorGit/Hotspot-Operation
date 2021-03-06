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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.api.utils.FileUtils;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.SavorApplication;
import com.savor.operation.activity.PublishTaskActivity;
import com.savor.operation.adapter.ExeSpinnerAdapter;
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
public class InstallDialog implements OnClickListener {
    private Context context;
    private Dialog dialog;
    private View view;
    private Display display;
    private LayoutInflater mInflater;
    private TextView submit;
    private TextView cancel;
    private MaintenanceCallBack callBack;
    private Activity activity;
    private LinearLayout msg_la;
    private String tvNum;
    private int num;
    private  List<String> urls = new ArrayList<String>();
    private String currentImagePath;
    private String hotel_id;
    private int currentPos;
    private  TextView currentTv;
    private  ImageView currentIv ;
    private  TextView cdel;
    private ProgressBar pb_loading;
    private List<ExecutorInfoBean> elist;
    private TextView mAddTv;
    private TextView mReduceTv;
    private TextView mBoxNumTv;
    private String Strnum;


    public InstallDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        mInflater = LayoutInflater.from(context);
        //inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public InstallDialog(Context context, List<TaskDetailRepair> repair_list , RefuseCallBack callBack){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        //this.callBack = callBack;
        builder();
    }

    public InstallDialog(Context context, String tvNum , String hotel_id,Activity activity, MaintenanceCallBack callBack,List<ExecutorInfoBean> elist){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.tvNum = tvNum;
        this.callBack = callBack;
        this.activity = activity;
        this.hotel_id = hotel_id;
        this.elist = elist;
        builder();
    }
    public InstallDialog builder() {
        view = LayoutInflater.from(context).inflate(R.layout.item_install_layout, null);

        submit = (TextView) view.findViewById(R.id.submit);
        cancel = (TextView) view.findViewById(R.id.cancel);
        msg_la = (LinearLayout) view.findViewById(R.id.msg_la);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        mAddTv = (TextView) view.findViewById(R.id.tv_add);
        mReduceTv = (TextView) view.findViewById(R.id.tv_reduce);
        mBoxNumTv = (TextView) view.findViewById(R.id.tv_box_num);
        submit.setText("保存");
        if (dialog == null) {
            dialog = new Dialog(context, R.style.AlertDialogStyle);
        }
        dialog.setContentView(view);
        num = Integer.parseInt(tvNum);
        for (int i = 0; i < num; i++) {
            urls.add("");
        }
        addView();
        setListeners();
        return this;
    }

    public void loadFinish() {
        pb_loading.setVisibility(View.GONE);
    }

    public void startLoading() {
//        pb_loading.setVisibility(View.VISIBLE);
    }

    public void setListeners() {
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        mAddTv.setOnClickListener(this);
        mReduceTv.setOnClickListener(this);

    }

    private void addView(){

        for (int i = 0; i < num; i++) {
            final int pos = i;
            View v = mInflater.inflate(R.layout.item_installation_pic_layout, null);
            final TextView tv_select_pic1 = (TextView)v.findViewById(R.id.tv_select_pic1);
            final ImageView iv_exce_pic1 = (ImageView)v.findViewById(R.id.iv_exce_pic1);
            final TextView del = (TextView)v.findViewById(R.id.del);
            if (elist != null && elist.size()>0&&elist.size()<=num) {
                if (elist.size()-1>= i) {
                    String urlo =  elist.get(i).getRepair_img();
                    tv_select_pic1.setVisibility(View.GONE);
                    iv_exce_pic1.setVisibility(View.VISIBLE);
                    del.setVisibility(View.VISIBLE);
                    Glide.with(context).load(urlo).into(iv_exce_pic1);
                }
            }
            tv_select_pic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPos = pos;
                    currentTv = tv_select_pic1;
                    currentIv = iv_exce_pic1;
                    cdel = del;
                    takePhoto();
                }
            });
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPos = pos;
                    setDel();
                }
            });
            //convertView = mInflater.inflate(R.layout.item_video, null);
            msg_la.addView(v);
        }
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
            case R.id.submit:
                startLoading();
                callBack.toInstallation(urls,Strnum);
                dismiss();
                //callBack.toMaintenance(box_id,ev_info.getText().toString(),state,urls);
                break;
            case R.id.cancel:
                dismiss();
                break;
            case R.id.tv_add:
                Strnum = mBoxNumTv.getText().toString();

                try {
                    int boxNum = Integer.valueOf(Strnum);
                    // 如果当前是非安装验收类型，数量达到最大不可在操作
//                    if(boxNum>=elist.size()) {
//                            ShowMessage.showToast(context,"不能大于该酒楼总版位数量");
//                            return;
//                    }

                    boxNum += 1;
                    mBoxNumTv.setText(String.valueOf(boxNum));
                } catch (Exception e) {
                    mBoxNumTv.setText("0");
                }
                break;
            case R.id.tv_reduce:
                Strnum = mBoxNumTv.getText().toString();
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
        if(urls!=null && urls.size()>=currentPos) {

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
            if(!TextUtils.isEmpty(copyPath)) {
                currentIv.setVisibility(View.VISIBLE);
                cdel.setVisibility(View.VISIBLE);
                Glide.with(context).load(copyPath).placeholder(R.drawable.kong_mrjz).into(currentIv);
            }else {
                currentTv.setVisibility(View.GONE);
                cdel.setVisibility(View.GONE);
            }


            if (elist != null && elist.size()>0) {
                if (currentPos <= elist.size()-1) {
                    elist.remove(currentPos);
                }
            }
            urls.set(currentPos ,copyPath);
            setbtn();
        }
    }


    /**
     * 拍照以后更新图片路径到实体类中
     */
    public void updataPhotoPath() {
        if(urls!=null && urls.size()>0) {

            if(!TextUtils.isEmpty(currentImagePath)) {
                currentIv.setVisibility(View.VISIBLE);
                cdel.setVisibility(View.VISIBLE);
                Glide.with(context).load(currentImagePath).placeholder(R.drawable.kong_mrjz).into(currentIv);
            }else {
                currentTv.setVisibility(View.GONE);
                cdel.setVisibility(View.GONE);
            }


            if (elist != null && elist.size()>0) {
                if (currentPos <= elist.size()-1) {
                    elist.remove(currentPos);
                }
            }
            urls.set(currentPos ,currentImagePath);
            setbtn();
        }
    }

    private void setbtn(){
        int isfull = 0;
        if (urls != null && urls.size()>0) {
            for (int i = 0; i < urls.size() ; i++) {
                String obj = urls.get(i);
                if (TextUtils.isEmpty(obj)) {
                    isfull = isfull+1;
                }
            }
            if (isfull >0) {
                submit.setText("保存");
            }else {
                submit.setText("完成安装");
            }
        }
        callBack.Updata(elist);
    }
    private void setDel(){
        urls.set(currentPos ,"");
        msg_la.getChildAt(currentPos);
        View v = msg_la.getChildAt(currentPos);
        final TextView tv_select_pic1 = (TextView)v.findViewById(R.id.tv_select_pic1);
        final ImageView iv_exce_pic1 = (ImageView)v.findViewById(R.id.iv_exce_pic1);
        final TextView del = (TextView)v.findViewById(R.id.del);
        tv_select_pic1.setVisibility(View.VISIBLE);
        iv_exce_pic1.setVisibility(View.GONE);
        del.setVisibility(View.GONE);
        setbtn();
    }
}
