package com.savor.operation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.common.api.utils.DensityUtil;
import com.savor.operation.R;
import com.savor.operation.adapter.CityListAdapter;
import com.savor.operation.bean.City;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.SkillList;
import com.savor.operation.widget.decoration.SpacesItemDecoration;

import java.util.List;

public class CityListActivity extends BaseActivity implements View.OnClickListener {
    public static final int RESULT_CODE_CITY = 100;
    public static final int REQUEST_CODE_CITY = 101;
    private ImageView mBackBtn;
    private RecyclerView mCityListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);

        mCityListView = (RecyclerView) findViewById(R.id.rlv_city_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        CityListAdapter adapter = new CityListAdapter(this);
        mCityListView.setAdapter(adapter);
        LoginResponse loginResponse = mSession.getLoginResponse();
        SkillList skill_list = loginResponse.getSkill_list();
        if(skill_list!=null) {
            List<City> manage_city = skill_list.getManage_city();
            adapter.setData(manage_city);
        }
        //添加ItemDecoration，item之间的间隔
        int leftRight = DensityUtil.dip2px(this,2);
        int topBottom = DensityUtil.dip2px(this,2);
        mCityListView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, getResources().getColor(R.color.grid_item_divider)));
    }

    @Override
    public void setViews() {

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
