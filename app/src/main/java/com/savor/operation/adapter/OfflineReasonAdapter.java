package com.savor.operation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.FixHistoryResponse;

import java.util.List;

/**
 * Created by hezd on 2017/9/5.
 */

public class OfflineReasonAdapter extends RecyclerView.Adapter<OfflineReasonAdapter.BoxFixHolder> {

    private final Context mContext;
    private List<String> data;

    public OfflineReasonAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public BoxFixHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BoxFixHolder(LayoutInflater.from(mContext).inflate(R.layout.item_offline_reason,parent,false));
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BoxFixHolder holder, int position) {
        String reason = data.get(position);
        holder.tv_fix_desc.setText(reason);
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    public class BoxFixHolder extends RecyclerView.ViewHolder {
        public TextView tv_fix_desc;
        public BoxFixHolder(View itemView) {
            super(itemView);
            tv_fix_desc = (TextView) itemView.findViewById(R.id.tv_fix_desc);
        }
    }
}
