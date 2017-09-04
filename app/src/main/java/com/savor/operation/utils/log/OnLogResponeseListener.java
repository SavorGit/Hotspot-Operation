package com.savor.operation.utils.log;

public interface OnLogResponeseListener {
	void onLogNull();

	void onLogSuccess(int actionType, LogRespVo result);

}
