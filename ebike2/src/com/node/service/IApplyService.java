/**
 * 文件名：IApplyService.java
 * 版本信息：Version 1.0
 * 日期：2016年3月14日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service;

import java.util.List;
import java.util.Map;

import com.node.model.DdcApproveUser;
import com.node.model.DdcHyxhBasb;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.model.DdcHyxhSsdwclsbLog;
import com.node.model.DdcSjzd;
import com.node.util.HqlHelper;
import com.node.util.Page;

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

	/**
	 * 方法描述：
	 * 
	 * @param dmlb代码类别
	 * @param value
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午2:26:47
	 */
	List<DdcSjzd> getAllByProperties(String dmlb, String value);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdwclsb
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午3:23:24
	 */
	void saveDdcHyxhSsdwclsb(DdcHyxhSsdwclsb ddcHyxhSsdwclsb);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdwclsb
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午3:24:37
	 */
	void updateDdcHyxhSsdwclsb(DdcHyxhSsdwclsb ddcHyxhSsdwclsb);

	/**
	 * 方法描述：
	 * 
	 * @param sql
	 * @param object
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午3:45:53
	 * @return
	 */
	Map<String, Object> getBySpringSql(String sql, Page page);

	/**
	 * 方法描述：
	 * 
	 * @param sql
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午3:57:50
	 */
	Object getDateBySQL(String sql);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdwclsbLog
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午4:21:09
	 */
	void saveDdcHyxhSsdwclsbLog(DdcHyxhSsdwclsbLog ddcHyxhSsdwclsbLog);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdwclsb
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午6:37:47
	 */
	String findHmd(String man1, String man2);

	/**
	 * 方法描述：
	 * 
	 * @param sbId
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午6:48:09
	 */
	DdcHyxhSsdwclsb getDdcHyxhSsdwclsbById(long sbId);

	/**
	 * 方法描述：
	 * 
	 * @param string代码类别
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午3:39:40
	 */
	List<DdcSjzd> getSjzdByDmlb(String string);

	/**
	 * 方法描述：
	 * 
	 * @param sbId
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月19日 下午12:18:08
	 */
	DdcHyxhBasb getDdcHyxhBasbById(long sbId);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhBasb
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月19日 下午1:53:05
	 */
	void saveDdcHyxhBasb(DdcHyxhBasb ddcHyxhBasb);

	/**
	 * 方法描述：
	 * 
	 * @param ssdwId
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月2日 上午10:30:36
	 */
	DdcHyxhSsdw getDdcHyxhSsdwById(String ssdwId);

	/**
	 * 方法描述：
	 * 
	 * @param approveTableName
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月2日 上午10:32:56
	 */
	List<DdcApproveUser> findApproveUsersByProperties(String approveTableName,
			Long id);

	/**
	 * 方法描述：
	 * 
	 * @param tbyy
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月2日 上午10:39:08
	 */
	List<DdcSjzd> getDbyyList(String tbyy, String type);

	/**
	 * 方法描述：
	 * 
	 * @param ddcApproveUser
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月10日 上午11:44:27
	 */
	void saveDdcApproveUser(DdcApproveUser ddcApproveUser);

}
