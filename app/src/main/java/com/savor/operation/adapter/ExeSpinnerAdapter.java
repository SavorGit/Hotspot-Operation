package com.savor.operation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.ExecutorInfoBean;
import com.savor.operation.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/6.
 */

public class ExeSpinnerAdapter extends BaseAdapter {
    private final Context mContext;
    private List<ExecutorInfoBean> mData = new ArrayList<ExecutorInfoBean>();

    public ExeSpinnerAdapter(Context context, List<ExecutorInfoBean> list) {
        this.mContext = context;
        this.mData = list;
    }

    public void setData(List<ExecutorInfoBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
    public void addData(List<ExecutorInfoBean> data) {
        if(data!=null&&data.size()>0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<ExecutorInfoBean> getData() {
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
            convertView = View.inflate(mContext, R.layout.item_user,null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        ExecutorInfoBean item = (ExecutorInfoBean) getItem(position);
        //holder.name.setText(item.getUsername());
       holder.name.setText(item.getBox_name());
        return convertView;
    }

    public class ViewHolder {
        public TextView name;
    }
}
