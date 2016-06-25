package com.xianglin.station.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description 各种日期时间工具
 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
 * @update 2014-5-12 11:44:11
 */
public class MyDate {
	public static String getDateCN() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}

	/**
	 * @Description 获取中文日期（精确到毫秒）
	 */
	public static String getDateCNSSS() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}

	public static String getDateEN() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = f.format(new Date(System.currentTimeMillis()));
		return date1;
	}

	public static String getDate() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(new Date(System.currentTimeMillis()));
		return date;
	}

	public static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}

	public static String getDate(long time) {
		Calendar gc = Calendar.getInstance();
		gc.setTimeInMillis(time);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd");
		return format.format(gc.getTime());
	}

	public static int getCurrentYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	public static String timestamp2date(String timestamp) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(timestamp + "000")));
	}

	public static String timestamp2hours(long mss) {
		mss *= 1000;
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + " 天 " + hours + ":" + minutes + ":" + seconds;
	}

	public static String FormatTime(int h, int m, int s) {
		if (h > 23 || h < 0 || m > 59 || m < 0 || s > 59 || s < 0)
			return null;
		return String.format("%02d", h) + ":" + String.format("%02d", m) + ":" + String.format("%02d", s);
	}

	public static String FormatTime(int h, int m) {
		if (h > 23 || h < 0 || m > 59 || m < 0)
			return null;

		return String.format("%02d", h) + ":" + String.format("%02d", m);
	}

	public static String FormatTime(int sh, int sm, int eh, int em) {
		if (sh > 23 || sh < 0 || sm > 59 || sm < 0)
			return null;
		if (eh > 23 || eh < 0 || em > 59 || em < 0)
			return null;
		return String.format("%02d", sh) + ":" + String.format("%02d", sm) + "至" + String.format("%02d", eh) + ":"
				+ String.format("%02d", em);
	}

	public static long getTimestamp() {
		return (System.currentTimeMillis() / 1000);
	}

}