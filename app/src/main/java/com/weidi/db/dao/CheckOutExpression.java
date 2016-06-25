package com.xianglin.station.db.dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * 封装用于校验字符串的处理类
 */
public abstract class CheckOutExpression {
	/**
	 * 校验数据格式
	 * 
	 * @param text 传入的参与校验的字符串
	 * @param regex 校验规则
	 */
	public boolean CheckOut(String text,String regex)
	{
		if (!TextUtils.isEmpty(text)) {
			// 验证电话格式
			boolean result = checkRule(text, regex);
			//通过校验 
			if (result) {
				return onCheckSuccess(text,result);
			} else {//验证未通过
				return onCheckFail(text,result);
			}
		} else {
			return onCheckEmpty(false);
		}
	}
	
	/**
	 * 校验字符串是否为空字符串
	 * 
	 * @param text
	 * @return
	 */
	public boolean CheckOutIsEmpty(String text)
	{
		if(TextUtils.isEmpty(text))
		{
			return onCheckEmpty(false);
		}
		else
		{
			return onCheckSuccess(text,true);
		}
	}

	/**
	 * 根据传入的校验规则，验证文本的格式是否正确
	 * 
	 * @param text 传入的参与校验的字符串
	 * @param regex 校验规则
	 * @return
	 */
	public boolean checkRule(String text,String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	/**
	 * 通过校验调用的方法
	 * 
	 * @param text 
	 * @param result
	 * @return
	 */
	public abstract boolean onCheckSuccess(String text,boolean result);

	/**
	 * 未通过校验调用的方法
	 * 
	 * @param text
	 * @param result
	 * @return
	 */
	public abstract boolean onCheckFail(String text,boolean result);
	
	/**
	 * 检查校验数据为空时调用的方法
	 * 
	 * @param result
	 * @return
	 */
	public abstract boolean onCheckEmpty(boolean result);
}