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
import com.savor.operation.bean.MissionTaskListBean;
import com.savor.operation.bean.RepairListBean;
import com.savor.operation.bean.RepairRecordListBean;
import com.savor.operation.bean.TaskListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/7.
 */

public class MissionAdapter extends BaseAdapter {

    private Context context;
    private List<MissionTaskListBean> list = new ArrayList<MissionTaskListBean>();
    private LayoutInflater mInflater;
    public MissionAdapter(Context context) {
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
        MissionAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_mission_,null);
            holder = new MissionAdapter.ViewHolder();
            holder.plan_state = (TextView) convertView.findViewById(R.id.plan_state);
            holder.level_state = (TextView) convertView.findViewById(R.id.level_state);
            holder.screen_num = (TextView) convertView.findViewById(R.id.screen_num);
            holder.mold = (TextView) convertView.findViewById(R.id.mold);
            holder.hotel_name = (TextView) convertView.findViewById(R.id.hotel_name);
            holder.release_execute_time = (TextView) convertView.findViewById(R.id.release_execute_time);
            holder.release_time = (TextView) convertView.findViewById(R.id.release_time);
            holder.execute_time = (TextView) convertView.findViewById(R.id.execute_time);
            holder.complete_time = (TextView) convertView.findViewById(R.id.complete_time);

            convertView.setTag(holder);
        }else {
            holder = (MissionAdapter.ViewHolder) convertView.getTag();
        }
        MissionTaskListBean item = (MissionTaskListBean) getItem(position);
        holder.plan_state.setText(item.getState()+"("+item.getRegion_name()+")");
        String task_emerge_id = item.getTask_emerge_id();
        if ("2".equals(task_emerge_id)) {
            holder.level_state.setVisibility(View.VISIBLE);
            holder.level_state.setText(item.getTask_emerge());
        }else {
            holder.level_state.setVisibility(View.INVISIBLE);
        }

        holder.screen_num.setText("版位数量 ："+item.getTv_nums());
        holder.mold.setText(item.getTask_type_desc());
        holder.hotel_name.setText(item.getHotel_name());
        holder.release_execute_time.setText(item.getHotel_name());

        return convertView;
    }

    public class ViewHolder {
        public TextView plan_state;
        public TextView level_state;
        public TextView screen_num;
        public TextView mold;
        public TextView hotel_name;
        public TextView release_execute_time;
        public TextView release_time;
        public TextView execute_time;
        public TextView complete_time;

    }
}
