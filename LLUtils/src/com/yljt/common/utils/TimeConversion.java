package com.yljt.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.format.DateUtils;
import android.util.Log;

/**
 * 时间格式转换
 * 
 * @author Administrator
 * 
 */
public class TimeConversion {
	/**
	 * 返回几小时前(几分钟前)
	 * 
	 * @param createTime
	 * @return
	 */
	public static String getClacTime(String createTime) {
		long diff = System.currentTimeMillis() - Long.valueOf(createTime)
				* 1000;// 这样得到的差值是微秒级别
		String timeStr = "";
		// 计算年月日时分秒
		int year = (int) (diff / DateUtils.YEAR_IN_MILLIS);
		int month = (int) (diff / (DateUtils.WEEK_IN_MILLIS * 4));
		int day = (int) (diff / DateUtils.DAY_IN_MILLIS);
		int hour = (int) (diff / DateUtils.HOUR_IN_MILLIS);
		int minute = (int) (diff / DateUtils.MINUTE_IN_MILLIS);
		if (year > 0) {
			timeStr = String.valueOf(year) + "年前";
		} else if (month > 0) {
			timeStr = String.valueOf(month) + "月前";
		} else if (day > 0) {
			timeStr = String.valueOf(day) + "天前";
		} else if (hour > 0) {
			timeStr = String.valueOf(hour) + "小时前";
		} else if (minute > 0) {
			timeStr = String.valueOf(minute) + "分钟前";
		} else {
			timeStr = "1分钟内";
		}
		return timeStr;
	}

	/**
	 * 日志标签
	 */
	private final static String LOG_TAG = "ZVEZDA";

	/**
	 * 获得时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getTime(Object time) {
		return getTime(time, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 更具格式获得日期
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String getTime(Object time, String format) {
		try {
			Date date = null;
			if (time instanceof String) {
				date = new Date(Long.parseLong(time.toString()));
			} else if (time instanceof Long) {
				date = new Date((Long) time);
			} else {
				return time.toString();
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} catch (Exception e) {
			Log.e(LOG_TAG, "转化时间异常------>" + e.toString());
		}
		return time.toString();
	}

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * @return 返回当前系统中文格式时间
	 */
	public static String getSystemAppTimeCN() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 返回没有时分秒的时间
	 * 
	 * @return LILIN 下午12:10:16
	 */
	public static String getSystemAppTimeNotHasHour() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static String getDate(String month, String day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		java.util.Date d = new java.util.Date();
		;
		String str = sdf.format(d);
		String nowmonth = str.substring(5, 7);
		String nowday = str.substring(8, 10);
		String result = null;

		int temp = Integer.parseInt(nowday) - Integer.parseInt(day);
		switch (temp) {
		case 0:
			result = "今天";
			break;
		case 1:
			result = "昨天";
			break;
		case 2:
			result = "前天";
			break;
		default:
			StringBuilder sb = new StringBuilder();
			sb.append(Integer.parseInt(month) + "月");
			sb.append(Integer.parseInt(day) + "日");
			result = sb.toString();
			break;
		}
		return result;
	}

	/**
	 * 获取友好的 今天14.30时间格式 Write By LILIN 2014-3-14
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getTime(int timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = null;
		try {
			java.util.Date currentdate = new java.util.Date();// 当前时间

			long i = (currentdate.getTime() / 1000 - timestamp) / (60);
			System.out.println(currentdate.getTime());
			System.out.println(i);
			Timestamp now = new Timestamp(System.currentTimeMillis());// 获取系统当前时间
			System.out.println("now-->" + now);// 返回结果精确到毫秒。

			String str = sdf.format(new Timestamp(IntToLong(timestamp)));
			time = str.substring(11, 16);

			String month = str.substring(5, 7);
			String day = str.substring(8, 10);
			System.out.println(str);
			System.out.println(time);
			System.out.println(getDate(month, day));
			time = getDate(month, day) + time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	// java Timestamp构造函数需传入Long型
	public static long IntToLong(int i) {
		long result = (long) i;
		result *= 1000;
		return result;
	}

	/**
	 * 输入一个时间，获取该时间的时间戳
	 * 
	 * @param @param dateString
	 * @param @return
	 * @param @throws ParseException
	 */
	public long string2Timestamp(String dateString) throws ParseException {
		Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse(dateString);
		long temp = date1.getTime();// JAVA的时间戳长度是13位
		return temp;
	}

	/**
	 * 
	 * @Description:输入一个时间，获取与当前时间的相差秒数
	 * @param @param dateString
	 * @param @return
	 */
	public long distanceBetweenCurren(String dateString) {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
					.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long temp = date1.getTime();// JAVA的时间戳长度是13位
		long current = System.currentTimeMillis();
		return (current - temp) / 1000;

	}

	/**
	 * 时间戳转制定格式时间 Write By LILIN 2014-3-14
	 * 
	 * @param timeStamp
	 * @param format
	 * @return
	 */
	public static String timeStampToTime(String timeStamp, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long unixLong = 0;
		String date = "";
		try {
			unixLong = Long.parseLong(timeStamp) * 1000;// 时间戳是秒为单位
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		date = sdf.format(unixLong);
		return date;
	}
}
