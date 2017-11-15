package com.savor.operation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.savor.operation.R;
import com.savor.operation.bean.MissionTaskListBean;
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.widget.ShowPicDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/7.
 */

public class RepairAdapter extends BaseAdapter {

    private Context context;
    private List<TaskDetailRepair> list = new ArrayList<TaskDetailRepair>();
    private LayoutInflater mInflater;
    public RepairAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<TaskDetailRepair> data) {
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
        RepairAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_task_detail,null);
            holder = new RepairAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            holder.imge_la = (ImageView) convertView.findViewById(R.id.image);


            convertView.setTag(holder);
        }else {
            holder = (RepairAdapter.ViewHolder) convertView.getTag();
        }
        final TaskDetailRepair item = (TaskDetailRepair) getItem(position);
        holder.name.setText("版位名称："+item.getBox_name());
        holder.desc.setText("故障现象："+item.getFault_desc());
        Glide.with(context).load(item.getFault_img_url()).into(holder.imge_la);
        holder.imge_la.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowPicDialog(context,item.getFault_img_url()).show();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public TextView name;
        public TextView desc;
        public ImageView imge_la;

    }
}
