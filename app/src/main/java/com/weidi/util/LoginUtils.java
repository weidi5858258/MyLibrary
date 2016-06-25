package com.xianglin.station.util;

import android.content.Intent;

import com.xianglin.station.XLApplication;
import com.xianglin.station.activity.LoginPhoneActivity;

/**
 * 关于登录的工具的类 1.当登录状态失效时，跳转到登录，并结束当前的activity 2.判断密码的格式
 * 
 * @author ZX update by huang yang at 2015/10/23
 * @version $Id: LoginUtils.java, v 1.0.0 2015年8月7日 下午7:33:53 ST Exp $
 */
public class LoginUtils {

	/**
	 * 如果登录状态失效，跳转到正常登录。并结束当前的activity
	 */
	public static void toLogin() {
		Intent intent = new Intent();
		intent.setClass(XLApplication.context, LoginPhoneActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		XLApplication.context.startActivity(intent);
		EnrManager.getInstance().setContext(XLApplication.context);
		/**
		 * 判断是否有登录状态，true为有登录状态，false为登录状态失效，在欢迎界面（WelcomeActivity）中判断，
		 * 为true则直接跳转到主界面，为false则跳转到登录界面
		 */
		EnrManager.getInstance().setEnrValue("islogin", "false");
		/**
		 * 当登录状态失效时，将mlogin设置为false，跳转到正常登录（LoginPhoneActivity）界面后点击回退键不能回退
		 */
		EnrManager.getInstance().setEnrValue("mlogin", "false");
	}

	/**
	 * 验证密码格式 不能全是相同的数字或者字母（如：000000、111111、aaaaaa） 全部相同返回true
	 * 
	 * @param numOrStr
	 * @return
	 */
	public static boolean equalStr(String numOrStr) {
		boolean flag = true;
		char str = numOrStr.charAt(0);
		for (int i = 0; i < numOrStr.length(); i++) {
			if (str != numOrStr.charAt(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 验证密码格式 不能是连续的数字--递增（如：123456、12345678） 连续数字返回true
	 * 
	 * @param numOrStr
	 * @return
	 */
	public static boolean isOrderNumeric(String numOrStr) {
		boolean flag = true;// 如果全是连续数字返回true
		boolean isNumeric = true;// 如果全是数字返回true
		for (int i = 0; i < numOrStr.length(); i++) {
			if (!Character.isDigit(numOrStr.charAt(i))) {
				isNumeric = false;
				break;
			}
		}
		if (isNumeric) {// 如果全是数字则执行是否连续数字判断
			for (int i = 0; i < numOrStr.length(); i++) {
				if (i > 0) {// 判断如123456
					int num = Integer.parseInt(numOrStr.charAt(i) + "");
					int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") + 1;
					if (num != num_) {
						flag = false;
						break;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证密码格式 不能是连续的数字--递减（如：987654、876543） 连续数字返回true
	 * 
	 * @param numOrStr
	 * @return
	 */
	public static boolean isOrderNumeric_(String numOrStr) {
		boolean flag = true;// 如果全是连续数字返回true
		boolean isNumeric = true;// 如果全是数字返回true
		for (int i = 0; i < numOrStr.length(); i++) {
			if (!Character.isDigit(numOrStr.charAt(i))) {
				isNumeric = false;
				break;
			}
		}
		if (isNumeric) {// 如果全是数字则执行是否连续数字判断
			for (int i = 0; i < numOrStr.length(); i++) {
				if (i > 0) {// 判断如654321
					int num = Integer.parseInt(numOrStr.charAt(i) + "");
					int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") - 1;
					if (num != num_) {
						flag = false;
						break;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}
}
