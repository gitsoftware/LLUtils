package com.yljt.common.utils;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

import android.util.Log;
/**
 * JSON数据解析
 * @author LILIN
 * 下午4:32:49
 */
public class JSONParser {

	public static Object JSON2Object(String jsonStr, Class<?> cls) {
		// 不为空，并且服务器没有异常
		if(BaseUtils.IsNotEmpty(jsonStr)){
			if(jsonStr.indexOf("exception") == -1 ){
				System.out.println("JSON "+jsonStr+"\nJSON");
				return JSON.parseObject(jsonStr, cls);
			} else {
				Log.e("","服务器异常："+jsonStr);
			}
		}
		return null;
	}
	public static Object JSON2Array(String jsonStr, Class<?> cls){
		// 不为空，并且服务器没有异常
		if(BaseUtils.IsNotEmpty(jsonStr)){
			if(jsonStr.indexOf("exception") == -1 ){
				System.out.println("JSON "+jsonStr+"\nJSON");
				return JSON.parseArray(jsonStr, cls);
			} else {
				Log.e("","服务器异常："+jsonStr);
			}
		}
		return null;
	}
	  /**
     * @param string      获取的字段
     * @param jsonString  JSON数据串
     * @return
     */
    public static String getStringFromJsonString(String string,String jsonString){
    	JSONObject dataJson;
    	String success="";
		try {
			dataJson = new JSONObject(jsonString);
			success=dataJson.optString(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return success;
    }
}
