package com.yljt.database;
import java.sql.SQLException;
import android.app.Activity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import com.yljt.common.utils.LG;
/**
 * 模块描述：得到操作Dao
 * 2014-7-9 下午4:34:21
 * Write by LILIN
 */
public class ORMOpearationDao {
	
	private Activity mActivity=null;
	
	
	public ORMOpearationDao(Activity mActivity) {
		this.mActivity = mActivity;
	}
	/**
	 * 数据助手
	 */
	private DataHelper databaseHelper = null;
	/**
	 * 数据连接
	 */
	private DatabaseConnection databaseConnection = null;
	/**
	 * 打开数据助手
	 */
	private void openDataHelper()
	{
		if(databaseHelper == null)
		{
			try
			{
				databaseHelper = new DataHelper(mActivity);
				databaseConnection = databaseHelper.getConnectionSource().getReadWriteConnection();
			} 
			catch (SQLException e)
			{
				LG.e(getClass(), "初始化数据库异常---->"+e.toString());
			}
		}
	}
	/**
	 * 载入数据
	 */
	public <T> Dao<T, Integer> getDao(Class<T> c)
	{
		openDataHelper();
		Dao<T, Integer> dataDao = null;
		try
		{
			if (dataDao == null)
			{
				dataDao = databaseHelper.getDao((Class<T>) c);
				TableUtils.createTableIfNotExists(databaseHelper.getConnectionSource(), c);
			}
			
		} 
		catch (Exception e)
		{
			LG.e(getClass(), "初始化数据异常------------->"+e.toString());
		}
		return dataDao;
	}
	/**
	 * 关闭数据库助手
	 */
	public void closeDataHelper()
	{
		if(databaseConnection != null)
		{
			try
			{
				databaseConnection.close();
			} 
			catch (SQLException e)
			{
				System.out.println("关闭数据库连接异常--------->"+e.toString());
			}
			databaseConnection = null;
		}
		if (databaseHelper != null)
		{
			databaseHelper.close();
			databaseHelper = null;
		}
	}
	
}
