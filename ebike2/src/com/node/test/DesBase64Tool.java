/**
  * 文件名：DesBase64Tool.java
  * 版本信息：Version 1.0
  * 日期：2016年8月3日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年8月3日 下午2:27:18 
 */
public class DesBase64Tool {
	
	private static SecretKey secretKey = null;
	private static Cipher cipher = null;
	private static String keyString="mMnwKKRPrAPLrG5sY01DDGmt";
	static{
		try {
			secretKey = new SecretKeySpec(keyString.getBytes(), "DESede");
			cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * 加密
	 */
	
	public static String desEncrypt(String message) {
		String result="";
		String newResult="";
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] resultBytes=cipher.doFinal(message.getBytes("UTF-8"));
			BASE64Encoder enc = new BASE64Encoder();
			result = enc.encode(resultBytes);
			newResult = filter(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return newResult;
	}
	
	/**
	  * 方法描述：
	  * @param result
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年8月3日 下午2:47:13
	  */
	private static String filter(String str) {
		String outputString="";
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<str.length();i++){
			int asc = str.charAt(i);
			if(asc !=10 && asc!=13){
				sb.append(str.subSequence(i, i+1));
			}
		}
		outputString = new String(sb);
		return outputString;
	}
	
	public static void main(String[] args) {
		try {
			String string = "<REQUEST><LOGIN_USER>230102198012070418</LOGIN_USER><YHLY>C</YHLY></REQUEST>";
			String deseResult = desEncrypt(string);
			System.out.println("加密结果："+deseResult);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
