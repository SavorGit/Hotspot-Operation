package com.savor.operation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.savor.operation.R;

/**
 * Created by bushlee on 2017/9/4.
 */

public class AbnormalityInfoActivity extends BaseActivity implements View.OnClickListener{
    private Context context;
    private RelativeLayout back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormality_info);
        context = this;
        getViews();
        setViews();
        setListeners();
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
