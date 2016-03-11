/**
 * 文件名：UserServiceImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.node.dao.IDdcHyxhBaseDao;
import com.node.dao.IHyxhMenuDao;
import com.node.model.DdcHyxhBase;
import com.node.model.HyxhMenu;
import com.node.service.IUserService;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月11日 下午2:27:12
 */
@Service
public class UserServiceImp implements IUserService {
	@Autowired
	IDdcHyxhBaseDao iDdcHyxhBaseDao;

	@Autowired
	IHyxhMenuDao iHyxhMenuDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IUserService#getByUserAccount(java.lang.String)
	 */
	@Override
	public DdcHyxhBase getByUserAccount(String cuser) {
		List<DdcHyxhBase> ddcHyxhBases = iDdcHyxhBaseDao.findByProperty(
				"hyxhzh", cuser);
		if (CollectionUtils.isEmpty(ddcHyxhBases)) {
			return null;
		} else {
			return ddcHyxhBases.get(0);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IUserService#getAllMenus()
	 */
	@Override
	public List<HyxhMenu> getAllMenus() {
		List<HyxhMenu> hyxhMenus = iHyxhMenuDao.findAll();
		return hyxhMenus;
	}
}
