package com.savor.operation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.savor.operation.R;
import com.savor.operation.bean.TaskDetailExecute;
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.bean.TaskDetailRepairImg;
import com.savor.operation.widget.ShowPicDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/7.
 */

public class MaintenanceRepairAdapter extends BaseAdapter {

    private Context context;
    private List<TaskDetailExecute> list = new ArrayList<TaskDetailExecute>();
    private LayoutInflater mInflater;
    public MaintenanceRepairAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<TaskDetailExecute> data) {
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
        MaintenanceRepairAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_task_completed_detail,null);
            holder = new MaintenanceRepairAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            holder.box = (TextView) convertView.findViewById(R.id.box);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.msg_la = (LinearLayout) convertView.findViewById(R.id.msg_la);


            convertView.setTag(holder);
        }else {
            holder = (MaintenanceRepairAdapter.ViewHolder) convertView.getTag();
        }
        final TaskDetailExecute item = (TaskDetailExecute) getItem(position);
        holder.name.setText("执行人："+item.getUsername());
        holder.desc.setText("故障现象："+item.getRemark());
        holder.box.setText("维修版位："+item.getBox_name());
        holder.time.setText("操作时间："+item.getRemark());
//        Glide.with(context).load(item.getFault_img_url()).into(holder.imge_la);
//        holder.imge_la.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        List<TaskDetailRepairImg> repair_img = item.getRepair_img();
        if (repair_img != null && repair_img.size()>0) {
         for (int i = 0; i < repair_img.size(); i++) {
             final  TaskDetailRepairImg obj = repair_img.get(i);
            View v = mInflater.inflate(R.layout.item_pic_layout, null);
            final ImageView iv_exce_pic1 = (ImageView)v.findViewById(R.id.iv_exce_pic1);
             iv_exce_pic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShowPicDialog(context,obj.getImg()).show();
                }
            });
            //convertView = mInflater.inflate(R.layout.item_video, null);
             holder.msg_la.addView(v);
        }
        }



        return convertView;
    }

    public class ViewHolder {
        public TextView name;
        public TextView desc;
        public TextView box;
        public TextView time;
        public LinearLayout msg_la;

    }
}
