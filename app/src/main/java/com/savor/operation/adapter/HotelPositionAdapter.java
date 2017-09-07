package com.savor.operation.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.FixHistoryResponse;

import java.util.List;

/**
 * 酒楼版位信息适配器
 * Created by hezd on 2017/9/5.
 */

public class HotelPositionAdapter extends BaseAdapter {

    private final Context mContext;
    private List<FixHistoryResponse.PositionInfo.BoxInfoBean> data;
    private OnFixBtnClickListener mOnFixBtnClickListener;

    public HotelPositionAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<FixHistoryResponse.PositionInfo.BoxInfoBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext,R.layout.item_hotel_position,null);
            holder.rlv_fix_history = (RecyclerView) convertView.findViewById(R.id.rlv_fix_history);
            holder.tv_box_info = (TextView) convertView.findViewById(R.id.tv_box_info);
            holder.tv_last_xintiao = (TextView) convertView.findViewById(R.id.tv_last_xintiao);
            holder.tv_last_log = (TextView) convertView.findViewById(R.id.tv_last_log);
            holder.tv_hint = (TextView) convertView.findViewById(R.id.tv_hint);
            holder.tv_fix = (TextView) convertView.findViewById(R.id.tv_fix);
            holder.tv_box_status = (ImageView) convertView.findViewById(R.id.tv_box_status);
            holder.divider = convertView.findViewById(R.id.divider);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final FixHistoryResponse.PositionInfo.BoxInfoBean boxInfoBean = (FixHistoryResponse.PositionInfo.BoxInfoBean) getItem(position);
        List<FixHistoryResponse.PositionInfo.BoxInfoBean.RepaireInfo> repair_record = boxInfoBean.getRepair_record();

        holder.tv_box_info.setText(boxInfoBean.getRname()+" "+boxInfoBean.getMac()+" "+boxInfoBean.getBoxname());

        int ustate = boxInfoBean.getUstate();
        if(ustate == 1) {
            holder.tv_box_status.setImageResource(R.drawable.cirlce_green);
        }else {
            holder.tv_box_status.setImageResource(R.drawable.cirlce_red);
        }

        String last_heart_time = boxInfoBean.getLast_heart_time();
        if(!TextUtils.isEmpty(last_heart_time)) {
            holder.tv_last_xintiao.setText("最后心跳时间："+last_heart_time);
        }

        String ltime = boxInfoBean.getLtime();
        if(!TextUtils.isEmpty(ltime)) {
            holder.tv_last_log.setText("最后上传日志时间："+ltime);
        }

        TvBoxFixHistoryAdapter adapter = new TvBoxFixHistoryAdapter(mContext);
        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        holder.rlv_fix_history.setLayoutManager(manager);
        holder.rlv_fix_history.setAdapter(adapter);

        adapter.setData(repair_record);

        if(repair_record==null||repair_record.size() == 0) {
            holder.tv_hint.setVisibility(View.INVISIBLE);
        }else {
            holder.tv_hint.setVisibility(View.VISIBLE);
        }

        holder.tv_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnFixBtnClickListener!=null) {
                    mOnFixBtnClickListener.onFixBtnClick(position,boxInfoBean);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public RecyclerView rlv_fix_history;
        public View divider;
        public TextView tv_box_info;
        public TextView tv_last_log;
        public TextView tv_fix;
        public TextView tv_last_xintiao;
        public ImageView tv_box_status;
        public TextView tv_hint;
    }

    public interface OnFixBtnClickListener {
        void onFixBtnClick(int position,FixHistoryResponse.PositionInfo.BoxInfoBean boxInfoBean);
    }

    public void setOnFixBtnClickListener(OnFixBtnClickListener listener) {
        this.mOnFixBtnClickListener = listener;
    }

}
