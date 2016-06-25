package com.c1tech.dress.net;


public interface NetAsyncHandler {

	/**
	 * 执行中
	 */
	void onThrowable(Throwable t, Object cookie);

	/**
	 * 完成时
	 */
	void onCompleted(int statusCode, String Content, Object cookie);
}
