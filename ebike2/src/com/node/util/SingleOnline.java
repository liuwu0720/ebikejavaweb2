/**
 * 文件名：SingleOnline.java
 * 版本信息：Version 1.0
 * 日期：2016年5月22日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年5月22日 上午8:56:20
 */
public class SingleOnline {
	private static Map<String, String> mapOnline = new HashMap<String, String>();

	/**
	 * 将用户添加到在线列表
	 * 
	 * @param userCode
	 * @param sessionId
	 */
	public static synchronized void addUser(String userCode, String sessionId) {
		if (!mapOnline.containsKey(userCode)){
			mapOnline.put(userCode, sessionId);
		}
		
	}

	/**
	 * 是否为合法用户
	 * 
	 * @param userCode
	 * @param sessionId
	 * @return
	 */
	public static boolean isValidUser(String userCode, String sessionId) {
		if (!mapOnline.containsKey(userCode)){
			return false;
		}
			

		if (!mapOnline.get(userCode).equals(sessionId)){
			return false;
		}
			

		return true;
	}

	
	/**
	  * 方法描述：
	  * @param cuser 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年5月22日 上午9:41:46
	  */
	public static void removeValidUser(String cuser) {
		// TODO Auto-generated method stub
		mapOnline.remove(cuser);
	}

}
