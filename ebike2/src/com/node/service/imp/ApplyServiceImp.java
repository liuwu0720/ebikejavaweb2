/**
 * 文件名：ApplyServiceImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月14日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDdcApprovalUserDao;
import com.node.dao.IDdcDaxxbDao;
import com.node.dao.IDdcDriverTempDao;
import com.node.dao.IDdcFlowDao;
import com.node.dao.IDdcHmdDao;
import com.node.dao.IDdcHyxhBasbDao;
import com.node.dao.IDdcHyxhSsdwDao;
import com.node.dao.IDdcHyxhSsdwclsbDao;
import com.node.dao.IDdcHyxhSsdwclsbLogDao;
import com.node.dao.IDdcSjzdDao;
import com.node.model.DdcApproveUser;
import com.node.model.DdcDaxxb;
import com.node.model.DdcDriverTemp;
import com.node.model.DdcFlow;
import com.node.model.DdcHmd;
import com.node.model.DdcHyxhBasb;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.model.DdcHyxhSsdwclsbLog;
import com.node.model.DdcSjzd;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
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

	@Autowired
	IDdcHyxhBasbDao iDdcHyxhBasbDao;
	@Autowired
	IDdcHyxhSsdwDao iDdcHyxhSsdwDao;
	@Autowired
	IDdcApprovalUserDao iDdcApprovalUserDao;

	@Autowired
	ICompanyService iCompanyService;

	@Autowired
	IDdcFlowDao iDdcFlowDao;

	@Autowired
	IDdcDaxxbDao iDdcDaxxbDao;
	@Autowired
	IDdcDriverTempDao iDdcDriverTempDao;

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
	public String findHmd(String man1, String man2) {
		List<DdcHmd> ddcHmds1 = iDdcHmdDao.findByProperty("jsrxm", man1);
		List<DdcHmd> ddcHmds2 = iDdcHmdDao.findByProperty("jsrxm", man2);
		if (ddcHmds1 != null && ddcHmds1.size() > 0) {
			return "身份证【" + man1 + "】在黑名单里，不能申报";
		} else if (ddcHmds2 != null && ddcHmds2.size() > 0) {
			return "身份证【" + man2 + "】在黑名单里，不能申报";
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
		DdcHyxhSsdwclsb ddcHyxhSsdwclsb = iDdcHyxhSsdwclsbDao.get(sbId);
		String cysyName = findByProPerties("CSYS", ddcHyxhSsdwclsb.getCysy());

		ddcHyxhSsdwclsb.setCysyName(cysyName);// 车身颜色
		String xsqyName = findByProPerties("SSQY", ddcHyxhSsdwclsb.getXsqy());
		ddcHyxhSsdwclsb.setXsqyName(xsqyName);// 行驶区域
		// 申报单位
		if (StringUtils.isNotBlank(ddcHyxhSsdwclsb.getSsdwId())) {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
					.parseLong(ddcHyxhSsdwclsb.getSsdwId()));
			if (ddcHyxhSsdw != null) {
				ddcHyxhSsdwclsb.setSsdwName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcHyxhSsdwclsb.setSsdwName(null);
			}
		}
		if (StringUtils.isBlank(ddcHyxhSsdwclsb.getSlyj())) {
			if (ddcHyxhSsdwclsb.getSlIndex() == 0) {
				ddcHyxhSsdwclsb.setNote("等待行业协会审批");
			} else {
				ddcHyxhSsdwclsb.setNote("等待交警审批");
			}
		} else if (ddcHyxhSsdwclsb.getSlyj().equals("1")) {
			ddcHyxhSsdwclsb.setNote("已拒绝");
		} else if (ddcHyxhSsdwclsb.getSlyj().equals("0")) {
			ddcHyxhSsdwclsb.setNote("已同意");
		}

		return iDdcHyxhSsdwclsbDao.get(sbId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getSjzdByDmlb(java.lang.String)
	 */
	@Override
	public List<DdcSjzd> getSjzdByDmlb(String string) {
		// TODO Auto-generated method stub
		return iDdcSjzdDao.findByProperty("dmlb", string);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getDdcHyxhBasbById(long)
	 */
	@Override
	public DdcHyxhBasb getDdcHyxhBasbById(long sbId) {
		// TODO Auto-generated method stub
		return iDdcHyxhBasbDao.get(sbId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IApplyService#saveDdcHyxhBasb(com.node.model.DdcHyxhBasb
	 * )
	 */
	@Override
	public void saveDdcHyxhBasb(DdcHyxhBasb ddcHyxhBasb) {
		// TODO Auto-generated method stub
		iDdcHyxhBasbDao.save(ddcHyxhBasb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getDdcHyxhSsdwById(java.lang.String)
	 */
	@Override
	public DdcHyxhSsdw getDdcHyxhSsdwById(String ssdwId) {
		// TODO Auto-generated method stub
		return iDdcHyxhSsdwDao.get(Long.parseLong(ssdwId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IApplyService#findApproveUsersByProperties(java.lang
	 * .String, java.lang.Long)
	 */
	@Override
	public List<DdcApproveUser> findApproveUsersByProperties(
			String approveTableName, Long id) {
		String[] propertyNames = { "approveTable", "approveTableid" };
		Object[] values = { approveTableName, id };
		List<DdcApproveUser> ddcApproveUsers = iDdcApprovalUserDao
				.findByPropertysOrderBy(propertyNames, values, "approveTime",
						"asc");
		if (ddcApproveUsers != null && ddcApproveUsers.size() > 0) {
			return ddcApproveUsers;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getDbyyList(java.lang.String)
	 */
	@Override
	public List<DdcSjzd> getDbyyList(String tbyy, String type) {
		if (StringUtils.isNotBlank(tbyy)) {
			String[] tbyyStrings = tbyy.split(",");
			List<DdcSjzd> allDdcSjzds = new ArrayList<>();
			for (String dmz : tbyyStrings) {
				List<DdcSjzd> ddcSjzds = iDdcSjzdDao.findByPropertys(
						new String[] { "dmz", "dmlb" }, new Object[] { dmz,
								type });
				if (ddcSjzds != null && ddcSjzds.size() > 0) {
					allDdcSjzds.addAll(ddcSjzds);
				}

			}

			return allDdcSjzds;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#saveDdcApproveUser(com.node.model.
	 * DdcApproveUser)
	 */
	@Override
	public void saveDdcApproveUser(DdcApproveUser ddcApproveUser) {
		// TODO Auto-generated method stub
		iDdcApprovalUserDao.save(ddcApproveUser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getDdcFlowById(long)
	 */
	@Override
	public DdcFlow getDdcFlowById(long id) {
		// TODO Auto-generated method stub
		return iDdcFlowDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#getDdcDaxxbByDabh(java.lang.String)
	 */
	@Override
	public DdcDaxxb getDdcDaxxbByDabh(String dabh) {
		List<DdcDaxxb> daxxbs = iDdcDaxxbDao.findByProperty("dabh", dabh);
		if (daxxbs != null && daxxbs.size() > 0) {
			return daxxbs.get(0);
		} else {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IApplyService#findApproveUsersByLsh(java.lang.String)
	 */
	@Override
	public List<DdcApproveUser> findApproveUsersByLsh(String lsh) {
		// TODO Auto-generated method stub
		return iDdcApprovalUserDao.findByPropertyOrderBy("lsh", lsh,
				"approveTime");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IApplyService#findIsValid(com.node.model.DdcHyxhSsdwclsb
	 * )
	 */
	@Override
	public String findIsValid(DdcHyxhSsdwclsb ddcHyxhSsdwclsb) {
		String message = "success";
		List<DdcDriverTemp> ddcDriverTemps1 = iDdcDriverTempDao
				.findByPropertys(new String[] { "vcUserName", "vcUserCard" },
						new Object[] { ddcHyxhSsdwclsb.getJsrxm1(),
								ddcHyxhSsdwclsb.getSfzmhm1() });

		if (CollectionUtils.isEmpty(ddcDriverTemps1)) {
			message = "驾驶人1姓名【" + ddcHyxhSsdwclsb.getJsrxm1() + "】和身份证号码【"
					+ ddcHyxhSsdwclsb.getSfzmhm1() + "】未通过实名验证";
		}
		if (StringUtils.isNotBlank(ddcHyxhSsdwclsb.getJsrxm2())) {
			List<DdcDriverTemp> ddcDriverTemps2 = iDdcDriverTempDao
					.findByPropertys(
							new String[] { "vcUserName", "vcUserCard" },
							new Object[] { ddcHyxhSsdwclsb.getJsrxm2(),
									ddcHyxhSsdwclsb.getSfzmhm2() });
			if (CollectionUtils.isEmpty(ddcDriverTemps2)) {
				message = "驾驶人2姓名【" + ddcHyxhSsdwclsb.getJsrxm2() + "】和身份证号码【"
						+ ddcHyxhSsdwclsb.getSfzmhm2() + "】未通过实名验证";
			}
			if (StringUtils.isBlank(ddcHyxhSsdwclsb.getVcUser2WorkImg())) {
				message = "驾驶人2在职证明或居住证为空";
			}
		}
		if (StringUtils.isBlank(ddcHyxhSsdwclsb.getVcEbikeImg())) {
			message = "车辆照片为空";
		}
		if (StringUtils.isBlank(ddcHyxhSsdwclsb.getVcEbikeInsuranceImg())) {
			message = "投保凭证为空";
		}
		if (StringUtils.isBlank(ddcHyxhSsdwclsb.getVcEbikeInvoiceImg())) {
			message = "购车发票为空";
		}
		if (StringUtils.isBlank(ddcHyxhSsdwclsb.getVcQualifiedImg())) {
			message = "车辆合格证为空";
		}
		if (StringUtils.isBlank(ddcHyxhSsdwclsb.getVcUser1WorkImg())) {
			message = "驾驶人1 在职证明或居住证为空";
		}

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IApplyService#findIsValidByDaxxb(com.node.model.DdcDaxxb
	 * )
	 */
	@Override
	public String findIsValidByDaxxb(DdcDaxxb newDaxxb) {
		String message = "success";
		List<DdcDriverTemp> ddcDriverTemps1 = iDdcDriverTempDao
				.findByPropertys(
						new String[] { "vcUserName", "vcUserCard" },
						new Object[] { newDaxxb.getJsrxm1(),
								newDaxxb.getSfzmhm1() });

		if (CollectionUtils.isEmpty(ddcDriverTemps1)) {
			message = "驾驶人1姓名【" + newDaxxb.getJsrxm1() + "】和身份证号码【"
					+ newDaxxb.getSfzmhm1() + "】未通过实名验证";
		}
		if (StringUtils.isNotBlank(newDaxxb.getJsrxm2())) {
			List<DdcDriverTemp> ddcDriverTemps2 = iDdcDriverTempDao
					.findByPropertys(
							new String[] { "vcUserName", "vcUserCard" },
							new Object[] { newDaxxb.getJsrxm2(),
									newDaxxb.getSfzmhm2() });
			if (CollectionUtils.isEmpty(ddcDriverTemps2)) {
				message = "驾驶人2姓名【" + newDaxxb.getJsrxm2() + "】和身份证号码【"
						+ newDaxxb.getSfzmhm2() + "】未通过实名验证";
			}
		}

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IApplyService#findSameDjh(java.lang.String)
	 */
	@Override
	public String findSameDjh(String djh) {
		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs = iDdcHyxhSsdwclsbDao
				.findByPropertys(new String[] { "djh", "nEnable" },
						new Object[] { djh, 1 });
		if (CollectionUtils.isEmpty(ddcHyxhSsdwclsbs)) {
			return "success";
		} else {
			return "电机号【" + djh + "】已经存在";
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IApplyService#getSlStatus(com.node.model.DdcHyxhSsdwclsb
	 * )
	 */
	@Override
	public int getSlStatus(DdcHyxhSsdwclsb ddcHyxhSsdwclsb) {
		if (StringUtils.isNotBlank(ddcHyxhSsdwclsb.getJsrxm1())
				&& StringUtils.isNotBlank(ddcHyxhSsdwclsb.getDjh())
				&& StringUtils.isNotBlank(ddcHyxhSsdwclsb.getPpxh())
				&& StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcEbikeImg())
				&& StringUtils.isNotBlank(ddcHyxhSsdwclsb
						.getVcEbikeInsuranceImg())
				&& StringUtils.isNotBlank(ddcHyxhSsdwclsb
						.getVcEbikeInvoiceImg())
						&& StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcUser1WorkImg())
				&& StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcQualifiedImg())) {
			if(StringUtils.isNotBlank(ddcHyxhSsdwclsb.getJsrxm2())){
				if(StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcUser2WorkImg())){
					return 0;
				}else{
					return -1;
				}
			}
			return 0;
			
			
		} else {
			return -1;
		}

	}
}
