package com.savor.operation.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.savor.operation.R;

/**
 * 维修对话框
 * Created by hezd on 2017/3/9.
 */

public class FixDialog extends Dialog implements View.OnClickListener{

    private OnSubmitBtnClickListener mOnSubmitListener;
    private RadioGroup mResovleRg;
    private EditText mCommentEt;
    private RelativeLayout mDamageLayout;

    private Context mContext;
    public FixDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public FixDialog(@NonNull Context context,OnSubmitBtnClickListener listener) {
        super(context);
        this.mContext = context;
        this.mOnSubmitListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fix);

        getViews();
        setViews();
        setListeners();
    }

    private void getViews() {
        mResovleRg = (RadioGroup) findViewById(R.id.rg_resovle);
        mCommentEt = (EditText) findViewById(R.id.et_desc);
        mDamageLayout = (RelativeLayout) findViewById(R.id.rl_damage_layout);
    }

    private void setViews() {

    }

    private void setListeners() {
        mDamageLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_damage_layout:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                final String[] sex = {"男", "女", "未知性别"};
                //    设置一个单项选择下拉框
                /**
                 * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                 * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                 * 第三个参数给每一个单选项绑定一个监听器
                 */
                builder.setMultiChoiceItems(sex, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
        }
    }

    public interface OnSubmitBtnClickListener {
        /**
         * 提交
         * @param isResolve 是否已解决
         * @param damageDesc 故障原因
         * @param comment 评论
         */
        void onSubmitClick(boolean isResolve,String damageDesc,String comment);
    }
}
