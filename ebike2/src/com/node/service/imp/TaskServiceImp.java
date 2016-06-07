/**
  * 文件名：TaskServiceImp.java
  * 版本信息：Version 1.0
  * 日期：2016年6月7日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDdcHyxhSsdwclsbDao;
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
	
		/* (non-Javadoc)
		 * @see com.node.service.ITaskService#findAllClsbs()
		 */
	@Override
	public List<DdcHyxhSsdwclsb> findAllClsbs() {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwclsbDao.findAll();
	}

}
