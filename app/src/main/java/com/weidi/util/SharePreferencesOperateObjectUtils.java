package com.xianglin.station.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * sp操作对象
 * 
 * @author huang yang
 * @version $Id: SharePreferencesOperateObjectUtils.java, v 1.0.0 2015年10月19日
 *          下午6:01:29 huangyang Exp $
 */
public class SharePreferencesOperateObjectUtils {
	public static final String SP_NAME = "spobjectconfig";
	public static final String CardObjConfig = "cardobjconfig";
	public static SharedPreferences sp;

	/**
	 * desc:保存对象
	 * 
	 * @param context
	 * @param key
	 * @param obj
	 *            要保存的对象，只能保存实现了serializable的对象 modified:
	 */
	public static void saveObject(Context context, String spName, String key, Object obj) {
		try {
			// 保存对象
			SharedPreferences.Editor sharedata = context.getSharedPreferences(spName == null ? SP_NAME : spName, 0).edit();
			// 先将序列化结果写到byte缓存中，其实就分配一个内存空间
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			// 将对象序列化写入byte缓存
			os.writeObject(obj);
			// 将序列化的数据转为16进制保存
			String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
			// 保存该16进制数组
			sharedata.putString(key, bytesToHexString);
			sharedata.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * desc:获取保存的Object对象
	 * 
	 * @param context
	 * @param key
	 * @return modified:
	 */
	public static Object readObject(Context context, String spname, String key) {
		try {
			SharedPreferences sharedata = context.getSharedPreferences(spname == null ? SP_NAME : spname, 0);
			if (sharedata.contains(key)) {
				String string = sharedata.getString(key, "");
				if (TextUtils.isEmpty(string)) {
					return null;
				} else {
					// 将16进制的数据转为数组，准备反序列化
					byte[] stringToBytes = StringUtil.StringToBytes(string);
					ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
					ObjectInputStream is = new ObjectInputStream(bis);
					// 返回反序列化得到的对象
					Object readObject = is.readObject();
					return readObject;
				}
			}
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 所有异常返回null
		return null;
	}

	/**
	 * 根据key移除对象
	 * 
	 * @param context
	 * @param spname
	 * @param key
	 */
	public static void removeByKey(Context context, String spname, String key) {
		SharedPreferences sharedata = context.getSharedPreferences(spname == null ? SP_NAME : spname, 0);
		if (sharedata.contains(key))
			sharedata.edit().remove(key).commit();
	}

	/**
	 * 清除所有对象
	 * 
	 * @param context
	 * @param spname
	 */
	public static void removeAll(Context context, String spname) {
		SharedPreferences sharedata = context.getSharedPreferences(spname == null ? SP_NAME : spname, 0);
		if (sharedata != null)
			sharedata.edit().clear().commit();
	}
}
