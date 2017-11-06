package com.savor.operation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.savor.operation.R;
import com.savor.operation.activity.MainActivity;
import com.savor.operation.bean.FixTask;
import com.savor.operation.widget.ChoosePicDialog;

import java.util.List;

/**
 * 维修任务列表
 * Created by hezd on 2017/11/2.
 */

public class FixTaskListAdapter extends BaseAdapter {
    private static final int TAKE_PHOTO_REQUEST = 0x1;
    private final Activity mContext;
    private List<FixTask> mData;

    public FixTaskListAdapter(Activity context) {
        this.mContext = context;
    }

    public void setData(List<FixTask> data) {
        this.mData = data;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_maintain_layout,null);
            holder.tv_select_pic = (TextView) convertView.findViewById(R.id.tv_select_pic);
            holder.rl_box_layout = (RelativeLayout) convertView.findViewById(R.id.rl_box_layout);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 故障照片
        holder.tv_select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChoosePicDialog(mContext, new ChoosePicDialog.OnTakePhotoBtnClickListener() {
                    @Override
                    public void onTakePhotoClick() {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
        holder.rl_box_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String[] items = {"V001","V002","V003","V004","V005"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
                alertBuilder.setTitle("版位名称");
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int index) {
                        Toast.makeText(mContext, items[index], Toast.LENGTH_SHORT).show();
//                        finalAlertDialog.dismiss();
                    }
                });
                AlertDialog finalAlertDialog = alertBuilder.create();
                finalAlertDialog.show();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public TextView tv_select_pic;
        public RelativeLayout rl_box_layout;
    }
}
