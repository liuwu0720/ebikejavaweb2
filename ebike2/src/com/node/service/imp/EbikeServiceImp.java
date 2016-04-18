/**
 * 文件名：EbikeServiceImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月16日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.node.dao.IDdcApprovalUserDao;
import com.node.dao.IDdcDaxxbDao;
import com.node.dao.IDdcDaxxbLogDao;
import com.node.dao.IDdcFlowDao;
import com.node.dao.IDdcHyxhBasbDao;
import com.node.dao.IDdcHyxhBaseDao;
import com.node.dao.IDdcHyxhSsdwDao;
import com.node.dao.IDdcHyxhSsdwclsbDao;
import com.node.dao.IFileRecordDao;
import com.node.model.DdcApproveUser;
import com.node.model.DdcDaxxb;
import com.node.model.DdcDaxxbLog;
import com.node.model.DdcFlow;
import com.node.model.DdcHyxhBasb;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.model.FileRecord;
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
	IDdcDaxxbLogDao iDdcDaxxbLogDao;

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
	 * @see
	 * com.node.service.IEbikeService#saveDdcDaxxbLog(com.node.model.DdcDaxxbLog
	 * )
	 */
	@Override
	public void saveDdcDaxxbLog(DdcDaxxbLog daxxbLog) {
		// TODO Auto-generated method stub
		iDdcDaxxbLogDao.save(daxxbLog);
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
	 * com.node.service.IEbikeService#createDaxxbExcel(jxl.write.WritableCellFormat
	 * , jxl.write.WritableCellFormat, jxl.write.WritableSheet)
	 */
	@Override
	public void createDaxxbExcel(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws) throws Exception {
		Label label = new Label(0, 0, "DDCDAXXB", wcfFC);
		ws.mergeCells(0, 0, 6, 0);
		ws.addCell(label);
		int j0 = 0;
		ws.addCell(new Label(j0, 2, "ID", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "DABH", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "YWLX", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "YWYY", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "HYXHZH", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SSDWID", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "CPHM", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "PPXH", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "CYSY", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "DJH", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "JTZZ", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "JSRXM1", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "XB1", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SFZMHM1", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "LXDH1", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "JSRXM2", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "XB2", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SFZMHM2", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "LXDH2", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "XSQY", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "BZ", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "ZT", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SYRQ", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SLZL", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SLYJ", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SLBZ", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SLR", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SLRQ", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SLBM", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "GDYJ", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "TBYY", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "GDBZ", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "GDR", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "GDRQ", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "GDBM", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "SYN_FLAG", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "VC_USER1IMG", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "VC_USER2IMG", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "VC_EBIKE_IMG", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "VC_USER1_CARDIMG1", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "VC_USER1_CARDIMG2", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "VC_USER2_CARDIMG1", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "VC_USER2_CARDIMG2", wcfFC2));
		ws.addCell(new Label(j0 += 1, 2, "VC_EBIKE_INVOICE", wcfFC2));
		List<DdcDaxxb> daxxbs = iDdcDaxxbDao.findByProperty("synFlag", "UP");
		int i = 3;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (DdcDaxxb daxxb : daxxbs) {
			int j = 0;
			ws.addCell(new Label(j, i, String.valueOf(daxxb.getId())));
			ws.addCell(new Label(j += 1, i, daxxb.getDabh()));
			ws.addCell(new Label(j += 1, i, daxxb.getYwlx()));
			ws.addCell(new Label(j += 1, i, daxxb.getYwyy()));
			ws.addCell(new Label(j += 1, i, daxxb.getHyxhzh()));
			ws.addCell(new Label(j += 1, i, daxxb.getSsdwId()));
			ws.addCell(new Label(j += 1, i, daxxb.getCphm()));
			ws.addCell(new Label(j += 1, i, daxxb.getPpxh()));
			ws.addCell(new Label(j += 1, i, daxxb.getCysy()));
			ws.addCell(new Label(j += 1, i, daxxb.getDjh()));
			ws.addCell(new Label(j += 1, i, daxxb.getJtzz()));
			ws.addCell(new Label(j += 1, i, daxxb.getJsrxm1()));
			ws.addCell(new Label(j += 1, i, daxxb.getXb1()));
			ws.addCell(new Label(j += 1, i, daxxb.getSfzmhm1()));
			ws.addCell(new Label(j += 1, i, daxxb.getLxdh1()));
			ws.addCell(new Label(j += 1, i, daxxb.getJsrxm2()));
			ws.addCell(new Label(j += 1, i, daxxb.getXb2()));
			ws.addCell(new Label(j += 1, i, daxxb.getSfzmhm2()));
			ws.addCell(new Label(j += 1, i, daxxb.getLxdh2()));
			ws.addCell(new Label(j += 1, i, daxxb.getXsqy()));
			ws.addCell(new Label(j += 1, i, daxxb.getBz()));
			ws.addCell(new Label(j += 1, i, daxxb.getZt()));
			ws.addCell(new Label(j += 1, i, sdf.format(daxxb.getSyrq())));
			ws.addCell(new Label(j += 1, i, daxxb.getSlzl()));
			ws.addCell(new Label(j += 1, i, daxxb.getSlyj()));
			ws.addCell(new Label(j += 1, i, daxxb.getSlbz()));
			ws.addCell(new Label(j += 1, i, daxxb.getSlr()));
			if (daxxb.getSlrq() != null) {
				ws.addCell(new Label(j += 1, i, sdf.format(daxxb.getSlrq())));
			} else {
				ws.addCell(new Label(j += 1, i, ""));
			}

			ws.addCell(new Label(j += 1, i, daxxb.getSlbm()));
			ws.addCell(new Label(j += 1, i, daxxb.getGdyj()));
			ws.addCell(new Label(j += 1, i, daxxb.getTbyy()));
			ws.addCell(new Label(j += 1, i, daxxb.getGdbz()));
			ws.addCell(new Label(j += 1, i, daxxb.getGdr()));
			if (daxxb.getGdrq() != null) {
				ws.addCell(new Label(j += 1, i, sdf.format(daxxb.getGdrq())));
			} else {
				ws.addCell(new Label(j += 1, i, ""));
			}

			ws.addCell(new Label(j += 1, i, daxxb.getGdbm()));
			ws.addCell(new Label(j += 1, i, daxxb.getSynFlag()));
			ws.addCell(new Label(j += 1, i, daxxb.getVcUser1Img()));
			ws.addCell(new Label(j += 1, i, daxxb.getVcUser2Img()));
			ws.addCell(new Label(j += 1, i, daxxb.getVcEbikeImg()));
			ws.addCell(new Label(j += 1, i, daxxb.getVcUser1CardImg1()));
			ws.addCell(new Label(j += 1, i, daxxb.getVcUser1CardImg2()));
			ws.addCell(new Label(j += 1, i, daxxb.getVcUser2CardImg1()));
			ws.addCell(new Label(j += 1, i, daxxb.getVcUser2CardImg2()));
			ws.addCell(new Label(j += 1, i, daxxb.getVcEbikeInvoiceImg()));
			i++;

		}

	}

	@Override
	public void createApproveUsers(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws) throws Exception {
		// TODO Auto-generated method stub
		Label label = new Label(0, 0, "审批报表", wcfFC);
		ws.mergeCells(0, 0, 6, 0);
		ws.addCell(label);
		int j1 = 0;
		ws.addCell(new Label(j1, 2, "USER_NAME", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "USER_ORGNAME", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "USER_ROLE_NAME", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "APPROVE_INDEX", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "APPROVE_NOTE", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "APPROVE_TABLE", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "APPROVE_TABLEID", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "APPROVE_TIME", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "APPROVE_STATE", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "LSH", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "SYN_FLAG", wcfFC2));
		ws.addCell(new Label(j1 += 1, 2, "APPROVE_NO", wcfFC2));
		List<DdcApproveUser> ddcApproveUsers = iDdcApprovalUserDao
				.findByProperty("sysFlag", "ADD");
		int i = 3;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (DdcApproveUser ddcApproveUser : ddcApproveUsers) {
			int j = 0;
			ws.addCell(new Label(j, i, ddcApproveUser.getUserName()));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getUserOrgname()));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getUserRoleName()));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getApproveIndex()
					+ ""));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getApproveNote()));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getApproveTable()));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getApproveTableid()
					+ ""));
			ws.addCell(new Label(j += 1, i, sdf.format(ddcApproveUser
					.getApproveTime())));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getApproveState()
					+ ""));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getLsh()));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getSysFlag() + ""));
			ws.addCell(new Label(j += 1, i, ddcApproveUser.getApproveNo() + ""));
			i++;

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#createDdcflows(jxl.write.WritableCellFormat
	 * , jxl.write.WritableCellFormat, jxl.write.WritableSheet)
	 */
	@Override
	public void createDdcflows(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws) throws Exception {
		Label label = new Label(0, 0, "DDCFLOW", wcfFC);
		ws.mergeCells(0, 0, 6, 0);
		ws.addCell(label);
		int j = 0;
		ws.addCell(new Label(j, 2, "LSH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "YWLX", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "YWYY", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHZH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "DABH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "CPHM", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "PPXH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "CYSY", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "DJH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "JTZZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "JSRXM1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "XB1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SFZMHM1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "LXDH1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "JSRXM2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "XB2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SFZMHM2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "LXDH2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "XSQY", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLZL", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLYJ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLBZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLRQ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLBM", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "GDYJ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "TBYY", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "GDBZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "GDR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "GDRQ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "GDBM", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SYN_FLAG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "YCLB", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER1IMG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER2IMG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_EBIKE_IMG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER1_CARDIMG1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER1_CARDIMG2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER2_CARDIMG1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER2_CARDIMG2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_EBIKE_INVOICE", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SSDWID", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "TABLENAME", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "TABLEID", wcfFC2));
		List<DdcFlow> ddcFlows = iDdcFlowDao.findByProperty("synFlag", "ADD");
		int i = 3;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (DdcFlow ddcFlow : ddcFlows) {
			int j1 = 0;
			ws.addCell(new Label(j1, i, ddcFlow.getLsh()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getYwlx()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getYwyy()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getHyxhzh()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getDabh()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getCphm()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getPpxh()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getCysy()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getDjh()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getJtzz()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getJsrxm1()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getXb1()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getSfzmhm1()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getLxdh1()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getJsrxm2()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getXb2()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getSfzmhm2()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getLxdh2()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getXsqy()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getBz()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getSlzl()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getSlyj()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getSlbz()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getSlr()));
			ws.addCell(new Label(j1 += 1, i, sdf.format(ddcFlow.getSlrq())));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getSlbm()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getGdyj()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getTbyy()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getGdbz()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getGdr()));
			if (ddcFlow.getGdrq() != null) {
				ws.addCell(new Label(j1 += 1, i, sdf.format(ddcFlow.getGdrq())));
			} else {
				ws.addCell(new Label(j1 += 1, i, ""));
			}
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getGdbm()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getSynFlag()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getYclb()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getVcUser1Img()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getVcUser2Img()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getVcEbikeImg()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getVcUser1CardImg1()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getVcUser1CardImg2()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getVcUser2CardImg1()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getVcUser2CardImg2()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getVcEbikeInvoiceImg()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getSsdwId()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getVcTableName()));
			ws.addCell(new Label(j1 += 1, i, ddcFlow.getiTableId() + ""));
			i++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#createDdcHyxhBasb(jxl.write.WritableCellFormat
	 * , jxl.write.WritableCellFormat, jxl.write.WritableSheet)
	 */
	@Override
	public void createDdcHyxhBasb(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws) throws Exception {
		Label label = new Label(0, 0, "ddc_hyxh_basb", wcfFC);
		ws.mergeCells(0, 0, 6, 0);
		ws.addCell(label);
		int j = 0;
		ws.addCell(new Label(j, 2, "LSH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHZH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHSQPE", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SQR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SQRQ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHBCSJPE", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BJJG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BJBZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BZJR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BJBM", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BJRQ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SYN_FLAG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SL_INDEX", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHMC", wcfFC2));
		List<DdcHyxhBasb> ddcHyxhBasbs = iDdcHyxhBasbDao.findByProperty(
				"synFlag", SystemConstants.SYSNFLAG_ADD);
		int i = 3;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (DdcHyxhBasb ddcHyxhBasb : ddcHyxhBasbs) {
			int j1 = 0;
			ws.addCell(new Label(j1, i, ddcHyxhBasb.getLsh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getHyxhzh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getHyxhsqpe() + ""));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getBz()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getSqr()));
			ws.addCell(new Label(j1 += 1, i, sdf.format(ddcHyxhBasb.getSqrq())));
			if (ddcHyxhBasb.getHyxhbcsjpe() != null) {
				ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getHyxhbcsjpe()
						+ ""));
			} else {
				ws.addCell(new Label(j1 += 1, i, ""));
			}

			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getBjjg()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getBjbz()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getBzjr()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getBjbm()));
			if (ddcHyxhBasb.getBjrq() != null) {
				ws.addCell(new Label(j1 += 1, i, sdf.format(ddcHyxhBasb
						.getBjrq())));
			} else {
				ws.addCell(new Label(j1 += 1, i, ""));
			}

			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getSynFlag()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getSlIndex() + ""));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBasb.getHyxhmc()));
			i++;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#createDdcHyxhBase(jxl.write.WritableCellFormat
	 * , jxl.write.WritableCellFormat, jxl.write.WritableSheet)
	 */
	@Override
	public void createDdcHyxhBase(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws) throws Exception {
		Label label = new Label(0, 0, "DDC_HYXH_BASE", wcfFC);
		ws.mergeCells(0, 0, 6, 0);
		ws.addCell(label);
		int j = 0;
		ws.addCell(new Label(j, 2, "ID", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHZH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHMM", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHMC", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHDZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHFZR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHFZRDH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHSJZPE", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "CJR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "CJRQ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "CJBM", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHLB", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SYN_FLAG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "TOTALPE", wcfFC2));
		List<DdcHyxhBase> ddcHyxhBases = iDdcHyxhBaseDao.findByProperty(
				"synFlag", SystemConstants.SYSNFLAG_UPDATE);
		int i = 3;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (DdcHyxhBase ddcHyxhBase : ddcHyxhBases) {
			int j1 = 0;
			ws.addCell(new Label(j1, i, String.valueOf(ddcHyxhBase.getId())));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getHyxhzh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getHyxhmm()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getHyxhmc()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getHyxhdz()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getHyxhfzr()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getHyxhfzrdh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getHyxhsjzpe() + ""));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getBz()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getCjr()));
			if (ddcHyxhBase.getCjrq() != null) {
				ws.addCell(new Label(j1 += 1, i, sdf.format(ddcHyxhBase
						.getCjrq())));
			} else {
				ws.addCell(new Label(j1 += 1, i, null));
			}

			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getCjbm()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getHyxhlb()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getSynFlag()));
			if (ddcHyxhBase.getTotalPe() != null) {
				ws.addCell(new Label(j1 += 1, i, ddcHyxhBase.getTotalPe() + ""));
			} else {
				ws.addCell(new Label(j1 += 1, i, ""));
			}

			i++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#createDdcHyxhSsdw(jxl.write.WritableCellFormat
	 * , jxl.write.WritableCellFormat, jxl.write.WritableSheet)
	 */
	@Override
	public void createDdcHyxhSsdw(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws) throws Exception {
		List<DdcHyxhSsdw> ddcHyxhSsdws = new ArrayList<>();
		List<DdcHyxhSsdw> ddcHyxhSsdws1 = iDdcHyxhSsdwDao.findByProperty(
				"synFlag", SystemConstants.SYSNFLAG_UPDATE);
		List<DdcHyxhSsdw> ddcHyxhSsdws2 = iDdcHyxhSsdwDao.findByProperty(
				"synFlag", SystemConstants.SYSNFLAG_ADD);
		ddcHyxhSsdws.addAll(ddcHyxhSsdws1);
		ddcHyxhSsdws.addAll(ddcHyxhSsdws2);
		Label label = new Label(0, 0, "ddc_hyxh_ssdw", wcfFC);
		ws.mergeCells(0, 0, 6, 0);
		ws.addCell(label);
		int j = 0;
		ws.addCell(new Label(j, 2, "ID", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHZH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "DWMC", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "ZZJGDMZH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "ZSDZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "LXR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "LXDH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SQR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SQRQ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "ZT", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SHR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SHRQ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SHBM", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SYN_FLAG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "DWPE", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_PICPATH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "USERCODE", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "PASSWORD", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SHFLAG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "TOTALPE", wcfFC2));
		int i = 3;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (DdcHyxhSsdw ddcHyxhSsdw : ddcHyxhSsdws) {
			int j1 = 0;
			ws.addCell(new Label(j1, i, String.valueOf(ddcHyxhSsdw.getId())));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getHyxhzh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getDwmc()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getZzjgdmzh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getZsdz()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getLxr()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getLxdh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getBz()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getSqr()));
			ws.addCell(new Label(j1 += 1, i, sdf.format(ddcHyxhSsdw.getSqrq())));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getZt()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getShr()));
			if (ddcHyxhSsdw.getShrq() != null) {
				ws.addCell(new Label(j1 += 1, i, sdf.format(ddcHyxhSsdw
						.getShrq())));
			} else {
				ws.addCell(new Label(j1 += 1, i, null));
			}

			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getShbm()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getSynFlag()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getDwpe() + ""));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getVcPicPath()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getUserCode()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getPassWord()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getShFlag() + ""));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdw.getTotalPe() + ""));
			i++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IEbikeService#createDdcHyxhSsdwClSb(jxl.write.
	 * WritableCellFormat, jxl.write.WritableCellFormat,
	 * jxl.write.WritableSheet)
	 */
	@Override
	public void createDdcHyxhSsdwClSb(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws) throws Exception {
		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs = iDdcHyxhSsdwclsbDao
				.findByProperty("synFlag", SystemConstants.SYSNFLAG_ADD);
		Label label = new Label(0, 0, "DDC_HYXH_SSDWCLSB", wcfFC);
		ws.mergeCells(0, 0, 6, 0);
		ws.addCell(label);
		int j = 0;
		ws.addCell(new Label(j, 2, "LSH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "HYXHZH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SSDWID", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "CPHM", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "PPXH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "CYSY", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "DJH", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "JTZZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "JSRXM1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "XB1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SFZMHM1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "LXDH1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "JSRXM2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "XB2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SFZMHM2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "LXDH2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "XSQY", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "BZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SQR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SQRQ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLZL", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLYJ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLBZ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLR", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLRQ", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SLBM", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SYN_FLAG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SQIP", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER1IMG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER2IMG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_EBIKE_IMG", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "TBYY", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "SL_INDEX", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER1_CARDIMG1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER1_CARDIMG2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER2_CARDIMG1", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_USER2_CARDIMG2", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "ENABLE", wcfFC2));
		ws.addCell(new Label(j += 1, 2, "VC_EBIKE_INVOICE", wcfFC2));
		int i = 3;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (DdcHyxhSsdwclsb ddcHyxhSsdwclsb : ddcHyxhSsdwclsbs) {
			int j1 = 0;
			ws.addCell(new Label(j1, i, ddcHyxhSsdwclsb.getLsh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getHyxhzh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSsdwId()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getCphm()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getPpxh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getCysy()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getDjh()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getJtzz()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getJsrxm1()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getXb1()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSfzmhm1()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getLxdh1()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getJsrxm2()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getXb2()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSfzmhm2()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getLxdh2()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getXsqy()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getBz()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSqr()));
			if (ddcHyxhSsdwclsb.getSqrq() != null) {
				ws.addCell(new Label(j1 += 1, i, sdf.format(ddcHyxhSsdwclsb
						.getSqrq())));
			} else {
				ws.addCell(new Label(j1 += 1, i, null));
			}

			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSlzl()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSlyj()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSlbz()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSlr()));
			if (ddcHyxhSsdwclsb.getSlrq() != null) {
				ws.addCell(new Label(j1 += 1, i, sdf.format(ddcHyxhSsdwclsb
						.getSlrq())));
			} else {
				ws.addCell(new Label(j1 += 1, i, null));
			}

			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSlbm()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSynFlag()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSqip()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getVcUser1Img()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getVcUser2Img()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getVcEbikeImg()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getTbyy()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getSlIndex() + ""));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb
					.getVcUser1CardImg1()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb
					.getVcUser1CardImg2()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb
					.getVcUser2CardImg1()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb
					.getVcUser2CardImg2()));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb.getnEnable() + ""));
			ws.addCell(new Label(j1 += 1, i, ddcHyxhSsdwclsb
					.getVcEbikeInvoiceImg()));
			i++;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.node.service.IEbikeService#saveFileRecord(com.node.model.FileRecord)
	 */
	@Override
	public void saveFileRecord(FileRecord fileRecord) {
		// TODO Auto-generated method stub
		iFileRecordDao.save(fileRecord);
	}
}
