package com.xianglin.station.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.koolcloud.bean.ConsumeChannelInfo;
import com.xianglin.station.db.BaseSqlDao;

import android.content.ContentValues;

/**
 * 酷云的消费渠道
 * @author 王力伟
 * @version $Id: ConsumeChannelDao.java, v 1.0.0 2015年8月22日 下午7:55:16 Administrator Exp $
 */
public class ConsumeChannelInfoDao {
	
	private static String table = "ConsumeChannelInfo";
	private BaseSqlDao baseDao;
	public ConsumeChannelInfoDao() {
		baseDao = BaseSqlDao.getInstance();
	}

	/**
	 * 插入数据
	 * @param consumeChannelInfo
	 * @return
	 */
	public long insertBean(ConsumeChannelInfo consumeChannelInfo) {
		long result = 0;
		try {
			result = baseDao.insertData(table, consumeChannelInfo);
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
	public ConsumeChannelInfo queryBean(String payment_id){
		ConsumeChannelInfo info = null;
		HashMap<String, String> where = new HashMap<String, String>();
		where.put("payment_id", payment_id);
		info = (ConsumeChannelInfo) baseDao.selectSingleData(table, where, ConsumeChannelInfo.class);
		return info;
	}
	
	/**
	 * 删除数据
	 * @param custId
	 */
	public boolean deleteBean(String payment_id){
		HashMap<String, String> where = new HashMap<String, String>();
		where.put("payment_id", payment_id);
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
	 * @param payment_id 支付活动号
	 * @return
	 */
	public boolean isBeanExist(String payment_id) {
		Map<String, String> where = new HashMap<String, String>();
		where.put("payment_id", payment_id);
		return baseDao.isExist(table, where);
	}
	
	/**
	 * 
	 * @param table
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
