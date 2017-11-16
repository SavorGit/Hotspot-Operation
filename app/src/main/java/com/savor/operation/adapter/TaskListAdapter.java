package com.savor.operation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.activity.PublishTaskActivity;
import com.savor.operation.activity.TaskActivity;
import com.savor.operation.bean.Task;

import java.util.List;

/**
 * 选择任务适配器
 * Created by hezd on 2017/11/6.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> {

    private final Context mContext;
    private List<Task> mTaskList;

    /**信息检测*/
    public static final int TYPE_INFO_CHECK = 1;
    /**网络改造*/
    public static final int TYPE_NETWORK_REMOULD = 8;
    /**安装与验收*/
    public static final int TYPE_SETUP_CHECK = 2;
    /**维修*/
    public static final int TYPE_FIX = 4;


    public TaskListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<Task> taskList) {
        this.mTaskList = taskList;
        notifyDataSetChanged();
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskHolder(LayoutInflater.from(mContext).inflate(R.layout.item_task_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        final Task task = mTaskList.get(position);
        String bref = task.getBref();
        String type_name = task.getType_name();
        final int type_id = task.getType_id();

        holder.tv_pre.setText(bref);
        holder.tv_content.setText(type_name);

        holder.ll_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (type_id) {
                    case TYPE_FIX:
                        intent = new Intent(mContext,TaskActivity.class);
                        intent.putExtra("type", PublishTaskActivity.TaskType.FIX);
                        intent.putExtra("task",task);
                        mContext.startActivity(intent);
                        break;
                    case TYPE_INFO_CHECK:
                        intent = new Intent(mContext,TaskActivity.class);
                        intent.putExtra("type", PublishTaskActivity.TaskType.INFO_CHECK);
                        intent.putExtra("task",task);
                        mContext.startActivity(intent);
                        break;
                    case TYPE_NETWORK_REMOULD:
                        intent = new Intent(mContext,TaskActivity.class);
                        intent.putExtra("type", PublishTaskActivity.TaskType.NETWORK_REMOULD);
                        intent.putExtra("task",task);
                        mContext.startActivity(intent);
                        break;
                    case TYPE_SETUP_CHECK:
                        intent = new Intent(mContext,TaskActivity.class);
                        intent.putExtra("type", PublishTaskActivity.TaskType.SETUP_AND_CHECK);
                        intent.putExtra("task",task);
                        mContext.startActivity(intent);
                        break;
                }
            }
        });
//        switch (type_id) {
//            case T
//        }
    }

    @Override
    public int getItemCount() {
        return mTaskList == null? 0:mTaskList.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {

        public TextView tv_pre;
        public TextView tv_content;
        public LinearLayout ll_parent;
        public TaskHolder(View itemView) {
            super(itemView);
            tv_pre = (TextView) itemView.findViewById(R.id.tv_pre);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            ll_parent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
        }
    }
}
