/**
  * 文件名：TaskServiceImp.java
  * 版本信息：Version 1.0
  * 日期：2016年6月7日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.service.imp;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDdcDaxxbDao;
import com.node.dao.IDdcDriverDao;
import com.node.dao.IDdcHyxhBaseDao;
import com.node.dao.IDdcHyxhSsdwDao;
import com.node.dao.IDdcHyxhSsdwclsbDao;
import com.node.model.DdcDaxxb;
import com.node.model.DdcDriver;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.service.ITaskService;
import com.node.util.SystemConstants;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月7日 下午9:35:33 
 */
@Service
public class TaskServiceImp implements ITaskService{
	@Autowired
	IDdcHyxhSsdwclsbDao iDdcHyxhSsdwclsbDao;
	@Autowired
	IDdcDriverDao iDdcDriverDao;
	
	@Autowired
	IDdcDaxxbDao iDdcDaxxbDao;
	@Autowired
	IDdcHyxhSsdwDao iDdcHyxhSsdwDao;
	@Autowired
	IDdcHyxhBaseDao iDdcHyxhBaseDao;
	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#findAllClsbs()
		 */
	@Override
	public List<DdcHyxhSsdwclsb> findAllClsbs() {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwclsbDao.findAll();
	}

	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#findAllDriversNotValid()
		 */
	@Override
	public List<DdcDriver> findAllDriversNotValid() {
		List<DdcDriver> ddcDrivers = iDdcDriverDao.findByProperty("userStatus", 0);
		return ddcDrivers;
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#findClsbByDriver(com.node.model.DdcDriver)
		 */
	@Override
	public DdcHyxhSsdwclsb findClsbByDriver(DdcDriver ddcDriver) {
		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs = iDdcHyxhSsdwclsbDao.findByProperty("sfzmhm1", ddcDriver.getSfzhm());
		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs2 = iDdcHyxhSsdwclsbDao.findByProperty("sfzmhm2", ddcDriver.getSfzhm());
		if(CollectionUtils.isNotEmpty(ddcHyxhSsdwclsbs)){
			return ddcHyxhSsdwclsbs.get(0);
		}
		if(CollectionUtils.isNotEmpty(ddcHyxhSsdwclsbs2)){
			return ddcHyxhSsdwclsbs2.get(0);
		}
		return null;
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#getAllNewDaxxb()
		 */
	@Override
	public List<DdcDaxxb> getAllNewDaxxb() {
		// TODO Auto-generated method stub
		return null;
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#updateBySql(java.lang.String)
		 */
	@Override
	public int updateBySql(String sql2) {
		// TODO Auto-generated method stub
	   int row=	iDdcDriverDao.updateBySql(sql2);
	   return row;
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#updateDdcDriver(com.node.model.DdcDriver)
		 */
	@Override
	public void updateDdcDriver(DdcDriver ddcDriver) {
		// TODO Auto-generated method stub
		iDdcDriverDao.update(ddcDriver);
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#findAllStartDrivers()
		 */
	@Override
	public List<DdcDriver> findAllStartDrivers() {
		List<DdcDriver> ddcDrivers = iDdcDriverDao.findByProperty("userStatus", 2);
		//List<DdcDriver> ddcDrivers2 = iDdcDriverDao.findByProperty("userStatus", 3);
		//List<DdcDriver> ddcDrivers3 = new ArrayList<DdcDriver>();
		//ddcDrivers3.addAll(ddcDrivers);
		//ddcDrivers3.addAll(ddcDrivers2);
		return ddcDrivers;
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#getAllDdcHyxhSsdws()
		 */
	@Override
	public List<DdcHyxhSsdw> getAllDdcHyxhSsdws() {
		List<DdcHyxhSsdw> ddcHyxhSsdws = iDdcHyxhSsdwDao.findByProperty("zt",SystemConstants.ENABLE_ZT);
		return ddcHyxhSsdws;
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#getObjectBySql(java.lang.String)
		 */
	@Override
	public int getObjectBySql(String sql) {
		Object object =  iDdcHyxhSsdwclsbDao.getDateBySQL(sql);
		if(object!=null){
			return Integer.parseInt(object.toString());
		}
		return 0;
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#getAllDdcHyxh()
		 */
	@Override
	public List<DdcHyxhBase> getAllDdcHyxh() {
		List<DdcHyxhBase> ddcHyxhBases = iDdcHyxhBaseDao.findAll();
		return ddcHyxhBases;
	}


}
