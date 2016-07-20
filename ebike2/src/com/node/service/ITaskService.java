/**
  * 文件名：ITaskService.java
  * 版本信息：Version 1.0
  * 日期：2016年6月7日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.service;

import java.util.List;

import com.node.model.DdcDaxxb;
import com.node.model.DdcDriver;
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

	
	/**
	  * 方法描述：
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月9日 下午3:12:08
	  */
	List<DdcDriver> findAllDriversNotValid();


	
	/**
	  * 方法描述：
	  * @param ddcDriver
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月9日 下午3:21:49
	  */
	DdcHyxhSsdwclsb findClsbByDriver(DdcDriver ddcDriver);


	
	/**
	  * 方法描述：
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月22日 下午2:13:57
	  */
	List<DdcDaxxb> getAllNewDaxxb();


	
	/**
	  * 方法描述：
	  * @param sql2 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年7月12日 上午8:38:35
	  */
	void updateBySql(String sql2);

}
