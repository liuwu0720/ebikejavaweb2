/**
 * 文件名：CompanyQtyAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月16日
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
import com.node.model.DdcHyxhSsdw;
import com.node.service.ICompanyService;
import com.node.service.IUserService;
import com.node.util.AjaxUtil;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.ServiceUtil;

/**
 * 类描述：配额管理
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月16日 下午2:32:08
 */
@Controller
@RequestMapping("/companyQtyAction")
public class CompanyQtyAction {

	@Autowired
	ICompanyService iCompanyService;

	@Autowired
	IUserService iUserService;

	/**
	 * 
	 * 方法描述：页面跳转
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午2:37:03
	 */
	@RequestMapping("/getAll")
	public String getAll() {
		return "company/companyQtys";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午2:46:03
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request,
			String dwmc, String lxr) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		Page p = ServiceUtil.getcurrPage(request);

		HqlHelper hql = new HqlHelper(DdcHyxhSsdw.class);
		hql.addEqual("hyxhzh", ddcHyxhBase.getHyxhzh());
		hql.addEqual("zt", "1");
		if (StringUtils.isNotBlank(dwmc)) {
			hql.addEqual("dwmc", dwmc);
		}
		if (StringUtils.isNotBlank(lxr)) {
			hql.addEqual("lxr", lxr);
		}
		hql.setQueryPage(p);
		hql.addOrderBy("dwmc", "asc");
		Map<String, Object> resultMap = iCompanyService.queryByHql(hql);
		DdcHyxhBase ddcHyxhBase2 = iUserService.getById(ddcHyxhBase.getId());// 重新查一次，因为数量有变化在session中体现不了
		request.setAttribute("ddcHyxhBase", ddcHyxhBase2);
		return resultMap;
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param id
	 * @param dwpe
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午3:23:16
	 */
	@RequestMapping("/updateQty")
	public void updateQty(HttpServletRequest request,
			HttpServletResponse response, String id, String dwpe) {
		long ssdwId = Long.parseLong(id);
		int ssdwDwpe = Integer.parseInt(dwpe);
		DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(ssdwId);
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		int minusNum = ssdwDwpe - ddcHyxhSsdw.getDwpe();
		if (ssdwDwpe == ddcHyxhSsdw.getDwpe()) {
			AjaxUtil.rendJson(response, true, "修改成功！");
			return;
		} else {
			if (ddcHyxhBase.getHyxhsjzpe() < minusNum) {
				AjaxUtil.rendJson(response, false, "配额不足，修改失败");
				return;
			} else {
				ddcHyxhSsdw.setDwpe(ssdwDwpe);
				iCompanyService.update(ddcHyxhSsdw);
				ddcHyxhBase.setHyxhsjzpe(ddcHyxhBase.getHyxhsjzpe() - minusNum);
				iUserService.update(ddcHyxhBase);
				AjaxUtil.rendJson(response, true, "修改成功！");
			}
		}

	}
}
