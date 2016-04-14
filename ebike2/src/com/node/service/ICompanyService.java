/**
 * 文件名：ICompanyService.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service;

import java.util.List;
import java.util.Map;

import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwLog;
import com.node.model.PicPath;
import com.node.util.HqlHelper;
import com.node.util.Page;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月11日 下午10:11:12
 */
public interface ICompanyService {

	/**
	 * 方法描述：
	 * 
	 * @param hql
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月11日 下午10:13:13
	 */
	Map<String, Object> queryByHql(HqlHelper hql);

	/**
	 * 方法描述：
	 * 
	 * @param dwId
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 上午9:46:48
	 */
	DdcHyxhSsdw queryInfoById(long dwId);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdw
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 上午10:36:21
	 */
	String queryIsSame(DdcHyxhSsdw ddcHyxhSsdw);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdw
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 上午10:44:02
	 */
	void save(DdcHyxhSsdw ddcHyxhSsdw);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdw
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 上午11:03:25
	 */
	void update(DdcHyxhSsdw ddcHyxhSsdw);

	/**
	 * 方法描述：
	 * 
	 * @param picLicense
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午2:25:13
	 */
	PicPath getPathById(Integer picLicense);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdwLog
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午6:07:36
	 */
	void saveLog(DdcHyxhSsdwLog ddcHyxhSsdwLog);

	/**
	 * 方法描述：
	 * 
	 * @param hyxhzh
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月14日 下午8:33:56
	 */
	List<DdcHyxhSsdw> getAllCompany(String hyxhzh);

	/**
	 * 方法描述：
	 * 
	 * @param zzjgdmzh
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 上午10:07:41
	 */
	DdcHyxhSsdw queryByProperties(String zzjgdmzh);

	/**
	 * 方法描述：
	 * 
	 * @param dId
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月1日 下午5:30:33
	 */
	void deleteCompanyById(long dId);

	/**
	 * 方法描述：
	 * 
	 * @param sql
	 * @param page
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月9日 下午5:02:35
	 */
	Map<String, Object> getBySpringSql(String sql, Page page);

	/**
	 * 方法描述：
	 * 
	 * @param hyxhzh
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月14日 下午2:12:58
	 */
	DdcHyxhBase getHyxhZhByCode(String hyxhzh);

}
