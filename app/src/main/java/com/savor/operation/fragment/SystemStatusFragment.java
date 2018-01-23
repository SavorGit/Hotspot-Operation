package com.savor.operation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.BoxStatus;
import com.savor.operation.bean.HotelStatus;
import com.savor.operation.bean.SmallStatus;
import com.savor.operation.bean.SystemStatus;
import com.savor.operation.bean.SystemStatusResponse;
import com.savor.operation.bean.TotalStatus;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;

import java.util.List;

/**
 * 系统状态
 * @author hezd
 */
public class SystemStatusFragment extends BaseFragment implements ApiRequestListener, View.OnClickListener {
    private static final String ARG_CITY_ID = "params_city_id";
    private static final String ON_LINE = "在线  ";
    private static final String OFF_LINE = "离线  ";
    private static final String NORMAL = "正常  ";
    private static final String EXCEPTION = "异常  ";
    private static final String BLACK_LIST = "黑名单  ";
    private static final String FREEZEE = "冻结  ";
    private static final String COUNT = "总数  ";
    private static final String BROKEN = "报损  ";

    private String cityId;
    private TextView mOnLineTimeTv;
    private TextView mOnlineTv;
    private TextView mOfflineTv;
    private TextView mHotelRemarkTv;
    private TextView mHotelStatusTv;
    private TextView mSmallNormalTv;
    private TextView mExeSmallTv;
    private TextView mBoxNormalTv;
    private TextView mExeBoxTv;
    private TextView mBlackListTv;
    private TextView mHotelCountTv;
    private TextView mHotelNoramlTv;
    private TextView mHotelFreezeTv;
    private TextView mOneBoxCountTv;
    private TextView mOneBoxNormalTv;
    private TextView mFreezeOneBoxTv;
    private TextView mOneBoxNameTv;
    private TextView mTwoBoxNameTv;
    private TextView mTwoBoxCountTv;
    private TextView mTwoBoxNormalTv;
    private TextView mTwoFreezeTv;
    private TextView mBoxt5GNameTv;
    private TextView mCount5GTv;
    private TextView mNormal5GTv;
    private TextView mFreeze5GTv;
    private TextView mBoxThreeNameTv;
    private TextView mBoxThreeCountTv;
    private TextView mThreeNormalTv;
    private TextView mFreezeThreeTv;
    private TextView mSmallCountTv;
    private TextView mSmallNormalCountTv;
    private TextView mSmallFreezeCountTv;
    private TextView mBoxCountTv;
    private TextView mBoxNormalCountTv;
    private TextView mBoxBaosunTv;
    private TextView mBoxFreezeTv;
    private TextView mBoxOneTv;
    private TextView mBoxOneCountTv;
    private TextView mBoxOneNormalTv;
    private TextView mBoxOneBrokenTv;
    private TextView mBoxOneFreezeeTv;
    private TextView mBoxTwoNameTv;
    private TextView mBoxTwoCountTv;
    private TextView mBoxTwoNormalTv;
    private TextView mBoxTwoBrokenTv;
    private TextView mBoxTwoFreezeeTv;
    private TextView mBox5GNameTv;
    private TextView mBox5GCountTv;
    private TextView mBox5GNormalTv;
    private TextView mBox5GBreakTv;
    private TextView mBox5GFreezeeTv;
    private TextView mThreeBoxNameTv;
    private TextView mThreeBoxCountTv;
    private TextView mThreeBoxNormalTv;
    private TextView mThreeBoxBreakTv;
    private TextView mThreeBoxFreezeeTv;
    private ProgressBar mLoadingPb;
    private ScrollView contentSv;
    private ImageView mRefreshBtn;


    public SystemStatusFragment() {
        // Required empty public constructor
    }

    public static SystemStatusFragment newInstance(String cityId) {
        SystemStatusFragment fragment = new SystemStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityId = getArguments().getString(ARG_CITY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentLayout = inflater.inflate(R.layout.fragment_system_status, container, false);
        initView(parentLayout);
        setListeners();
        return parentLayout;
    }

    @Override
    public void setListeners() {
        mRefreshBtn.setOnClickListener(this);
    }

    private void initView(View parentLayout) {
        mRefreshBtn = (ImageView) parentLayout.findViewById(R.id.iv_refresh);
        contentSv = (ScrollView) parentLayout.findViewById(R.id.slv_content);
        mLoadingPb = (ProgressBar) parentLayout.findViewById(R.id.pb_loading);

        mBlackListTv = (TextView) parentLayout.findViewById(R.id.tv_black_list);
        mBoxNormalTv = (TextView) parentLayout.findViewById(R.id.tv_normal_box);
        mExeBoxTv = (TextView) parentLayout.findViewById(R.id.tv_exe_box);
        mSmallNormalTv = (TextView) parentLayout.findViewById(R.id.tv_normal_small);
        mExeSmallTv = (TextView) parentLayout.findViewById(R.id.tv_exe_small);
        mOnLineTimeTv = (TextView) parentLayout.findViewById(R.id.tv_online_time);
        mOnlineTv = (TextView) parentLayout.findViewById(R.id.tv_online);
        mOfflineTv = (TextView) parentLayout.findViewById(R.id.tv_offline);
        mHotelStatusTv = (TextView) parentLayout.findViewById(R.id.tv_hotel_status);
        mHotelRemarkTv = (TextView) parentLayout.findViewById(R.id.tv_hotel_remark);

        mHotelCountTv = (TextView) parentLayout.findViewById(R.id.tv_hotel_count);
        mHotelNoramlTv = (TextView) parentLayout.findViewById(R.id.tv_hotel_normal);
        mHotelFreezeTv = (TextView) parentLayout.findViewById(R.id.tv_hotel_freeze);

        mOneBoxNameTv = (TextView) parentLayout.findViewById(R.id.tv_one_box_name);
        mOneBoxCountTv = (TextView) parentLayout.findViewById(R.id.tv_one_box_count);
        mOneBoxNormalTv = (TextView) parentLayout.findViewById(R.id.tv_normal_one);
        mFreezeOneBoxTv = (TextView) parentLayout.findViewById(R.id.tv_exe_one);

        mTwoBoxNameTv = (TextView) parentLayout.findViewById(R.id.tv_two_box_name);
        mTwoBoxCountTv = (TextView) parentLayout.findViewById(R.id.tv_two_box_count);
        mTwoBoxNormalTv = (TextView) parentLayout.findViewById(R.id.tv_normal_two);
        mTwoFreezeTv = (TextView) parentLayout.findViewById(R.id.tv_exe_two);

        mBoxt5GNameTv = (TextView) parentLayout.findViewById(R.id.tv_5g_name);
        mCount5GTv = (TextView) parentLayout.findViewById(R.id.tv_count_5g);
        mNormal5GTv = (TextView) parentLayout.findViewById(R.id.tv_normal_5g);
        mFreeze5GTv = (TextView) parentLayout.findViewById(R.id.tv_exe_5g);

        mBoxThreeNameTv = (TextView) parentLayout.findViewById(R.id.tv_box_three_name);
        mBoxThreeCountTv = (TextView) parentLayout.findViewById(R.id.tv_box_three_count);
        mThreeNormalTv = (TextView) parentLayout.findViewById(R.id.tv_normal_three);
        mFreezeThreeTv = (TextView) parentLayout.findViewById(R.id.tv_exe_three);

        mSmallCountTv = (TextView) parentLayout.findViewById(R.id.tv_small_count);
        mSmallNormalCountTv = (TextView) parentLayout.findViewById(R.id.tv_small_normal);
        mSmallFreezeCountTv = (TextView) parentLayout.findViewById(R.id.tv_small_freezee_count);

        mBoxCountTv = (TextView) parentLayout.findViewById(R.id.tv_box_count);
        mBoxNormalCountTv = (TextView) parentLayout.findViewById(R.id.tv_box_normal);
        mBoxBaosunTv = (TextView) parentLayout.findViewById(R.id.tv_box_baosun);
        mBoxFreezeTv = (TextView) parentLayout.findViewById(R.id.tv_box_freeze);

        mBoxOneTv = (TextView) parentLayout.findViewById(R.id.tv_box_one_name);
        mBoxOneCountTv = (TextView) parentLayout.findViewById(R.id.tv_box_one_count);
        mBoxOneNormalTv = (TextView) parentLayout.findViewById(R.id.tv_normal_one_box);
        mBoxOneBrokenTv = (TextView) parentLayout.findViewById(R.id.tv_broken_one_box);
        mBoxOneFreezeeTv = (TextView) parentLayout.findViewById(R.id.tv_exe_one_box);

        mBoxTwoNameTv = (TextView) parentLayout.findViewById(R.id.tv_box_two_name);
        mBoxTwoCountTv = (TextView) parentLayout.findViewById(R.id.tv_box_two_count);
        mBoxTwoNormalTv = (TextView) parentLayout.findViewById(R.id.tv_normal_two_box);
        mBoxTwoBrokenTv = (TextView) parentLayout.findViewById(R.id.tv_broken_two_box);
        mBoxTwoFreezeeTv = (TextView) parentLayout.findViewById(R.id.tv_exe_two_box);

        mBox5GNameTv = (TextView) parentLayout.findViewById(R.id.tv_box_5g_name);
        mBox5GCountTv = (TextView) parentLayout.findViewById(R.id.tv_box_5g_count);
        mBox5GNormalTv = (TextView) parentLayout.findViewById(R.id.tv_normal_5g_box);
        mBox5GBreakTv = (TextView) parentLayout.findViewById(R.id.tv_broken_5g_box);
        mBox5GFreezeeTv = (TextView) parentLayout.findViewById(R.id.tv_exe_5g_box);

        mThreeBoxNameTv = (TextView) parentLayout.findViewById(R.id.tv_three_box_name);
        mThreeBoxCountTv = (TextView) parentLayout.findViewById(R.id.tv_three_box_count);
        mThreeBoxNormalTv = (TextView) parentLayout.findViewById(R.id.tv_normal_three_box);
        mThreeBoxBreakTv = (TextView) parentLayout.findViewById(R.id.tv_broken_three_box);
        mThreeBoxFreezeeTv = (TextView) parentLayout.findViewById(R.id.tv_exe_three_box);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
    }

    private void getData() {
        if(!TextUtils.isEmpty(cityId)) {
            showLoadingLayout();
            AppApi.getSystemStatus(getContext(),cityId,this);
        }
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_SYSTEM_STATUS_JSON:
                if(obj instanceof SystemStatusResponse) {
                    SystemStatusResponse systemStatusResponse = (SystemStatusResponse) obj;
                    initSystemStatus(systemStatusResponse.getList());
                    hideLoadingLayout();
                }
                break;
        }
    }

    private void initSystemStatus(SystemStatus systemStatus) {
        TotalStatus heart = systemStatus.getHeart();
        String update_time = heart.getUpdate_time();
        int hotel_online = heart.getHotel_online();
        int hotel_not_onlie = heart.getHotel_not_onlie();
        String hotel_10_72_not_onlie = heart.getHotel_10_72_not_onlie();
        String remark = heart.getRemark();
        int small_plat_normal_num = heart.getSmall_plat_normal_num();
        int small_plat_not_normal_num = heart.getSmall_plat_not_normal_num();
        int box_normal_num = heart.getBox_normal_num();
        int box_not_normal_num = heart.getBox_not_normal_num();
        String black_box_num = heart.getBlack_box_num();

        mOnLineTimeTv.setText("更新时间："+update_time);
        mOnlineTv.setText(ON_LINE+hotel_online);
        mOfflineTv.setText(OFF_LINE+hotel_not_onlie);
        mHotelStatusTv.setText(hotel_10_72_not_onlie);
        mHotelRemarkTv.setText(remark);
        mSmallNormalTv.setText(NORMAL+small_plat_normal_num);
        mExeSmallTv.setText(EXCEPTION+small_plat_not_normal_num);
        mBoxNormalTv.setText(NORMAL+box_normal_num);
        mExeBoxTv.setText(EXCEPTION+box_not_normal_num);
        mBlackListTv.setText(BLACK_LIST+black_box_num);

        HotelStatus hotel = systemStatus.getHotel();
        String hotel_all_freeze_nums = hotel.getHotel_all_freeze_nums();
        String hotel_all_normal_nums = hotel.getHotel_all_normal_nums();
        String hotel_all_nums = hotel.getHotel_all_nums();

        mHotelCountTv.setText(hotel_all_nums);
        mHotelNoramlTv.setText(NORMAL+hotel_all_normal_nums);
        mHotelFreezeTv.setText(FREEZEE+hotel_all_freeze_nums);

        List<HotelStatus.ListBean> list = hotel.getList();
        HotelStatus.ListBean oneBox = list.get(0);
        String hotel_all_freeze_nums1 = oneBox.getHotel_all_freeze_nums();
        String hotel_all_normal_nums1 = oneBox.getHotel_all_normal_nums();
        String hotel_all_nums1 = oneBox.getHotel_all_nums();
        String name = oneBox.getName();
        mOneBoxNameTv.setText(name);
        mOneBoxNormalTv.setText(NORMAL+hotel_all_normal_nums1);
        mOneBoxCountTv.setText(COUNT+hotel_all_nums1);
        mFreezeOneBoxTv.setText(FREEZEE+hotel_all_freeze_nums1);

        HotelStatus.ListBean twoBox = list.get(1);
        String hotel_all_freeze_nums2 = twoBox.getHotel_all_freeze_nums();
        String hotel_all_normal_nums2 = twoBox.getHotel_all_normal_nums();
        String hotel_all_nums2 = twoBox.getHotel_all_nums();
        String name1 = twoBox.getName();
        mTwoBoxNameTv.setText(name1);
        mTwoBoxCountTv.setText(COUNT+hotel_all_nums2);
        mTwoBoxNormalTv.setText(NORMAL+hotel_all_normal_nums2);
        mTwoFreezeTv.setText(FREEZEE+hotel_all_freeze_nums2);

        HotelStatus.ListBean hotelBox5G = list.get(2);
        String hotel_all_freeze_nums3 = hotelBox5G.getHotel_all_freeze_nums();
        String hotel_all_normal_nums3 = hotelBox5G.getHotel_all_normal_nums();
        String hotel_all_nums3 = hotelBox5G.getHotel_all_nums();
        String name2 = hotelBox5G.getName();
        mBoxt5GNameTv.setText(name2);
        mCount5GTv.setText(COUNT+hotel_all_nums3);
        mNormal5GTv.setText(NORMAL+hotel_all_normal_nums3);
        mFreeze5GTv.setText(FREEZEE+hotel_all_freeze_nums3);

        HotelStatus.ListBean threeBox = list.get(3);
        String hotel_all_freeze_nums4 = threeBox.getHotel_all_freeze_nums();
        String hotel_all_normal_nums4 = threeBox.getHotel_all_normal_nums();
        String hotel_all_nums4 = threeBox.getHotel_all_nums();
        String name3 = threeBox.getName();
        mBoxThreeNameTv.setText(name3);
        mBoxThreeCountTv.setText(COUNT+hotel_all_nums4);
        mThreeNormalTv.setText(NORMAL+hotel_all_normal_nums4);
        mFreezeThreeTv.setText(FREEZEE+hotel_all_freeze_nums4);

        SmallStatus small = systemStatus.getSmall();
        String all_nums = small.getAll_nums();
        String freeze_nums = small.getFreeze_nums();
        String normal_nums = small.getNormal_nums();
        mSmallCountTv.setText(all_nums);
        mSmallNormalCountTv.setText(NORMAL+normal_nums);
        mSmallFreezeCountTv.setText(FREEZEE+freeze_nums);

        BoxStatus box = systemStatus.getBox();
        String all_num = box.getAll_num();
        String break_all_num = box.getBreak_all_num();
        String freeze_all_num = box.getFreeze_all_num();
        String normal_all_num = box.getNormal_all_num();
        mBoxCountTv.setText(all_num);
        mBoxNormalCountTv.setText(normal_all_num);
        mBoxBaosunTv.setText(break_all_num);
        mBoxFreezeTv.setText(freeze_all_num);

        List<BoxStatus.ListBean> boxList = box.getList();
        BoxStatus.ListBean boxOne = boxList.get(0);
        String box_all_num = boxOne.getBox_all_num();
        String box_break_all_num = boxOne.getBox_break_all_num();
        String box_freeze_all_num = boxOne.getBox_freeze_all_num();
        String box_normal_all_num = boxOne.getBox_normal_all_num();
        String name4 = boxOne.getName();
        mBoxOneTv.setText(name4);
        mBoxOneBrokenTv.setText(BROKEN+box_break_all_num);
        mBoxOneCountTv.setText(COUNT+box_all_num);
        mBoxOneNormalTv.setText(NORMAL+box_normal_all_num);
        mBoxOneFreezeeTv.setText(FREEZEE+box_freeze_all_num);

        BoxStatus.ListBean boxTwo = boxList.get(1);
        String box_all_num1 = boxTwo.getBox_all_num();
        String box_break_all_num1 = boxTwo.getBox_break_all_num();
        String box_freeze_all_num1 = boxTwo.getBox_freeze_all_num();
        String box_normal_all_num1 = boxTwo.getBox_normal_all_num();
        String name5 = boxTwo.getName();
        mBoxTwoNameTv.setText(name5);
        mBoxTwoCountTv.setText(COUNT+box_all_num1);
        mBoxTwoNormalTv.setText(NORMAL+box_normal_all_num1);
        mBoxTwoBrokenTv.setText(BROKEN+box_break_all_num1);
        mBoxTwoFreezeeTv.setText(FREEZEE+box_freeze_all_num1);

        BoxStatus.ListBean box5G = boxList.get(2);
        String box_all_num2 = box5G.getBox_all_num();
        String box_break_all_num2 = box5G.getBox_break_all_num();
        String box_freeze_all_num2 = box5G.getBox_freeze_all_num();
        String box_normal_all_num2 = box5G.getBox_normal_all_num();
        String name6 = box5G.getName();
        mBox5GNameTv.setText(name6);
        mBox5GCountTv.setText(COUNT+box_all_num2);
        mBox5GNormalTv.setText(NORMAL+box_normal_all_num2);
        mBox5GBreakTv.setText(BROKEN+box_break_all_num2);
        mBox5GFreezeeTv.setText(FREEZEE+box_freeze_all_num2);

        BoxStatus.ListBean boxThree = boxList.get(3);
        String box_all_num3 = boxThree.getBox_all_num();
        String box_break_all_num3 = boxThree.getBox_break_all_num();
        String box_freeze_all_num3 = boxThree.getBox_freeze_all_num();
        String box_normal_all_num3 = boxThree.getBox_normal_all_num();
        String name7 = boxThree.getName();
        mThreeBoxNameTv.setText(name7);
        mThreeBoxCountTv.setText(COUNT+box_all_num3);
        mThreeBoxNormalTv.setText(NORMAL+box_normal_all_num3);
        mThreeBoxBreakTv.setText(BROKEN+box_break_all_num3);
        mThreeBoxFreezeeTv.setText(FREEZEE+box_freeze_all_num3);
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method,obj);
        hideLoadingLayout();
    }

    @Override
    public void onNetworkFailed(AppApi.Action method) {

    }

    public void showLoadingLayout() {
        contentSv.setVisibility(View.GONE);
        mLoadingPb.setVisibility(View.VISIBLE);
    }

    public void hideLoadingLayout() {
        contentSv.setVisibility(View.VISIBLE);
        mLoadingPb.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_refresh:
                getData();
                break;
        }
    }
}
