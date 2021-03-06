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

public class TvBoxFixHistoryAdapter extends RecyclerView.Adapter<TvBoxFixHistoryAdapter.BoxFixHolder> {

    private final Context mContext;
    private List<FixHistoryResponse.PositionInfo.BoxInfoBean.RepaireInfo> data;

    public TvBoxFixHistoryAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public BoxFixHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BoxFixHolder(LayoutInflater.from(mContext).inflate(R.layout.item_box_fix_history,parent,false));
    }

    public void setData(List<FixHistoryResponse.PositionInfo.BoxInfoBean.RepaireInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BoxFixHolder holder, int position) {
        FixHistoryResponse.PositionInfo.BoxInfoBean.RepaireInfo repaireInfo = data.get(position);
        holder.tv_fix_desc.setText(repaireInfo.getCtime()+"("+repaireInfo.getNickname()+")");
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
