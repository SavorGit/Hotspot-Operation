package com.savor.operation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.core.AppApi;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mSearchTv;
    private TextView mFixHistoryTv;
    private TextView mExceptionReprotTv;

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
        mSearchTv = (TextView) findViewById(R.id.tv_search);
        mFixHistoryTv = (TextView) findViewById(R.id.tv_fix_history);
        mExceptionReprotTv = (TextView) findViewById(R.id.tv_exception_report);
    }

    @Override
    public void setViews() {

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

    
}
