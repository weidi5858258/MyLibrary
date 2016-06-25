package com.xianglin.station.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.koolcloud.bean.ClientInfo;
import com.xianglin.station.db.BaseSqlDao;

import android.content.ContentValues;

/**
 * 客户号、用户名、密码等的操作
 * @author wangliwei
 * @version $Id: ClientInfoDao.java, v 1.0.0 2015年9月7日 下午2:06:10 ex-wangliwei Exp $
 */
public class ClientInfoDao {
	private static String table = "ClientInfo";
	private BaseSqlDao baseDao;
	public ClientInfoDao() {
		baseDao = BaseSqlDao.getInstance();
	}

	/**
	 * 插入数据
	 * @param consumeChannelInfo
	 * @return
	 */
	public long insertBean(ClientInfo clientInfo) {
		long result = 0;
		try {
			result = baseDao.insertData(table, clientInfo);
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
	 * 根据ID查询数据库
	 * @param custId
	 * @return
	 */
	public ClientInfo queryBean(String client){
		ClientInfo info = null;
		HashMap<String, String> where = new HashMap<String, String>();
		where.put("client", client);
		info = (ClientInfo) baseDao.selectSingleData(table, where, ClientInfo.class);
		return info;
	}
	
	/**
	 * 删除数据
	 * @param custId
	 */
	public boolean deleteBean(String client){
		HashMap<String, String> where = new HashMap<String, String>();
		where.put("client", client);
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
	 * @param client 客户号
	 * @return
	 */
	public boolean isBeanExist(String client) {
		Map<String, String> where = new HashMap<String, String>();
		where.put("client", client);
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
	
	public void clearTable(){
		baseDao.clearTable(table);
	}
	
	public void clearAllTables(){
		baseDao.clearAllTable();
	}
}
