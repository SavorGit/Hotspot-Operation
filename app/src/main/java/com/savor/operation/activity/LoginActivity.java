package com.savor.operation.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.common.api.utils.ShowMessage;
import com.savor.operation.R;

/**
 * @author hezd
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mAccountEt;
    private EditText mPwdEt;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        mAccountEt = (EditText) findViewById(R.id.et_account);
        mPwdEt = (EditText) findViewById(R.id.et_password);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {
        String account = mAccountEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(pwd)) {
            ShowMessage.showToast(this,"请输入账号和密码进行登录");
            return;
        }


    }
}
