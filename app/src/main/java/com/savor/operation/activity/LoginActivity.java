package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.common.api.utils.ShowMessage;
import com.savor.operation.R;
import com.savor.operation.bean.Account;
import com.savor.operation.bean.City;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.SkillList;
import com.savor.operation.core.AppApi;

import java.util.List;

/**
 * @author hezd
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mAccountEt;
    private EditText mPwdEt;
    private Button mLoginBtn;
    private long exitTime;
    private String account;
    private String pwd;
    private ProgressBar mLoadingPb;

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
        mLoadingPb = (ProgressBar) findViewById(R.id.pb_loading);

        mAccountEt = (EditText) findViewById(R.id.et_account);
        mPwdEt = (EditText) findViewById(R.id.et_password);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void setViews() {
        Account account = mSession.getAccount();
        if(account!=null&&!TextUtils.isEmpty(account.getAccount())&&!TextUtils.isEmpty(account.getPwd())) {
            mPwdEt.setText(account.getPwd());
            mAccountEt.setText(account.getAccount());
            login();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this,SavorMainActivity.class);
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
        account = mAccountEt.getText().toString();
        pwd = mPwdEt.getText().toString();
        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(pwd)) {
            ShowMessage.showToast(this,"请输入账号和密码进行登录");
            return;
        }

        mLoadingPb.setVisibility(View.VISIBLE);
        AppApi.login(this, account, pwd,this);

    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_LOGIN_JSON:
                if(obj instanceof LoginResponse) {
                    mLoadingPb.setVisibility(View.GONE);
                    ShowMessage.showToast(this,getString(R.string.login_success));
                    LoginResponse loginResponse = (LoginResponse) obj;
                    SkillList skill_list = loginResponse.getSkill_list();
                    if(skill_list!=null) {
                        List<City> manage_city = skill_list.getManage_city();
                        if(manage_city!=null&&manage_city.size()>0) {
                            manage_city.get(0).setSelect(true);
                        }
                    }
                    mSession.setLoginResponse(loginResponse);
                    Account acc = new Account();
                    acc.setAccount(this.account);
                    acc.setPwd(this.pwd);
                    mSession.setAccount(acc);
                    startMainActivity();
                }
                break;
        }

    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_LOGIN_JSON:
                mLoadingPb.setVisibility(View.GONE);
                ShowMessage.showToast(this,"登录失败");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ShowMessage.showToast(this,getString(R.string.confirm_exit_app));
                exitTime = System.currentTimeMillis();
            } else {
                exitApp();
            }
        }
        return true;
    }

    private void exitApp() {
        Process.killProcess(android.os.Process.myPid());
    }
}
