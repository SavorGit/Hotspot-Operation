package com.savor.operation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.common.api.utils.ShowMessage;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.core.Session;
import com.savor.operation.interfaces.IBaseView;
import com.savor.operation.utils.ActivitiesManager;
import com.savor.operation.widget.LoadingDialog;


public abstract class BaseFragmentActivity extends FragmentActivity implements IBaseView,ApiRequestListener {

	protected Session mSession;
	protected Context mContext;
	private LoadingDialog loadingDialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mSession = Session.get(getApplicationContext());
		mContext = this;
		ActivitiesManager.getInstance().pushActivity(this);
	}

	public void showToast(String message) {
		ShowMessage.showToastSavor(this,message);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivitiesManager.getInstance().popActivity(this);
	}

	@Override
	public void onSuccess(AppApi.Action method, Object obj) {

	}

	@Override
	public void onError(AppApi.Action method, Object obj) {

		if(obj instanceof ResponseErrorMessage) {
			ResponseErrorMessage message = (ResponseErrorMessage) obj;
			String msg = message.getMessage();
			if(!TextUtils.isEmpty(msg)) {
				showToast(msg);
			}else {
				showToast("网络连接失败");
			}
//			if(1006==code||12056==code) {
//				ActivityPagerUtils.launchLoginActivity(this);
//				mSession.signOut();
//
//				Intent intent = new Intent(this, RegisterGCMSNSService.class);
//				intent.setAction("com.etago.life.pushgcm");
//				startService(intent);
//			}
		}
	}

	@Override
	public void onNetworkFailed(AppApi.Action method) {

	}

	public void showLoadingLayout() {
		if(loadingDialog == null)
			loadingDialog = new LoadingDialog(this,"");
		loadingDialog.show();
	}

	public void hideLoadingLayout() {

		loadingDialog.dismiss();
	}
}
