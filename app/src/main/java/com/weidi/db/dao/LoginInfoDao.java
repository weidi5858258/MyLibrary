package com.xianglin.station.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.koolcloud.bean.KoolYinLoginInfo;
import com.xianglin.station.db.BaseSqlDao;

import android.content.ContentValues;

public class LoginInfoDao {
	private static String table = "KoolYinLoginInfo";
	private BaseSqlDao baseDao;
	public LoginInfoDao() {
		baseDao = BaseSqlDao.getInstance();
	}

	/**
	 * 插入数据
	 * @param KoolYinLoginInfo
	 * @return
	 */
	public long insertBean(KoolYinLoginInfo KoolYinLoginInfo) {
		long result = 0;
		try {
			result = baseDao.insertData(table, KoolYinLoginInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 更新数据
	 * @param values
	 * @param wheres
	 * @return
	 */
	public long updateBean(ContentValues values, ContentValues wheres){
		long result = 0;
		result = baseDao.updateSql(table, values, wheres);
		return result;
	}

	
	/**
	 * 根据渠道标识查询数据库
	 * @param payParamVersion
	 * @return
	 */
	public KoolYinLoginInfo queryBean(String payParamVersion){
		KoolYinLoginInfo info = null;
		HashMap<String, String> where = new HashMap<String, String>();
		where.put("payParamVersion", payParamVersion);
		info = (KoolYinLoginInfo) baseDao.selectSingleData(table, where, KoolYinLoginInfo.class);
		return info;
	}
	
	/**
	 * 删除数据
	 * @param payParamVersion
	 */
	public boolean deleteBean(String payParamVersion){
		HashMap<String, String> where = new HashMap<String, String>();
		where.put("payParamVersion", payParamVersion);
		return baseDao.delData(table, where);
	}
	
	/**
	 * 判断表名某个值是否已存在
	 * @param table 表名
	 * @param wheres 字段 = 值
	 * @return
	 */
	public boolean isBeanExist(Map<String, String> where){
		return baseDao.isExist(table, where);
	}
	
	/**
	 * 判断表名某个值是否已存在
	 * @param payParamVersion 渠道标识
	 * @return
	 */
	public boolean isBeanExist(String payParamVersion) {
		Map<String, String> where = new HashMap<String, String>();
		where.put("payParamVersion", payParamVersion);
		return baseDao.isExist(table, where);
	}
	
	/**
	 * 
	 * @param coulums
	 * @param wheres
	 * @param extraStr
	 * @return
	 */
	public List<?> selectList(String[] coulums, Map<String, String> where, String extraStr) {
		return baseDao.selectList(table, coulums, where, extraStr);
	}
	
	public void clearAllTables(){
		baseDao.clearAllTable();
	}
}
