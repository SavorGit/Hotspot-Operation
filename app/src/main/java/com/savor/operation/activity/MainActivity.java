package com.savor.operation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.adapter.IndexInfoAdapter;
import com.savor.operation.bean.IndexInfo;
import com.savor.operation.core.AppApi;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mSearchTv;
    private TextView mFixHistoryTv;
    private TextView mExceptionReprotTv;
    private TextView mRemarkTv;
    private RecyclerView mInfoLv;
    private IndexInfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();
        setViews();
        setListeners();
        getData();
    }

    private void getData() {
        AppApi.getIndexInfo(this,this);
    }

    @Override
    public void getViews() {
        mInfoLv = (RecyclerView) findViewById(R.id.rlv_info);
        mRemarkTv = (TextView) findViewById(R.id.tv_reamrk);
        mSearchTv = (TextView) findViewById(R.id.tv_search);
        mFixHistoryTv = (TextView) findViewById(R.id.tv_fix_history);
        mExceptionReprotTv = (TextView) findViewById(R.id.tv_exception_report);
    }

    @Override
    public void setViews() {
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mInfoLv.setLayoutManager(manager);

        mAdapter = new IndexInfoAdapter(this);
        mInfoLv.setAdapter(mAdapter);
    }

    @Override
    public void setListeners() {
        mSearchTv.setOnClickListener(this);
        mFixHistoryTv.setOnClickListener(this);
        mExceptionReprotTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:

                break;
            case R.id.tv_fix_history:

                break;
            case R.id.tv_exception_report:

                break;
        }
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_INDEX_JSON:
                if(obj instanceof IndexInfo) {
                    IndexInfo indexInfo = (IndexInfo) obj;
                    List<String> list = indexInfo.getList();
                    mAdapter.setData(list);
                }
                break;
        }
    }
}
