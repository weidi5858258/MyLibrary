package com.xianglin.station.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast统一处理工具类
 * 
 * @author songdiyuan001  update by huang yang at 2015/10/23
 * @version $Id: ToastUtils.java, v 1.0.0 2015-08-07 上午11:42:23 songdy Exp $/
 */
public class ToastUtils {
	private static long lastClickTime;

	/**
	 * 防止多次点击事件处理
	 * 
	 * @author songdiyuan
	 * @return
	 */
	public synchronized static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (timeD < 2000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	public static void showCenterToast(String text, Context context) {
		showCenterToast(text, context, 0);
	}

	public static void showCenterToast(String text, Context context, int length) {
		Toast toast = Toast.makeText(context, text, length);
		toast.setMargin(0, 0.58f);
		toast.show();
	}

	/**
	 * @author XJK-MINGPENGFEI462
	 * 
	 * @param text
	 *            输入要显示的文字
	 * @param context
	 * @param c-这个参数以后备用，这里方便重载
	 */
	public static void showCenterToast(String text, Context context, boolean c) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showCenterToastForInput(String text, Context context) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setMargin(0, 0.48f);
		toast.show();
	}

	public static void showCenterToastForInputLong(String text, Context context) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		toast.setMargin(0, 0.48f);
		toast.show();
	}

	private static Toast mToast;

	/**
	 * 长时间弹出消息提示
	 * 
	 * @param context
	 * @param msg
	 */
	public static void toastForLong(Context context, String msg) {
		if (TextUtils.isEmpty(msg)) {
			return;
		}
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_LONG);
		}
		setToastPostion(context);

		mToast.show();
	}

	/**
	 * 长时间弹出消息提示
	 * 
	 * @param context
	 * @param res
	 */
	public static void toastForLong(Context context, int res) {
		if (res == 0) {
			return;
		}

		String msg = context.getResources().getString(res);

		if (mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_LONG);
		}
		setToastPostion(context);

		mToast.show();
	}

	/**
	 * 短时间弹出消息提示
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static void toastForShort(Context context, String msg) {
		if (TextUtils.isEmpty(msg)) {
			return;
		}
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		setToastPostion(context);

		mToast.show();
	}

	/**
	 * 设置toast位置
	 * 
	 * @param context
	 */
	private static void setToastPostion(Context context) {
		// try {
		// InputMethodManager imm =
		// (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		// boolean isOpen=imm.isActive();
		// //((Activity) context).getWindow().getAttributes().softInputMode ==
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED
		// if (isOpen) {
		// mToast.setGravity(Gravity.CENTER, 0, 0);
		// }else{
		// mToast.setGravity(Gravity.BOTTOM, 0, 0);
		// }
		// } catch (Exception e) {
		// CustomLog.e("ToastUtils", e);
		// }
	}

	/**
	 * 短时间弹出消息提示
	 * 
	 * @param context
	 * @param res
	 */
	public static void toastForShort(Context context, int res) {
		if (res == 0) {
			return;
		}

		String msg = context.getResources().getString(res);

		if (mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}

		setToastPostion(context);
		mToast.show();
	}

	/**
	 * 自定义时间弹出消息提示
	 * 
	 * @param context
	 * @param res
	 * @param duration
	 */
	public static void toastForTime(Context context, int res, int duration) {
		if (res == 0) {
			return;
		}

		String msg = context.getResources().getString(res);

		if (mToast == null) {
			mToast = Toast.makeText(context, msg, duration);
		} else {
			mToast.setText(msg);
			mToast.setDuration(duration);
		}

		setToastPostion(context);
		mToast.show();
	}

	/**
	 * 自定义时间弹出消息提示
	 * 
	 * @param context
	 * @param msg
	 * @param duration
	 */
	public static void toastForTime(Context context, String msg, int duration) {
		if (TextUtils.isEmpty(msg)) {
			return;
		}
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, duration);
		} else {
			mToast.setText(msg);
			mToast.setDuration(duration);
		}
		setToastPostion(context);

		mToast.show();
	}

	/**
	 * 在主线程弹出消息框,支持在子线程中使用此方法
	 * 
	 * @param activity
	 * @param msg
	 */
	public static void showSafeToast(final Activity activity, final String msg) {
		if(activity == null || activity.isFinishing()) {
			return;
		}
		if ("main".equals(Thread.currentThread().getName())) {
			showCenterToast(msg, activity);
		} else {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showCenterToast(msg, activity);
				}
			});
		}
	}
}
