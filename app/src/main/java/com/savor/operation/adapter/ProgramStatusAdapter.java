package com.savor.operation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.Program;

import java.util.List;

/**
 * 机顶盒详情里 节目状态列表
 * Created by hezd on 2018/1/18.
 */

public class ProgramStatusAdapter extends RecyclerView.Adapter<ProgramStatusAdapter.ProgramStatusHolder> {

    private Context mContext;
    private List<Program> mData;

    public ProgramStatusAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<Program> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ProgramStatusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProgramStatusHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_system_status,parent,false));
    }

    @Override
    public void onBindViewHolder(ProgramStatusHolder holder, int position) {
        Program program = mData.get(position);
        String ads_name = program.getName();
        int flag = program.getFlag();
        String type = program.getType();
        if(!TextUtils.isEmpty(ads_name)) {
            holder.name.setText(ads_name);
        }
        if(!TextUtils.isEmpty(type)) {
            holder.label.setText(type);
        }

        if(flag == 0) {
            holder.statusIv.setImageResource(R.drawable.ico_exist);
        }else {
            holder.statusIv.setImageResource(R.drawable.ico_noexit);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null?0:mData.size();
    }

    public class ProgramStatusHolder extends RecyclerView.ViewHolder {

        public TextView label;
        public TextView name;
        public ImageView statusIv;
        public ProgramStatusHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.tv_hint);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            statusIv = (ImageView) itemView.findViewById(R.id.iv_status);
        }
    }
}
