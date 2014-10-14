package com.yljt.core;
import net.tsz.afinal.FinalActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
/**
 * 基础类,封装一些重复使用的代码
 * @author LILIN
 * 下午4:21:40
 */
public class LLBaseActivity extends FinalActivity{
	private static final String TAG=LLBaseActivity.class.getName();
	protected Activity mActivity;
	protected Resources resources;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		mActivity = this;
		resources=mActivity.getResources();
		//将该Activity添加到栈，方便管理
		AppManager.getAppManager().addActivity(mActivity);
		Log.i(TAG, "当前所在Activity------------>"+mActivity.getClass().getName());
	}
	/**
	 * 跳转Activity
	 * Write By LILIN
	 * 2014-4-2
	 * @param clazz
	 */
	protected void goActivity(Class <?> clazz){
		startActivity(new Intent(mActivity, clazz));
	}
}
