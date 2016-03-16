/**
 * 文件名：EbikeChangAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月16日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.node.model.DdcHyxhBase;
import com.node.service.IApplyService;
import com.node.service.IEbikeService;
import com.node.util.Page;
import com.node.util.ServiceUtil;

/**
 * 类描述：电动车变更管理
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月16日 下午4:00:17
 */
@Controller
@RequestMapping("/ebikeChangeAction")
public class EbikeChangAction {

	@Autowired
	IEbikeService iEbikeService;
	@Autowired
	IApplyService iApplyService;

	/**
	 * 
	 * 方法描述：页面跳转
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午4:01:17
	 */
	@RequestMapping("/getAll")
	public String getAll() {
		return "ebike/ebikeInfos";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param response
	 * @param dabh
	 * @param cphm
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午5:02:52
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletResponse response,
			String dabh, String cphm, HttpServletRequest request) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		Page p = ServiceUtil.getcurrPage(request);
		StringBuffer sb = new StringBuffer();
		sb.append("select new DdcDaxxb(a.id,a.dabh,a.cphm,a.djh,a.slrq,");
		sb.append("(select c.dwmc from DdcHyxhSsdw c where c.id=a.zzjgdmzh) as zzjgdmzh,");
		sb.append("(select e.dmms1 from DdcSjzd e where e.dmz=a.zt and e.dmlb='CLZT') as zt) from DdcDaxxb a where a.hyxhzh='"
				+ ddcHyxhBase.getHyxhzh() + "'");
		sb.append(" order by a.id desc");
		String sql = sb.toString();
		Map<String, Object> resultMap = iEbikeService.queryBySpringHql(sql, p);
		/*
		 * HqlHelper hql = new HqlHelper(DdcDaxxb.class); hql.addEqual("hyxhzh",
		 * ddcHyxhBase.getHyxhzh());
		 * 
		 * if (StringUtils.isNotBlank(dabh)) { hql.addEqual("dabh", dabh); } if
		 * (StringUtils.isNotBlank(cphm)) { hql.addEqual("cphm", cphm); }
		 * hql.setQueryPage(p); hql.addOrderBy("id", "desc"); Map<String,
		 * Object> resultMap = iEbikeService.queryByHql(hql); List<DdcDaxxb>
		 * list = (List<DdcDaxxb>) resultMap.get("rows"); for (DdcDaxxb daxxb :
		 * list) { String zzjgdmzhName = iEbikeService.findByProPerties(daxxb
		 * .getZzjgdmzh()); daxxb.setZzjgdmzhName(zzjgdmzhName); String ztName =
		 * iApplyService.findByProPerties("CLZT", daxxb.getZt());
		 * daxxb.setZtName(ztName); }
		 */
		return resultMap;
	}
}
