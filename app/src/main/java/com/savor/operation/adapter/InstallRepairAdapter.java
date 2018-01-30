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
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.bean.TaskDetailRepairImg;
import com.savor.operation.widget.ShowPicDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/7.
 */

public class InstallRepairAdapter extends BaseAdapter {

    private Context context;
    private List<TaskDetailRepair> list = new ArrayList<TaskDetailRepair>();
    private LayoutInflater mInflater;
    public InstallRepairAdapter(Context context) {
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
        InstallRepairAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_task_install_detail,null);
            holder = new InstallRepairAdapter.ViewHolder();
            holder.box = (TextView) convertView.findViewById(R.id.box);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.pic = (ImageView) convertView.findViewById(R.id.pic);
            holder.remark = (TextView) convertView.findViewById(R.id.remark);
            holder.user  = (TextView) convertView.findViewById(R.id.user);
            convertView.setTag(holder);
        }else {
            holder = (InstallRepairAdapter.ViewHolder) convertView.getTag();
        }
        final TaskDetailRepair item = (TaskDetailRepair) getItem(position);
        //holder.user.setText("执行人："+item.getUsername());
        String User = item.getUsername();
        if (!TextUtils.isEmpty(User)) {
            holder.user.setVisibility(View.VISIBLE);
            holder.user.setText("执行人："+item.getUsername());
        }else {
            holder.user.setText("执行人：无");
        }


        String Box_name = item.getBox_name();
        if (!TextUtils.isEmpty(Box_name)) {
            holder.box.setText("版位名称："+Box_name);
        }else {
            holder.box.setText("版位名称：无");
        }

        String time = item.getRepair_time();
        if (!TextUtils.isEmpty(time)) {
            holder.time.setText("操作时间："+time);
        }else {
            holder.time.setText("操作时间：无");
        }

        String remark = item.getRemark();
        if (!TextUtils.isEmpty(remark)) {
            holder.remark.setText("备注："+remark);
        }else {
            holder.remark.setText("备注：无");
        }






        List<TaskDetailRepairImg> repair_img = item.getRepair_img();
        if (repair_img != null && repair_img.size()>0) {
            final  TaskDetailRepairImg obj = repair_img.get(0);
            Glide.with(context).load(obj.getImg()).into(holder.pic);
            if (!TextUtils.isEmpty(obj.getImg())) {
                holder.pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    new ShowPicDialog(context,obj.getImg()).show();
                        PhotoShowActivity.startPhotoShowActivity(context,obj.getImg());
                    }
                });
            }

        }



        return convertView;
    }

    public class ViewHolder {
        public TextView user;
        public TextView box;
        public TextView remark;
        public TextView time;
        public ImageView pic;


    }
}
