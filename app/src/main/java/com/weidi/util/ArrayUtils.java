package com.xianglin.station.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RadioButton;

/**
 * 数组操作类
 * 
 * @author huang yang
 * @version $Id: ArrayUtils.java, v 1.0.0 2015年9月12日 下午3:32:11 huangyang Exp $
 */
public class ArrayUtils {
	/**
	 * 将两个数组的item 和 number关联起来
	 * 
	 * @param itemArray
	 *            选择项array
	 * @param numberArray
	 *            选择项array中 每一项对应的编号数组
	 * @param itemNum
	 *            传入参与匹配的item 的编号
	 * @return
	 */
	public static String getArrayItem(Context context, int itemArray, int numberArray, String itemNum) {
		String[] itemArrays = context.getResources().getStringArray(itemArray);
		String[] numberArrays = context.getResources().getStringArray(numberArray);
		int position = getPosition(numberArrays, itemNum);
		if (position > itemArrays.length - 1)
			position = 0;
		return itemArrays[position];
	}

	/**
	 * 查询字符串在字符数组中的位置
	 * 
	 * @param strArray
	 * @param itemNum
	 * @return
	 */
	public static int getPosition(String[] strArray, String itemNum) {
		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i].equals(itemNum))
				return i;
		}
		return -1;
	}

	/**
	 * 获取RadioGroup中选择项在数组 arr中的位置
	 * 
	 * @param Activity
	 *            上下文
	 * @param checkedId
	 *            RadioGroup选择项id
	 * @param arr
	 *            数组
	 * @return
	 */
	public static int getPosition(Activity activity, int checkedId, int arr) {
		RadioButton mRadioButton = (RadioButton) activity.findViewById(checkedId);
		int position = ArrayUtils.getPosition(activity.getResources().getStringArray(arr), mRadioButton.getText().toString());
		return position;
	}

	/**
	 * 获取RadioGroup中选择项在数组 arr中的位置
	 * 
	 * @param View
	 *            view
	 * @param checkedId
	 *            RadioGroup选择项id
	 * @param arr
	 *            数组
	 * @return
	 */
	public static int getPosition(View view, int checkedId, int arr) {
		RadioButton mRadioButton = (RadioButton) view.findViewById(checkedId);
		int position = ArrayUtils.getPosition(view.getResources().getStringArray(arr), mRadioButton.getText().toString());
		return position;
	}

	/**
	 * 获取控件
	 * 
	 * @param mContext
	 * @param id
	 *            资源文件ID
	 * @return
	 */
	public static RadioButton getRadioButton(Activity activity, int id) {
		RadioButton radioButton = (RadioButton) activity.findViewById(id);
		return radioButton;
	}

	/**
	 * 获取控件RadioButton
	 * 
	 * @param view
	 * @param id
	 * @return
	 */
	public static RadioButton getRadioButton(View view, int id) {
		RadioButton radioButton = (RadioButton) view.findViewById(id);
		return radioButton;
	}
}
