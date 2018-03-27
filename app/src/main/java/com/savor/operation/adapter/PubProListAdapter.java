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
import com.savor.operation.bean.PubProgram;

import java.util.List;

/**
 * 机顶盒详情里 节目状态列表
 * Created by hezd on 2018/1/18.
 */

public class PubProListAdapter extends BaseAdapter {

    private LoadingType type;
    private Context mContext;
    private List<PubProgram> mData;

    /**
     * 下载列表类型，节目单，广告。
     */
    public enum LoadingType {
        TYPE_ADS,
        TYPE_PROGRAM,
        TYPE_PUBLISH,
    }

    public PubProListAdapter(Context context,LoadingType type) {
        this.mContext = context;
        this.type = type;
    }

    public void setData(List<PubProgram> data) {
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
            holder.statusTv = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        } else {
            holder = (ProgramStatusHolder) convertView.getTag();
        }

        PubProgram program = (PubProgram) getItem(position);

        String ads_name = program.getName();
        String type = program.getType();
        if (!TextUtils.isEmpty(ads_name)) {
            holder.name.setText(ads_name);
        }
        if (!TextUtils.isEmpty(type)) {
            holder.label.setText(type);
        }
//
        int state = program.getState();
        if(this.type == LoadingType.TYPE_PUBLISH) {
            holder.statusIv.setVisibility(View.GONE);
            holder.statusTv.setVisibility(View.GONE);
        }else {
            holder.statusIv.setVisibility(View.GONE);
            holder.statusTv.setVisibility(View.VISIBLE);
            if(state == 1) {
                holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.app_color_green));
                holder.statusTv.setText("已下载");
            }else {
                holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                holder.statusTv.setText("未下载");
            }
        }

        return convertView;
    }

    public class ProgramStatusHolder {

        public TextView label;
        public TextView name;
        public ImageView statusIv;
        public TextView statusTv;
    }
}
