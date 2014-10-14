package com.yljt.algorithm.utils;

/**
 * RC4算法
 * 
 * @author Administrator
 * 
 */
public class RC4 extends Algorithm
{	
	/**
	 * 密匙
	 */
	private String key = null;
	/**
	 * 构造方法
	 * @param key
	 */
	public RC4(String key)
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
	/**
	 * RC4
	 * @param data
	 * @param key
	 * @return
	 */
	private String runRC4(String data, String key)
	{
		int[] iS = new int[256];
		byte[] iK = new byte[256];
		for (int i = 0; i < 256; i++)
		{
			iS[i] = i;
		}
		int j = 1;
		for (short i = 0; i < 256; i++)
		{
			iK[i] = (byte) key.charAt((i % key.length()));
		}
		j = 0;
		for (int i = 0; i < 255; i++)
		{
			j = (j + iS[i] + iK[i]) % 256;
			int temp = iS[i];
			iS[i] = iS[j];
			iS[j] = temp;
		}
		int i = 0;
		j = 0;
		char[] iInputChar = data.toCharArray();
		char[] iOutputChar = new char[iInputChar.length];
		for (short x = 0; x < iInputChar.length; x++)
		{
			i = (i + 1) % 256;
			j = (j + iS[i]) % 256;
			int temp = iS[i];
			iS[i] = iS[j];
			iS[j] = temp;
			int t = (iS[i] + (iS[j] % 256)) % 256;
			int iY = iS[t];
			char iCY = (char) iY;
			iOutputChar[x] = (char) (iInputChar[x] ^ iCY);
		}
		return new String(iOutputChar);
	}

	@Override
	public String getEncrypt(String data)
	{
		// TODO Auto-generated method stub
		return runRC4(data,key);
	}

	@Override
	public String getDecrypt(String data)
	{
		// TODO Auto-generated method stub
		return runRC4(data,key);
	}
}
