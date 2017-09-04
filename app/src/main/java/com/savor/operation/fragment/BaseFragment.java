package com.savor.operation.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.api.utils.LogUtils;
import com.common.api.utils.ShowProgressDialog;
import com.savor.operation.R;
import com.savor.operation.core.ApiRequestListener;
import com.savor.operation.core.AppApi;
import com.savor.operation.core.ResponseErrorMessage;
import com.savor.operation.core.Session;
import com.savor.operation.interfaces.IBaseView;


/**
 * Fragment基类
 *
 * 基类实现了bindview和senseview接口
 * 模拟适配器模式，有一些接口方法是空实现
 * @author bc
 * 
 */
public abstract class BaseFragment extends Fragment implements ApiRequestListener ,IBaseView {
	Session mSession;
//	protected PictureUtils bitmapUtils;
//	protected BitmapDisplayConfig config;
	
	protected FrameLayout backFL;
	protected ImageView backIV;
	protected TextView backTV;
	protected FrameLayout nextFL;
	protected ImageView nextIV;
	protected TextView nextTV;
	protected TextView titleTV;

	protected Activity mActivity;
	private ProgressDialog mProgressDialog;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtils.i(getFragmentName() + " onAttach()");
		mActivity = activity;
		mSession = Session.get(activity);
	}
	protected void initTitleView(){
//		View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
//		backFL = (FrameLayout) rootView.findViewById(R.id.backFL);
//		backIV = (ImageView) rootView.findViewById(R.id.backIV);
//		backTV = (TextView) rootView.findViewById(R.id.backTV);
//		nextFL = (FrameLayout) rootView.findViewById(R.id.nextFL);
//		nextIV = (ImageView) rootView.findViewById(R.id.nextIV);
//		nextTV = (TextView) rootView.findViewById(R.id.nextTV);
//		titleTV = (TextView) rootView.findViewById(R.id.titleTV);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.i(getFragmentName() + " onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		LogUtils.i(getFragmentName() + " onCreateView()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		LogUtils.i(getFragmentName() + " onViewCreated()");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtils.i(getFragmentName() + " onActivityCreated()");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtils.i(getFragmentName() + " onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtils.i(getFragmentName() + " onResume()");
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtils.i(getFragmentName() + " onPause()");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtils.i(getFragmentName() + " onStop()");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtils.i(getFragmentName() + " onDestroyView()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtils.i(getFragmentName() + " onDestroy()");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LogUtils.i(getFragmentName() + " onDetach()");
	}

	protected void initBitmapUtils() {
//		bitmapUtils = PictureUtils.getInstance(getActivity());
//		config = new BitmapDisplayConfig();
//		config.setLoadingDrawable(getActivity().getResources().getDrawable(
//				R.drawable.ic_empty));
//		config.setLoadFailedDrawable(getActivity().getResources().getDrawable(
//				R.drawable.ic_empty));
	}
	
	/**
	 * fragment name
	 */
	public String getFragmentName() {
		return getClass().getSimpleName();
	}

	
	@Override
	public void onError(AppApi.Action method, Object statusCode) {

		if(statusCode instanceof ResponseErrorMessage) {
			ResponseErrorMessage msg = (ResponseErrorMessage) statusCode;
			int code = msg.getCode();
			String message = msg.getMessage();
//			showToast(message);
		}
	}

	@Override
	public void onSuccess(AppApi.Action method, Object obj) {

	}

	@Override
	public void onNetworkFailed(AppApi.Action method) {

	}

	protected void showErrorToast(Object obj, String defaultMsg) {
		if (obj instanceof ResponseErrorMessage) {
			ResponseErrorMessage errorMessage = (ResponseErrorMessage) obj;
			if (!TextUtils.isEmpty(errorMessage.getMessage())) {
				defaultMsg = errorMessage.getMessage();
			}
		}
//		showToast(defaultMsg);
	}


	@Override
	public void getViews() {

	}

	@Override
	public void setViews() {

	}

	@Override
	public void setListeners() {
		
	}

}
