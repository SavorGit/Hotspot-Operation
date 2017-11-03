package com.savor.operation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.savor.operation.R;

import java.util.List;

/**
 * Created by hezd on 2017/11/2.
 */

public class MaintainTaskAdapter extends BaseAdapter {
    private final Context mContext;
    private List<String> mData;

    public MaintainTaskAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
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
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_maintain_layout,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHolder {

    }
}
