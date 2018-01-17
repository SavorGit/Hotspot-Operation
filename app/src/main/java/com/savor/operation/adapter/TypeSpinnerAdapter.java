package com.savor.operation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.StateConf;
import com.savor.operation.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/6.
 */

public class TypeSpinnerAdapter extends BaseAdapter {
    private final Context mContext;
    private List<StateConf> mData = new ArrayList<StateConf>();

    public TypeSpinnerAdapter(Context context, List<StateConf> list) {
        this.mContext = context;
        this.mData = list;
    }

    public void setData(List<StateConf> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
    public void addData(List<StateConf> data) {
        if(data!=null&&data.size()>0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<StateConf> getData() {
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


        StateConf item = (StateConf) getItem(position);
        //holder.name.setText(item.getUsername());
       holder.name.setText(item.getName());
        return convertView;
    }

    public class ViewHolder {
        public TextView name;
    }
}
