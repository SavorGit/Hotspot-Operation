package com.savor.operation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.savor.operation.R;

/**
 * 机顶盒详情里 节目状态列表
 * Created by hezd on 2018/1/18.
 */

public class ProgramStatusAdapter extends RecyclerView.Adapter<ProgramStatusAdapter.ProgramStatusHolder> {

    private Context mContext;

    public ProgramStatusAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ProgramStatusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProgramStatusHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_system_status,parent,false));
    }

    @Override
    public void onBindViewHolder(ProgramStatusHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ProgramStatusHolder extends RecyclerView.ViewHolder {

        public TextView label;
        public TextView name;
        public ImageView statusIv;
        public ProgramStatusHolder(View itemView) {
            super(itemView);

        }
    }
}
