package com.xianglin.station.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期操作工具类
 * 
 * @author huang yang
 * @version $Id: DateUtils.java, v 1.0.0 2015年10月6日 下午1:55:11 huangyang Exp $
 */
public class DateUtils {
	/**
	 * 时间格式转换
	 * 
	 * @param gmt
	 * @return
	 */
	public static String dateFomatter(String gmt) {
		String cc = gmt.substring(0, 19) + gmt.substring(33, 38);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", new Locale("English"));
		try {
			Date date = sdf.parse(cc);
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String result = dateformat.format(date);
			return result;
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 时间格式转换
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFomatter(Date date) {
		if (date != null)
			return dateFomatter(date, "yyyy-MM-dd");
		return null;
	}

	/**
	 * 时间格式转换 Date to String
	 * 
	 * @param date
	 * @param fomatterString
	 * @return
	 */
	public static String dateFomatter(Date date, String fomatterString) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(fomatterString);
			String result = sdf.format(date);
			return result;
		}
		return "";
	}

	/**
	 * 字符串转换成Date
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date string2Date(String strDate) {
		return string2Date(strDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 字符串转换成Date
	 * 
	 * @param strDate
	 * @param formatterString
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date string2Date(String strDate, String formatterString) {
		if (strDate == null)
			return new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(formatterString);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
