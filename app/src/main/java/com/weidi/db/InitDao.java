package com.xianglin.station.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xianglin.station.constants.ReleaseSwitch;

/**
 * 数据库初始化执行类
 */
public class InitDao{
	public static String innerDbName;
	public static int innerDBVersion = ReleaseSwitch.DB_DEBUG_VER; 
	public static InitDao initDao = null;
	public static SQLiteOpenHelper sSQLiteOpenHelper = null;
	private static DBHelper sDBHelper = null; 
	private SQLiteDatabase mDb = null; 
	
	public static InitDao init(String dbName, int dbVer) {
		if(initDao == null){
			initDao = new InitDao();
		}
		InitDao.innerDbName = dbName;
		InitDao.innerDBVersion = dbVer;
		
		return initDao;
	}
	
	public DBHelper getInnerDBHelper(Context context) { 
		if (sDBHelper == null) {
			// 加同步锁 
			sDBHelper = new DBHelper(context,InitDao.innerDbName,
					InitDao.innerDBVersion, 
					true);
		}
		sSQLiteOpenHelper = sDBHelper;
		getReadable();
		return sDBHelper;
	}
	
	protected SQLiteDatabase getReadableDatabase() {
		try{
			if (mDb == null || !mDb.isOpen()) {
				while (sSQLiteOpenHelper.getReadableDatabase().isDbLockedByCurrentThread()) {
					Thread.sleep(10);
				}
				mDb = sSQLiteOpenHelper.getReadableDatabase();
			}
		} catch (Exception e) {
		}
		return mDb;
	}

	protected SQLiteDatabase getWrittableDatabase() {
		try{
			if (mDb == null || !mDb.isOpen()) {
				while (sSQLiteOpenHelper.getWritableDatabase().isDbLockedByCurrentThread()) {
					Thread.sleep(10);
				}
				mDb = sSQLiteOpenHelper.getWritableDatabase();
			}
		} catch (Exception e) {
		}
		return mDb;
	}
}
