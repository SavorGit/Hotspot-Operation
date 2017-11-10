package com.savor.operation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.activity.AbnormalityReportActivity;
import com.savor.operation.activity.BindBoxActivity;
import com.savor.operation.activity.MaintenanceRecordActivity;
import com.savor.operation.activity.MissionListActivity;
import com.savor.operation.activity.PublishTaskActivity;
import com.savor.operation.activity.SavorMainActivity;
import com.savor.operation.activity.SearchActivity;
import com.savor.operation.activity.SystemStatusActivity;
import com.savor.operation.bean.ActionListItem;
import com.savor.operation.enums.FunctionType;

import java.util.List;

/**
 * 功能列表适配器
 * Created by hezd on 2017/11/2.
 */

public class ActionListAdapter extends RecyclerView.Adapter<ActionListAdapter.ActionHolder> {
    private final Context mContext;
    private List<ActionListItem> mData;

    public ActionListAdapter (Context context) {
        this.mContext = context;
    }

    public void setData(List<ActionListItem> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public List<ActionListItem> getData() {
        return mData;
    }

    @Override
    public ActionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActionHolder(LayoutInflater.from(mContext).inflate(R.layout.item_action_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ActionHolder holder, int position) {
        ActionListItem actionListItem = mData.get(position);
        final int num = actionListItem.getNum();

        final FunctionType type = actionListItem.getType();
        int imageId = -1;
        String desc = "";
        initViews(holder, num, type, imageId, desc);


        holder.rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (type) {
                    case PUBLISH_TASK:
                        intent = new Intent(mContext, PublishTaskActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case TASK_LIST:
                        intent = new Intent(mContext, MissionListActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case SYSTEM_STATUS:
                        intent = new Intent(mContext, SystemStatusActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case EXCEPTION_REPORT:
                        intent = new Intent(mContext, AbnormalityReportActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case FIX_HISTORY:
                        intent = new Intent(mContext, MaintenanceRecordActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case BIND_BOX:
                        intent = new Intent(mContext, BindBoxActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case MY_TASK:
                        break;
                    case SEARCH_HOTEL:
                        intent = new Intent(mContext, SearchActivity.class);
                        mContext.startActivity(intent);
                        break;
                }
            }
        });
    }

    private void initViews(ActionHolder holder, int num, FunctionType type, int imageId, String desc) {
        switch (type) {
            case PUBLISH_TASK:
                imageId = R.drawable.ico_publish_task;
                desc = "发布任务";
                break;
            case TASK_LIST:
                imageId = R.drawable.ico_task_list;
                desc = "任务列表";
                if(num>0) {
                    holder.tv_num.setVisibility(View.VISIBLE);
                    holder.tv_num.setText(String.valueOf(num));
                }
                break;
            case SYSTEM_STATUS:
                imageId = R.drawable.ico_system_status;
                desc = "系统状态";
                break;
            case EXCEPTION_REPORT:
                imageId = R.drawable.ico_exc_report;
                desc = "异常报告";
                break;
            case FIX_HISTORY:
                imageId = R.drawable.ico_fix_history;
                desc = "维修记录";
                break;
            case BIND_BOX:
                imageId = R.drawable.ico_bind_box;
                desc = "绑定版位";
                break;
            case MY_TASK:
                imageId = R.drawable.ico_task_list;
                desc = "我的任务";
                break;
            case SEARCH_HOTEL:
                imageId = R.drawable.ico_publish_task;
                desc = "搜索酒楼";
                break;
        }
        holder.iv_image.setImageResource(imageId);
        holder.tv_desc.setText(desc);
    }

    @Override
    public int getItemCount() {
        return mData == null?0:mData.size();
    }

    public class ActionHolder extends RecyclerView.ViewHolder {
        public ImageView iv_image;
        public TextView tv_desc;
        public TextView tv_num;
        public RelativeLayout rl_parent;
        public ActionHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            rl_parent = (RelativeLayout) itemView.findViewById(R.id.rl_parent);
        }
    }
}
