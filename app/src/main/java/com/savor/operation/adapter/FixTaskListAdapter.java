package com.savor.operation.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.common.api.utils.FileUtils;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.SavorApplication;
import com.savor.operation.bean.BoxInfo;
import com.savor.operation.bean.RepairInfo;
import com.savor.operation.widget.ChoosePicDialog;

import java.io.File;
import java.util.List;

/**
 * 维修任务列表
 * Created by hezd on 2017/11/2.
 */

public class FixTaskListAdapter extends BaseAdapter {
    public static final int TAKE_PHOTO_REQUEST = 0x1;
    public static final int REQUEST_CODE_IMAGE = 0x2;
    private final Activity mContext;
    private List<RepairInfo> mData;
    private int currentTakePhonePos;
    private String currentImagePath;

    public FixTaskListAdapter(Activity context) {
        this.mContext = context;
    }

    public void setData(List<RepairInfo> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public List<RepairInfo> getData() {
        return mData;
    }

    /**
     * 拍照以后更新图片路径到实体类中
     */
    public void updataPhotoPath() {
        if(mData!=null && mData.size()>0) {
            mData.get(currentTakePhonePos).setFault_img_url(currentImagePath);
            notifyDataSetChanged();
        }
    }

    /**
     * 更新图片路径
     */
    public void updateImagePath(String path) {
        if(mData!=null && mData.size()>=currentTakePhonePos) {

            String cachePath = ((SavorApplication)mContext.getApplication()).imagePath;
            File dir = new File(cachePath);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            String box_id = mData.get(currentTakePhonePos).getBox_id();
            long timeMillis = System.currentTimeMillis();
            String key = box_id+"_"+timeMillis+".jpg";
            String copyPath = dir.getAbsolutePath()+File.separator+key;

            File sFile = new File(path);
            FileUtils.copyFile(sFile, copyPath);
            mData.get(currentTakePhonePos).setFault_img_url(copyPath);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_maintain_layout,null);
            holder.tv_select_pic = (TextView) convertView.findViewById(R.id.tv_select_pic);
            holder.tv_boxname = (TextView) convertView.findViewById(R.id.tv_boxname);
            holder.iv_exce_pic = (ImageView) convertView.findViewById(R.id.iv_exce_pic);
            holder.rl_box_layout = (RelativeLayout) convertView.findViewById(R.id.rl_box_layout);
            holder.et_exception_desc = (EditText) convertView.findViewById(R.id.et_exception_desc);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 版位名称
        final RepairInfo repairInfo = (RepairInfo) getItem(position);

        // 故障照片
        holder.tv_select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(repairInfo, position);
            }
        });

        holder.iv_exce_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(repairInfo,position);
            }
        });

        // 版位名称
        final List<BoxInfo> boxInfoList = repairInfo.getBoxInfoList();
        final ViewHolder finalHolder = holder;
        holder.rl_box_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boxInfoList==null||boxInfoList.size()==0) {
                    ShowMessage.showToast(mContext,"获取版位失败");
                    return;
                }
                final String[] items = new String[boxInfoList.size()];
                for(int i = 0;i<boxInfoList.size();i++) {
                    items[i] = boxInfoList.get(i).getBox_name();
                }
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
                alertBuilder.setTitle("版位名称");
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int index) {
                        for(int i =0;i<mData.size();i++) {
                            RepairInfo rInfo = mData.get(i);
                            if(i!=position) {
                                if(items[index].equals(rInfo.getBox_name())) {
                                    ShowMessage.showToast(mContext,"不能重复选择版位");
                                    return;
                                }
                            }
                        }
                        finalHolder.tv_boxname.setText(items[index]);
                        repairInfo.setBox_id(boxInfoList.get(index).getBox_id());
                        repairInfo.setBox_name(items[index]);
                        arg0.dismiss();
                    }
                });
                AlertDialog finalAlertDialog = alertBuilder.create();
                finalAlertDialog.show();
            }
        });


        holder.tv_boxname.setText(TextUtils.isEmpty(repairInfo.getBox_name())?"":repairInfo.getBox_name());

        String fault_img_url = repairInfo.getFault_img_url();
        if(!TextUtils.isEmpty(fault_img_url)) {
            holder.iv_exce_pic.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(fault_img_url).into(holder.iv_exce_pic);
        }else {
            holder.iv_exce_pic.setVisibility(View.GONE);
        }

        // 故障描述
        final ViewHolder finalHolder1 = holder;
        holder.et_exception_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String desc = finalHolder1.et_exception_desc.getText().toString();
                repairInfo.setFault_desc(desc);
            }
        });

        String fault_desc = repairInfo.getFault_desc();
        holder.et_exception_desc.setText(TextUtils.isEmpty(fault_desc)?"":fault_desc);

        return convertView;
    }

    private void takePhoto(final RepairInfo repairInfo, final int position) {
        if(TextUtils.isEmpty(repairInfo.getBox_id())) {
            ShowMessage.showToast(mContext,"请选择版位");
            return;
        }
        new ChoosePicDialog(mContext, new ChoosePicDialog.OnTakePhotoBtnClickListener() {
            @Override
            public void onTakePhotoClick() {
                currentTakePhonePos = position;
                String cacheDir = ((SavorApplication) mContext.getApplication()).imagePath;
                File cachePath = new File(cacheDir);
                if(!cachePath.exists()) {
                    cachePath.mkdirs();
                }
                currentImagePath = cacheDir+ File.separator+repairInfo.getBox_id()+"_"+System.currentTimeMillis()+".jpg";
                File file = new File(currentImagePath);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                mContext.startActivityForResult(intent, TAKE_PHOTO_REQUEST);
            }
        },
        new ChoosePicDialog.OnAlbumBtnClickListener() {
            @Override
            public void onAlbumBtnClick() {
                currentTakePhonePos = position;
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mContext.startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        }
        ).show();
    }

    public class ViewHolder {
        public TextView tv_select_pic;
        public TextView tv_boxname;
        public ImageView iv_exce_pic;
        public RelativeLayout rl_box_layout;
        public EditText et_exception_desc;
    }
}
