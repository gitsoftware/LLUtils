package com.yljt.data.utils;

import com.yljt.algorithm.utils.Algorithm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * XML保存数据类
 * @author Administrator
 *
 */
public class DataSP
{
    /**
     * xml文件的名字
     */
    private String xmlFileName = "";
    /**
     * 上下文
     */
    public Context context = null;
    /**
     * 创建一个XML保存数据类的对象
     * @param xmlName xml文件名字
     */
    public DataSP(Context context,String xmlFileName)
    {
        this.xmlFileName = xmlFileName;
        this.context = context;
    }
    /**
     * 加密
     */
    private Algorithm algorithm = null;
    /**
     * 创建一个保存XML的对象
     * @param context
     * @param xmlFileName
     * @param algorithm
     */
    public DataSP(Context context,String xmlFileName,Algorithm algorithm)
    {
        this.xmlFileName = xmlFileName;
        this.context = context;
        this.algorithm = algorithm;
    }
    /**
     * 将某个值写到某个键中去
     * @param keyName 键
     * @param value   值
     */
    public void setValue(String keyName, String value)
    {
        SharedPreferences settings = context.getSharedPreferences(xmlFileName, 0); 
        SharedPreferences.Editor editor = settings.edit(); 
        if(algorithm != null)
        {
            editor.putString(keyName, algorithm.getEncrypt(value)); 
        }
        else
        {
            editor.putString(keyName, value); 
        }
        editor.commit();
    }
    
    /**
     * 将某个值写到某个键中去
     * @param keyName 键
     * @param value   值
     */
    public void setValue(String keyName, int value)
    {
        SharedPreferences settings = context.getSharedPreferences(xmlFileName, 0); 
        SharedPreferences.Editor editor = settings.edit(); 
        editor.putInt(keyName, value); 
        editor.commit();
    }
    
    /**
     * 将某个值写到某个键中去
     * @param keyName 键
     * @param value   值
     */
    public void setValue(String keyName, boolean value)
    {
        SharedPreferences settings = context.getSharedPreferences(xmlFileName, 0); 
        SharedPreferences.Editor editor = settings.edit(); 
        editor.putBoolean(keyName, value); 
        editor.commit();
    }
    /**
     * 根据键获得数值
     * @param keyName 键
     * @return 值
     */
    public String getSValue(String keyName,String defaultValue)
    {
        String tempValue = null;
        SharedPreferences user = context.getSharedPreferences(xmlFileName, 0);
        tempValue = user.getString(keyName, defaultValue);
        if(tempValue.equals(defaultValue))
        {
        	return tempValue;
        }
        else if(algorithm != null)
        {
        	tempValue = algorithm.getDecrypt(tempValue);
        }
        return tempValue;
    }
    /**
     * 根据键获得数值
     * @param keyName 键
     * @return 值
     */
    public int getIValue(String keyName,int defaultValue)
    {
        int tempValue = 0;
        SharedPreferences user = context.getSharedPreferences(xmlFileName, 0);
        tempValue = user.getInt(keyName, defaultValue);
        return tempValue;
    }
    /**
     * 根据键获得数值
     * @param keyName 键
     * @return 值
     */
    public boolean getBValue(String keyName,boolean defaultValue)
    {
        boolean tempValue = false;
        SharedPreferences user = context.getSharedPreferences(xmlFileName, 0);
        tempValue = user.getBoolean(keyName, defaultValue);
        return tempValue;
    }
}
