/**
 * 文件名：IEbikeService.java
 * 版本信息：Version 1.0
 * 日期：2016年3月16日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service;

import java.util.List;
import java.util.Map;

import com.node.model.DdcDaxxb;
import com.node.model.DdcDaxxbLog;
import com.node.model.DdcDriver;
import com.node.model.DdcFlow;
import com.node.model.DdcHyxhSsdw;
import com.node.util.HqlHelper;
import com.node.util.Page;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月16日 下午5:02:00
 */
public interface IEbikeService {

	/**
	 * 方法描述：
	 * 
	 * @param hql
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午5:03:42
	 */
	Map<String, Object> queryByHql(HqlHelper hql);

	/**
	 * 方法描述：
	 * 
	 * @param zzjgdmzh
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午5:16:44
	 */
	String findByProPerties(String zzjgdmzh);

	/**
	 * 方法描述：
	 * 
	 * @param sql
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午6:28:00
	 */
	Map<String, Object> queryBySpringSql(String sql, Page page);

	/**
	 * 方法描述：
	 * 
	 * @param sql
	 * @param p
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午6:36:16
	 */
	Map<String, Object> queryBySpringHql(String sql, Page p);

	/**
	 * 方法描述：
	 * 
	 * @param sbId
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 上午10:02:03
	 */
	DdcDaxxb getById(long sbId);

	/**
	 * 方法描述：
	 * 
	 * @param daxxb
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午7:51:13
	 */
	void update(DdcDaxxb daxxb);

	/**
	 * 方法描述：
	 * 
	 * @param daxxbLog
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午8:11:46
	 */
	void saveDdcDaxxbLog(DdcDaxxbLog daxxbLog);

	/**
	 * 方法描述：
	 * 
	 * @param ddcFlow
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午10:06:10
	 */
	void saveDdcFlow(DdcFlow ddcFlow);

	/**
	 * 方法描述：
	 * 
	 * @param flowId
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 下午6:27:07
	 */
	DdcFlow getFlowById(long flowId);

	/**
	 * 方法描述：
	 * 
	 * @param ddcFlow
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月13日 下午8:03:41
	 */
	void updateDdcFlow(DdcFlow ddcFlow);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdw
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月13日 下午8:04:05
	 */
	void updateDdcHyxhSsdw(DdcHyxhSsdw ddcHyxhSsdw);

	/**
	 * 方法描述：
	 * 
	 * @param string
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月14日 下午5:30:53
	 */
	List<DdcDaxxb> findDdcDaxxbsByFlag(String flag);

	/**
	 * 方法描述：
	 * 
	 * @param userCode
	 * @param passWord
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月23日 上午9:05:05
	 */
	List<DdcDriver> findDriverByUserInfo(String userCode, String passWord);

	/**
	 * 方法描述：
	 * 
	 * @param userCode
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月25日 上午10:31:18
	 */
	List<DdcDriver> findDriverByUserCode(String userCode);

	/**
	 * 方法描述：
	 * 
	 * @param ddcDriver
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月25日 下午12:02:10
	 */
	void updateDdcDriver(DdcDriver ddcDriver);

}
