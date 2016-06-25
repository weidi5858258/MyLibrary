package com.xianglin.station.util;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import com.xianglin.station.dialog.LoadingDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @Description  
 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
 * @update 2014-6-17 10:54:31
 */
public abstract class AsyncHandle {
	private Context context;
	private LoadingDialog dialog;
	private Map<String, Object> params;
	private ThreadHandler handler = null;
	private boolean showdeftip = true;
	private JSONObject reData = null;
	private static ExecutorService exeService = Executors.newCachedThreadPool();

	public AsyncHandle init(Map<String, Object> params) {
		this.context = null;
		this.dialog = null;
		this.params = params;
		return this;
	}
	public AsyncHandle init(Context context, Map<String, Object> params, boolean needDialog) {
		this.context = context;
		this.params = params;
		if (needDialog && context != null) {
			dialog = new LoadingDialog(context) {
				@Override
				public void onBackPressed() {
					super.onBackPressed();
					exeService.shutdownNow();
				}
			};
		} else {
			dialog = null;
		}
		return this;
	}
	
	//当runTask(params)方法执行后的结果能够生成一个JSONObject对象时，就会执行handleData()，handleLongTime()
	//否则，不能生成JSONObject对象时调用netWorkFail()
	//出异常时，会执行errorFinally()
	
	//protected只能在子类中使用
	protected abstract String runTask(Map<String, Object> parameters);
	
	//如果执行耗时任务，需要开启子线程
	protected abstract void handleData(JSONObject json, Map<String, Object> parameters) throws JSONException;
	
	//可以执行耗时操作，不需要再开启子线程
	protected void handleLongTime(JSONObject json, Map<String, Object> parameters) throws JSONException {
	}

	protected void netWorkFail(Map<String, Object> params) {
	}
	//调用execute()方法后，最后执行
	protected void startWaitView(Map<String, Object> params) {
	}
	//在ThreadHandler中执行，比startWaitView先执行
	protected void endWaitView(Map<String, Object> params) {
	}

	protected void errorFinally(Map<String, Object> params) {
	}


	

	public boolean isShowdeftip() {//默认显示在顶部
		return showdeftip;
	}

	public void setShowdeftip(boolean showdeftip) {
		this.showdeftip = showdeftip;
	}

	@SuppressLint("HandlerLeak")
	class ThreadHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (dialog != null) {
				dialog.dismiss();
			}
			endWaitView(params);//有什么作用呢？？？
			try {
				int type = (Integer) msg.obj;
				//当runTask(params)方法执行后的结果能够生成一个JSONObject对象时，就会执行handleData()
				if (type == 0 && reData != null) {
					handleData(reData, params);
				} else {
					if (type == 1) {
						//否则，不能生成JSONObject对象时调用netWorkFail()
						netWorkFail(params);
					} else {
						//出异常时，会执行errorFinally()
						errorFinally(params);
					}
				}
			} catch (Exception e) {
				errorFinally(params);
				e.printStackTrace();
			}
		}
	}

	class UpdateThread extends Thread {
		public void run() {
			try {
				Looper.prepare();
				String returnStr = runTask(params);//用户覆写这个方法后得到的返回值
				int type = 0;
				try {
					JSONObject returnData = AppUtils.checkReturnData(context, returnStr, showdeftip);
					if (returnData != null) {
						reData = returnData;
						handleLongTime(returnData, params);
						//已经是在子线程中，所以可以执行耗时任务而不需要再开启另外的线程了。
					} else {//如果用户处理runTask()这个函数后返回的不是json格式的字符串
						type = 1;
					}
				} catch (JSONException e) {
					type = 2;
				} catch (Exception e) {
					type = 3;
				}
				Message msg = new Message();
				msg.obj = type;
				if (handler != null) {
					handler.sendMessage(msg);
				}
				Looper.loop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void execute() {
		handler = new ThreadHandler();//只是先初始化，用于处理从其他地方传过来的不同消息
		UpdateThread thread = new UpdateThread();
		exeService.execute(thread);
		if (dialog != null) {
			dialog.show();
		}
		startWaitView(params);
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	};

}
