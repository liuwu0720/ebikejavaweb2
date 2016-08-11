package com.node.test;

import java.util.TreeMap;

public class  Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TreeMap<String, Object> map = new TreeMap<String, Object>();
		 map.put("name","18912345678");
		 map.put("pwd","123456");
		 String desString = "password";
		 System.out.println("加密前："+desString);
		 String desinfo = MathUtils.encryptAsString(desString,
		 MathUtils.COMMON_KEY);
		 System.out.println("加密后："+desinfo);

		// 解密
		MathUtils.desEncryptAsString(desString, MathUtils.COMMON_KEY);
 
	}

}
