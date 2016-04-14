/**
 * 文件名：EbikeQueryAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月18日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.node.model.DdcDaxxb;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.PicPath;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.service.IEbikeService;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：档案查询
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月18日 下午3:20:56
 */
@Controller
@RequestMapping("/ebikeQueryAction")
public class EbikeQueryAction {

	@Autowired
	IEbikeService iEbikeService;

	@Autowired
	IApplyService iApplyService;

	@Autowired
	ICompanyService iCompanyService;

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 下午3:22:52
	 */
	@RequestMapping("/getAll")
	public String getAll() {
		return "ebike/ebikeDaList";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 下午3:23:32
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request, String djh,
			String xsqy, String cphm, String jsrxm1, String dabh, String ssdw,
			HttpServletResponse response) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);

		String sql = "select A.ID,A.DABH,A.CPHM,A.DJH,A.JSRXM1,A.GDYJ,A.SSDWID, (SELECT S.DWMC FROM DDC_HYXH_SSDW S WHERE S.ID=A.SSDWID and rownum = 1) AS DWMC,"
				+ "(select d.DMMS1 from ddc_sjzd d where d.dmz=a.xsqy and d.dmlb='SSQY' and rownum = 1) as xsqy, "
				+ "(SELECT D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.ZT AND D.DMLB='CLZT' and rownum = 1 )AS ZT from DDC_DAXXB A WHERE A.HYXHZH='"
				+ ddcHyxhBase.getHyxhzh() + "'  ";
		// 电机号
		if (StringUtils.isNotBlank(djh)) {
			sql += " and a.djh like '%" + djh + "%'";
		}
		// 档案编号
		if (StringUtils.isNotBlank(dabh)) {
			sql += " and a.dabh like '%" + dabh + "%'";
		}
		// 车牌号
		if (StringUtils.isNotBlank(cphm)) {
			sql += " and a.sfzhm1 like '%" + cphm + "%'";
		}
		// 驾驶人
		if (StringUtils.isNotBlank(jsrxm1)) {
			sql += " and a.JSRXM1 like '%" + jsrxm1 + "%'";
		}
		// 所属单位
		if (StringUtils.isNotBlank(ssdw)) {
			sql += " and a.SSDWID ='" + ssdw + "'";
		}
		// 行驶区域
		if (StringUtils.isNotBlank(xsqy)) {
			sql += " and a.XSQY = '" + xsqy + "'";
		}
		sql += " and a.ywlx !='D' and a.slyj=0  order by A.ID DESC";// 查出没有注销且变更不在审批中的记录

		Map<String, Object> resultMap = iEbikeService.queryBySpringSql(sql, p);
		return resultMap;

	}

	/**
	 * 
	 * 方法描述：生成二维码
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月14日 下午2:05:36
	 */
	@RequestMapping("/queryQRCodeById")
	public String queryQRCodeById(HttpServletRequest request, String id) {
		long sbId = Long.parseLong(id);
		DdcDaxxb ddcDaxxb = iEbikeService.getById(sbId);
		String cysyName = iApplyService.findByProPerties("CSYS",
				ddcDaxxb.getCysy());

		ddcDaxxb.setCysyName(cysyName);// 车身颜色
		String xsqyName = iApplyService.findByProPerties("SSQY",
				ddcDaxxb.getXsqy());
		ddcDaxxb.setXsqyName(xsqyName);// 所属区域

		String ztName = iApplyService
				.findByProPerties("CLZT", ddcDaxxb.getZt());
		ddcDaxxb.setZtName(ztName);
		// 申报单位
		if (StringUtils.isNotBlank(ddcDaxxb.getSsdwId())) {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
					.parseLong(ddcDaxxb.getSsdwId()));
			if (ddcHyxhSsdw != null) {
				ddcDaxxb.setSsdwName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcDaxxb.setSsdwName(null);
			}
		}
		DdcHyxhBase ddcHyxhBase = iCompanyService.getHyxhZhByCode(ddcDaxxb
				.getHyxhzh());
		ddcDaxxb.setHyxhzhName(ddcHyxhBase.getHyxhmc());
		String showUser1Img = parseUrl(ddcDaxxb.getVcUser1Img());
		String showUser2Img = parseUrl(ddcDaxxb.getVcUser2Img());

		ddcDaxxb.setVcShowUser1Img(showUser1Img);
		ddcDaxxb.setVcShowUser2Img(showUser2Img);
		request.setAttribute("ddcDaxxb", ddcDaxxb);
		return "ebike/ebikeQRCode";
	}

	private String parseUrl(String vcPicPath) {
		if (StringUtils.isNotBlank(vcPicPath)) {
			PicPath picPath = iCompanyService
					.getPathById(SystemConstants.PIC_IMG);
			String subPath = picPath.getVcParsePath();
			if (!subPath.endsWith("/")) {
				subPath += "/";
			}
			return subPath + vcPicPath;
		} else {
			return null;
		}

	}
}
