package com.weidi.threadpool;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

public class CustomRunnable implements Runnable {

	private static final String TAG = "CustomRunnable";
	/**
	 * 取消任务：false表示没有取消任务，任务在不断地进行；设为true时表示要取消任务了，不想再进行任务
	 */
	private boolean cancleTask = false;
	/**  */
	private boolean cancleException = false;
	/**  */
	private MHandler mHandler = null;

	private static final int RUNBEFORE = 0;
	private static final int RUNAFTER = 1;
	private static CallBack mCallBack = null;

	private static Object mObject;

	private int runBeforeSleepTime = 0;
	private int runAfterSleepTime = 0;

	public CustomRunnable() {
		if (mObject != null) {
			mObject = null;
		}
		if (mHandler == null) {
			mHandler = new MHandler();
		}
	}

	private static class MHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (mCallBack == null) {
				return;
			}
			switch (msg.what) {
				case RUNBEFORE:
					mCallBack.runBefore();
					break;
				case RUNAFTER:
					mCallBack.runAfter(mObject);
					break;
			}
		}
	}

	@Override
	public void run() {
		try {
			mHandler.sendEmptyMessage(RUNBEFORE);
			if (!cancleTask && !cancleException) {
				if (mCallBack != null) {
					if(runBeforeSleepTime != 0){
						SystemClock.sleep(runBeforeSleepTime);
					}
					mObject = mCallBack.running();
				}
			}
			if(runAfterSleepTime != 0){
				SystemClock.sleep(runAfterSleepTime);
			}
			mHandler.sendEmptyMessage(RUNAFTER);
		} catch (Exception e) {
			cancleException = true;
			Log.e(TAG, "run()方法出现异常");
		}
	}

	/**
	 * 设为false时取消任务正常进行；设为true时取消任务
	 */
	public CustomRunnable setCancleTaskUnit(boolean cancleTask) {
		this.cancleTask = cancleTask;
		return this;
	}

	public CustomRunnable setCallBack(CallBack callBack) {
		mCallBack = callBack;
		return this;
	}

	public CustomRunnable setRunBeforeSleepTime(int seconds) {
		if (seconds < 0) {
			runBeforeSleepTime = 0;
		} else {
			runBeforeSleepTime = seconds;
		}
		return this;
	}

	public CustomRunnable setRunAfterSleepTime(int seconds) {
		if (seconds < 0) {
			runAfterSleepTime = 0;
		} else {
			runAfterSleepTime = seconds;
		}
		return this;
	}

	public static interface CallBack {
		/**
		 * 主线程
		 */
		void runBefore();

		/**
		 * 子线程
		 */
		Object running();

		/**
		 * 主线程
		 */
		void runAfter(Object object);
	}

	/**
	 new CommonRunnable().setCallBack(new CallBack() {
	@Override public void runBefore () {

	}

	@Override public Object running () {
	return null;
	}

	@Override public void runAfter (Object object) {

	}
	});
	 */

}
