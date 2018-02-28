package com.savor.operation.adapter;

import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.MissionTaskListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络测评列表适配器，已完成或未完成
 * Created by hezd on 2018/02/08.
 */

public class NetworkTestListAdapter extends BaseAdapter {

    private Context context;
    private List<MissionTaskListBean> list = new ArrayList<MissionTaskListBean>();
    private LayoutInflater mInflater;
    public NetworkTestListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<MissionTaskListBean> data) {
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
        return 10;
//        return list == null?0:list.size();
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
        NetworkTestListAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_network_test,null);
            holder = new NetworkTestListAdapter.ViewHolder();
            holder.mold = (TextView) convertView.findViewById(R.id.mold);
            holder.line = (ImageView) convertView.findViewById(R.id.line);
            holder.hotel_name = (TextView) convertView.findViewById(R.id.hotel_name);
            holder.release_execute_time = (TextView) convertView.findViewById(R.id.release_execute_time);
            holder.release_time = (TextView) convertView.findViewById(R.id.release_time);
            holder.execute_time = (TextView) convertView.findViewById(R.id.execute_time);
            holder.complete_time = (TextView) convertView.findViewById(R.id.complete_time);
            holder.refuse_time = (TextView) convertView.findViewById(R.id.refuse_time);
            convertView.setTag(holder);
        }else {
            holder = (NetworkTestListAdapter.ViewHolder) convertView.getTag();
        }

        if(position == 0) {
            holder.line.setVisibility(View.GONE);
        }else {
            holder.line.setVisibility(View.VISIBLE);
        }
//        MissionTaskListBean item = (MissionTaskListBean) getItem(position);
//
//
//        holder.mold.setText(item.getTask_type_desc());
//        holder.hotel_name.setText(item.getHotel_name());
//        String appoint_exe_time = item.getAppoint_exe_time();
//        if (!TextUtils.isEmpty(appoint_exe_time)) {
//            holder.release_execute_time.setVisibility(View.VISIBLE);
//            holder.release_execute_time.setText("执行指派时间:"+appoint_exe_time+"("+item.getExeuser()+")");
//        }else {
//            holder.release_execute_time.setVisibility(View.GONE);
//            holder.release_execute_time.setText("");
//        }
//
//
//        String create_time = item.getCreate_time();
//        if (!TextUtils.isEmpty(create_time)) {
//            holder.release_time.setVisibility(View.VISIBLE);
//            holder.release_time.setText("发布时间："+create_time+"("+item.getPublish_user()+")");
//        }else {
//            holder.release_time.setVisibility(View.GONE);
//            holder.release_time.setText("");
//        }
//
//        String appoint_time = item.getAppoint_time();
//        if (!TextUtils.isEmpty(appoint_time)) {
//            holder.execute_time.setVisibility(View.VISIBLE);
//            holder.execute_time.setText("指派时间："+appoint_time+"("+item.getAppoint_user()+")");
//        }else {
//            holder.execute_time.setVisibility(View.GONE);
//            holder.execute_time.setText("");
//        }
//
//        String complete_time = item.getComplete_time();
//        if (!TextUtils.isEmpty(complete_time)) {
//            holder.complete_time.setVisibility(View.VISIBLE);
//            holder.complete_time.setText("完成时间："+complete_time+"("+item.getExeuser()+")");
//        }else {
//            holder.complete_time.setVisibility(View.GONE);
//            holder.complete_time.setText("");
//        }
//        String refuseT = item.getRefuse_time();
//        if (!TextUtils.isEmpty(refuseT)) {
//            holder.refuse_time.setVisibility(View.VISIBLE);
//            holder.refuse_time.setText("拒绝时间："+refuseT+"("+item.getAppoint_user()+")");
//        }else {
//            holder.refuse_time.setVisibility(View.GONE);
//            holder.refuse_time.setText("");
//        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView line;
        public TextView mold;
        public TextView hotel_name;
        public TextView release_execute_time;
        public TextView release_time;
        public TextView execute_time;
        public TextView complete_time;
        public TextView refuse_time;

    }
}
