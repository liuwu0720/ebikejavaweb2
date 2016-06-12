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
import com.node.model.DdcDriver;
import com.node.model.DdcDriverTemp;
import com.node.model.DdcFlow;
import com.node.model.DdcHyxhBase;
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

	
	/**
	  * 方法描述：
	  * @param ddcHyxhBase 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年5月22日 下午1:25:35
	  */
	void saveDdcHyxhBase(DdcHyxhBase ddcHyxhBase);

	
	/**
	  * 方法描述：
	  * @param ddcHyxhBase 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年5月22日 下午1:27:01
	  */
	void updateDdchyxhBase(DdcHyxhBase ddcHyxhBase);

	
	/**
	  * 方法描述：
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年5月22日 下午1:28:17
	  */
	List<DdcHyxhBase> getAllDDcHyxhBase();

	
	/**
	  * 方法描述：
	  * @param vcUserName
	  * @param vcUserCard
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月5日 上午9:47:27
	  */
	boolean findDdcDriverTemp(String vcUserName, String vcUserCard);

	
	/**
	  * 方法描述：
	  * @param ddcDriverTemp 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月5日 上午9:52:51
	  */
	void saveDriverTemp(DdcDriverTemp ddcDriverTemp);

	
	/**
	  * 方法描述：
	  * @param ddcDriverTemp 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月5日 上午10:19:53
	  */
	void updateDriverTemp(DdcDriverTemp ddcDriverTemp);

	
	/**
	  * 方法描述：
	  * @param ddcDriver2 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月5日 下午3:34:59
	  */
	void saveDdcDriver(DdcDriver ddcDriver2);

	
	/**
	  * 方法描述：根据司机ID找出所有该司机的所有车辆档案
	  * @param id
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月7日 下午9:57:23
	  */
	List<Long> findAllDaxxByDriverId(Long id);

	
	/**
	  * 方法描述：
	  * @param newDaxxb 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月11日 下午6:19:38
	 * @param oldDaxxb 
	 * @param ddcDriver 
	  */
	void updateDdcDriverDaxxb(DdcDaxxb newDaxxb, DdcDaxxb oldDaxxb);

	
	/**
	  * 方法描述：
	  * @param newDaxxb 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月11日 下午6:28:37
	 * @return 
	  */
	void saveUpdateDriver(DdcDaxxb newDaxxb);

}
