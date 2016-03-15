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

import com.node.dao.IDdcHmdDao;
import com.node.dao.IDdcHyxhSsdwclsbDao;
import com.node.dao.IDdcHyxhSsdwclsbLogDao;
import com.node.dao.IDdcSjzdDao;
import com.node.model.DdcHmd;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.model.DdcHyxhSsdwclsbLog;
import com.node.model.DdcSjzd;
import com.node.service.IApplyService;
import com.node.util.HqlHelper;
import com.node.util.Page;

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

	@Autowired
	IDdcHmdDao iDdcHmdDao;

	@Autowired
	IDdcHyxhSsdwclsbLogDao iDdcHyxhSsdwclsbLogDao;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getAllByProperties(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<DdcSjzd> getAllByProperties(String dmlb, String value) {
		// TODO Auto-generated method stub
		return iDdcSjzdDao.findByProperty(dmlb, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#saveDdcHyxhSsdwclsb(com.node.model.
	 * DdcHyxhSsdwclsb)
	 */
	@Override
	public void saveDdcHyxhSsdwclsb(DdcHyxhSsdwclsb ddcHyxhSsdwclsb) {

		iDdcHyxhSsdwclsbDao.save(ddcHyxhSsdwclsb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#updateDdcHyxhSsdwclsb(com.node.model.
	 * DdcHyxhSsdwclsb)
	 */
	@Override
	public void updateDdcHyxhSsdwclsb(DdcHyxhSsdwclsb ddcHyxhSsdwclsb) {
		// TODO Auto-generated method stub
		iDdcHyxhSsdwclsbDao.updateCleanBefore(ddcHyxhSsdwclsb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getBySpringSql(java.lang.String,
	 * com.node.util.Page)
	 */
	@Override
	public Map<String, Object> getBySpringSql(String sql, Page page) {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = iDdcHyxhSsdwclsbDao
				.getSpringSQL(sql, page);
		return resMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getDateBySQL(java.lang.String)
	 */
	@Override
	public Object getDateBySQL(String sql) {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwclsbDao.getDateBySQL(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IApplyService#saveDdcHyxhSsdwclsbLog(com.node.model.
	 * DdcHyxhSsdwclsbLog)
	 */
	@Override
	public void saveDdcHyxhSsdwclsbLog(DdcHyxhSsdwclsbLog ddcHyxhSsdwclsbLog) {
		// TODO Auto-generated method stub
		iDdcHyxhSsdwclsbLogDao.save(ddcHyxhSsdwclsbLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IApplyService#findHmd(com.node.model.DdcHyxhSsdwclsb)
	 */
	@Override
	public String findHmd(DdcHyxhSsdwclsb ddcHyxhSsdwclsb) {
		List<DdcHmd> ddcHmds1 = iDdcHmdDao.findByProperty("sfzhm",
				ddcHyxhSsdwclsb.getSfzmhm1());
		List<DdcHmd> ddcHmds2 = iDdcHmdDao.findByProperty("sfzhm",
				ddcHyxhSsdwclsb.getSfzmhm2());
		if (ddcHmds1 != null && ddcHmds1.size() > 0) {
			return "身份证【" + ddcHyxhSsdwclsb.getSfzmhm1() + "】在黑名单里，不能申报";
		} else if (ddcHmds2 != null && ddcHmds2.size() > 0) {
			return "身份证【" + ddcHyxhSsdwclsb.getSfzmhm2() + "】在黑名单里，不能申报";
		} else {
			return "success";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getDdcHyxhSsdwclsbById(long)
	 */
	@Override
	public DdcHyxhSsdwclsb getDdcHyxhSsdwclsbById(long sbId) {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwclsbDao.get(sbId);
	}

}
