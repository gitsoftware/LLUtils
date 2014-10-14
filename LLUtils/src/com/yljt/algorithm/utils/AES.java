package com.yljt.algorithm.utils;


/**
 * AES算法
 * @author Administrator
 *
 */
public class AES extends Algorithm
{
	/**
	 * 加密类型
	 */
	private static final String TYPE = "AES";
	/**
	 * 密匙
	 */
	private String key = null;
	/**
	 * 构造算法
	 * @param key 密匙长度必须为16
	 */
	public AES(String key)
	{
		this.key = key;
	}
	/**
	 * 获得密匙
	 * @return
	 */
	public String getKey()
	{
		return key;
	}
	/**
	 * 设置密匙
	 * @param key
	 */
	public void setKey(String key)
	{
		this.key = key;
	}
	@Override
	public String getEncrypt(String data)
	{
		String result = null;
		if(data == null || key == null)
		{
			return null;
		}
		if(key.length() == 16)
		{
			try
			{
				byte[] resultbytes = AlgorithmUtils.encrypt(data.getBytes(), key.getBytes("UTF-8"),TYPE);
				if (resultbytes != null)
				{
					result = AlgorithmUtils.byte2hex(resultbytes);
				}
			} 
			catch (Exception e)
			{
				System.out.println("获得AES加密异常----------->"+e.toString());
			}
		}
		else
		{
			throw new IllegalArgumentException("密匙长度必须为16");
		}
		return result;
	}

	@Override
	public String getDecrypt(String data)
	{
		String result = null;
		if(data == null || key == null)
		{
			return null;
		}
		if(key.length() == 16)
		{
			try
			{
				byte[] databytes = AlgorithmUtils.hex2byte(data);
				byte[] resultbytes = AlgorithmUtils.decrypt(databytes, key.getBytes("UTF-8"),TYPE);
				if (resultbytes != null)
				{
					result = new String(resultbytes);
				}
			} 
			catch (Exception e)
			{
				System.out.println("获得AES解密异常----------->"+e.toString());
			}
		}
		else
		{
			throw new IllegalArgumentException("密匙长度必须为16");
		}
		return result;
	}
}
