package com.savor.operation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.savor.operation.R;

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
        int padding15 = DensityUtil.dip2px(mContext, 15);
        int padding10 = DensityUtil.dip2px(mContext, 10);
        holder.textView.setPadding(padding15,padding10,padding15,padding10);
        holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.text_size));
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
