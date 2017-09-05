package com.savor.operation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;

import java.util.List;

/**
 * 首页信息列表
 * Created by hezd on 2017/9/5.
 */

public class IndexInfoAdapter extends RecyclerView.Adapter<IndexInfoAdapter.IndexHolder> {

    private final Context mContext;
    private List<String> mData;

    public IndexInfoAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public IndexHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IndexHolder holder = new IndexHolder(new TextView(parent.getContext()));
        return holder;
    }

    @Override
    public void onBindViewHolder(IndexHolder holder, int position) {
        String content = mData.get(position);
        holder.textView.setText(content);
    }

    @Override
    public int getItemCount() {
        return mData == null?0:mData.size();
    }

    public class IndexHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public IndexHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            int padding = DensityUtil.dip2px(mContext,5);
            textView.setPadding(padding,0,padding,padding);
        }
    }
}
