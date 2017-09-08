package com.savor.operation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.ErrorReportBean;
import com.savor.operation.bean.RepairListBean;
import com.savor.operation.bean.RepairRecordListBean;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;

/**
 * Created by admin on 2017/9/7.
 */

public class MaintenanceRecordAdapter extends BaseAdapter {

    private Context context;
    private List<RepairRecordListBean> list = new ArrayList<RepairRecordListBean>();
    private LayoutInflater mInflater;
    public MaintenanceRecordAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<RepairRecordListBean> data,boolean isup) {
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
        MaintenanceRecordAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_maintenance_record_group,null);
            holder = new MaintenanceRecordAdapter.ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.hotle_name = (TextView) convertView.findViewById(R.id.hotle_name);
            holder.msg_la = (LinearLayout) convertView.findViewById(R.id.msg_la);
            convertView.setTag(holder);
        }else {
            holder = (MaintenanceRecordAdapter.ViewHolder) convertView.getTag();
        }


        RepairRecordListBean item = (RepairRecordListBean) getItem(position);
        holder.date.setText(item.getDatetime());
        holder.hotle_name.setText(item.getHotel_name()+"  维修记录");
        List<RepairListBean> repair_list = item.getRepair_list();
        if (repair_list != null && repair_list.size()>0) {
            holder.msg_la.removeAllViews();
            for (int i = 0; i <repair_list.size(); i++) {
                View v = mInflater.inflate(R.layout.item_maintenance_record_child, null);
                TextView state = (TextView)v.findViewById(R.id.state);
                TextView time = (TextView)v.findViewById(R.id.time);
                TextView repair_error = (TextView)v.findViewById(R.id.repair_error);
                TextView remark = (TextView)v.findViewById(R.id.remark);
                RepairListBean bean = repair_list.get(i);
                state.setText(bean.getState());
                time.setText(bean.getCreate_time());
                String error = bean.getRepair_error();
                if (!TextUtils.isEmpty(error)) {
                    repair_error.setText("维修记录："+error);
                }else {
                    repair_error.setText("维修记录：无");
                }
                String remarkStr = bean.getRemark();
                if (!TextUtils.isEmpty(remarkStr)) {
                    remark.setText("备注："+remarkStr);
                }else {
                    remark.setText("备注：无");
                }
                holder.msg_la.addView(v);
            }
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView date;
        public TextView hotle_name;
        public LinearLayout msg_la;
    }
}
