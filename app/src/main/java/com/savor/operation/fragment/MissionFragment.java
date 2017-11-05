package com.savor.operation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.common.api.widget.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.core.ApiRequestListener;


import java.util.ArrayList;
import java.util.List;

/**
 * 创富生活fragment
 * Created by Administrator on 2017/7/4.
 */

public class MissionFragment extends BaseFragment implements ApiRequestListener,
        PullToRefreshListView.NetworkUnavailableOnClick ,
        AbsListView.OnScrollListener{
    private static final String TAG = "WealthLifeFragment";
    private Context context;
    private int category_id=0;
    @Override
    public String getFragmentName() {
        return TAG;
    }
    public static MissionFragment getInstance(int type) {
        MissionFragment missionFragment = new MissionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        missionFragment.setArguments(bundle);
        return  missionFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mission,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        Bundle argument = getArguments();

        initViews(view);
        setViews();

    }

    private void initViews(View view){


    }

    @Override
    public void setViews() {
        if(101 == category_id) {// 创富

        }else if(102 == category_id) {// 生活


        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDisplay();
    }

    private void initDisplay(){
        getDataFromServer("0");
    }

    private void getDataFromServer(String sort_num){

    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void bottomOnClick() {

    }
}
