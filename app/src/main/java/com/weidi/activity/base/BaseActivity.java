package com.weidi.activity.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * @author ex-wangliwei
 */
public class BaseActivity extends Activity {
	/** 连续双击back退出 */
	private long PRESS_TIME;
	private static final int TIME = 2000;
	protected Context mContext = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 在2秒之间连续按两次“后退”键，才能退出应用。
	 * 放到IndexActivity中去
	 */
	protected void exit() {
		// 因为第一次按的时候“PRESS_TIME”为“0”，所以肯定大于2000
		if (SystemClock.uptimeMillis() - PRESS_TIME > TIME) {
//			APP.tip(this, "再按一次 退出" + getResources().getString(R.string.app_name));
			PRESS_TIME = SystemClock.uptimeMillis();
		} else {
			// 按第二次的时候如果距离前一次的时候在2秒之内，那么就走下面的路线
//			APPManager.getInstance().exit();
		}
	}

	

	

	
	/**
	 * 打开页面时，页面从右往左滑入
	 * 底下的页面不需要有动画
	 */
	protected void animRightToLeft() {
		try {
//			overridePendingTransition(R.anim.push_right_in2, R.anim.push_right_to2);
		} catch (Exception e) {
		}
	}

	/**
	 * 关闭页面时，页面从左往右滑出
	 */
	protected void animLeftToRight() {
		try {
//			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		} catch (Exception e) {
		}
	}

	protected void animBottomToTop() {
		try {
//			overridePendingTransition(R.anim.roll_up, R.anim.roll);
		} catch (Exception e) {
		}
	}

}