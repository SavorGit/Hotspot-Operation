package com.savor.operation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.ErrorDetailBean;
import com.savor.operation.bean.MyHotelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/6.
 */

public class MyHotelAdapter extends BaseAdapter {
    private final Context mContext;
    private List<MyHotelBean> mData = new ArrayList<MyHotelBean>();

    public MyHotelAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<MyHotelBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
    public void addData(List<MyHotelBean> data) {
        if(data!=null&&data.size()>0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<MyHotelBean> getData() {
        return mData;
    }
    @Override
    public int getCount() {
        return mData == null?0:mData.size();
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
        ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_my_hotel_layout,null);
            holder = new ViewHolder();
            holder.hotel_name = (TextView) convertView.findViewById(R.id.hotel_name);
            holder.small_palt_info = (TextView) convertView.findViewById(R.id.small_palt_info);
            holder.box_info = (TextView) convertView.findViewById(R.id.box_info);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        MyHotelBean item = (MyHotelBean) getItem(position);
        holder.hotel_name.setText(item.getBox_info());
        holder.small_palt_info.setText(item.getSmall_palt_info());
        holder.box_info.setText(item.getBox_info());
        return convertView;
    }

    public class ViewHolder {
        public TextView hotel_name;
        public TextView small_palt_info;
        public TextView box_info;
    }
}
