/**
  * 文件名：BaAction.java
  * 版本信息：Version 1.0
  * 日期：2016年6月17日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.action;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;


/**
 * 类描述：新修改：人车分离备案申报
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月17日 上午11:09:30 
 */
@RequestMapping("/baAction")
public class BaAction {
	@Autowired
	IApplyService iApplyService;
	
	@Autowired
	ICompanyService iCompanyService;
	/**
	 * 
	  * 方法描述：
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月17日 上午11:12:21
	 */
	@RequestMapping("/getAll")
	public String getAll(HttpServletRequest request){
		DdcHyxhSsdw dw = (DdcHyxhSsdw) request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		DdcHyxhSsdw ddcHyxhSsdw = iApplyService.getDdcHyxhSsdwById(String
				.valueOf(dw.getId()));
		request.setAttribute("ddcHyxhSsdw", ddcHyxhSsdw);
		return "baApply/clsblist";
	}
	/**
	 * 
	  * 方法描述：
	  * @param request
	  * @param zt
	  * @param xsqy
	  * @param jsrxm1
	  * @param ssdw
	  * @param djh
	  * @param dtstart
	  * @param dtend
	  * @return
	  * @throws ParseException 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月17日 上午11:27:26
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request, String zt,
			String xsqy, String jsrxm1, String ssdw, String djh,
			String dtstart, String dtend) throws ParseException {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);
		String sql = "select s.id,s.lsh,s.ppxh,(select d.DMMS1 from ddc_sjzd d where d.dmz = s.cysy and d.dmlb='CSYS' and rownum=1)as csysname,s.ssdwid,"
				+ "  dw.dwmc as SSDWNAME,"
				+ "s.JSRXM1,s.djh,(select  d.DMMS1 from ddc_sjzd d where d.dmz = s.xsqy and d.dmlb='SSQY' and rownum=1 )as xsqyname ,s.sqrq,s.sl_index,s.slyj "
				+ "from DDC_HYXH_SSDWCLSB s,ddc_hyxh_ssdw dw where 1=1 and s.SSDWID = dw.id and s.ENABLE = 1";
		sql += " and s.HYXHZH = '" + ddcHyxhBase.getHyxhzh() + "'";
		if (StringUtils.isNotBlank(zt)) {
			if (zt.equals("0") || zt.equals("1")) {
				sql += " and s.slyj =" + Integer.parseInt(zt);
			} else {
				sql += " and s.SLYJ is null ";
			}

		}
		if (StringUtils.isNotBlank(ssdw)) {
			sql += "  and dw.dwmc like  '%" + ssdw+"%'";
		}
		if (StringUtils.isNotBlank(dtstart)) {
			sql += " and s.SQRQ > to_date('" + dtstart + "','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(dtend)) {
			sql += " and s.SQRQ < to_date('" + dtend + "','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(djh)) {
			sql += " and s.djh like '" + djh + "'";
		}
		if (StringUtils.isNotBlank(xsqy)) {
			sql += " and s.XSQY = '" + xsqy + "'";
		}
		if (StringUtils.isNotBlank(jsrxm1)) {
			sql += " and s.jsrxm1 like '%" + jsrxm1 + "%'";
		}

		sql += " order by s.id desc";

		Map<String, Object> map = iCompanyService.getBySpringSql(sql, p);
		return map;

	}
}
