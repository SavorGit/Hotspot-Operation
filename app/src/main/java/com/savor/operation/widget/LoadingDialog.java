package com.savor.operation.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import com.savor.operation.R;

/**
 * 上传投屏图片进度
 * Created by hezd on 2016/12/26.
 */

public class LoadingDialog extends Dialog {


    private final Activity mContext;
    private String hint;
    private TextView mPercentTv;
    private TextView mHintTv;
    private TextView mCancelBtn;

    public LoadingDialog(Activity context,String hint) {
        super(context, R.style.loading_progress_bar);
        this.mContext = context;
        this.hint = hint;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCancelable(false);
        getViews();
        setViews();
    }

    private void setViews() {
        if(!TextUtils.isEmpty(hint)) {
            mHintTv.setText(hint);
        }
    }

    public void updateProgress(String hint) {
        if(!TextUtils.isEmpty(hint)) {
            mHintTv.setText(hint);
        }
    }

    private void getViews() {
        mHintTv = (TextView) findViewById(R.id.tv_hint);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
        }
        return true;
    }
}
