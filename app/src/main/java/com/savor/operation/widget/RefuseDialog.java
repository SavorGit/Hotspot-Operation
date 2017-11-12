package com.savor.operation.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.interfaces.RefuseCallBack;

/**
 * 自定义AlertDialog
 * Created by luminita on 16/11/21.
 */
public class RefuseDialog implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private EditText ev_info;
    private View view;
    private OnClickListener submitListener;
    private Display display;
    private LayoutInflater mInflater;
    private LinearLayout msg_la;
    private TextView submit;
    private RefuseCallBack callBack;

    public RefuseDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        mInflater = LayoutInflater.from(context);
        //inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public RefuseDialog(Context context, RefuseCallBack callBack){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.callBack = callBack;
        builder();
    }

    public RefuseDialog builder() {
        view = LayoutInflater.from(context).inflate(R.layout.refuse_dialog_layout, null);
        ev_info = (EditText) view.findViewById(R.id.ev_info);
        submit = (TextView) view.findViewById(R.id.submit);
        if (dialog == null) {
            dialog = new Dialog(context, R.style.AlertDialogStyle);
        }
        dialog.setContentView(view);

        dialog.setCancelable(true);// 不可以用“返回键”取消
//        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (
//                display.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));
        setListeners();
        return this;
    }

    public void setListeners() {
        submit.setOnClickListener(this);

    }



    public void dismiss(){
        if(dialog != null){
            dialog.dismiss();
        }
    }

    public void show(){
        if(dialog != null){
            dialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        callBack.toRefuse(ev_info.getText().toString());
    }
}
