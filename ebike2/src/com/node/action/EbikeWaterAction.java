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
			String ywlx, String djh, String cphm, String dtstart, String dtend) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		Page p = ServiceUtil.getcurrPage(request);

		String sql = "select A.ID,A.LSH,A.CPHM,A.DJH,A.SLRQ,a.SLYJ,(SELECT S.DWMC FROM DDC_HYXH_SSDW S WHERE S.ID=A.ZZJGDMZH ) AS DWMC,"
				+ " (select  D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.YWLX AND D.DMLB='YWLX') as YWLX ,"
				+ "  (select  D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.XSQY AND D.DMLB='SSQY') as SSQY ,"
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
	@ResponseBody
	public DdcFlow queryInfoById(HttpServletRequest request, String id,
			HttpServletResponse response) {
		long flowId = Long.parseLong(id);
		DdcFlow ddcFlow = iEbikeService.getFlowById(flowId);
		String cysyName = iApplyService.findByProPerties("CSYS",
				ddcFlow.getCysy());
		ddcFlow.setCysyName(cysyName);// 车身颜色
		String xsqyName = iApplyService.findByProPerties("SSQY",
				ddcFlow.getXsqy());
		ddcFlow.setXsqyName(xsqyName);// 所属区域

		// 申报单位
		if (StringUtils.isNotBlank(ddcFlow.getZzjgdmzh())) {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
					.parseLong(ddcFlow.getZzjgdmzh()));
			if (ddcHyxhSsdw != null) {
				ddcFlow.setZzjgdmzhName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcFlow.setZzjgdmzhName(null);
			}
		}
		// 业务类型
		String ywlxName = iApplyService.findByProPerties("YWLX",
				ddcFlow.getYwlx());
		ddcFlow.setYwlxName(ywlxName);
		// 业务原因
		String ywyyName = iApplyService.findByProPerties("YWYY_A",
				ddcFlow.getYwyy());
		ddcFlow.setYwyyName(ywyyName);
		// 受理资料
		String[] slzls = ddcFlow.getSlzl().split(",");
		List<String> slzllist = new ArrayList<>();
		for (String s : slzls) {
			String dmz = s;
			String dmlb = "BASQZL";
			String ss = iApplyService.findByProPerties(dmlb, dmz);
			slzllist.add(ss);
		}
		ddcFlow.setSlzlList(slzllist);
		String showEbikeImg = parseUrl(ddcFlow.getVcEbikeImg());
		String showUser1Img = parseUrl(ddcFlow.getVcUser1Img());
		String showUser2Img = parseUrl(ddcFlow.getVcUser2Img());
		ddcFlow.setVcShowEbikeImg(showEbikeImg);
		ddcFlow.setVcShowUser1Img(showUser1Img);
		ddcFlow.setVcShowUser2Img(showUser2Img);
		return ddcFlow;

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
