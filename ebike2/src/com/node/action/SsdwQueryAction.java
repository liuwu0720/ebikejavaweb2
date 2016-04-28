/**
 * 文件名：SsdwQueryAction.java
 * 版本信息：Version 1.0
 * 日期：2016年4月13日
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

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcHyxhSsdw;
import com.node.service.IEbikeService;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：单位 档案查询
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月13日 下午2:43:27
 */
@Controller
@RequestMapping("/ssdwQueryAction")
@ApiIgnore
public class SsdwQueryAction {
	@Autowired
	IEbikeService iEbikeService;

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月13日 下午3:12:35
	 */
	@RequestMapping("/getDaList")
	public String getDaList(HttpServletRequest request) {
		return "company/daList";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月13日 下午3:16:27
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request, String djh,
			String zt, String cphm, String jsrxm1, String dabh, String ywlx,
			String slyj, String xsqy, HttpServletResponse response) {
		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);

		String sql = "select A.ID,A.DABH,A.CPHM,A.DJH,A.JSRXM1,A.GDYJ,A.SFZMHM1,A.YWLX,A.SLRQ, (SELECT S.DWMC FROM DDC_HYXH_SSDW S WHERE S.ID=A.SSDWID and rownum = 1) AS DWMC,"
				+ " (select d.DMMS1 from ddc_sjzd d where d.dmz = a.cysy and d.dmlb='CSYS' and rownum=1)as csysname,"
				+ "(select d.DMMS1 from ddc_sjzd d where d.dmz = a.YWLX and d.dmlb='YWLX' and rownum=1)as YWLXNAME,"
				+ "(select d.DMMS1 from ddc_sjzd d where d.dmz=a.xsqy and d.dmlb='SSQY' and rownum = 1) as xsqy ,a.SYRQ,a.slyj ,"
				+ "(SELECT D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.ZT AND D.DMLB='CLZT' and rownum = 1 )AS ZT "
				+ " from DDC_DAXXB A WHERE A.SSDWID='"
				+ ddcHyxhSsdw.getId()
				+ "'  ";
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
			sql += " and a.cphm like '%" + cphm + "%'";
		}
		// 驾驶人
		if (StringUtils.isNotBlank(jsrxm1)) {
			sql += " and a.JSRXM1 like '%" + jsrxm1 + "%'";
		}

		if (StringUtils.isNotBlank(xsqy)) {
			sql += " and a.XSQY = '" + xsqy + "'";
		}
		if (StringUtils.isNotBlank(zt)) {
			sql += " and a.zt = '" + zt + "'";
		}
		sql += " and a.zt!='E'   order by A.ID DESC";// 查出没有注销且变更不在审批中的记录

		Map<String, Object> resultMap = iEbikeService.queryBySpringSql(sql, p);
		return resultMap;
	}
}
