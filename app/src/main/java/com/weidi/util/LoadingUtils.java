package com.xianglin.station.util;

import com.xianglin.station.activity.BaseActivity;
import com.xianglin.station.dialog.LoadingDialog;

import android.content.Context;
import android.os.Handler;

/**
 * 贷款相关服务
 * @author yangjibin
 * @version $Id: LoadingUtils.java, v 1.0.0 2015-10-28 下午2:54:53 xl Exp $
 */
public class LoadingUtils {
	public static LoadingDialog loadingDialog;

	/**
	 * @Description 加载中的等待框
	 */
	public static void loadingShow(final Context context) {
		BaseActivity.handler.post(new Runnable() {
			@Override
			public void run() {
				if (loadingDialog == null) {
					loadingDialog = new LoadingDialog(context);
				}
				if (loadingDialog != null && !loadingDialog.isShowing()) {
					try {
						if (loadingDialog != null) {
							loadingDialog.show();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * @Description 加载中的等待框
	 */
	public static void loadingShow(final Context context, final String loadingText) {
		BaseActivity.handler.post(new Runnable() {
			@Override
			public void run() {
				if (loadingDialog == null) {
					loadingDialog = new LoadingDialog(context);
				}
				if(loadingText != null){
					loadingDialog.setLoadText(loadingText);
				}
				if (loadingDialog != null && !loadingDialog.isShowing()) {
					try {
						if (loadingDialog != null) {
							loadingDialog.show();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * @Description 取消
	 */
	public static void loadingDismiss() {
		if (loadingDialog != null && loadingDialog.isShowing()) {
			try {
				BaseActivity.handler.post(new Runnable() {
					@Override
					public void run() {
						if (loadingDialog != null) {
							loadingDialog.dismiss();
							loadingDialog = null;
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
