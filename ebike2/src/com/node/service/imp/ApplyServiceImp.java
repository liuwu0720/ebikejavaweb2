/**
 * 文件名：ApplyServiceImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月14日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDdcHyxhSsdwclsbDao;
import com.node.dao.IDdcSjzdDao;
import com.node.model.DdcSjzd;
import com.node.service.IApplyService;
import com.node.util.HqlHelper;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月14日 下午5:27:16
 */
@Service
public class ApplyServiceImp implements IApplyService {

	@Autowired
	IDdcHyxhSsdwclsbDao iDdcHyxhSsdwclsbDao;

	@Autowired
	IDdcSjzdDao iDdcSjzdDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#queryByHql(com.node.util.HqlHelper)
	 */
	@Override
	public Map<String, Object> queryByHql(HqlHelper hql) {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwclsbDao.findAllByHqlHelp(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#findByProPerties(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String findByProPerties(String string, String cysy) {
		List<DdcSjzd> ddcSjzds = iDdcSjzdDao.findByPropertys(new String[] {
				"dmz", "dmlb" }, new Object[] { cysy, string });
		if (ddcSjzds != null && ddcSjzds.size() > 0) {
			return ddcSjzds.get(0).getDmms1();
		} else {
			return null;
		}
	}

}
