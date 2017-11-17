package com.savor.operation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.savor.operation.R;
import com.savor.operation.bean.ExecuteRepair;
import com.savor.operation.bean.TaskDetailRepair;
import com.savor.operation.bean.TaskDetailRepairImg;
import com.savor.operation.widget.ShowPicDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/7.
 */

public class CompleteRepairAdapter extends BaseAdapter {

    private Context context;
    private List<ExecuteRepair> list = new ArrayList<ExecuteRepair>();
    private LayoutInflater mInflater;
    public CompleteRepairAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<ExecuteRepair> data) {
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
        CompleteRepairAdapter.ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_task_completed_big_pic_detail,null);
            holder = new CompleteRepairAdapter.ViewHolder();
            holder.la_a = (TextView) convertView.findViewById(R.id.la_a);
            holder.img_a = (ImageView) convertView.findViewById(R.id.img_a);
            holder.la_b = (TextView) convertView.findViewById(R.id.la_b);
            holder.img_b = (ImageView) convertView.findViewById(R.id.img_b);
            convertView.setTag(holder);
        }else {
            holder = (CompleteRepairAdapter.ViewHolder) convertView.getTag();
        }
        final ExecuteRepair item = (ExecuteRepair) getItem(position);

        List<TaskDetailRepairImg> repair_img = item.getRepair_img();
        if (repair_img != null && repair_img.size()>0) {
            final TaskDetailRepairImg obj1 = repair_img.get(0);
            if (repair_img.size() == 1) {
                holder.la_a.setText("信息检测单");
                Glide.with(context).load(repair_img.get(0).getImg()).placeholder(R.drawable.kong_mrjz).into(holder.img_a);
                holder.img_a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ShowPicDialog(context,obj1.getImg()).show();
                    }
                });
                holder.la_a.setVisibility(View.VISIBLE);
                holder.img_a.setVisibility(View.VISIBLE);

                holder.la_b.setVisibility(View.GONE);
                holder.img_b.setVisibility(View.GONE);
            }else if (repair_img.size() == 2) {
                final TaskDetailRepairImg obj2 = repair_img.get(1);
                holder.la_a.setVisibility(View.VISIBLE);
                holder.img_a.setVisibility(View.VISIBLE);

                holder.la_b.setVisibility(View.VISIBLE);
                holder.img_b.setVisibility(View.VISIBLE);

                holder.la_a.setText("改造设备图");
                Glide.with(context).load(obj1.getImg()).placeholder(R.drawable.kong_mrjz).into(holder.img_a);
                holder.img_a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ShowPicDialog(context,obj1.getImg()).show();
                    }
                });

                holder.la_b.setText("网络改造检测单");
                Glide.with(context).load(obj2.getImg()).placeholder(R.drawable.kong_mrjz).into(holder.img_b);
                holder.img_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ShowPicDialog(context,obj2.getImg()).show();
                    }
                });
            }
        }
//        Glide.with(context).load(item.getFault_img_url()).placeholder(R.drawable.kong_mrjz).into(holder.imge_la);
//        holder.imge_la.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new ShowPicDialog(context,item.getFault_img_url()).show();
//            }
//        });


        return convertView;
    }

    public class ViewHolder {
        public TextView time;
        public TextView la_a;
        public ImageView img_a;
        public TextView la_b;
        public ImageView img_b;

    }
}
