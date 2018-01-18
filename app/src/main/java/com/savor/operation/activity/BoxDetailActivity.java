package com.savor.operation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.savor.operation.R;
import com.savor.operation.adapter.ProgramStatusAdapter;

public class BoxDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private RecyclerView mProgramRlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_detail);

        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mProgramRlv = (RecyclerView) findViewById(R.id.rlv_program_status);

    }

    @Override
    public void setViews() {
        ProgramStatusAdapter programStatusAdapter = new ProgramStatusAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProgramRlv.setLayoutManager(linearLayoutManager);
        mProgramRlv.setAdapter(programStatusAdapter);
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
