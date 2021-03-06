/**
 * 文件名：CompanyServiceImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service.imp;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDcHyxhSsdwLogDao;
import com.node.dao.IDdcDriverDao;
import com.node.dao.IDdcHyxhBaseDao;
import com.node.dao.IDdcHyxhSsdwDao;
import com.node.dao.IPicPathDao;
import com.node.model.DdcDriver;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwLog;
import com.node.model.PicPath;
import com.node.service.ICompanyService;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.SystemConstants;

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

	@Autowired
	IPicPathDao iPicPathDao;

	@Autowired
	IDcHyxhSsdwLogDao iDcHyxhSsdwLogDao;

	@Autowired
	IDdcHyxhBaseDao iDdcHyxhBaseDao;
	
	@Autowired
	IDdcDriverDao iDdcDriverDao;
	
	
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#queryInfoById(int)
	 */
	@Override
	public DdcHyxhSsdw queryInfoById(long dwId) {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwDao.get(dwId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.ICompanyService#queryIsSame(com.node.model.DdcHyxhSsdw)
	 */
	@Override
	public String queryIsSame(DdcHyxhSsdw ddcHyxhSsdw) {
		String meesageString = "success";
		List<DdcHyxhSsdw> ddcHyxhSsdws = iDdcHyxhSsdwDao.findByProperty("dwmc",
				ddcHyxhSsdw.getDwmc());
		List<DdcHyxhSsdw> ddcHyxhSsdws2 = iDdcHyxhSsdwDao.findByProperty(
				"userCode", ddcHyxhSsdw.getUserCode());
		if (ddcHyxhSsdws != null && ddcHyxhSsdws.size() > 0) {
			meesageString = "单位名称【" + ddcHyxhSsdw.getDwmc() + "】重复";

		}
		if (ddcHyxhSsdws2 != null && ddcHyxhSsdws2.size() > 0) {
			meesageString = "单位帐号【" + ddcHyxhSsdw.getUserCode() + "】重复";

		}

		return meesageString;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#save(com.node.model.DdcHyxhSsdw)
	 */
	@Override
	public void save(DdcHyxhSsdw ddcHyxhSsdw) {
		// TODO Auto-generated method stub
		iDdcHyxhSsdwDao.save(ddcHyxhSsdw);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#update(com.node.model.DdcHyxhSsdw)
	 */
	@Override
	public void update(DdcHyxhSsdw ddcHyxhSsdw) {
		// TODO Auto-generated method stub
		iDdcHyxhSsdwDao.updateCleanBefore(ddcHyxhSsdw);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#getPathById(java.lang.Integer)
	 */
	@Override
	public PicPath getPathById(Integer picLicense) {
		// TODO Auto-generated method stub
		return iPicPathDao.get(picLicense);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.ICompanyService#saveLog(com.node.model.DdcHyxhSsdwLog)
	 */
	@Override
	public void saveLog(DdcHyxhSsdwLog ddcHyxhSsdwLog) {
		// TODO Auto-generated method stub
		iDcHyxhSsdwLogDao.save(ddcHyxhSsdwLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#getAllCompany(java.lang.String)
	 */
	@Override
	public List<DdcHyxhSsdw> getAllCompany(String hyxhzh) {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwDao.findByProperty("hyxhzh", hyxhzh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#queryByProperties(java.lang.String)
	 */
	@Override
	public DdcHyxhSsdw queryByProperties(String zzjgdmzh) {
		List<DdcHyxhSsdw> ddcHyxhSsdws = iDdcHyxhSsdwDao.findByProperty(
				"zzjgdmzh", zzjgdmzh);
		if (ddcHyxhSsdws != null && ddcHyxhSsdws.size() > 0) {
			return ddcHyxhSsdws.get(0);

		} else {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#deleteCompanyById(long)
	 */
	@Override
	public void deleteCompanyById(long dId) {
		// TODO Auto-generated method stub
		iDdcHyxhSsdwDao.deleteByKey(dId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#getBySpringSql(java.lang.String,
	 * com.node.util.Page)
	 */
	@Override
	public Map<String, Object> getBySpringSql(String sql, Page page) {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwDao.getSpringSQL(sql, page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.ICompanyService#getHyxhZhByCode(java.lang.String)
	 */
	@Override
	public DdcHyxhBase getHyxhZhByCode(String hyxhzh) {
		List<DdcHyxhBase> ddcHyxhBases = iDdcHyxhBaseDao.findByProperty(
				"hyxhzh", hyxhzh);
		if (ddcHyxhBases != null && ddcHyxhBases.size() > 0) {
			return ddcHyxhBases.get(0);
		} else {
			return null;
		}

	}

	
		/* (non-Javadoc)
		 * @see com.node.service.ICompanyService#getDdcHyxhSsdwById(long)
		 */
	@Override
	public DdcHyxhSsdw getDdcHyxhSsdwById(long dId) {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwDao.get(dId);
	}

	
		/* (non-Javadoc)
		 * @see com.node.service.ICompanyService#getjsrStateBySfzhm(java.lang.String)
		 */
	@Override
	public String getjsrStateBySfzhm(String sfzmhm1) {
		List<DdcDriver> ddcDrivers = iDdcDriverDao.findByProperty("sfzhm", sfzmhm1);
		if(CollectionUtils.isNotEmpty(ddcDrivers)){
			DdcDriver ddcDriver = ddcDrivers.get(0);
			if(ddcDriver.getUserStatus()==0){
				return SystemConstants.USER_STATUS_0;
			}else if(ddcDriver.getUserStatus()==1){
				return SystemConstants.USER_STATUS_1;
			}else {
				return SystemConstants.USER_STATUS_2;
			}
		}else {
			return SystemConstants.USER_STATUS_0;
		}
		
	}

}
