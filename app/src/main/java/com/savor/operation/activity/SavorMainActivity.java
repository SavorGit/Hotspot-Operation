package com.savor.operation.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.common.api.utils.AppUtils;
import com.common.api.utils.DensityUtil;
import com.savor.operation.R;
import com.savor.operation.adapter.ActionListAdapter;
import com.savor.operation.bean.ActionListItem;
import com.savor.operation.bean.City;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.RoleInfo;
import com.savor.operation.bean.SkillList;
import com.savor.operation.bean.TaskNum;
import com.savor.operation.core.AppApi;
import com.savor.operation.enums.FunctionType;
import com.savor.operation.utils.log.ActionType;
import com.savor.operation.widget.CommonDialog;
import com.savor.operation.widget.decoration.SpacesItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.savor.operation.activity.CityListActivity.REQUEST_CODE_CITY;
import static com.savor.operation.activity.CityListActivity.RESULT_CODE_CITY;

public class SavorMainActivity extends BaseActivity implements View.OnClickListener {

    private static final int GRID_ROW_COUNT = 2;
    private TextView mCityTv;
    private TextView mSearchTv;
    private RecyclerView mItemRlv;
    private TextView mUserInfoTv;
    private TextView mExitBtn;
    /**发布者*/
    public static final List<ActionListItem> PUBLISH_TEMS = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.PUBLISH_TASK,0));
            add(new ActionListItem(FunctionType.PUB_TASK_LIST,0));
            add(new ActionListItem(FunctionType.SYSTEM_STATUS,0));
            add(new ActionListItem(FunctionType.EXCEPTION_REPORT,0));
            add(new ActionListItem(FunctionType.FIX_HISTORY,0));
        }
    };
    /**
     * 执行者
     */
    public static final List<ActionListItem> PERFORM_ITEMS = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.MY_TASK,0));
            add(new ActionListItem(FunctionType.SYSTEM_STATUS,0));
            add(new ActionListItem(FunctionType.EXCEPTION_REPORT,0));
            add(new ActionListItem(FunctionType.FIX_HISTORY,0));
            add(new ActionListItem(FunctionType.BIND_BOX,0));
        }
    };
    /**
     * 指派者
     */
    public static final List<ActionListItem> APPOINTER_ITEMS = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.APPOINT_TASK_LIST,0));
            add(new ActionListItem(FunctionType.SYSTEM_STATUS,0));
            add(new ActionListItem(FunctionType.EXCEPTION_REPORT,0));
            add(new ActionListItem(FunctionType.FIX_HISTORY,0));
        }
    };

    /**
     * 查看者
     */
    public static final List<ActionListItem> LOOK_ITEMS = new ArrayList<ActionListItem>(){
        {
            add(new ActionListItem(FunctionType.TASK_LIST,0));
            add(new ActionListItem(FunctionType.SYSTEM_STATUS,0));
            add(new ActionListItem(FunctionType.EXCEPTION_REPORT,0));
            add(new ActionListItem(FunctionType.FIX_HISTORY,0));
        }
    };
    private ActionListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savor_main);

        getViews();
        setViews();
        setListeners();

    }

    private void getData() {
        LoginResponse loginResponse = mSession.getLoginResponse();
        SkillList skill_list = loginResponse.getSkill_list();
        String userid = loginResponse.getUserid();
        List<City> manage_city = skill_list.getManage_city();
        String id = getCityId(manage_city);
        AppApi.getTaskNum(this,id,userid,this);
    }

    private String getCityId(List<City> manage_city) {
        for(City city: manage_city) {
            if(city.isSelect()) {
                return city.getId();
            }
        }
        return null;
    }

    @Override
    public void getViews() {
        mCityTv = (TextView) findViewById(R.id.tv_city);
        mSearchTv = (TextView) findViewById(R.id.tv_search);
        mItemRlv = (RecyclerView) findViewById(R.id.rlv_items);
        mUserInfoTv = (TextView) findViewById(R.id.tv_user_info);
        mExitBtn = (TextView) findViewById(R.id.tv_exit);
    }

    @Override
    public void setViews() {
        // 初始化城市信息
        LoginResponse loginResponse = mSession.getLoginResponse();
        SkillList skill_list = loginResponse.getSkill_list();
        List<City> manage_city = skill_list.getManage_city();
        if(manage_city!=null&&manage_city.size()>0) {
            City city = getSelectCity(manage_city);
            if(manage_city.size()>1) {
                Drawable drawable = getResources().getDrawable(R.drawable.ico_arraw_down_normal);
                mCityTv.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
                mCityTv.setOnClickListener(this);
            }else {
                mCityTv.setOnClickListener(null);
                mCityTv.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }
            mCityTv.setText(city.getRegion_name());
        }else {
            mCityTv.setOnClickListener(null);
            mCityTv.setCompoundDrawables(null,null,null,null);
        }

        // 用户信息
        String nickname = loginResponse.getNickname();
        String appVersion = AppUtils.getAppVersion(this);
        mUserInfoTv.setText("运维端"+appVersion+"--登录账号："+nickname);

        // 功能列表
        GridLayoutManager manager = new GridLayoutManager(this,GRID_ROW_COUNT);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mAdapter = new ActionListAdapter(this);
        mItemRlv.setLayoutManager(manager);
        mItemRlv.setAdapter(mAdapter);
        //添加ItemDecoration，item之间的间隔
        int leftRight = DensityUtil.dip2px(this,2);
        int topBottom = DensityUtil.dip2px(this,2);

        mItemRlv.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, getResources().getColor(R.color.grid_item_divider)));

        if(skill_list!=null) {
            RoleInfo role_info = skill_list.getRole_info();
            String id = role_info.getId();
            if("1".equals(id)) {
                mAdapter.setData(PUBLISH_TEMS);
            }else if("2".equals(id)) {
                mAdapter.setData(APPOINTER_ITEMS);
                getData();
            }else if("3".equals(id)) {
                mAdapter.setData(PERFORM_ITEMS);
                getData();
            }else if("4".equals(id)) {
                mAdapter.setData(LOOK_ITEMS);
            }
        }
    }

    private City getSelectCity(List<City> manage_city) {
        for(City city : manage_city) {
            if(city.isSelect())
                return city;
        }
        return null;
    }

    @Override
    public void setListeners() {
        mExitBtn.setOnClickListener(this);
        mSearchTv.setOnClickListener(this);
//        mCityTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_city:
                intent = new Intent(this,CityListActivity.class);
                startActivityForResult(intent,REQUEST_CODE_CITY);
                break;
            case R.id.tv_search:
                intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_exit:
                new CommonDialog(this, "是否要注销当前登录账号", new CommonDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        mSession.setLoginResponse(null);
                        mSession.setAccount(null);
                        Intent intent = new Intent(SavorMainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }, new CommonDialog.OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                },"确定").show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CITY&&resultCode == RESULT_CODE_CITY) {
            LoginResponse loginResponse = mSession.getLoginResponse();
            SkillList skill_list = loginResponse.getSkill_list();
            if(skill_list!=null) {
                List<City> manage_city = skill_list.getManage_city();
                for(City city:manage_city) {
                    if(city.isSelect()) {
                        mCityTv.setText(city.getRegion_name());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_TASK_NUM_JSON:
                if(obj instanceof TaskNum) {
                    TaskNum taskNum = (TaskNum) obj;
                    String nums = taskNum.getNums();
                    int num = 0;
                    try {
                        num = Integer.valueOf(nums);
                    }catch (Exception e){}
                    List<ActionListItem> data = mAdapter.getData();
                    for(ActionListItem item : data) {
                        FunctionType type = item.getType();
                        if(type == FunctionType.MY_TASK) {
                            item.setNum(num);
                            break;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
