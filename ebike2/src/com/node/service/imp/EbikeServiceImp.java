/**
 * 文件名：EbikeServiceImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月16日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service.imp;

import java.text.SimpleDateFormat;
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
import com.node.dao.IDdcHyxhSsdwDao;
import com.node.model.DdcApproveUser;
import com.node.model.DdcDaxxb;
import com.node.model.DdcDaxxbLog;
import com.node.model.DdcFlow;
import com.node.model.DdcHyxhSsdw;
import com.node.service.IEbikeService;
import com.node.util.HqlHelper;
import com.node.util.Page;

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
		Label label = new Label(0, 0, "外网的电动车档案信息表", wcfFC);
		ws.mergeCells(0, 0, 6, 0);
		ws.addCell(label);
		Label label1 = new Label(0, 2, "ID", wcfFC2);
		ws.addCell(label1);
		Label label2 = new Label(1, 2, "DABH", wcfFC2);
		ws.addCell(label2);
		Label label3 = new Label(2, 2, "YWLX", wcfFC2);
		ws.addCell(label3);
		Label label4 = new Label(3, 2, "YWYY", wcfFC2);
		ws.addCell(label4);
		Label label5 = new Label(4, 2, "CYSY", wcfFC2);
		ws.addCell(label5);
		Label label6 = new Label(5, 2, "JTZZ", wcfFC2);
		ws.addCell(label6);
		Label label7 = new Label(6, 2, "JSRXM1", wcfFC2);
		ws.addCell(label7);
		Label label8 = new Label(7, 2, "XB1", wcfFC2);
		ws.addCell(label8);
		Label label9 = new Label(8, 2, "SFZMHM1", wcfFC2);
		ws.addCell(label9);
		Label label10 = new Label(9, 2, "LXDH1", wcfFC2);
		ws.addCell(label10);
		Label label11 = new Label(10, 2, "JSRXM2", wcfFC2);
		ws.addCell(label11);
		Label label12 = new Label(11, 2, "XB2", wcfFC2);
		ws.addCell(label12);
		Label label13 = new Label(12, 2, "SFZMHM2", wcfFC2);
		ws.addCell(label13);
		Label label14 = new Label(13, 2, "LXDH2", wcfFC2);
		ws.addCell(label14);
		Label label15 = new Label(14, 2, "XSQY", wcfFC2);
		ws.addCell(label15);
		Label label16 = new Label(15, 2, "ZT", wcfFC2);
		ws.addCell(label16);
		Label label17 = new Label(16, 2, "SYN_FLAG", wcfFC2);
		ws.addCell(label17);
		Label label18 = new Label(17, 2, "VC_USER1IMG", wcfFC2);
		ws.addCell(label18);
		Label label19 = new Label(18, 2, "VC_USER2IMG", wcfFC2);
		ws.addCell(label19);
		Label label20 = new Label(19, 2, "VC_EBIKE_IMG", wcfFC2);
		ws.addCell(label20);
		Label label21 = new Label(20, 2, "VC_USER1_CARDIMG1", wcfFC2);
		ws.addCell(label21);
		Label label22 = new Label(21, 2, "VC_USER1_CARDIMG2", wcfFC2);
		ws.addCell(label22);
		Label label23 = new Label(22, 2, "VC_USER2_CARDIMG1", wcfFC2);
		ws.addCell(label23);
		Label label24 = new Label(23, 2, "VC_USER2_CARDIMG2", wcfFC2);
		ws.addCell(label24);
		Label label25 = new Label(20, 2, "VC_EBIKE_INVOICE", wcfFC2);
		ws.addCell(label25);
		List<DdcDaxxb> daxxbs = iDdcDaxxbDao.findByProperty("synFlag", "UP");
		int i = 3;

		for (DdcDaxxb daxxb : daxxbs) {
			Label labelB = new Label(0, i, String.valueOf(daxxb.getId()));
			ws.addCell(labelB);
			Label labelC0 = new Label(1, i, daxxb.getDabh());
			ws.addCell(labelC0);
			Label labelC1 = new Label(2, i, daxxb.getYwlx());
			ws.addCell(labelC1);
			Label labelC2 = new Label(3, i, daxxb.getYwyy());
			ws.addCell(labelC2);
			Label labelC3 = new Label(4, i, daxxb.getCysy());
			ws.addCell(labelC3);
			Label labelC4 = new Label(5, i, daxxb.getJtzz());
			ws.addCell(labelC4);
			Label labelC5 = new Label(6, i, daxxb.getJsrxm1());
			ws.addCell(labelC5);
			Label labelC6 = new Label(7, i, daxxb.getXb1());
			ws.addCell(labelC6);
			Label labelC7 = new Label(8, i, daxxb.getSfzmhm1());
			ws.addCell(labelC7);
			Label labelC8 = new Label(9, i, daxxb.getLxdh1());
			ws.addCell(labelC8);
			Label labelC9 = new Label(10, i, daxxb.getJsrxm2());
			ws.addCell(labelC9);
			Label labelC10 = new Label(11, i, daxxb.getXb2());
			ws.addCell(labelC10);
			Label labelC11 = new Label(12, i, daxxb.getSfzmhm1());
			ws.addCell(labelC11);
			Label labelC12 = new Label(13, i, daxxb.getXsqy());
			ws.addCell(labelC12);
			Label labelC13 = new Label(14, i, daxxb.getLxdh1());
			ws.addCell(labelC13);
			Label labelC14 = new Label(15, i, daxxb.getZt());
			ws.addCell(labelC14);
			Label labelC15 = new Label(16, i, daxxb.getSynFlag());
			ws.addCell(labelC15);
			Label labelC16 = new Label(17, i, daxxb.getVcUser1Img());
			ws.addCell(labelC16);
			Label labelC17 = new Label(18, i, daxxb.getVcUser2Img());
			ws.addCell(labelC17);
			Label labelC18 = new Label(19, i, daxxb.getVcEbikeImg());
			ws.addCell(labelC18);
			Label labelC19 = new Label(20, i, daxxb.getVcUser1CardImg1());
			ws.addCell(labelC19);
			Label labelC20 = new Label(21, i, daxxb.getVcUser1CardImg2());
			ws.addCell(labelC20);
			Label labelC21 = new Label(22, i, daxxb.getVcUser2CardImg1());
			ws.addCell(labelC21);
			Label labelC22 = new Label(23, i, daxxb.getVcUser2CardImg2());
			ws.addCell(labelC22);

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
		Label label1 = new Label(0, 2, "USER_NAME", wcfFC2);
		ws.addCell(label1);
		Label label2 = new Label(1, 2, "USER_ORGNAME", wcfFC2);
		ws.addCell(label2);
		Label label3 = new Label(2, 2, "USER_ROLE_NAME", wcfFC2);
		ws.addCell(label3);
		Label label4 = new Label(3, 2, "APPROVE_INDEX", wcfFC2);
		ws.addCell(label4);
		Label label5 = new Label(4, 2, "APPROVE_NOTE", wcfFC2);
		ws.addCell(label5);
		Label label6 = new Label(5, 2, "APPROVE_TABLE", wcfFC2);
		ws.addCell(label6);
		Label label7 = new Label(6, 2, "APPROVE_TABLEID", wcfFC2);
		ws.addCell(label7);
		Label label8 = new Label(7, 2, "APPROVE_TIME", wcfFC2);
		ws.addCell(label8);
		Label label9 = new Label(8, 2, "APPROVE_STATE", wcfFC2);
		ws.addCell(label9);
		Label label10 = new Label(9, 2, "LSH", wcfFC2);
		ws.addCell(label10);
		Label label11 = new Label(10, 2, "SYN_FLAG", wcfFC2);
		ws.addCell(label11);
		List<DdcApproveUser> ddcApproveUsers = iDdcApprovalUserDao
				.findByProperty("sysFlag", "ADD");
		int i = 3;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (DdcApproveUser ddcApproveUser : ddcApproveUsers) {
			int j = 0;
			Label labelC0 = new Label(0, i, ddcApproveUser.getUserName());
			ws.addCell(labelC0);
			Label labelC1 = new Label(j += 1, i,
					ddcApproveUser.getUserOrgname());
			ws.addCell(labelC1);
			Label labelC2 = new Label(j += 1, i,
					ddcApproveUser.getUserRoleName());
			ws.addCell(labelC2);
			Label labelC3 = new Label(j += 1, i,
					ddcApproveUser.getApproveIndex() + "");
			ws.addCell(labelC3);
			Label labelC4 = new Label(j += 1, i,
					ddcApproveUser.getApproveNote());
			ws.addCell(labelC4);
			Label labelC5 = new Label(j += 1, i,
					ddcApproveUser.getApproveTable());
			ws.addCell(labelC5);
			Label labelC6 = new Label(j += 1, i,
					ddcApproveUser.getApproveTableid() + "");
			ws.addCell(labelC6);
			Label labelC7 = new Label(j += 1, i, sdf.format(ddcApproveUser
					.getApproveTime()));
			ws.addCell(labelC7);
			Label labelC8 = new Label(j += 1, i,
					ddcApproveUser.getApproveState() + "");
			ws.addCell(labelC8);
			Label labelC11 = new Label(j += 1, i, ddcApproveUser.getLsh());
			ws.addCell(labelC11);
			Label labelC12 = new Label(j += 1, i, ddcApproveUser.getSysFlag()
					+ "");
			ws.addCell(labelC12);
			i++;

		}
	}
}
