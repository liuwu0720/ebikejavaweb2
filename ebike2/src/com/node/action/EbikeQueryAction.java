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

import com.node.model.DdcHyxhBase;
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
			String cphm, String jsrxm1, String dabh, String ssdw,
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
		// 身份证
		if (StringUtils.isNotBlank(ssdw)) {
			sql += " and a.SSDWID =" + ssdw + "";
		}

		sql += "  order by A.ID DESC";

		Map<String, Object> resultMap = iEbikeService.queryBySpringSql(sql, p);
		return resultMap;

	}

}
