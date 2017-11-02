package com.savor.operation.activity;

import android.app.LauncherActivity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.savor.operation.R;
import com.savor.operation.adapter.ActionListAdapter;
import com.savor.operation.bean.ActionListItem;
import com.savor.operation.widget.DividerGridItemDecoration;
import com.savor.operation.widget.DividerItemDecoration;
import com.savor.operation.widget.decoration.SpacesItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SavorMainActivity extends BaseActivity {

    private static final int GRID_ROW_COUNT = 2;
    private TextView mCityTv;
    private TextView mSearchTv;
    private RecyclerView mItemRlv;

    /**
     * 首页功能分类类型
     */
    public enum ActionType implements Serializable{
        /**发布任务*/
        PUBLISH_TASK,
        /**任务列表*/
        TASK_LIST,
        /**系统状态*/
        SYSTEM_STATUS,
        /**搜索酒楼*/
        SEARCH_HOTEL,
        /**异常报告*/
        EXCEPTION_REPORT,
        /**维修记录*/
        FIX_HISTORY,
        /**我的任务*/
        MY_TASK,
        /**绑定版位*/
        BIND_BOX,
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savor_main);

        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        mCityTv = (TextView) findViewById(R.id.tv_city);
        mSearchTv = (TextView) findViewById(R.id.tv_search);
        mItemRlv = (RecyclerView) findViewById(R.id.rlv_items);
    }

    @Override
    public void setViews() {
        GridLayoutManager manager = new GridLayoutManager(this,GRID_ROW_COUNT);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        ActionListAdapter mAdapter = new ActionListAdapter(this);
        mItemRlv.setLayoutManager(manager);
        mItemRlv.setAdapter(mAdapter);
        //添加ItemDecoration，item之间的间隔
        int leftRight = DensityUtil.dip2px(this,2);
        int topBottom = DensityUtil.dip2px(this,2);

        mItemRlv.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, getResources().getColor(R.color.grid_item_divider)));
//        mItemRlv.addItemDecoration(new DividerGridItemDecoration(this));

        List<ActionListItem> list = new ArrayList<>();
        ActionListItem publishTask = new ActionListItem();
        publishTask.setType(ActionType.PUBLISH_TASK);

        ActionListItem taskList = new ActionListItem();
        taskList.setType(ActionType.TASK_LIST);
        taskList.setNum(3);

        ActionListItem systemStatus = new ActionListItem();
        systemStatus.setType(ActionType.SYSTEM_STATUS);

        ActionListItem excReport = new ActionListItem();
        excReport.setType(ActionType.EXCEPTION_REPORT);

        ActionListItem fixHistory = new ActionListItem();
        fixHistory.setType(ActionType.FIX_HISTORY);

        ActionListItem bindBox = new ActionListItem();
        bindBox.setType(ActionType.BIND_BOX);

        list.add(publishTask);
        list.add(taskList);
        list.add(systemStatus);
        list.add(excReport);
        list.add(fixHistory);
        list.add(bindBox);

        mAdapter.setData(list);
    }

    @Override
    public void setListeners() {

    }
}
