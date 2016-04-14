/**
 * 文件名：EbikeWaterAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月18日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.node.model.DdcFlow;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcSjzd;
import com.node.model.PicPath;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.service.IEbikeService;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月18日 下午4:20:17
 */
@Controller
@RequestMapping("/ebikeWaterAction")
public class EbikeWaterAction {
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
	 * @version: 2016年3月18日 下午4:21:52
	 */
	@RequestMapping("/getAll")
	public String getAll(HttpServletRequest request) {
		List<DdcSjzd> ywylxs = iApplyService.getSjzdByDmlb("YWLX");// 业务原因
		request.setAttribute("ywylxs", ywylxs);
		return "ebike/ebikeflowList";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 下午4:23:25
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request,
			String dabh, String ssdw, String ywlx, String djh, String cphm,
			String dtstart, String dtend) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);

		String sql = "select A.DABH, A.ID,A.LSH,A.CPHM,A.DJH,A.SLRQ,a.SLYJ,(SELECT distinct S.DWMC FROM DDC_HYXH_SSDW S WHERE S.ID=A.SSDWID and rownum=1) AS DWMC,"
				+ " (select distinct D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.YWLX AND D.DMLB='YWLX' and rownum=1) as YWLX ,"
				+ "  (select distinct D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.XSQY AND D.DMLB='SSQY' and rownum=1 ) as SSQY ,"
				+ "(SELECT D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.YWLX AND D.DMLB='CLZT')AS ZT from DDC_FLOW A WHERE A.HYXHZH='"
				+ ddcHyxhBase.getHyxhzh() + "'  ";
		if (StringUtils.isNotBlank(ywlx)) {
			sql += " AND A.YWLX = '" + ywlx + "'";
		}
		if (StringUtils.isNotBlank(djh)) {
			sql += " AND A.djh = '" + djh + "'";
		}
		if (StringUtils.isNotBlank(cphm)) {
			sql += " AND A.cphm = '" + cphm + "'";
		}
		if (StringUtils.isNotBlank(dtstart)) {
			sql += " and a.slrq >=to_date('" + dtstart + "','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(dtend)) {
			sql += " and a.slrq <=to_date('" + dtend + "','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(ssdw)) {
			sql += " and a.SSDWID = " + ssdw;
		}
		if (StringUtils.isNotBlank(dabh)) {
			sql += " and a.dabh = '" + dabh + "'";
		}

		sql += " order by a.id desc";
		Map<String, Object> resultMap = iEbikeService.queryBySpringSql(sql, p);

		return resultMap;
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @param id
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 下午5:52:45
	 */
	@RequestMapping("/queryInfoById")
	public String queryInfoById(HttpServletRequest request, String id,
			String type, HttpServletResponse response) {
		long flowId = Long.parseLong(id);
		DdcFlow ddcFlow = iEbikeService.getFlowById(flowId);
		String cysyName = iApplyService.findByProPerties("CSYS",
				ddcFlow.getCysy());
		ddcFlow.setCysyName(cysyName);// 车身颜色
		String xsqyName = iApplyService.findByProPerties("SSQY",
				ddcFlow.getXsqy());
		ddcFlow.setXsqyName(xsqyName);// 所属区域

		// 申报单位
		if (StringUtils.isNotBlank(ddcFlow.getSsdwId())) {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
					.parseLong(ddcFlow.getSsdwId()));
			if (ddcHyxhSsdw != null) {
				ddcFlow.setSsdwName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcFlow.setSsdwName(null);
			}
		}
		// 业务类型
		String ywlxName = iApplyService.findByProPerties("YWLX",
				ddcFlow.getYwlx());
		ddcFlow.setYwlxName(ywlxName);
		// 注销的业务原因
		if (ddcFlow.getYwlx().equalsIgnoreCase("D")) {
			String[] ywyyStrings = ddcFlow.getYwyy().split(",");
			String ywyyNameList = "";
			for (String ywyy : ywyyStrings) {
				String ywyyName = iApplyService
						.findByProPerties("YWYY_D", ywyy);
				ywyyNameList += ywyyName + ",";
			}
			ddcFlow.setYwyyName(ywyyNameList);
		}

		// 受理资料
		List<DdcSjzd> slzlDdcSjzds = new ArrayList<>();
		// 退办原因
		List<DdcSjzd> tbyyDdcSjzds = iApplyService.getDbyyList(
				ddcFlow.getTbyy(), "TBYY");
		if (ddcFlow.getYwlx().equals("A")) {// 备案
			slzlDdcSjzds = iApplyService.getDbyyList(ddcFlow.getSlzl(),
					"BASQZL");
		}
		// 变更、转移
		if (ddcFlow.getYwlx().equals("B") || ddcFlow.getYwlx().equals("C")) {
			slzlDdcSjzds = iApplyService.getDbyyList(ddcFlow.getSlzl(),
					"BGSQZL");
		}
		// 注销
		if (ddcFlow.getYwlx().equals("D")) {
			slzlDdcSjzds = iApplyService.getDbyyList(ddcFlow.getSlzl(),
					"ZXSQZL");
		}

		String showEbikeImg = parseUrl(ddcFlow.getVcEbikeImg());
		String showUser1Img = parseUrl(ddcFlow.getVcUser1Img());
		String showUser2Img = parseUrl(ddcFlow.getVcUser2Img());
		String vcUser1CardImg1Show = parseUrl(ddcFlow.getVcUser1CardImg1());
		String vcUser1CardImg2Show = parseUrl(ddcFlow.getVcUser1CardImg2());
		String vcUser2CardImg1Show = parseUrl(ddcFlow.getVcUser2CardImg1());
		String vcUser2CardImg2Show = parseUrl(ddcFlow.getVcUser2CardImg2());
		String vcEbikeInvoiceImgShow = parseUrl(ddcFlow.getVcEbikeInvoiceImg());
		ddcFlow.setVcShowEbikeImg(showEbikeImg);
		ddcFlow.setVcShowUser1Img(showUser1Img);
		ddcFlow.setVcShowUser2Img(showUser2Img);
		ddcFlow.setVcUser1CardImg1Show(vcUser1CardImg1Show);
		ddcFlow.setVcUser1CardImg2Show(vcUser1CardImg2Show);
		ddcFlow.setVcUser2CardImg1Show(vcUser2CardImg1Show);
		ddcFlow.setVcUser2CardImg2Show(vcUser2CardImg2Show);
		ddcFlow.setVcEbikeInvoiceImgShow(vcEbikeInvoiceImgShow);
		request.setAttribute("ddcFlow", ddcFlow);
		request.setAttribute("slzlDdcSjzds", slzlDdcSjzds);
		request.setAttribute("tbyyDdcSjzds", tbyyDdcSjzds);
		request.setAttribute("type", type);
		return "ebike/flowDetail";

	}

	/**
	 * 方法描述：图片显示路径进行解析
	 * 
	 * @param vcPicPath
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午3:23:40
	 */
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
