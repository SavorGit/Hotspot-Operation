package com.savor.operation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.savor.operation.R;
import com.savor.operation.bean.ExeUserList;
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.bean.TaskInfoListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/7.
 */

public class JobAdapter extends BaseAdapter {

    private Context context;
    private List<ExeUserList> list = new ArrayList<ExeUserList>();
    private LayoutInflater mInflater;
    private Appoint callback;
    public JobAdapter(Context context,Appoint callback) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    public void setData(List<ExeUserList> data) {
//        if (isup) {
//            list.clear();
//        }
        this.list = data;
        notifyDataSetChanged();
    }

    public List<ExeUserList> getData() {
        return list;
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
        JobAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.exe_user_app_layout,null);
            holder = new JobAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.job_la = (TextView) convertView.findViewById(R.id.job_la);
            holder.assign = (TextView) convertView.findViewById(R.id.assign);
            holder.msg_la = (LinearLayout) convertView.findViewById(R.id.msg_la);


            convertView.setTag(holder);
        }else {
            holder = (JobAdapter.ViewHolder) convertView.getTag();
        }
        ExeUserList item = (ExeUserList) getItem(position);
        holder.name.setText(item.getUsername());
        List<TaskInfoListBean> task_info = item.getTask_info();
        if (task_info != null && task_info.size()>0) {
            for (int i = 0; i < task_info.size(); i++) {
                View v = mInflater.inflate(R.layout.upgrade_info_item_layout, null);
                TextView info = (TextView)v.findViewById(R.id.info);
                String msg = task_info.get(i).getTask_type_desc();
                String hotel_name = task_info.get(i).getHotel_name();
                if (!TextUtils.isEmpty(msg)) {
                    info.setText(msg+hotel_name);
                }
                //convertView = mInflater.inflate(R.layout.item_video, null);
                holder.msg_la.addView(v);
            }
        }
        holder.assign.setOnClickListener(new mStoreListener(item) );
//        TaskDetailRepair item = (TaskDetailRepair) getItem(position);
//        holder.name.setText("版位名称："+item.getBox_name());
//        holder.desc.setText("故障现象："+item.getFault_desc());
//        Glide.with(context).load(item.getFault_img_url()).into(holder.imge_la);
        return convertView;
    }

    /**
     * 单击收藏事件监听器
     */
    public class mStoreListener implements View.OnClickListener {
        private ExeUserList itemVo;

        public mStoreListener(ExeUserList vodAndTopicItemVo) {
            this.itemVo = vodAndTopicItemVo;
        }

        @Override
        public void onClick(View view) {
            callback.appoint(itemVo);
        }
    }

    public interface Appoint {
        void appoint(ExeUserList itemVo);
    }


    public class ViewHolder {
        public TextView name;
        public TextView job_la;
        public TextView assign;
        public LinearLayout msg_la;

    }
}
