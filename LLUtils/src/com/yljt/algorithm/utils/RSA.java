package com.yljt.algorithm.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
/**
 * RSA算法
 * @author Administrator
 *
 */
public class RSA extends Algorithm
{
	/**
	 * 加密类型
	 */
	private static final String TYPE = "RSA";
	/**
	 * 密匙
	 */
	private KeyPair keyPair = null;
	/**
	 * 构造函数
	 * @param keyPair
	 */
	public RSA(int size)
	{
		this.keyPair = getKeyPair(size);
	}
	/**
	 * 获得密匙
	 * @return
	 */
	public KeyPair getKeyPair()
	{
		return keyPair;
	}
	/**
	 * 设置密匙
	 * @param size长度必须大于等于512
	 */
	public void setKeyPair(int size)
	{
		this.keyPair = getKeyPair(size);
	}
	/**
	 * 加密
	 * @param publicKey
	 * @param serBytes
	 * @return
	 * @throws Exception
	 */
	private byte[] decrypt(PublicKey publicKey, byte[] serBytes) throws Exception
	{
		byte[] result = null;
		if(publicKey != null)
		{
			Cipher cipher = Cipher.getInstance(TYPE);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			result = cipher.doFinal(serBytes);
		}
		return result;
	}
	/**
	 * 解密
	 * @param privateKey
	 * @param srcBytes
	 * @return
	 * @throws Exception
	 */
	private byte[] encrypt(PrivateKey privateKey, byte[] srcBytes) throws Exception
	{
		byte[] result = null;
		if (privateKey != null)
		{
			Cipher cipher = Cipher.getInstance(TYPE);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			result = cipher.doFinal(srcBytes);
		}
		return result;
	}
	/**
	 * 获得密匙
	 * @param size密匙长度必须大于512
	 * @return
	 */
	private KeyPair getKeyPair(int size)
	{
		KeyPair keyPair = null;
		try
		{
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(TYPE);
			keyPairGen.initialize(size);
			keyPair = keyPairGen.generateKeyPair();
		} 
		catch (Exception e)
		{
			System.out.println("获得RAS密匙异常----->"+e.toString());
		}
		return keyPair;
	}
	@Override
	public String getEncrypt(String data)
	{
		String result = null;
		try
		{
			byte[] resultbytes = encrypt(keyPair.getPrivate(), data.getBytes());
			if (resultbytes != null)
			{
				result = AlgorithmUtils.byte2hex(resultbytes);
			}
		} 
		catch (Exception e)
		{
			System.out.println("获得RSA加密异常----------->"+e.toString());
		}
		return result;
	}
	@Override
	public String getDecrypt(String data)
	{
		String result = null;
		try
		{
			byte[] databytes = AlgorithmUtils.hex2byte(data);
			byte[] resultbytes = decrypt(keyPair.getPublic(), databytes);
			if (resultbytes != null)
			{
				result = new String(resultbytes);
			}
		} 
		catch (Exception e)
		{
			System.out.println("获得RSA解密异常----------->"+e.toString());
		}
		return result;
	}
}
