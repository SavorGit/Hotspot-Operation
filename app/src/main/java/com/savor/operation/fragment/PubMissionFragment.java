package com.savor.operation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.common.api.utils.ShowMessage;
import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.common.api.widget.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.activity.SeekTaskDetailActivity;
import com.savor.operation.activity.TaskDetailActivity;
import com.savor.operation.adapter.MissionAdapter;
import com.savor.operation.bean.City;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.MissionTaskListBean;
import com.savor.operation.bean.SkillList;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布者任务列表
 * Created by bushlee on 2017/7/4.
 */

public class PubMissionFragment extends BaseFragment implements ApiRequestListener,
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
    private boolean isUp = false;
    private City city;
    @Override
    public String getFragmentName() {
        return TAG;
    }
    public static PubMissionFragment getInstance(int type) {
        PubMissionFragment missionFragment = new PubMissionFragment();
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
            Intent intent = new Intent(context, SeekTaskDetailActivity.class);
            intent.putExtra("id",item.getId());
            startActivity(intent);


        }
    };
    @Override
    public void setViews() {

    }

    OnRefreshListener onRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            page = 1;
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
        getCity();
        getData();
    }

    private void getData(){
        AppApi.getPubTaskList(context,page,state,mSession.getLoginResponse().getUserid(),city.getId(),this);
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
            case POST_PUB_TASK_LIST_JSON:
                pullToRefreshListView.onRefreshComplete();
                if (obj instanceof List<?>){
                    List<MissionTaskListBean> mlist = (List<MissionTaskListBean>) obj;
                    handleWealthData(mlist);

                }


                break;
        }

    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        pullToRefreshListView.onRefreshComplete();
        switch (method){
            case POST_PUB_TASK_LIST_JSON:
                pullToRefreshListView.onLoadComplete(false,true);
                if (page == 1 ) {
                    missionAdapter.clear();
                }
//                if (obj instanceof ResponseErrorMessage){
//                    ResponseErrorMessage errorMessage = (ResponseErrorMessage)obj;
//                    String statusCode = String.valueOf(errorMessage.getCode());
//                    ShowMessage.showToast(context,errorMessage.getMessage());
//                }

                break;
        }
    }
    private void handleWealthData(List<MissionTaskListBean> mList){

        if (mList != null && mList.size() > 0) {
            page++;
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

            if (mList!=null && mList.size()<15) {
                pullToRefreshListView.onLoadComplete(false,true);
            }else {

                pullToRefreshListView.onLoadComplete(true,false);
            }
        }else {

            pullToRefreshListView.onLoadComplete(false,true);
        }



    }

    private void getCity(){
        LoginResponse loginResponse = mSession.getLoginResponse();
        SkillList skill_list = loginResponse.getSkill_list();
        List<City> manage_city = skill_list.getManage_city();
        if(manage_city!=null&&manage_city.size()>0) {
            city = getSelectCity(manage_city);
        }
    }
    private City getSelectCity(List<City> manage_city) {
        for(City city : manage_city) {
            if(city.isSelect())
                return city;
        }
        return null;
    }
}
