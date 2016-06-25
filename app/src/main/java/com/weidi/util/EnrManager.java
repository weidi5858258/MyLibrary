package com.xianglin.station.util;

import android.content.Context;

//import java.util.LinkedList;
//import java.util.List;
//
//import com.xianglin.station.activity.BaseActivity;
//import com.xianglin.station.activity.IndexActivity;
//
//import android.app.Activity;
//import android.app.Application;

/**
 * @Description 管理全部环境变量
 * @Tip 
 * @author 
 * @date 
 * @update 
 */
public class EnrManager {

	private static EnrManager instance;
	private Context mContext;
	
	private EnrManager() {}

	public static EnrManager getInstance() {
		if (instance == null) {
			synchronized (EnrManager.class) {//在同一时刻，只能有一个调用者调用这个方法
				if (instance == null)
					instance = new EnrManager();
			}
		}
		return instance;
	}

	// 使用前必须先 设置Context
	public void setContext(Context context)
	{
		mContext = context;
	}
	
	public String getEnrValue(String key) {
		return SharedPreferencesUtil.getPreferences(mContext, key);
	}

	public void setEnrValue(String key, String value) {
		SharedPreferencesUtil.setPreferences(mContext, key, value);
	}
}
