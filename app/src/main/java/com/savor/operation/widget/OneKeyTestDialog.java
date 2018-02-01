package com.savor.operation.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.okhttp.OkHttpUtils;
import com.common.api.utils.DensityUtil;
import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.adapter.OfflineReasonAdapter;
import com.savor.operation.bean.BoxState;
import com.savor.operation.bean.DamageConfig;
import com.savor.operation.bean.FixHistoryResponse;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.OneKeyTestResponse;
import com.savor.operation.core.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * 一键测试对话框
 * Created by hezd on 2017/3/9.
 */

public class OneKeyTestDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private LinearLayout mResultLayout;
    private LinearLayout mLoadingLayout;
    private TextView mCancelTv;
    private TextView mReTestTv;
    private TextView mConfirmTv;
    private TextView mSmallStateTv;
    private TextView mBoxStateTv;
    private RecyclerView mOffLineReasonRlv;
    private OfflineReasonAdapter offlineReasonAdapter;
    private OnReTestBtnClickLisnter onReTestBtnClickLisnter;
    private OnCancelBtnClickListener onCancelBtnClickListener;
    private TextView mNetworkDelayedTv;

    public OneKeyTestDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_onekey_test);

        setCancelable(false);
        getViews();
        setViews();
        setListeners();
    }

    private void getViews() {
        mNetworkDelayedTv = (TextView) findViewById(R.id.tv_network_delayed);
        mOffLineReasonRlv = (RecyclerView) findViewById(R.id.rlv_offline_reason);
        mBoxStateTv = (TextView) findViewById(R.id.tv_box_state);
        mSmallStateTv = (TextView) findViewById(R.id.tv_small_state);
        mConfirmTv = (TextView) findViewById(R.id.tv_confirm);
        mReTestTv = (TextView) findViewById(R.id.tv_retest);
        mResultLayout = (LinearLayout) findViewById(R.id.ll_result_layout);
        mLoadingLayout = (LinearLayout) findViewById(R.id.ll_loading_layout);

        mCancelTv = (TextView) findViewById(R.id.tv_cancel);

    }

    private void setViews() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = DensityUtil.getScreenWidth(getContext());
        window.setAttributes(attributes);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOffLineReasonRlv.setLayoutManager(linearLayoutManager);
        offlineReasonAdapter = new OfflineReasonAdapter(mContext);
        mOffLineReasonRlv.setAdapter(offlineReasonAdapter);

    }

    private void setListeners() {
        mConfirmTv.setOnClickListener(this);
        mReTestTv.setOnClickListener(this);
        mCancelTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                dismiss();
                break;
            case R.id.tv_retest:
                mResultLayout.setVisibility(View.GONE);
                mLoadingLayout.setVisibility(View.VISIBLE);
                if (onReTestBtnClickLisnter!=null) {
                    onReTestBtnClickLisnter.onReTestBtnClick();
                }
                break;
            case R.id.tv_cancel:
                if(onCancelBtnClickListener!=null) {
                    onCancelBtnClickListener.onCancelBtnClick();
                }
                dismiss();
                break;
        }
    }

    public void init(OneKeyTestResponse oneKeyTestResponse) {
        if(oneKeyTestResponse == null) {
            return;
        }

        String small_device_name = oneKeyTestResponse.getSmall_device_name();
        String small_device_state = oneKeyTestResponse.getSmall_device_state();
        String box_device_name = oneKeyTestResponse.getBox_device_name();
        String box_device_state = oneKeyTestResponse.getBox_device_state();
        List<String> remark = oneKeyTestResponse.getRemark();
        mSmallStateTv.setText(small_device_name+"："+small_device_state);
        mBoxStateTv.setText(box_device_name+"："+box_device_state);
        offlineReasonAdapter.setData(remark);

        mLoadingLayout.setVisibility(View.GONE);
        mResultLayout.setVisibility(View.VISIBLE);

        String box_net_state = oneKeyTestResponse.getBox_net_state();
        mNetworkDelayedTv.setVisibility(TextUtils.isEmpty(box_net_state)?View.GONE:View.VISIBLE);
        mNetworkDelayedTv.setText(box_net_state);
    }

    public void reset() {
        if(mResultLayout!=null)
            mResultLayout.setVisibility(View.GONE);
        if(mLoadingLayout!=null)
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    public void setOnReTestBtnClickLisnter(OnReTestBtnClickLisnter onReTestBtnClickLisnter) {
        this.onReTestBtnClickLisnter = onReTestBtnClickLisnter;
    }

    public void setOnCancelBtnClickListener(OnCancelBtnClickListener onCancelBtnClickListener) {
        this.onCancelBtnClickListener = onCancelBtnClickListener;
    }

    public interface OnReTestBtnClickLisnter {
        void onReTestBtnClick();
    }

    public interface OnCancelBtnClickListener {
        void onCancelBtnClick();
    }
}
