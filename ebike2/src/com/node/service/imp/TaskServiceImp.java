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

import com.node.dao.IDdcDriverDao;
import com.node.dao.IDdcHyxhSsdwclsbDao;
import com.node.model.DdcDriver;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.service.ITaskService;


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
		// TODO Auto-generated method stub
		return iDdcDriverDao.findAll();
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

}
