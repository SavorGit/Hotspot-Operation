package com.savor.operation.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.activity.HotelPositionInfoAcitivty;
import com.savor.operation.bean.BoxState;
import com.savor.operation.bean.DamageConfig;
import com.savor.operation.bean.FixHistoryResponse;
import com.savor.operation.bean.Hotel;
import com.savor.operation.bean.RepairInfo;
import com.savor.operation.core.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * 维修对话框
 * Created by hezd on 2017/3/9.
 */

public class FixDialog extends Dialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private final OperationType type;
    private final FixHistoryResponse fixRespone;
    private Hotel mHotel;
    private DamageConfig mDamageConfig;
    private OnSubmitBtnClickListener mOnSubmitListener;
    private RadioGroup mResovleRg;
    private EditText mCommentEt;
    private RelativeLayout mDamageLayout;

    private Context mContext;
    private TextView mCancelBtn;
    private TextView mSubmitBtn;
    private List<String> selectedDamages = new ArrayList<>();
    private FixState currentFixSate = FixState.UNSELECTED;
    private AlertDialog alertDialog;
    private TextView mSelectDescTv;
    private RelativeLayout mBoxStateLayout;
    private AlertDialog stateAlertDialog;
    private Session session;
    private BoxState currentSeletBoxState;
    private TextView mStatusDescTv;

    public enum FixState {
        /**为选择*/
        UNSELECTED,
        /**已解决*/
        RESOLVED,
        /**未解决*/
        UNRESOLVED,
    }

    public enum OperationType {
        /**小平台*/
        TYPE_SMALL,
        /**机顶盒*/
        TYPE_BOX,
    }

    public FixDialog(@NonNull Context context, OnSubmitBtnClickListener listener, OperationType type, FixHistoryResponse fixHistoryResponse,DamageConfig damageConfig, Hotel hotel) {
        super(context);
        this.mContext = context;
        this.mOnSubmitListener = listener;
        this.mDamageConfig = damageConfig;
        this.mHotel = hotel;
        this.type = type;
        this.fixRespone = fixHistoryResponse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fix);

        session = Session.get(getContext());
        getViews();
        setViews();
        setListeners();
    }

    private void getViews() {
        mStatusDescTv = (TextView) findViewById(R.id.tv_status_desc);
        mBoxStateLayout = (RelativeLayout) findViewById(R.id.rl_status_layout);
        mResovleRg = (RadioGroup) findViewById(R.id.rg_resovle);
        mCommentEt = (EditText) findViewById(R.id.et_desc);
        mDamageLayout = (RelativeLayout) findViewById(R.id.rl_damage_layout);
        mCancelBtn = (TextView) findViewById(R.id.tv_cancel);
        mSubmitBtn = (TextView) findViewById(R.id.tv_submit);
        mSelectDescTv = (TextView)findViewById(R.id.tv_select_desc);
    }

    private void setViews() {
        final ArrayList<BoxState> boxStateConfig = session.getBoxStateConfig();
        if(boxStateConfig != null&&boxStateConfig.size() > 0) {
            currentSeletBoxState = boxStateConfig.get(0);
            mStatusDescTv.setText(boxStateConfig.get(0).getName());
        }
    }

    private void setListeners() {
        mDamageLayout.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mSubmitBtn.setOnClickListener(this);
        mResovleRg.setOnCheckedChangeListener(this);
        mBoxStateLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_submit:
                if(mOnSubmitListener!=null) {
                    String comment = mCommentEt.getText().toString();
                    if(currentFixSate == FixDialog.FixState.UNSELECTED) {
                        ShowMessage.showToast(mContext,"请选择修复状态已解决或未解决");
                        return;
                    }
                    if(TextUtils.isEmpty(comment)&&selectedDamages.size()==0) {
                        ShowMessage.showToast(mContext,"请选择维修记录或填写备注");
                        return;
                    }

                    if(currentSeletBoxState == null) {
                        ShowMessage.showToast(mContext,"请选择状态");
                        return;
                    }
                    mOnSubmitListener.onSubmitClick(type,fixRespone,currentFixSate,selectedDamages,currentSeletBoxState,comment,mHotel);
                }
                dismiss();
                break;
            case R.id.rl_status_layout:
                final ArrayList<BoxState> boxStateConfig = session.getBoxStateConfig();
                if(boxStateConfig == null||boxStateConfig.size() == 0) {
                    return;
                }
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
                final String[] items = new String[boxStateConfig.size()];
                for(int i = 0;i<boxStateConfig.size();i++) {
                    items[i] = boxStateConfig.get(i).getName();
                }
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int index) {
                        currentSeletBoxState = boxStateConfig.get(index);
                        mStatusDescTv.setText(boxStateConfig.get(index).getName());
                    }
                });
                AlertDialog finalAlertDialog = alertBuilder.create();
                finalAlertDialog.show();
                break;
            case R.id.rl_damage_layout:
                if(alertDialog == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    List<DamageConfig.DamageInfo> list = mDamageConfig.getList();
                    final String[] sex = new String[mDamageConfig.getList().size()];
                    final String[] ids  = new String[mDamageConfig.getList().size()];
                    for(int i =0;i<list.size();i++) {
                        sex[i] = list.get(i).getReason();
                        ids[i] = list.get(i).getId()+"";
                    }
                    //    设置一个单项选择下拉框
                    /**
                     * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                     * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                     * 第三个参数给每一个单选项绑定一个监听器
                     */
                    builder.setMultiChoiceItems(sex, null, new DialogInterface.OnMultiChoiceClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            String currentDamage = ids[which];
                            if(isChecked) {
                                if(!selectedDamages.contains(currentDamage)) {
                                    selectedDamages.add(currentDamage);
                                }
                            }else {
                                if(selectedDamages.contains(currentDamage)) {
                                    selectedDamages.remove(currentDamage);
                                }
                            }
                        }
                    });
                    builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(selectedDamages==null||selectedDamages.size()==0) {
                                mSelectDescTv.setText("故障说明与维修记录");
                            }else {
                                mSelectDescTv.setText("已选择"+selectedDamages.size()+"项");
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog = builder.create();
                }
                alertDialog.show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_resolved:
                currentFixSate = FixState.RESOLVED;
                break;
            case R.id.rb_unresolved:
                currentFixSate = FixState.UNRESOLVED;
                break;
        }
    }

    public interface OnSubmitBtnClickListener {
        /**
         * 提交
         * @param isResolve 是否已解决
         * @param damageDesc 故障原因
         * @param comment 评论
         */
        void onSubmitClick(OperationType type,FixHistoryResponse fixHistoryResponse,FixState isResolve, List<String> damageDesc,BoxState boxState, String comment,Hotel hotel);
    }
}
