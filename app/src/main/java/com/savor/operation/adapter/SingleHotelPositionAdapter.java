package com.savor.operation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.PositionListInfo;

import java.util.List;

/**
 * 酒楼版位信息适配器
 * Created by hezd on 2017/9/5.
 */

public class SingleHotelPositionAdapter extends BaseAdapter {

    private final Context mContext;
    private List<PositionListInfo.PositionInfo.BoxInfoBean> data;
    private OnFixBtnClickListener mOnFixBtnClickListener;
    private OnSignBtnClickListener mOnSignBtnClickListener;

    public SingleHotelPositionAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<PositionListInfo.PositionInfo.BoxInfoBean> data) {
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
            convertView = View.inflate(mContext, R.layout.item_single_hotel_position,null);
            holder.tv_last_optime = (TextView) convertView.findViewById(R.id.tv_last_optime);
            holder.tv_box_info = (TextView) convertView.findViewById(R.id.tv_box_info);
            holder.tv_last_operation = (TextView) convertView.findViewById(R.id.tv_last_operation);
            holder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
            holder.divider = convertView.findViewById(R.id.divider);
            holder.btn_fix = (Button) convertView.findViewById(R.id.btn_fix);
            holder.btn_sign = (Button) convertView.findViewById(R.id.btn_sign);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final PositionListInfo.PositionInfo.BoxInfoBean boxInfoBean = (PositionListInfo.PositionInfo.BoxInfoBean) getItem(position);

        holder.tv_box_info.setText(boxInfoBean.getRname()+" "+boxInfoBean.getMac()+" "+boxInfoBean.getBoxname());

        String last_ctime = boxInfoBean.getLast_ctime();
        String srtype = boxInfoBean.getSrtype();
        if(!TextUtils.isEmpty(srtype)) {
            holder.tv_last_operation.setText("最后操作状态："+srtype);
        }
        if(!TextUtils.isEmpty(last_ctime)) {
            holder.tv_last_optime.setText("最后操作时间："+last_ctime);
        }else {
            holder.tv_last_optime.setText("最后操作时间：无");
        }

        String current_location = boxInfoBean.getCurrent_location();
        if(!TextUtils.isEmpty(current_location)) {
            holder.tv_location.setText("最后操作位置："+current_location);
        }

        holder.btn_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnFixBtnClickListener!=null) {
                    mOnFixBtnClickListener.onFixBtnClick(boxInfoBean);
                }
            }
        });

        holder.btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnSignBtnClickListener!=null) {
                    mOnSignBtnClickListener.onSignBtnClick(boxInfoBean);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public View divider;
        public TextView tv_box_info;
        public TextView tv_last_operation;
        public TextView tv_last_optime;
        public TextView tv_location;
        public Button btn_fix;
        public Button btn_sign;
    }

    public void setOnFixBtnClickListener(OnFixBtnClickListener onFixBtnClickListener) {
        this.mOnFixBtnClickListener = onFixBtnClickListener;
    }

    public void setOnSignBtnClickListener(OnSignBtnClickListener onSignBtnClickListener) {
        this.mOnSignBtnClickListener = onSignBtnClickListener;
    }

    public interface OnFixBtnClickListener {
        void onFixBtnClick(PositionListInfo.PositionInfo.BoxInfoBean boxInfoBean);
    }

    public interface OnSignBtnClickListener {
        void onSignBtnClick(PositionListInfo.PositionInfo.BoxInfoBean boxInfoBean);
    }
}
