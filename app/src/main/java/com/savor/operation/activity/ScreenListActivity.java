package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.savor.operation.R;
import com.savor.operation.core.ApiRequestListener;

/**
 * Created by admin on 2017/11/5.
 */

public class ScreenListActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener {

    private Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_record);
        context = this;
        getViews();
        setViews();
        setListeners();
//        getData();
//        getRepairRecordList();
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void getViews() {

    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {

    }
}
