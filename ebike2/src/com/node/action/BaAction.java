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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.node.model.DdcDriver;
import com.node.model.DdcHyxhSsdw;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.service.IDriverSerivce;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;


/**
 * 类描述：新修改：人车分离备案申报
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月17日 上午11:09:30 
 */
@Controller
@RequestMapping("/baAction")
public class BaAction {
	@Autowired
	IApplyService iApplyService;
	
	@Autowired
	ICompanyService iCompanyService;
	
	@Autowired
	IDriverSerivce iDriverSerivce;
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
	public Map<String, Object> queryBaList(HttpServletRequest request,
			String djh, String dtStart, String dtend, String xsqy, String zt,
			String jsrxm1, HttpServletResponse response){
		Page page = ServiceUtil.getcurrPage(request);
		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		String sql = "select s.id,s.lsh,s.ppxh,(select d.DMMS1 from ddc_sjzd d where d.dmz = s.cysy and d.dmlb='CSYS' and rownum=1)as csysname,"
				+ "s.LXDH1,s.SFZMHM1, s.JSRXM1,s.djh,(select  d.DMMS1 from ddc_sjzd d where d.dmz = s.xsqy and d.dmlb='SSQY' and rownum=1 )as xsqyname ,s.sqrq,s.sl_index,s.slyj "
				+ "from DDC_HYXH_SSDWCLSB s where 1=1 and s.ENABLE = 1";
		sql += " and s.SSDWID=" + ddcHyxhSsdw.getId();
		if (StringUtils.isNotBlank(zt)) {
			if (zt.equals("0") || zt.equals("1")) {
				sql += " and s.slyj =" + Integer.parseInt(zt);
			} else {
				sql += " and s.SLYJ is null ";
			}

		}
		if (StringUtils.isNotBlank(dtStart)) {
			sql += " and s.SQRQ > to_date('" + dtStart + "','yyyy-MM-dd')";
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

		Map<String, Object> map = iCompanyService.getBySpringSql(sql, page);
		return map;

	}
	/**
	 * 
	  * 方法描述：
	  * @param request
	  * @param response
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月18日 上午11:16:14
	 */
	@RequestMapping("/getDriverList")
	@ResponseBody
	public Map<String, Object> getDriverList(HttpServletRequest request,HttpServletResponse response){
		Page page = ServiceUtil.getcurrPage(request);
		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		String jsrxm = null;
		if( request.getParameter("q")!=null){
			 jsrxm =  request.getParameter("q").toString();
		}
		HqlHelper hql = new HqlHelper(DdcDriver.class);
		hql.addEqual("ssdwId", ddcHyxhSsdw.getId());
		if(StringUtils.isNotBlank(jsrxm)){
			hql.addLike("jsrxm", jsrxm);
		}
		
		hql.addOrderBy("id", "desc");
		hql.setQueryPage(page);
		Map<String, Object> resultMap = iDriverSerivce.queryByHql(hql);
		return resultMap;
	}
	
}
