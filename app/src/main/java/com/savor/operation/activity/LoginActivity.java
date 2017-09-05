package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.core.AppApi;

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
        LoginResponse loginResponse = mSession.getLoginResponse();
        if(loginResponse !=null) {
            startMainActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
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

        AppApi.login(this,account,pwd,this);

    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_LOGIN_JSON:
                if(obj instanceof LoginResponse) {
                    ShowMessage.showToast(this,getString(R.string.login_success));
                    LoginResponse loginResponse = (LoginResponse) obj;
                    mSession.setLoginResponse(loginResponse);
                    startMainActivity();
                }
                break;
        }

    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        super.onError(method, obj);
    }
}
