package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.savor.operation.R;
import com.savor.operation.adapter.ActionListAdapter;
import com.savor.operation.bean.ActionListItem;
import com.savor.operation.enums.FunctionType;
import com.savor.operation.widget.CommonDialog;
import com.savor.operation.widget.decoration.SpacesItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SavorMainActivity extends BaseActivity implements View.OnClickListener {

    private static final int GRID_ROW_COUNT = 2;
    private TextView mCityTv;
    private TextView mSearchTv;
    private RecyclerView mItemRlv;
    private TextView mUserInfoTv;
    private TextView mExitBtn;


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
        mUserInfoTv = (TextView) findViewById(R.id.tv_user_info);
        mExitBtn = (TextView) findViewById(R.id.tv_exit);
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
        publishTask.setType(FunctionType.PUBLISH_TASK);

        ActionListItem taskList = new ActionListItem();
        taskList.setType(FunctionType.TASK_LIST);
        taskList.setNum(3);

        ActionListItem systemStatus = new ActionListItem();
        systemStatus.setType(FunctionType.SYSTEM_STATUS);

        ActionListItem excReport = new ActionListItem();
        excReport.setType(FunctionType.EXCEPTION_REPORT);

        ActionListItem fixHistory = new ActionListItem();
        fixHistory.setType(FunctionType.FIX_HISTORY);

        ActionListItem bindBox = new ActionListItem();
        bindBox.setType(FunctionType.BIND_BOX);

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
        mExitBtn.setOnClickListener(this);
        mSearchTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_search:
                intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_exit:
                new CommonDialog(this, "是否要注销当前登录账号", new CommonDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        mSession.setLoginResponse(null);
                        Intent intent = new Intent(SavorMainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }, new CommonDialog.OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                },"确定").show();
                break;
        }
    }
}
