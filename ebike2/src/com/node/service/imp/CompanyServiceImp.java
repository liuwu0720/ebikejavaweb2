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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDcHyxhSsdwLogDao;
import com.node.dao.IDdcHyxhSsdwDao;
import com.node.dao.IPicPathDao;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwLog;
import com.node.model.PicPath;
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

	@Autowired
	IPicPathDao iPicPathDao;

	@Autowired
	IDcHyxhSsdwLogDao iDcHyxhSsdwLogDao;

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
		if (ddcHyxhSsdws != null && ddcHyxhSsdws.size() > 0) {
			return "单位名称【" + ddcHyxhSsdw.getDwmc() + "】重复";

		} else {
			return meesageString;
		}

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

}
