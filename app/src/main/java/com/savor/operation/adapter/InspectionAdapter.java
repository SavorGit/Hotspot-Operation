package com.savor.operation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.MyInspect;
import com.savor.operation.bean.RepairListBean;
import com.savor.operation.bean.RepairRecordListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/7.
 */

public class InspectionAdapter extends BaseAdapter {

    private Context context;
    private List<MyInspect> list = new ArrayList<MyInspect>();
    private LayoutInflater mInflater;
    public InspectionAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<MyInspect> data) {
//        if (isup) {
//            list.clear();
//        }
        this.list = data;
        notifyDataSetChanged();
    }

    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list == null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InspectionAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_inspection_layout,null);
            holder = new InspectionAdapter.ViewHolder();
            holder.hotle_info = (TextView) convertView.findViewById(R.id.hotle_info);
            holder.small_palt_info = (TextView) convertView.findViewById(R.id.small_palt_info);
            holder.box_info = (TextView) convertView.findViewById(R.id.box_info);
            convertView.setTag(holder);
        }else {
            holder = (InspectionAdapter.ViewHolder) convertView.getTag();
        }


        MyInspect item = (MyInspect) getItem(position);
        String hotle_infoStr = item.getHotel_info();
        if (!TextUtils.isEmpty(hotle_infoStr)) {
            holder.hotle_info.setText(hotle_infoStr);
        }else {
            holder.hotle_info.setText("");
        }

        String small_palt_infoStr = item.getSmall_palt_info();
        if (!TextUtils.isEmpty(small_palt_infoStr)) {
            holder.small_palt_info.setText(small_palt_infoStr);
        }else {
            holder.small_palt_info.setText("");
        }

        String box_infoStr = item.getBox_info();
        if (!TextUtils.isEmpty(box_infoStr)) {
            holder.box_info.setText(box_infoStr);
        }else {
            holder.box_info.setText("");
        }

        return convertView;
    }

    public class ViewHolder {
        public TextView hotle_info;
        public TextView small_palt_info;
        public TextView box_info;
    }
}
