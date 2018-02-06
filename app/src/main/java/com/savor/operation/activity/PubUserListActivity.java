package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.savor.operation.R;
import com.savor.operation.adapter.CityListAdapter;
import com.savor.operation.adapter.PubUserListAdapter;
import com.savor.operation.bean.City;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.PubUser;
import com.savor.operation.bean.PubUserBean;
import com.savor.operation.bean.SkillList;
import com.savor.operation.widget.decoration.SpacesItemDecoration;

import java.util.List;

public class PubUserListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private RecyclerView mCityListView;
    private TextView tv_name;
    private PubUser pubUser;
    private List<PubUserBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        getIntentData();
        getViews();
        setViews();
        setListeners();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if (intent != null) {
            pubUser = (PubUser)intent.getSerializableExtra("PubUser");
        }
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        tv_name = (TextView) findViewById(R.id.tv_name);
        mCityListView = (RecyclerView) findViewById(R.id.rlv_city_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mCityListView.setLayoutManager(manager);


        PubUserListAdapter adapter = new PubUserListAdapter(this);
        mCityListView.setAdapter(adapter);
        if (pubUser != null) {
            list =  pubUser.getList();
            if (list != null && list.size()>0) {
                adapter.setData(list);
            }
        }
//        LoginResponse loginResponse = mSession.getLoginResponse();
//        SkillList skill_list = loginResponse.getSkill_list();
//        if(skill_list!=null) {
//            List<City> manage_city = skill_list.getManage_city();
//            adapter.setData(manage_city);
//        }
        //添加ItemDecoration，item之间的间隔
        int leftRight = DensityUtil.dip2px(this,2);
        int topBottom = DensityUtil.dip2px(this,2);
        mCityListView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, getResources().getColor(R.color.grid_item_divider)));
    }

    @Override
    public void setViews() {
        tv_name.setText("选择维护人");
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
