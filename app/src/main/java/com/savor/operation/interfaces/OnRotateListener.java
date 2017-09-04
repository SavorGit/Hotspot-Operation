package com.savor.operation.interfaces;


import com.savor.operation.bean.RotateProResponse;

public interface OnRotateListener extends OnBaseListenner {
	/**
	 * 图片旋转
	 * 
	 * @param responseVo
	 */
	void rotate(RotateProResponse responseVo);

	/**
	 * 旋转失败
	 */
	void rotateFailed(RotateProResponse responseVo);
}
