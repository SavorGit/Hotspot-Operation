package com.savor.operation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.BindBoxListBean;

import java.util.List;

/**
 * 绑定版位适配器
 * Created by hezd on 2017/11/9.
 */

public class BindBoxAdapter extends BaseAdapter {

    private final Context mContext;
    private List<BindBoxListBean> mData;
    private OnBindBtnClickListener mListener;

    public BindBoxAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<BindBoxListBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public List<BindBoxListBean> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData == null? 0:mData.size();
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
        BindBoxHolder holder;
        if(convertView == null) {
            holder = new BindBoxHolder();
            convertView = View.inflate(mContext,R.layout.item_bind_box,null);
            holder.tv_room_name = (TextView) convertView.findViewById(R.id.tv_room_name);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_room = (TextView) convertView.findViewById(R.id.tv_room);
            holder.tv_box_name = (TextView) convertView.findViewById(R.id.tv_box_name);
            holder.tv_mac = (TextView) convertView.findViewById(R.id.tv_mac);
            holder.tv_bind = (TextView) convertView.findViewById(R.id.tv_bind);
            convertView.setTag(holder);
        }else {
            holder = (BindBoxHolder) convertView.getTag();
        }

        final BindBoxListBean bindBoxListBean = (BindBoxListBean) getItem(position);
        String room_name = bindBoxListBean.getRoom_name();
        String box_name = bindBoxListBean.getBox_name();
        String tv_brand = bindBoxListBean.getTv_brand();
        String box_mac = bindBoxListBean.getBox_mac();

        holder.tv_room.setText("包间名称："+room_name);
        holder.tv_room_name.setText(room_name);

        holder.tv_name.setText("电视名称："+tv_brand);
        holder.tv_box_name.setText("机顶盒名称："+box_name);
        holder.tv_mac.setText("MAC地址："+box_mac);

        holder.tv_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null) {
                    mListener.onBindBtnClick(bindBoxListBean);
                }
            }
        });

        return convertView;
    }

    public class BindBoxHolder {
        public TextView tv_room_name;
        public TextView tv_name;
        public TextView tv_room;
        public TextView tv_box_name;
        public TextView tv_mac;
        public TextView tv_bind;
    }

    public void setOnBindBtnClickListener(OnBindBtnClickListener listener) {
        this.mListener = listener;
    }

    public interface OnBindBtnClickListener {
        void onBindBtnClick(BindBoxListBean bindBoxListBean);
    }
}
