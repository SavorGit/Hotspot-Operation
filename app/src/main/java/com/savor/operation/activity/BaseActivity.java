package com.savor.operation.activity;

import android.app.Activity;
import android.os.Bundle;

import com.common.api.utils.ShowMessage;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.core.Session;
import com.savor.operation.interfaces.IBaseView;
import com.savor.operation.utils.ActivitiesManager;

/**
 * 基类
 * Created by hezd on 2016/12/13.
 */
public abstract class BaseActivity extends Activity implements ApiRequestListener,IBaseView {

    protected Session mSession;
    protected Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 统计应用启动数据,如果不调用此方法，将会导致按照"几天不活跃"条件来推送失效。
        mSession = Session.get(getApplicationContext());
        mContext = this;
        ActivitiesManager.getInstance().pushActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {

    }

    @Override
    public void onError(AppApi.Action method, Object obj) {

        if(obj instanceof ResponseErrorMessage) {
            ResponseErrorMessage message = (ResponseErrorMessage) obj;
            String msg = message.getMessage();
            ShowMessage.showToast(this,msg);
        }
    }

    @Override
    public void onNetworkFailed(AppApi.Action method) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesManager.getInstance().popActivity(this);
    }

}
