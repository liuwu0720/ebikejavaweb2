/**
 * 文件名：EbikeServiceImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月16日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service.imp;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDdcApprovalUserDao;
import com.node.dao.IDdcDaxxbDao;
import com.node.dao.IDdcDriverDao;
import com.node.dao.IDdcDriverTempDao;
import com.node.dao.IDdcFlowDao;
import com.node.dao.IDdcHyxhBasbDao;
import com.node.dao.IDdcHyxhBaseDao;
import com.node.dao.IDdcHyxhSsdwDao;
import com.node.dao.IDdcHyxhSsdwclsbDao;
import com.node.dao.IFileRecordDao;
import com.node.model.DdcDaxxb;
import com.node.model.DdcDriver;
import com.node.model.DdcDriverTemp;
import com.node.model.DdcFlow;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.service.IEbikeService;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.SystemConstants;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月16日 下午5:02:14
 */
@Service
public class EbikeServiceImp implements IEbikeService {
	@Autowired
	IDdcDaxxbDao iDdcDaxxbDao;

	@Autowired
	IDdcHyxhSsdwDao iDdcHyxhSsdwDao;

	@Autowired
	IDdcFlowDao iDdcFlowDao;

	@Autowired
	IDdcApprovalUserDao iDdcApprovalUserDao;

	@Autowired
	IDdcHyxhBasbDao iDdcHyxhBasbDao;

	@Autowired
	IDdcHyxhBaseDao iDdcHyxhBaseDao;
	@Autowired
	IDdcHyxhSsdwclsbDao iDdcHyxhSsdwclsbDao;
	@Autowired
	IFileRecordDao iFileRecordDao;

	@Autowired
	IDdcDriverDao iDdcDriverDao;

	@Autowired
	IDdcDriverTempDao iDdcDriverTempDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#queryByHql(com.node.util.HqlHelper)
	 */
	@Override
	public Map<String, Object> queryByHql(HqlHelper hql) {
		// TODO Auto-generated method stub
		return iDdcDaxxbDao.findAllByHqlHelp(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#findByProPerties(java.lang.String)
	 */
	@Override
	public String findByProPerties(String zzjgdmzh) {
		List<DdcHyxhSsdw> daxxbs = iDdcHyxhSsdwDao.findByProperty("zzjgdmzh",
				zzjgdmzh);
		if (daxxbs != null && daxxbs.size() > 0) {
			return daxxbs.get(0).getDwmc();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#queryBySpringSql(java.lang.String)
	 */
	@Override
	public Map<String, Object> queryBySpringSql(String sql, Page page) {
		// TODO Auto-generated method stub
		return iDdcDaxxbDao.getSpringSQL(sql, page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#queryBySpringHql(java.lang.String,
	 * com.node.util.Page)
	 */
	@Override
	public Map<String, Object> queryBySpringHql(String sql, Page p) {
		// TODO Auto-generated method stub
		return iDdcDaxxbDao.getSpringHql(sql, p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#getById(long)
	 */
	@Override
	public DdcDaxxb getById(long sbId) {
		// TODO Auto-generated method stub
		return iDdcDaxxbDao.get(sbId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#update(com.node.model.DdcDaxxb)
	 */
	@Override
	public void update(DdcDaxxb daxxb) {
		// TODO Auto-generated method stub
		iDdcDaxxbDao.updateCleanBefore(daxxb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#saveDdcFlow(com.node.model.DdcFlow)
	 */
	@Override
	public void saveDdcFlow(DdcFlow ddcFlow) {
		// TODO Auto-generated method stub
		iDdcFlowDao.save(ddcFlow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#getFlowById(long)
	 */
	@Override
	public DdcFlow getFlowById(long flowId) {

		return iDdcFlowDao.get(flowId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#updateDdcFlow(com.node.model.DdcFlow)
	 */
	@Override
	public void updateDdcFlow(DdcFlow ddcFlow) {
		// TODO Auto-generated method stub
		iDdcFlowDao.update(ddcFlow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#updateDdcHyxhSsdw(com.node.model.DdcHyxhSsdw
	 * )
	 */
	@Override
	public void updateDdcHyxhSsdw(DdcHyxhSsdw ddcHyxhSsdw) {
		// TODO Auto-generated method stub
		iDdcHyxhSsdwDao.update(ddcHyxhSsdw);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#findDdcDaxxbsByFlag(java.lang.String)
	 */
	@Override
	public List<DdcDaxxb> findDdcDaxxbsByFlag(String flag) {
		// TODO Auto-generated method stub
		return iDdcDaxxbDao.findByProperty("synFlag", flag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#findDriverByUserInfo(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<DdcDriver> findDriverByUserInfo(String userCode, String passWord) {
		String[] propertyNames = { "userCode", "userPassword" };
		Object[] values = { userCode, passWord };
		return iDdcDriverDao.findByPropertys(propertyNames, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#findDriverByUserCode(java.lang.String)
	 */
	@Override
	public List<DdcDriver> findDriverByUserCode(String userCode) {
		// TODO Auto-generated method stub
		return iDdcDriverDao.findByProperty("userCode", userCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#updateDdcDriver(com.node.model.DdcDriver)
	 */
	@Override
	public void updateDdcDriver(DdcDriver ddcDriver) {
		// TODO Auto-generated method stub
		iDdcDriverDao.updateCleanBefore(ddcDriver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#saveDdcHyxhBase(com.node.model.DdcHyxhBase
	 * )
	 */
	@Override
	public void saveDdcHyxhBase(DdcHyxhBase ddcHyxhBase) {
		// TODO Auto-generated method stub
		iDdcHyxhBaseDao.save(ddcHyxhBase);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#updateDdchyxhBase(com.node.model.DdcHyxhBase
	 * )
	 */
	@Override
	public void updateDdchyxhBase(DdcHyxhBase ddcHyxhBase) {
		// TODO Auto-generated method stub
		iDdcHyxhBaseDao.updateCleanBefore(ddcHyxhBase);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#getAllDDcHyxhBase()
	 */
	@Override
	public List<DdcHyxhBase> getAllDDcHyxhBase() {
		// TODO Auto-generated method stub
		return iDdcHyxhBaseDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#findDdcDriverTemp(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean findDdcDriverTemp(String vcUserName, String vcUserCard) {
		List<DdcDriverTemp> ddcDriverTemps = iDdcDriverTempDao.findByPropertys(
				new String[] { "vcUserName", "vcUserCard" }, new Object[] {
						vcUserName, vcUserCard });
		if(CollectionUtils.isNotEmpty(ddcDriverTemps)){
			return false;
		}else {
			return true;
		}
		
	}

	
		/* (non-Javadoc)
		 * @see com.node.service.IEbikeService#saveDriverTemp(com.node.model.DdcDriverTemp)
		 */
	@Override
	public void saveDriverTemp(DdcDriverTemp ddcDriverTemp) {
		// TODO Auto-generated method stub
		iDdcDriverTempDao.save(ddcDriverTemp);
	}

	
		/* (non-Javadoc)
		 * @see com.node.service.IEbikeService#updateDriverTemp(com.node.model.DdcDriverTemp)
		 */
	@Override
	public void updateDriverTemp(DdcDriverTemp ddcDriverTemp) {
		// TODO Auto-generated method stub
		iDdcDriverTempDao.update(ddcDriverTemp);
	}

	
		/* (non-Javadoc)
		 * @see com.node.service.IEbikeService#saveDdcDriver(com.node.model.DdcDriver)
		 */
	@Override
	public void saveDdcDriver(DdcDriver ddcDriver2) {
		List<DdcDriver> oldDdcDrivers = iDdcDriverDao.findByPropertys(new String[]{
			"jsrxm","sfzhm"	
		}, new Object[]{
			ddcDriver2.getJsrxm(),ddcDriver2.getSfzhm()	
		});
		List<DdcDriverTemp> ddcDriverTemps = iDdcDriverTempDao.findByPropertys(new String[]{
				"vcUserName","vcUserCard"	
			}, new Object[]{
				ddcDriver2.getJsrxm(),ddcDriver2.getSfzhm()	
			});
		if(CollectionUtils.isNotEmpty(ddcDriverTemps)){
			ddcDriver2.setUserStatus(1);//驾驶人状态0未认证 1实名认证 2星级用户
		}else {
			ddcDriver2.setUserStatus(0);
		}
		if(CollectionUtils.isNotEmpty(oldDdcDrivers)){
			ddcDriver2.setId(oldDdcDrivers.get(0).getId());
			ddcDriver2.setDaid(oldDdcDrivers.get(0).getDaid());
			ddcDriver2.setDabh(oldDdcDrivers.get(0).getDabh());
			ddcDriver2.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
			ddcDriver2.setUserPassword(oldDdcDrivers.get(0).getUserPassword());
			ddcDriver2.setIlleagalTimes(oldDdcDrivers.get(0).getIlleagalTimes());
			ddcDriver2.setUserStatus(oldDdcDrivers.get(0).getUserStatus());
			/*ddcDriver2.setVcUserImg(oldDdcDrivers.get(0).getVcUserImg());
			ddcDriver2.setVcUserWorkImg(oldDdcDrivers.get(0).getVcUserWorkImg());*/
			iDdcDriverDao.updateCleanBefore(ddcDriver2);
		}else {
		
			iDdcDriverDao.save(ddcDriver2);
		}
		
	}

}
