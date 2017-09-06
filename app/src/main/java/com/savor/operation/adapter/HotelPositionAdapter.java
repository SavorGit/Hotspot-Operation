package com.savor.operation.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.savor.operation.R;

/**
 * 酒楼版位信息适配器
 * Created by hezd on 2017/9/5.
 */

public class HotelPositionAdapter extends BaseAdapter {

    private final Context mContext;

    public HotelPositionAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return 10;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext,R.layout.item_hotel_position,null);
            holder.rlv_fix_history = (RecyclerView) convertView.findViewById(R.id.rlv_fix_history);
            holder.divider = convertView.findViewById(R.id.divider);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        TvBoxFixHistoryAdapter adapter = new TvBoxFixHistoryAdapter(mContext);
        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        holder.rlv_fix_history.setLayoutManager(manager);
        holder.rlv_fix_history.setAdapter(adapter);


        return convertView;
    }

    public class ViewHolder {
        public RecyclerView rlv_fix_history;
        public View divider;
    }

//    @Override
//    public PositionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new PositionHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hotel_position,parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(PositionHolder holder, int position) {
//        TvBoxFixHistoryAdapter adapter = new TvBoxFixHistoryAdapter(mContext);
//        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
//        holder.rlv_fix_history.setLayoutManager(manager);
//       holder.rlv_fix_history.setAdapter(adapter);
//        int itemCount = getItemCount();
//        if(itemCount == 0 || position == itemCount -1) {
//            holder.divider.setVisibility(View.GONE);
//        }else {
//            holder.divider.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 3;
//    }
//
//    public class PositionHolder extends RecyclerView.ViewHolder {
//        public RecyclerView rlv_fix_history;
//        public View divider;
//
//        public PositionHolder(View itemView) {
//            super(itemView);
//            rlv_fix_history = (RecyclerView) itemView.findViewById(R.id.rlv_fix_history);
//            divider = itemView.findViewById(R.id.divider);
//        }
//    }
}
