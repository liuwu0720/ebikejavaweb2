/**
  * 文件名：ITaskService.java
  * 版本信息：Version 1.0
  * 日期：2016年6月7日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.service;

import java.util.List;

import com.node.model.DdcHyxhSsdwclsb;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月7日 下午9:35:20 
 */
public interface ITaskService {

	
	/**
	  * 方法描述：
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月7日 下午9:39:55
	  */
	List<DdcHyxhSsdwclsb> findAllClsbs();

}
