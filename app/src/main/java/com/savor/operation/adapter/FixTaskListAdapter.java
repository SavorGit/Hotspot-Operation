package com.savor.operation.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

    public void updataPhotoPath() {
        mData.get(currentTakePhonePos).setFault_img_url(currentImagePath);
        notifyDataSetChanged();
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

                    }
                }
                ).show();
            }
        });

        // 版位名称
        final List<BoxInfo> boxInfoList = repairInfo.getBoxInfoList();
        final ViewHolder finalHolder = holder;
        holder.rl_box_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = new String[boxInfoList.size()];
                for(int i = 0;i<boxInfoList.size();i++) {
                    items[i] = boxInfoList.get(i).getBox_name();
                }
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
                alertBuilder.setTitle("版位名称");
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int index) {
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

        return convertView;
    }

    public class ViewHolder {
        public TextView tv_select_pic;
        public TextView tv_boxname;
        public ImageView iv_exce_pic;
        public RelativeLayout rl_box_layout;
    }
}
