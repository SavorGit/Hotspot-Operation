package com.savor.operation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.ErrorReportBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/6.
 */

public class ErrorReportListAdapter extends BaseAdapter {
    private final Context mContext;
    private List<ErrorReportBean> mData = new ArrayList<ErrorReportBean>();

    public ErrorReportListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ErrorReportBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
    public void addData(List<ErrorReportBean> data) {
        if(data!=null&&data.size()>0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<ErrorReportBean> getData() {
        return mData;
    }
    @Override
    public int getCount() {
        return mData == null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_error_report,null);
            holder = new ViewHolder();
            holder.info = (TextView) convertView.findViewById(R.id.info);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        ErrorReportBean item = (ErrorReportBean) getItem(position);
        holder.info.setText(item.getInfo());
        holder.time.setText(item.getDate());
        return convertView;
    }

    public class ViewHolder {
        public TextView info;
        public TextView time;
    }
}
