package com.yljt.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
/**
 * 模块描述：数据库助手
 * 2014-7-20 下午4:33:15
 * Write by LILIN
 */
public class DataHelper extends OrmLiteSqliteOpenHelper
{
	/**
	 * 数据库名字
	 */
	public static final String DATABASE_NAME = "progject_data.sqlite";
	/**
	 * 数据库版本
	 */
	private static final int DATABASE_VERSION = 1;
	/**
	 * 构造函数
	 * @param context
	 */
	public DataHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("DataHelper----------------->");
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
	{
		System.out.println("DataHelper onCreate----------------->");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
	{
		System.out.println("DataHelper onUpgrade----------------->");
	}
	
	@Override
	public void close()
	{
		super.close();
		System.out.println("DataHelper close----------------->");
	}
}
