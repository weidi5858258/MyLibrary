package com.xianglin.station.util;

import android.app.Activity;
import android.app.Fragment;

/**
 * Fragment操作类
 * 
 * @author huang yang
 * @version $Id: FragmentUtils.java, v 1.0.0 2015年10月21日 下午1:59:35 huangyang Exp
 *          $
 */
public class FragmentUtils {
	/**
	 * 切换Fragment,每次切换都会重新创建Fragment实例
	 */
	public static void switchFragment(Activity activity, int replaceId, Fragment fragment) {
		if (activity != null && fragment != null) {
			activity.getFragmentManager().beginTransaction().replace(replaceId, fragment).commit();
		}
	}
}
