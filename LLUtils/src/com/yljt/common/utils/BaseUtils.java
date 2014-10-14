package com.yljt.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
/**
 * 基本的工具
 * @author ws
 *
 */
public class BaseUtils
{
	/**
	 * 工具类标签
	 */
	public static final String TOOL_LOG = "LILINQ";
	private BaseUtils()
	{
		
	}
	/**
	 * 2个浮点的数据加法
	 * @param a
	 * @param b
	 * @return
	 */
	public static float getAdd(float a,float b)
	{
		BigDecimal ab = new BigDecimal(Float.toString(a));  
		BigDecimal bb = new BigDecimal(Float.toString(b)); 
		return ab.add(bb).floatValue();
	}
	/**
	 * 2个浮点的数据减法
	 * @param a
	 * @param b
	 * @return
	 */
	public static float getSub(float a,float b)
	{
		BigDecimal ab = new BigDecimal(Float.toString(a));  
		BigDecimal bb = new BigDecimal(Float.toString(b)); 
		return ab.subtract(bb).floatValue();
	}
	/**
	 * 2个浮点的数据加法
	 * @param a
	 * @param b
	 * @return
	 */
	public static double getAdd(double a,double b)
	{
		BigDecimal ab = BigDecimal.valueOf(a);
		BigDecimal bb = BigDecimal.valueOf(b);
		return ab.add(bb).doubleValue();
	}
	/**
	 * 2个浮点的数据减法
	 * @param a
	 * @param b
	 * @return
	 */
	public static double getSub(double a,double b)
	{
		BigDecimal ab = BigDecimal.valueOf(a);
		BigDecimal bb = BigDecimal.valueOf(b);
		return ab.subtract(bb).doubleValue();
	}
	/**
	 * 修剪浮点类型
	 * @param value 数值
	 * @param rules 规则(如:0.00保留2位小数)
	 * @return
	 */
	public static String getTrim(double value,String rules)
	{
		DecimalFormat df = new DecimalFormat(rules);
		return df.format(value);
	}
	/**
	 * dip转化px
	 * @param context
	 * @param dip
	 * @return
	 */
	public static int dipToPx(Context context, int dip)
	{
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}
	/**
	 * px转化dip
	 * @param context
	 * @param px
	 * @return
	 */
	public static int pxToDip(Context context, int px)
	{
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
	}
    /** 
     * 将px值转换为sp值，保证文字大小不变 
     *  
     * @param pxValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
	public static int px2sp(Context context, float pxValue)
	{
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}
  
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     *  
     * @param spValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
	public static int sp2px(Context context, float spValue)
	{
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获得文件路径
	 * 
	 * @param filePath
	 *            文件夹路径
	 * @param fileUrl
	 *            文件url
	 * @return
	 */
	public static String getFilePath(String filePath, String fileUrl)
	{
		return filePath + "/" + fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
	}
	/**
	 * 检查是否存在SD卡
	 * 
	 * @return
	 */
	public static boolean checkSDCard()
	{
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) { return true; }
		return false;
	}
	/**
	 * 将raw文件下的数据库文件写到系统默认路径下
	 * 
	 * @param context
	 * @param dbID
	 *            dbID 数据库文件的ID
	 * @param dbName
	 *            生成数据库文件的名字(此名字要和创建SqlHelper的名字一样)
	 */
	public static void writeDB(Context context, int dbID, String dbName)
	{
		final String DATA_BASE_PATH = "/data/data/" + context.getPackageName() + "/databases";
		try
		{
			File dir = new File(DATA_BASE_PATH);
			if (!dir.exists())
			{
				dir.mkdir();
			}
			String dbPath = DATA_BASE_PATH + "/" + dbName;
			if (!(new File(dbPath)).exists())
			{
				InputStream is = context.getResources().openRawResource(dbID);
				FileOutputStream fos = new FileOutputStream(dbPath);
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
		} catch (Exception e)
		{
			Log.e(TOOL_LOG, "T Exception------------->" + e.toString());
		}
	}
	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str)
	{
		String dest = "";
		if (str != null)
		{
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	/**
	 * 获得网络是否可用
	 * @param context
	 * @return true可用,false不可用
	 */
	public static boolean isNetworkAvailable(Context context)
	{
		if(context == null)
		{
			return false;
		}
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null)
		{		
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			if (info != null && info.isConnected())
			{
				if (info.getState() == NetworkInfo.State.CONNECTED)
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 网络不可用
	 */
	public static final int NET_NOT_AVAILABLE = -1;
	/**
	 * WIFi
	 */
	public static final int NET_WIFI = 1;
	/**
	 * 2G
	 */
	public static final int NET_2G = 2;
	/**
	 * 3G
	 */
	public static final int NET_3G = 3;

	/**
	 * 获取当前的网络状态 -1：没有网络 , 1：WIFI网络, 2：wap网络,3：net网络
	 * 
	 * @param context
	 * @return
	 */
	public static int getAPNType(Context context)
	{
		int netType = NET_NOT_AVAILABLE;
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		int subType = telephonyManager.getNetworkType();
		if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected())
		{
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE)
		{
			switch (subType)
			{
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return NET_2G; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return NET_2G; // ~ 14-64 kbps
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return NET_2G; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return NET_2G; // ~ 100 kbps
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return NET_2G;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return NET_3G; // ~ 400-1000 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return NET_3G; // ~ 600-1400 kbps
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return NET_3G; // ~ 2-14 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPA:
				return NET_3G; // ~ 700-1700 kbps
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return NET_3G; // ~ 1-23 Mbps
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return NET_3G; // ~ 400-7000 kbps
				// NOT AVAILABLE YET IN API LEVEL 7
				// case Connectivity.NETWORK_TYPE_EHRPD:
				// return NET_3G; // ~ 1-2 Mbps
				// case Connectivity.NETWORK_TYPE_EVDO_B:
				// return NET_3G; // ~ 5 Mbps
				// case Connectivity.NETWORK_TYPE_HSPAP:
				// return NET_3G; // ~ 10-20 Mbps
				// case Connectivity.NETWORK_TYPE_IDEN:
				// return NET_2G; // ~25 kbps
				// case Connectivity.NETWORK_TYPE_LTE:
				// return NET_3G; // ~ 10+ Mbps
				// Unknown
			default:
				return NET_2G;
			}
		} 
		else if (nType == ConnectivityManager.TYPE_WIFI)
		{
			netType = NET_WIFI;
		}
		return netType;
	}
	/**
	 * 关闭键盘
	 * @param context
	 */
	public static void closeSoftKeyBoard(Activity context)
	{
		View view = context.getWindow().peekDecorView();
		if (view != null)
		{
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	/**
	 * 打开键盘
	 * @param context
	 */
	public static void openSoftKeyBoard(Activity context)
	{
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
	}
	/**
	 * 读取文件创建时间
	 */
	public static String getCreateTime(String filePath)
	{
		String strTime = "";
		try
		{
			Process p = Runtime.getRuntime().exec("cmd /C dir " + filePath + "/tc");
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.endsWith(".txt"))
				{
					strTime = line.substring(0, 17);
					break;
				}
				else if(line.endsWith(".png"))
				{
					strTime = line.substring(0, 17);
					break;
				}
			}
		} 
		catch (IOException e)
		{
			Log.e(TOOL_LOG, "获取文件日期异常---------->"+e.toString());
		}
		return strTime;
	}
	/**
	 * 判断对象是否为空 也可判断String
	 * Write By LILIN
	 * 2014-3-3
	 * @param obj
	 * @return
	 */
	public static boolean IsNotEmpty(Object obj){
		if(obj==null){
			return false;
		}
		else if(obj.equals("")||obj.equals("null")||obj.equals("NULL")||obj.toString().trim().equals("")){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 返回一个带有颜色的字符串，需要以HTML的方式使用
	 * Write By LILIN
	 * 2014-3-12
	 * @param content  内容
	 * @param color    颜色#FF6A28
	 * @return
	 */
	public static String getColorHtmlText(String content,String color){
		String colorTextP="<font color=\""+color+"\">";
		String colorTextL="</font>";
		
		return colorTextP+content+colorTextL;
	}
	
	/**
	 * 拨打电话号码
	 * Write By LILIN
	 * 2014-3-13
	 * @param mobile
	 * @param activity
	 */
	public static void callTelephone(String mobile,Activity activity){
		    //此处应该对电话号码进行验证。。
		    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+mobile));
		    
		    activity.startActivity(intent);
		    
	} 
	
	/**
	 * 获取手机的IMEI号
	 * @param activity
	 * @return
	 */
	public static String getMobileIMEI(Activity activity){
		
		return((TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE)).getDeviceId();
	}
	 /**
     * @return 返回当前手机屏幕的显示指标
     * 包括分辨率 像素密度等
     */
    public  static DisplayMetrics getDisplayMetrics(Activity content){
    	  DisplayMetrics dm = new DisplayMetrics();  
    	  dm=content.getResources().getDisplayMetrics();  
         return dm;
    	
    }
}
