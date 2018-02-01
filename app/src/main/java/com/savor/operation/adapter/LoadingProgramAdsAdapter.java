package com.savor.operation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.LoadingProgramAds;
import com.savor.operation.bean.Program;

import java.util.List;

/**
 * 机顶盒详情里 节目状态列表
 * Created by hezd on 2018/1/18.
 */

public class LoadingProgramAdsAdapter extends BaseAdapter {

    private Context mContext;
    private List<LoadingProgramAds> mData;

    public LoadingProgramAdsAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<LoadingProgramAds> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
//        return 20;
        return mData == null ? 0 : mData.size();
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
        ProgramStatusHolder holder = null;
        if (convertView == null) {
            holder = new ProgramStatusHolder();
            convertView = View.inflate(mContext, R.layout.item_system_status, null);
            holder.label = (TextView) convertView.findViewById(R.id.tv_hint);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.statusIv = (ImageView) convertView.findViewById(R.id.iv_status);
            convertView.setTag(holder);
        } else {
            holder = (ProgramStatusHolder) convertView.getTag();
        }

        LoadingProgramAds program = (LoadingProgramAds) getItem(position);

        String ads_name = program.getAds_name();
        String type = program.getType();
        if (!TextUtils.isEmpty(ads_name)) {
            holder.name.setText(ads_name);
        }
        if (!TextUtils.isEmpty(type)) {
            holder.label.setText(type);
        }
//
        holder.statusIv.setVisibility(View.GONE);

        return convertView;
    }

    public class ProgramStatusHolder {

        public TextView label;
        public TextView name;
        public ImageView statusIv;
    }
}
