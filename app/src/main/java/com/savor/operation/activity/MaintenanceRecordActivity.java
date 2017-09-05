package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.savor.operation.R;

/**
 * Created by bushlee on 2017/9/4.
 */

public class MaintenanceRecordActivity extends BaseActivity implements View.OnClickListener{
    private Context context;
    private Spinner spinner;
    private RelativeLayout back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_record);
        context = this;
        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        spinner = (Spinner) findViewById(R.id.spinner);
        back = (RelativeLayout) findViewById(R.id.back);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {
        back.setOnClickListener(this);
    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void hideLoadingLayout() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

        }
    }
}
