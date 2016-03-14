/**
 * 文件名：IApplyService.java
 * 版本信息：Version 1.0
 * 日期：2016年3月14日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service;

import java.util.Map;

import com.node.util.HqlHelper;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月14日 下午5:26:56
 */
public interface IApplyService {

	/**
	 * 方法描述：
	 * 
	 * @param hql
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月14日 下午5:31:11
	 */
	Map<String, Object> queryByHql(HqlHelper hql);

	/**
	 * 方法描述：
	 * 
	 * @param string
	 *            代码类别
	 * @param cysy
	 *            代码值
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月14日 下午7:12:35
	 */
	String findByProPerties(String string, String cysy);

}
