package com.xianglin.station.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * 获取Viw集合的类
 * 
 * @author licaiyi
 * @version $Id: ViewUtils.java, v 1.0.0 2015-10-13 下午2:28:26 xl Exp $
 */
public class ViewUtils {

	/**
	 * 获得所有view
	 * 
	 * @param view
	 * @return
	 */
	public static List<View> getAllChildViews(View view) {
		List<View> allchildren = new ArrayList<View>();
		if (view instanceof ViewGroup) {
			ViewGroup vG = (ViewGroup) view;
			for (int i = 0; i < vG.getChildCount(); i++) {
				View viewchild = vG.getChildAt(i);
				allchildren.add(viewchild);
				allchildren.addAll(getAllChildViews(viewchild));
			}
		}
		return allchildren;
	}

	/**
	 * 获得所有EditText View
	 * 
	 * @param view
	 * @return
	 */
	public static List<View> getAllEditTexts(View view) {
		List<View> allchildren = new ArrayList<View>();

		for (View viewTemp : getAllChildViews(view)) {
			if (viewTemp instanceof EditText) {
				allchildren.add(viewTemp);
			}
		}
		return allchildren;
	}

	/**
	 * 获取页面所有的ExitText
	 * 
	 * @param activity
	 * @return
	 */
	private List<View> getAllEditTexts(Activity activity) {
		return  getAllEditTexts(activity.getWindow().getDecorView());

	}

}