package com.savor.operation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.savor.operation.R;

/**
 * Created by hezd on 2017/9/5.
 */

public class TvBoxFixHistoryAdapter extends RecyclerView.Adapter<TvBoxFixHistoryAdapter.BoxFixHolder> {

    private final Context mContext;

    public TvBoxFixHistoryAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public BoxFixHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BoxFixHolder(LayoutInflater.from(mContext).inflate(R.layout.item_box_fix_history,parent,false));
    }

    @Override
    public void onBindViewHolder(BoxFixHolder holder, int position) {
        holder.tv_fix_desc.setText("08-28 17：39（郑伟）");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class BoxFixHolder extends RecyclerView.ViewHolder {
        public TextView tv_fix_desc;
        public BoxFixHolder(View itemView) {
            super(itemView);
            tv_fix_desc = (TextView) itemView.findViewById(R.id.tv_fix_desc);
        }
    }
}
