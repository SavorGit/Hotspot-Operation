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
import com.savor.operation.activity.PhotoShowActivity;
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
    private List<TaskDetailRepair> list = new ArrayList<TaskDetailRepair>();
    private LayoutInflater mInflater;
    public MaintenanceRepairAdapter(Context context) {
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
        final  MaintenanceRepairAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_task_completed_detail,null);
            holder = new MaintenanceRepairAdapter.ViewHolder();
            //holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            holder.box = (TextView) convertView.findViewById(R.id.box);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.msg_la = (LinearLayout) convertView.findViewById(R.id.msg_la);
            holder.fault_img = (ImageView) convertView.findViewById(R.id.fault_img);
            holder.remark = (TextView) convertView.findViewById(R.id.remark);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            holder.user  = (TextView) convertView.findViewById(R.id.user);
            holder.pic_la = (TextView) convertView.findViewById(R.id.pic_la);
            convertView.setTag(holder);
        }else {
            holder = (MaintenanceRepairAdapter.ViewHolder) convertView.getTag();
        }
        final TaskDetailRepair item = (TaskDetailRepair) getItem(position);

        String desc = item.getFault_desc();
        if (!TextUtils.isEmpty(desc)) {
            holder.desc.setText("故障现象："+desc);
        }else {
            holder.desc.setText("故障现象：无");
        }

        String box = item.getBox_name();
        if (!TextUtils.isEmpty(box)) {
            holder.box.setText("维修版位："+box);
        }else {
            holder.box.setText("维修版位：无");
        }

        String time = item.getRepair_time();
        if (!TextUtils.isEmpty(time)) {
            holder.time.setText("操作时间："+time);
        }else {
            holder.time.setText("操作时间：无");
        }

        String Username = item.getUsername();
        String Remark = item.getRemark();
        if (!TextUtils.isEmpty(Username)) {
            holder.user.setVisibility(View.VISIBLE);
            holder.user.setText("执行人："+item.getUsername());
        }else {
            holder.user.setText("执行人：无");
        }

        if (!TextUtils.isEmpty(Remark)) {
            holder.remark.setVisibility(View.VISIBLE);
            holder.remark.setText("备注："+item.getRemark());
        }else {
            holder.remark.setText("备注：无");
        }


        String ts = item.getRepair_time();
        if (TextUtils.isEmpty(ts)) {
            ts = "无";
            holder.time.setVisibility(View.GONE);
        }else {
            holder.time.setVisibility(View.VISIBLE);
            holder.time.setText("操作时间："+ts);
        }


        Glide.with(context).load(item.getFault_img_url()).into(holder.fault_img);
        holder.fault_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ShowPicDialog(context,item.getFault_img_url()).show();
                PhotoShowActivity.startPhotoShowActivity(context,item.getFault_img_url());
            }
        });

        String stateS = item.getState();
        if (TextUtils.isEmpty(stateS)) {
            holder.state.setVisibility(View.GONE);
        }else {
            holder.state.setVisibility(View.VISIBLE);
            if ("1".equals(stateS)) {
                holder.state.setText("已解决");
            }else if("2".equals(stateS)){
                holder.state.setText("未解决");
            }
        }

        List<TaskDetailRepairImg> repair_img = item.getRepair_img();
        if (repair_img != null && repair_img.size()>0) {
            holder.msg_la.removeAllViews();
            holder.msg_la.setVisibility(View.VISIBLE);
            holder.pic_la.setVisibility(View.VISIBLE);
         for (int i = 0; i < repair_img.size(); i++) {
             final  TaskDetailRepairImg obj = repair_img.get(i);
             final View v = mInflater.inflate(R.layout.item_pic_layout, null);
             final ImageView iv_exce_pic1 = (ImageView)v.findViewById(R.id.iv_exce_pic1);
             Glide.with(context).load(obj.getImg()).into(iv_exce_pic1);
             iv_exce_pic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new ShowPicDialog(context,obj.getImg()).show();
                    PhotoShowActivity.startPhotoShowActivity(context,obj.getImg());
                }
            });
            //convertView = mInflater.inflate(R.layout.item_video, null);
             holder.msg_la.addView(v);
            }
        }else {
            holder.msg_la.setVisibility(View.GONE);
            holder.pic_la.setVisibility(View.GONE);

            //holder.pic_la.setText("无");
        }



        return convertView;
    }

    public class ViewHolder {
        public TextView name;
        public TextView desc;
        public TextView box;
        public TextView time;
        public LinearLayout msg_la;
        public ImageView fault_img;
        public TextView remark;
        public TextView state;
        public TextView user;
        public TextView pic_la;

    }
}
