package com.weidi.log;

/**
 * LogCat日志
 */
public class CustomLog {
	/**
	 * 是否打开LogCat输出
	 */
	private volatile static boolean mSwitch;

	/**
	 * 初始化LogCat日志
	 */
	public static synchronized void init() {
		mSwitch = true;
//		mSwitch = AppInfo.getInstance().isDebuggable();
	}

	/**
	 * @param e 异常
	 * @return
	 */
	public static void printStackTraceAndMore(Throwable e) {
		e.printStackTrace();
	}

	/**
	 * Info
	 *
	 * @param tag tag
	 * @param msg 消息
	 */
	public static void i(String tag, String msg) {
		if (mSwitch && tag != null && msg != null)
			android.util.Log.i(tag, msg);
	}

	/**
	 * Error
	 *
	 * @param tag tag
	 * @param msg 消息
	 */
	public static void e(String tag, String msg) {
		if (mSwitch && tag != null && msg != null)
			android.util.Log.e(tag, msg);
	}

	/**
	 * 记录tr 的getMessage信息
	 *
	 * @param tag tag
	 * @param tr  异常
	 */
	public static void e(String tag, Throwable tr) {
		e(tag, tr == null ? "" : tr.getMessage());
	}

	/**
	 * 错误级别日志
	 *
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void e(String tag, String msg, Throwable tr) {
		if (mSwitch && tag != null && msg != null)
			android.util.Log.e(tag, msg, tr);
	}

	/**
	 * Debug
	 *
	 * @param tag tag
	 * @param msg 消息
	 */
	public static void d(String tag, String msg) {
		if (mSwitch && tag != null && msg != null)
			android.util.Log.d(tag, msg);
	}

	/**
	 * Verbose
	 *
	 * @param tag tag
	 * @param msg 消息
	 */
	public static void v(String tag, String msg) {
		if (mSwitch && tag != null && msg != null)
			android.util.Log.v(tag, msg);
	}

	/**
	 * Warning
	 *
	 * @param tag tag
	 * @param msg 消息
	 */
	public static void w(String tag, String msg) {
		if (mSwitch && tag != null && msg != null)
			android.util.Log.w(tag, msg);
	}


	/**
	 * 警告级别：记录tr 的getMessage信息
	 *
	 * @param tag tag
	 * @param tr  异常
	 */
	public static void w(String tag, Throwable tr) {
		w(tag, tr == null ? "" : tr.getMessage());
	}

	/**
	 * @return 是否打开LogCat输出
	 */
	public static boolean isSwitch() {
		return mSwitch;
	}

	public static void setSwitch(boolean isOpenLog){
		mSwitch = isOpenLog;
	}

}
