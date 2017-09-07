package com.savor.operation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.savor.operation.R;

/**
 * 酒楼版位信息
 * Created by hezd on 2017/9/7.
 */

public class PositionDetailAdapter extends BaseAdapter {

    private final Context mContext;

    public PositionDetailAdapter (Context context) {
        this.mContext = context;
    }

    public void setData() {

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView = View.inflate(mContext, R.layout.item_position_detail,null);
            holder.tv_box_name = (TextView) convertView.findViewById(R.id.tv_box_name);
            holder.tv_room_name = (TextView) convertView.findViewById(R.id.tv_room_name);
            holder.tv_mac = (TextView) convertView.findViewById(R.id.tv_mac);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView tv_room_name;
        public TextView tv_box_name;
        public TextView tv_mac;
    }
}
