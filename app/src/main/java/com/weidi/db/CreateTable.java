package com.xianglin.station.db;


/**
 * 执行业务sql常量 
 */
public class CreateTable {
	public final static Class<?>[] BEAN_CLASS = {};
	
	public final static String[] CREATE_TABLE_SQL = {CreateTable.TABLE_CHANNEL, CreateTable.TABLE_CLIENT, CreateTable.TABLE_LOGIN};
	/* 表名 */
	public final static String[] TABLES_NAME = {"ConsumeChannelInfo", "ClientInfo", "KoolYinLoginInfo"};

	// 创建不同表的sql语句（用StringBuffer）
	private final static String TABLE_CHANNEL = "CREATE TABLE IF NOT EXISTS ConsumeChannelInfo ("
			+ "_id INTEGER PRIMARY KEY,"
			+ " payment_id TEXT,"
			+ " content TEXT,"
			+ " prdt_title TEXT,"
			+ " payment_name TEXT,"
			+ " open_brh_name TEXT" + " );";
	
	private final static String TABLE_CLIENT = "CREATE TABLE IF NOT EXISTS ClientInfo ("
			+ "_id INTEGER PRIMARY KEY,"
			+ " client TEXT,"
			+ " username TEXT,"
			+ " password TEXT" + ");";
	
	private final static String TABLE_LOGIN = "CREATE TABLE IF NOT EXISTS KoolYinLoginInfo ("
			+ "_id INTEGER PRIMARY KEY,"
			+ " responseCode TEXT,"
			+ " gradeId TEXT,"
			+ " stl_cur TEXT,"
			+ " tel TEXT,"
			+ " loginCount TEXT,"
			+ " addr TEXT,"
			+ " iposSn TEXT,"
			+ " payParamVersion TEXT,"
			+ " aliasName TEXT,"
			+ " sup_mer_id TEXT,"
			+ " batchNo TEXT,"
			+ " action TEXT,"
			+ " mer_name TEXT,"
			+ " mer_nm_short TEXT,"
			+ " merchId TEXT,"
			+ " iposId TEXT,"
			+ " errorMsg TEXT,"
			+ " lastLoginTime TEXT" + ");";

}//CREATE TABLE IF NOT EXISTS ConsumeChannelInfo (_id INTEGER PRIMARY KEY,payment_id TEXT,content TEXT,prdt_title TEXT,payment_name TEXT,open_brh_name TEXT);
