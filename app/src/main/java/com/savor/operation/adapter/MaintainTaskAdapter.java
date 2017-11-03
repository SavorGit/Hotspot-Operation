package com.savor.operation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.widget.ChoosePicDialog;

import java.util.List;

/**
 * Created by hezd on 2017/11/2.
 */

public class MaintainTaskAdapter extends BaseAdapter {
    private static final int TAKE_PHOTO_REQUEST = 0x1;
    private final Activity mContext;
    private List<String> mData;

    public MaintainTaskAdapter(Activity context) {
        this.mContext = context;
    }

    public void setData(List<String> data) {
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
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
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
        return convertView;
    }

    public class ViewHolder {
        public TextView tv_select_pic;
    }
}
