package com.savor.operation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.AdsBean;
import com.savor.operation.bean.LoadingProgramAds;
import com.savor.operation.bean.Program;

import java.util.List;

/**
 * 机顶盒详情里 节目状态列表
 * Created by hezd on 2018/1/18.
 */

public class LoadingProgramAdsAdapter extends BaseAdapter {

    private Context mContext;
    private List<AdsBean> mData;


    public LoadingProgramAdsAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<AdsBean> data) {
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
            convertView = View.inflate(mContext, R.layout.item_loading_list, null);
            holder.label = (TextView) convertView.findViewById(R.id.tv_hint);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.stausTv = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        } else {
            holder = (ProgramStatusHolder) convertView.getTag();
        }

        AdsBean program = (AdsBean) getItem(position);

        String ads_name = program.getName();
        String type = program.getType();
        if (!TextUtils.isEmpty(ads_name)) {
            holder.name.setText(ads_name);
        }
        if (!TextUtils.isEmpty(type)) {
            holder.label.setText(type);
        }

        int state = program.getState();
        if(state == 1) {
            holder.stausTv.setTextColor(mContext.getResources().getColor(R.color.app_color_green));
            holder.stausTv.setText("已下载");
        }else {
            holder.stausTv.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            holder.stausTv.setText("未下载");

        }
//
//        holder.statusIv.setVisibility(View.GONE);

        return convertView;
    }

    public class ProgramStatusHolder {

        public TextView label;
        public TextView name;
        public TextView stausTv;
    }
}
