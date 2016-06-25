package com.xianglin.station.db;

import java.lang.reflect.Field;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;

/**
 * 数据库帮助类
 */
public class DBHelper extends SQLiteOpenHelper {
	private String dbName;// ------------数据库名
	private int dbVersion;// ------------数据库版本
	private SQLiteDatabase db;
	private boolean isInnerDb;

	private String[] createTableSql;
	private Class<?>[] beanClass;
	
	public DBHelper(Context context, String name, int version,boolean isInnerDb) {
		super(context, name, null, version);
		this.dbName = name;
		this.dbVersion = version;
		this.isInnerDb = isInnerDb;
		if(Build.VERSION.SDK_INT >= 11){  
			getReadableDatabase().enableWriteAheadLogging();
		}  
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		this.createTableSql = CreateTable.CREATE_TABLE_SQL;
		this.beanClass = CreateTable.BEAN_CLASS;
		if(this.createTableSql!=null&&this.createTableSql.length>0){
			for(String sql : createTableSql){
					if(!TextUtils.isEmpty(sql)) db.execSQL(sql);
			}
		}
		if(this.beanClass!=null&&this.beanClass.length>0){
			for(Class<?> bean : beanClass){
				String sqlStr = getSqlByBean(bean);
				if(!TextUtils.isEmpty(sqlStr)) db.execSQL(sqlStr);
			}
		}
		if(isInnerDb){
			
		}else{	
			
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {			
		if(isInnerDb){
			clearAllTables(db);
			
			switch(oldVersion){
			
			case 1:
				

			case 2:
				

			}
		} 
	}
	
	private String getSqlByBean(Class<?> bean) {
		Field[] fields = bean.getDeclaredFields();
		Field.setAccessible(fields, true);
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		sql.append(bean.getSimpleName());
		sql.append("(");
		for (Field field : fields) {
			String colum = field.getName();
			if ("serialVersionUID".equals(colum)||"CREATOR".equals(colum))
				continue;
			sql.append("'");
			sql.append(colum);
			sql.append("' VARCHAR,");
		}
		String sqlStr = sql.toString();
		return sqlStr.substring(0, sqlStr.length()-2)+")";
	}
	
	private void clearAllTables(SQLiteDatabase db) {
		String[] tables = CreateTable.TABLES_NAME;
		for (String tableName : tables) {
			db.execSQL("delete from "+tableName);
		}
	}

	public boolean isDBNotFree() {
		return db != null
				&& (db.isDbLockedByCurrentThread());
	}
	
	public String getDbName() {
		return dbName;
	}

	public int getDbVersion() {
		return dbVersion;
	}
}
