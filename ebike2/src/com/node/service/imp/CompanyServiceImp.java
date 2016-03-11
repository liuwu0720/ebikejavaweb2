/**
 * 文件名：CompanyServiceImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDdcHyxhSsdwDao;
import com.node.service.ICompanyService;
import com.node.util.HqlHelper;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月11日 下午10:11:31
 */
@Service
public class CompanyServiceImp implements ICompanyService {
	@Autowired
	IDdcHyxhSsdwDao iDdcHyxhSsdwDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#queryByHql(com.node.util.HqlHelper)
	 */
	@Override
	public Map<String, Object> queryByHql(HqlHelper hql) {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwDao.findAllByHqlHelp(hql);
	}

}
