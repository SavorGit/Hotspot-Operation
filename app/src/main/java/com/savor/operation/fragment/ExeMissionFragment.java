package com.savor.operation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.common.api.widget.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.MissionAdapter;
import com.savor.operation.bean.MissionTaskListBean;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行者任务列表
 * Created by bushlee on 2017/7/4.
 */

public class ExeMissionFragment extends BaseFragment implements ApiRequestListener,
        PullToRefreshListView.NetworkUnavailableOnClick ,
        AbsListView.OnScrollListener{
    private static final String TAG = "WealthLifeFragment";
    private Context context;
    private int category_id=0;
    private PullToRefreshListView pullToRefreshListView;
    private MissionAdapter missionAdapter=null;
    private int state;
    private List<MissionTaskListBean> listItems = new ArrayList<>();
    private int page = 1;
    private boolean isUp = true;
    @Override
    public String getFragmentName() {
        return TAG;
    }
    public static ExeMissionFragment getInstance(int type) {
        ExeMissionFragment missionFragment = new ExeMissionFragment();
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
        state = argument.getInt("type");
        initViews(view);
        setViews();

    }

    private void initViews(View view){
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.wl_listview);
        missionAdapter = new MissionAdapter(context);
        pullToRefreshListView.setAdapter(missionAdapter);
        pullToRefreshListView.setOnItemClickListener(itemClickListener);
        pullToRefreshListView.setOnRefreshListener(onRefreshListener);
        pullToRefreshListView.setOnLastItemVisibleListener(onLastItemVisibleListener);

    }

    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            MissionTaskListBean item = (MissionTaskListBean)parent.getItemAtPosition(position);


        }
    };
    @Override
    public void setViews() {

    }

    OnRefreshListener onRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            page = 0;
            isUp = true;
            getData();
        }
    };

    OnLastItemVisibleListener onLastItemVisibleListener = new OnLastItemVisibleListener() {
        @Override
        public void onLastItemVisible() {
            isUp = false;
            getData();
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(listItems!=null) {
            listItems.clear();
        }
        initDisplay();

    }

    private void initDisplay(){
        getData();
    }

    private void getData(){
        AppApi.getExeTaskList(context,page,state,mSession.getLoginResponse().getUserid(),this);
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

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method){
            case POST_VIEW_TASK_LIST_JSON:
                if (obj instanceof List<?>){
                    List<MissionTaskListBean> mlist = (List<MissionTaskListBean>) obj;
                    handleWealthData(mlist);

                }


                break;
        }

    }

    private void handleWealthData(List<MissionTaskListBean> mList){

        if (mList != null && mList.size() > 0) {
            pullToRefreshListView.setVisibility(View.VISIBLE);
            if (isUp) {
                listItems.clear();
                missionAdapter.clear();
                pullToRefreshListView.onLoadComplete(true,false);

            }else {
                pullToRefreshListView.onLoadComplete(true,false);
            }

            listItems.addAll(mList);
            missionAdapter.setData(listItems);

            if (mList!=null && mList.size()<20) {
                pullToRefreshListView.onLoadComplete(false,false);
            }else {
                page++;
                pullToRefreshListView.onLoadComplete(true,false);
            }
        }else {

            pullToRefreshListView.onLoadComplete(false,true);
        }



    }
}
