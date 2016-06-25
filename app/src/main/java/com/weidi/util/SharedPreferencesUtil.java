package com.xianglin.station.util;

import java.io.File;
import java.io.IOException;

import com.xianglin.mobile.common.logging.LogCatLog;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * 
 * <h1>提供操作SharedPreferences写xml文件的常用方法</h1>
 * @author licaiyi972
 * @version $Id: SharedPreferencesUtil.java, v 1.0.0 2014年9月11日 下午2:54:51 licaiyi Exp $
 */
public class SharedPreferencesUtil {
	public static final String XML_ACCOUNT = "MyPrefsFileAcc";
	/*异常登录*/
	public static final String XML_EXCEPTION_LOGIN = "Exception_Login";
	public static final String KEY_FIELD_EXCEPTION_LOGIN_FLAG = "exception_login_flag";
	
	/*talkingdata数据统计页面label*/
	public static final String TALKING_DATA_PAGE_NAME_LABEL_FLAG = "talking_data_page_name_label_flag";
	
	
	/** if the file exist, the activity will finish itself and application return to login */
	public static final String FILE_FLAG = "MyPrefs";
	public static final String FILE_ACCOUNT_DELETE_FLAG = "MyPrefs_delete";	
	
	public static final String USER_STATUS_CONFIG = "userstatusconfig";	
	
	public static final String CARD_PACK = "CARD_PACK";
	
	public static final String CARD_PACK_IS_FIRST = "CARD_PACK_IS_FIRST";

	
	public static void setPreferences(Context context,String key,boolean value){
		if(context == null) return;
		SharedPreferences sharedPreferences = context.getSharedPreferences("xianglin", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	public static boolean getPreferencesBoolean(Context context,String key){
		if(context == null) return true;
		SharedPreferences sharedPreferences = context.getSharedPreferences("xianglin", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, true);
	}
	public static boolean getPreferencesBoolean(Context context,String key,boolean df){
		if(context == null) return false;
		SharedPreferences sharedPreferences = context.getSharedPreferences("xianglin", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, df);
	}
	public static void setPreferences(Context context,String key,String value){
		if(context == null) return;
		SharedPreferences sharedPreferences = context.getSharedPreferences("xianglin", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static String getPreferences(Context context,String key){
		if(context == null) return "";
		SharedPreferences sharedPreferences = context.getSharedPreferences("xianglin", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, "");
	}
	public static SharedPreferences getSharedPreferences(Context context, String fileName) {
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return prefer;
	}
	
	public static Editor getEditor(Context context, String fileName) {
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return prefer.edit();
	}
	
	public static String getString(Context context, String fileName, String name, String defaultValue) {
		if(context == null) return "";
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return prefer.getString(name, defaultValue);
	}
	
	public static void putString(Context context, String fileName, String name, String value) {
		if(context == null) return;
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		prefer.edit().putString(name, value).commit();
	}
	
	public static boolean getBoolean(Context context, String fileName, String name, boolean defaultValue) {
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return prefer.getBoolean(name, defaultValue);
	}

	public static void putBoolean(Context context, String fileName, String name, boolean value) {
		if(context == null) return;
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		prefer.edit().putBoolean(name, value).commit();
	}
	
	public static long getLong(Context context, String fileName, String name, long defaultValue) {
		if(context == null) return -1;
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return prefer.getLong(name, 0);
	}

	public static void putLong(Context context, String fileName, String name, long value) {
		if(context == null) return;
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		prefer.edit().putLong(name, value).commit();
	}
	
	public static int getInt(Context context, String fileName, String name, int defaultValue) {
		if(context == null) return -1;
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return prefer.getInt(name, defaultValue);
	}
	
	public static void putInt(Context context, String fileName, String name, int value) {
		if(context == null) return;
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		prefer.edit().putInt(name, value).commit();
	}
	
	public static void remove(Context context, String fileName, String name) {
		if(context == null) return;
		SharedPreferences prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		prefer.edit().remove(name).commit();
	}
	
	public static String getString(SharedPreferences prefer, String name, String defaultString) {
		return prefer.getString(name, defaultString);
	}

}
