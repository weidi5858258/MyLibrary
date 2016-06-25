package com.xianglin.station.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xianglin.mobile.common.logging.LogCatLog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 用户信息更新视图工具类
 * 
 * @author longfeng
 * @version $Id: UpdateView.java, v 1.0.0 2015年10月16日 上午11:56:06 xl Exp $
 */
public class UpdateView {
	/**
	 * 用户信息唯一性校验视图更新
	 * 
	 * @param mKaoHaoTv2
	 * @param mIdCardTv2
	 * @param mPhoneTv2
	 * @param str
	 */
	public static void updateUniquenessView(TextView mKaoHaoTv2, TextView mIdCardTv2, TextView mPhoneTv2, String str) {
		mKaoHaoTv2.setText(str);
		mKaoHaoTv2.setVisibility(View.VISIBLE);
		mIdCardTv2.setVisibility(View.INVISIBLE);
		mPhoneTv2.setVisibility(View.INVISIBLE);
	}

	/**
	 * 校验填写时间是否大于当前时间
	 * 
	 * @param str1
	 *            设备当前时间
	 * @param str2
	 *            用户选择事件
	 * @return
	 */
	public static boolean checkCreatBankTime(String str1, String str2, SimpleDateFormat sdf) {
		try {
			LogCatLog.i("系统时间", str1 + "--");
			LogCatLog.i("选择时间", str2 + "--");
			Date dt1 = sdf.parse(str1);
			Date dt2 = sdf.parse(str2);
			LogCatLog.i("系统时间", dt1.getTime() + "==");
			LogCatLog.i("选择时间", dt2.getTime() + "==");
			if (dt1.getTime() < dt2.getTime()) {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 定位
	 * 
	 * @param v
	 * @param mScrollView
	 */
	public static void scrollTo(View v, ScrollView mScrollView) {
		int[] viewPostion = { 0, 0 };
		v.getLocationInWindow(viewPostion);
		mScrollView.smoothScrollBy(viewPostion[0], viewPostion[1] - 300);
	}

	/**
	 * 图片模糊处理
	 * 
	 * @param bitmap
	 * @param context
	 * @return
	 */
	public static Bitmap blurBitmap(Bitmap bitmap, Context context) {
		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		RenderScript rs = RenderScript.create(context.getApplicationContext());
		ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
		Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
		blurScript.setRadius(10.0f);// 设置模糊程度
		blurScript.setInput(allIn);
		blurScript.forEach(allOut);
		allOut.copyTo(outBitmap);
		rs.destroy();
		return outBitmap;
	}
}
