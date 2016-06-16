/**
  * 文件名：IDriverSerivce.java
  * 版本信息：Version 1.0
  * 日期：2016年6月16日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.service;

import java.util.List;
import java.util.Map;

import com.node.model.DdcDriver;
import com.node.util.HqlHelper;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月16日 下午2:20:55 
 */
public interface IDriverSerivce {

	
	/**
	  * 方法描述：
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月16日 下午3:22:58
	  */
	List<DdcDriver> getAllDrivers();

	
	/**
	  * 方法描述：
	  * @param hql
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月16日 下午3:42:25
	  */
	Map<String, Object> queryByHql(HqlHelper hql);


	
	/**
	  * 方法描述：
	  * @param driverId
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月16日 下午5:32:48
	  */
	DdcDriver getDriverById(long driverId);


	
	/**
	  * 方法描述：
	  * @param hyxhzh
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月16日 下午6:13:21
	  */
	String getHyxhNameByHyxhzh(String hyxhzh);


	
	/**
	  * 方法描述：
	  * @param long1
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月16日 下午6:32:16
	  */
	String getDwmcById(Long long1);


	
	/**
	  * 方法描述：
	  * @param sfzhm
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月16日 下午7:10:42
	  */
	String findSameSfzhm(String sfzhm);


	
	/**
	  * 方法描述：
	  * @param ddcDriver 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月16日 下午7:12:44
	  */
	void saveDdcDriver(DdcDriver ddcDriver);


	
	/**
	  * 方法描述：
	  * @param ddcDriver 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月16日 下午7:13:12
	  */
	void updateDdcDriver(DdcDriver ddcDriver);

}
