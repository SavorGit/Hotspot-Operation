package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.adapter.SearchHotelListAdapter;
import com.savor.operation.bean.City;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.HotelListResponse;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.SkillList;
import com.savor.operation.core.AppApi;
import com.savor.operation.enums.SearchHotelOpType;
import com.savor.operation.widget.DividerItemDecoration;

import java.util.List;

public class SearchActivity extends BaseActivity implements SearchHotelListAdapter.OnHoteClickListener, View.OnClickListener {

    private EditText mSearchEt;
    private RecyclerView mHotelListView;
    private SearchHotelListAdapter mAdapter;
    private ImageView mBackBtn;
    private SearchHotelOpType type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        handleIntent();
        getViews();
        setViews();
        setListeners();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        type = (SearchHotelOpType) intent.getSerializableExtra("type");
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mSearchEt = (EditText) findViewById(R.id.et_search_hotel);
        mHotelListView = (RecyclerView) findViewById(R.id.rlv_hotel_list);
    }

    @Override
    public void setViews() {
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mHotelListView.setLayoutManager(manager);
        mAdapter = new SearchHotelListAdapter(this);
        mHotelListView.setAdapter(mAdapter);
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);
        mAdapter.setOnHotelClickListener(this);
        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = mSearchEt.getText().toString();
                    LoginResponse loginResponse = mSession.getLoginResponse();
                    SkillList skill_list = loginResponse.getSkill_list();
                    List<City> manage_city = skill_list.getManage_city();
                    City city = getSelectCity(manage_city);
                    if(!TextUtils.isEmpty(content)) {
                        AppApi.searchHotel(SearchActivity.this,content,city.getId(),SearchActivity.this);
                    }else {
                        ShowMessage.showToast(SearchActivity.this,"请输入酒楼名称");
                    }
                }
                return false;
            }
        });
    }

    private City getSelectCity(List<City> manage_city) {
        for(City city:manage_city) {
            if(city.isSelect())
                return city;
        }
        return null;
    }

    @Override
    public void onHotelClick(Hotel hotel) {
        Intent intent;
        if(type == SearchHotelOpType.PUBLIS_TASK) {
             intent = new Intent();
             intent.putExtra("hotel",hotel);
             setResult(TaskActivity.RESULT_CODE_SEARCH,intent);
             finish();
        }else {
            intent = new Intent(this,HotelPositionInfoAcitivty.class);
            intent.putExtra("hotel",hotel);
            startActivity(intent);
        }
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_SEARCH_HOTEL_JSON:
                if(obj instanceof HotelListResponse) {
                    HotelListResponse data = (HotelListResponse) obj;
                    List<Hotel> list = data.getList();
                    if(list!=null&&list.size()>0) {
                        mAdapter.setData(list);
                    }else {
                        ShowMessage.showToast(this,"无相关酒楼");
                    }
                }
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method, obj);
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
