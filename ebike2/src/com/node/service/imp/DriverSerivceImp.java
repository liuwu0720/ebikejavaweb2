/**
  * 文件名：DriverSerivceImp.java
  * 版本信息：Version 1.0
  * 日期：2016年6月16日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.service.imp;

import java.util.List;
import java.util.Map;

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
import com.node.service.IDriverSerivce;
import com.node.util.HqlHelper;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月16日 下午2:21:18 
 */
@Service
public class DriverSerivceImp implements IDriverSerivce{

	@Autowired
	IDdcDriverDao iDdcDriverDao;
	
	@Autowired
	IDdcHyxhBaseDao iDdcHyxhBaseDao;
	
	@Autowired
	IDdcHyxhSsdwDao iDdcHyxhSsdwDao;
	
	@Autowired
	IDdcHyxhSsdwclsbDao iDdcHyxhSsdwclsbDao;
	
	@Autowired
	IDdcDaxxbDao iDdcDaxxbDao;
	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#getAllDrivers()
		 */
	@Override
	public List<DdcDriver> getAllDrivers() {
		// TODO Auto-generated method stub
		return null;
	}

	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#queryByHql(com.node.util.HqlHelper)
		 */
	@Override
	public Map<String, Object> queryByHql(HqlHelper hql) {
		// TODO Auto-generated method stub
		return iDdcDriverDao.findAllByHqlHelp(hql);
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#getDriverById(long)
		 */
	@Override
	public DdcDriver getDriverById(long driverId) {
		// TODO Auto-generated method stub
		return iDdcDriverDao.get(driverId);
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#getHyxhNameByHyxhzh(java.lang.String)
		 */
	@Override
	public String getHyxhNameByHyxhzh(String hyxhzh) {
		List<DdcHyxhBase> ddcHyxhBases = iDdcHyxhBaseDao.findByProperty("hyxhzh", hyxhzh);
		if(CollectionUtils.isNotEmpty(ddcHyxhBases)){
			return ddcHyxhBases.get(0).getHyxhmc();
		}else {
			return null;
		}
		
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#getDwmcById(java.lang.Integer)
		 */
	@Override
	public String getDwmcById(Long ssdwId) {
		DdcHyxhSsdw ddcHyxhSsdw = iDdcHyxhSsdwDao.get(Long.parseLong(ssdwId+""));
		return ddcHyxhSsdw.getDwmc();
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#findSameSfzhm(java.lang.String)
		 */
	@Override
	public String findSameSfzhm(String sfzhm) {

		List<DdcDriver> ddcDrivers = iDdcDriverDao.findByProperty("sfzhm", sfzhm);
		if(CollectionUtils.isNotEmpty(ddcDrivers)){
			return "身份证号码【"+sfzhm+"】重复";
		}else {
			return "success";
		}
		
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#saveDdcDriver(com.node.model.DdcDriver)
		 */
	@Override
	public void saveDdcDriver(DdcDriver ddcDriver) {
		// TODO Auto-generated method stub
		iDdcDriverDao.save(ddcDriver);
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#updateDdcDriver(com.node.model.DdcDriver)
		 */
	@Override
	public void updateDdcDriver(DdcDriver ddcDriver) {
		// TODO Auto-generated method stub
		iDdcDriverDao.updateCleanBefore(ddcDriver);
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#updateClsb(com.node.model.DdcDriver)
		 */
	@Override
	public void updateClsb(DdcDriver ddcDriver) {
		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs1 = iDdcHyxhSsdwclsbDao.findByProperty("sfzmhm1", ddcDriver.getSfzhm());
		if(CollectionUtils.isNotEmpty(ddcHyxhSsdwclsbs1)){
			for(DdcHyxhSsdwclsb ddcHyxhSsdwclsb:ddcHyxhSsdwclsbs1){
				ddcHyxhSsdwclsb.setJsrxm1(ddcDriver.getJsrxm());
				ddcHyxhSsdwclsb.setSfzmhm1(ddcDriver.getSfzhm());
				ddcHyxhSsdwclsb.setLxdh1(ddcDriver.getLxdh());
				ddcHyxhSsdwclsb.setVcUser1CardImg1(ddcDriver.getVcUserCardImg1());
				ddcHyxhSsdwclsb.setVcUser1CardImg2(ddcDriver.getVcUserCardImg2());
				ddcHyxhSsdwclsb.setVcUser1Img(ddcDriver.getVcUserImg());
				ddcHyxhSsdwclsb.setVcUser1WorkImg(ddcDriver.getVcUserWorkImg());
				iDdcHyxhSsdwclsbDao.updateCleanBefore(ddcHyxhSsdwclsb);
			}
		}
		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs2= iDdcHyxhSsdwclsbDao.findByProperty("sfzmhm2", ddcDriver.getSfzhm());
		if(CollectionUtils.isNotEmpty(ddcHyxhSsdwclsbs2)){
			for(DdcHyxhSsdwclsb ddcHyxhSsdwclsb:ddcHyxhSsdwclsbs2){
				ddcHyxhSsdwclsb.setJsrxm2(ddcDriver.getJsrxm());
				ddcHyxhSsdwclsb.setSfzmhm2(ddcDriver.getSfzhm());
				ddcHyxhSsdwclsb.setLxdh2(ddcDriver.getLxdh());
				ddcHyxhSsdwclsb.setVcUser2CardImg1(ddcDriver.getVcUserCardImg1());
				ddcHyxhSsdwclsb.setVcUser2CardImg2(ddcDriver.getVcUserCardImg2());
				ddcHyxhSsdwclsb.setVcUser2Img(ddcDriver.getVcUserImg());
				ddcHyxhSsdwclsb.setVcUser2WorkImg(ddcDriver.getVcUserWorkImg());
				iDdcHyxhSsdwclsbDao.updateCleanBefore(ddcHyxhSsdwclsb);
			}
		}
		
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#updateClsbByDriver(com.node.model.DdcHyxhSsdwclsb)
		 */
	@Override
	public void updateClsbByDriver(DdcHyxhSsdwclsb ddcHyxhSsdwclsb) {
		List<DdcDriver> ddcDrivers1 = iDdcDriverDao.findByProperty("sfzhm", ddcHyxhSsdwclsb.getSfzmhm1());
		List<DdcDriver> ddcDrivers2 = iDdcDriverDao.findByProperty("sfzhm", ddcHyxhSsdwclsb.getSfzmhm2());
		if(CollectionUtils.isNotEmpty(ddcDrivers1)){
			DdcDriver driver1 = ddcDrivers1.get(0);
			ddcHyxhSsdwclsb.setJsrxm1(driver1.getJsrxm());
			ddcHyxhSsdwclsb.setLxdh1(driver1.getLxdh());
			ddcHyxhSsdwclsb.setSfzmhm1(driver1.getSfzhm());
			ddcHyxhSsdwclsb.setXb1(driver1.getXb());
			ddcHyxhSsdwclsb.setVcUser1CardImg1(driver1.getVcUserCardImg1());
			ddcHyxhSsdwclsb.setVcUser1CardImg2(driver1.getVcUserCardImg2());
			ddcHyxhSsdwclsb.setVcUser1Img(driver1.getVcUserImg());
			ddcHyxhSsdwclsb.setVcUser1WorkImg(driver1.getVcUserWorkImg());
		}else {
			ddcHyxhSsdwclsb.setJsrxm1(null);
			ddcHyxhSsdwclsb.setSfzmhm1(null);
			ddcHyxhSsdwclsb.setLxdh1(null);
			ddcHyxhSsdwclsb.setVcUser1CardImg1(null);
			ddcHyxhSsdwclsb.setVcUser1CardImg2(null);
			ddcHyxhSsdwclsb.setVcUser1Img(null);
			ddcHyxhSsdwclsb.setVcUser1WorkImg(null);
		}
		if(CollectionUtils.isNotEmpty(ddcDrivers2)){
			DdcDriver driver2 = ddcDrivers2.get(0);
			ddcHyxhSsdwclsb.setJsrxm2(driver2.getJsrxm());
			ddcHyxhSsdwclsb.setLxdh2(driver2.getLxdh());
			ddcHyxhSsdwclsb.setSfzmhm2(driver2.getSfzhm());
			ddcHyxhSsdwclsb.setXb2(driver2.getXb());
			ddcHyxhSsdwclsb.setVcUser2CardImg1(driver2.getVcUserCardImg1());
			ddcHyxhSsdwclsb.setVcUser2CardImg2(driver2.getVcUserCardImg2());
			ddcHyxhSsdwclsb.setVcUser2Img(driver2.getVcUserImg());
			ddcHyxhSsdwclsb.setVcUser2WorkImg(driver2.getVcUserWorkImg());
		}else {
			ddcHyxhSsdwclsb.setJsrxm2(null);
			ddcHyxhSsdwclsb.setSfzmhm2(null);
			ddcHyxhSsdwclsb.setLxdh2(null);
			ddcHyxhSsdwclsb.setVcUser2CardImg1(null);
			ddcHyxhSsdwclsb.setVcUser2CardImg2(null);
			ddcHyxhSsdwclsb.setVcUser2Img(null);
			ddcHyxhSsdwclsb.setVcUser2WorkImg(null);
		}
		
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#findIfdelete(com.node.model.DdcDriver)
		 */
	@Override
	public String findIfdelete(DdcDriver ddcDriver) {
		String message = "success";
		List<DdcDaxxb> daxxbs1 = iDdcDaxxbDao.findByProperty("sfzmhm1", ddcDriver.getSfzhm());
		if(CollectionUtils.isNotEmpty(daxxbs1)){
			message = "司机【"+ddcDriver.getJsrxm()+"】名下有备案车辆，如果删除该司机请先更换驾驶人";
		}
		List<DdcDaxxb> daxxbs2 = iDdcDaxxbDao.findByProperty("sfzmhm2", ddcDriver.getSfzhm());
		if(CollectionUtils.isNotEmpty(daxxbs2)){
			message = "司机【"+ddcDriver.getJsrxm()+"】名下有备案车辆，如果删除该司机请先更换驾驶人";
		}
		
		
		return message;
	}


	
		/* (non-Javadoc)
		 * @see com.node.service.IDriverSerivce#updateClsbDeleteByDriver(com.node.model.DdcDriver)
		 */
	@Override
	public String updateClsbDeleteByDriver(DdcDriver ddcDriver) {
		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs1 = iDdcHyxhSsdwclsbDao.findByProperty("sfzmhm1", ddcDriver.getSfzhm());
		String message = "success";
		if(CollectionUtils.isNotEmpty(ddcHyxhSsdwclsbs1)){
			for(DdcHyxhSsdwclsb ddcHyxhSsdwclsb:ddcHyxhSsdwclsbs1){
				if(ddcHyxhSsdwclsb.getSlIndex()>0){
					message="该司机有正在申报的车辆，且已经由协会审批通过，不能再删除，流水号【"+ddcHyxhSsdwclsb.getLsh()+"】";
					break;
				}
				ddcHyxhSsdwclsb.setJsrxm1(null);
				ddcHyxhSsdwclsb.setSfzmhm1(null);
				ddcHyxhSsdwclsb.setLxdh1(null);
				ddcHyxhSsdwclsb.setVcUser1CardImg1(null);
				ddcHyxhSsdwclsb.setVcUser1CardImg2(null);
				ddcHyxhSsdwclsb.setVcUser1Img(null);
				ddcHyxhSsdwclsb.setVcUser1WorkImg(null);
				iDdcHyxhSsdwclsbDao.updateCleanBefore(ddcHyxhSsdwclsb);
			}
		}
		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs2= iDdcHyxhSsdwclsbDao.findByProperty("sfzmhm2", ddcDriver.getSfzhm());
		if(CollectionUtils.isNotEmpty(ddcHyxhSsdwclsbs2)){
			for(DdcHyxhSsdwclsb ddcHyxhSsdwclsb:ddcHyxhSsdwclsbs2){
				if(ddcHyxhSsdwclsb.getSlIndex()>0){
					message="该司机有正在申报的车辆，且已经由协会审批通过，不能再删除，流水号【"+ddcHyxhSsdwclsb.getLsh()+"】";
					break;
				}
				ddcHyxhSsdwclsb.setJsrxm2(null);
				ddcHyxhSsdwclsb.setSfzmhm2(null);
				ddcHyxhSsdwclsb.setLxdh2(null);
				ddcHyxhSsdwclsb.setVcUser2CardImg1(null);
				ddcHyxhSsdwclsb.setVcUser2CardImg2(null);
				ddcHyxhSsdwclsb.setVcUser2Img(null);
				ddcHyxhSsdwclsb.setVcUser2WorkImg(null);
				iDdcHyxhSsdwclsbDao.updateCleanBefore(ddcHyxhSsdwclsb);
			}
		}
		return message;
	}
	
}
