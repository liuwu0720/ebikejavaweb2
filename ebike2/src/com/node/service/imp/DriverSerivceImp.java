/**
  * 文件名：DriverSerivceImp.java
  * 版本信息：Version 1.0
  * 日期：2016年6月16日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.service.imp;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDdcDriverDao;
import com.node.dao.IDdcHyxhBaseDao;
import com.node.dao.IDdcHyxhSsdwDao;
import com.node.model.DdcDriver;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.service.IDriverSerivce;
import com.node.util.HqlHelper;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月16日 下午2:21:18 
 */
@Service
public class DriverSerivceImp implements IDriverSerivce{

	@Autowired
	IDdcDriverDao iDdcDriverDao;
	
	@Autowired
	IDdcHyxhBaseDao iDdcHyxhBaseDao;
	
	@Autowired
	IDdcHyxhSsdwDao iDdcHyxhSsdwDao;
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#getAllDrivers()
		 */
	@Override
	public List<DdcDriver> getAllDrivers() {
		// TODO Auto-generated method stub
		return null;
	}

	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#queryByHql(com.node.util.HqlHelper)
		 */
	@Override
	public Map<String, Object> queryByHql(HqlHelper hql) {
		// TODO Auto-generated method stub
		return iDdcDriverDao.findAllByHqlHelp(hql);
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#getDriverById(long)
		 */
	@Override
	public DdcDriver getDriverById(long driverId) {
		// TODO Auto-generated method stub
		return iDdcDriverDao.get(driverId);
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#getHyxhNameByHyxhzh(java.lang.String)
		 */
	@Override
	public String getHyxhNameByHyxhzh(String hyxhzh) {
		List<DdcHyxhBase> ddcHyxhBases = iDdcHyxhBaseDao.findByProperty("hyxhzh", hyxhzh);
		if(CollectionUtils.isNotEmpty(ddcHyxhBases)){
			return ddcHyxhBases.get(0).getHyxhmc();
		}else {
			return null;
		}
		
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#getDwmcById(java.lang.Integer)
		 */
	@Override
	public String getDwmcById(Long ssdwId) {
		DdcHyxhSsdw ddcHyxhSsdw = iDdcHyxhSsdwDao.get(Long.parseLong(ssdwId+""));
		return ddcHyxhSsdw.getDwmc();
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#findSameSfzhm(java.lang.String)
		 */
	@Override
	public String findSameSfzhm(String sfzhm) {

		List<DdcDriver> ddcDrivers = iDdcDriverDao.findByProperty("sfzhm", sfzhm);
		if(CollectionUtils.isNotEmpty(ddcDrivers)){
			return "身份证号码【"+sfzhm+"】重复";
		}else {
			return "success";
		}
		
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#saveDdcDriver(com.node.model.DdcDriver)
		 */
	@Override
	public void saveDdcDriver(DdcDriver ddcDriver) {
		// TODO Auto-generated method stub
		iDdcDriverDao.save(ddcDriver);
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#updateDdcDriver(com.node.model.DdcDriver)
		 */
	@Override
	public void updateDdcDriver(DdcDriver ddcDriver) {
		// TODO Auto-generated method stub
		iDdcDriverDao.updateCleanBefore(ddcDriver);
	}
	
}
