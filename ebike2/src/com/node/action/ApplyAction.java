/**
 * 文件名：ApplyAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月14日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.ServiceUtil;

/**
 * 类描述：备案申报
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月14日 下午4:25:46
 */
@Controller
@RequestMapping("/applyAction")
public class ApplyAction {
	@Autowired
	IApplyService iApplyService;
	@Autowired
	ICompanyService iCompanyService;

	/**
	 * 
	 * 方法描述：页面跳转
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月14日 下午4:45:47
	 */
	@RequestMapping("/getAll")
	public String getAll() {
		return "apply/ebikeInfos";

	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月14日 下午5:40:32
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		Page p = ServiceUtil.getcurrPage(request);

		HqlHelper hql = new HqlHelper(DdcHyxhSsdwclsb.class);
		// hql.addEqual("hyxhzh", ddcHyxhBase.getHyxhzh());
		hql.setQueryPage(p);
		Map<String, Object> resultMap = iApplyService.queryByHql(hql);
		List<DdcHyxhSsdwclsb> list = (List<DdcHyxhSsdwclsb>) resultMap
				.get("rows");
		for (DdcHyxhSsdwclsb ddcHyxhSsdwclsb : list) {
			String cysyName = iApplyService.findByProPerties("CSYS",
					ddcHyxhSsdwclsb.getCysy());

			ddcHyxhSsdwclsb.setCysyName(cysyName);// 车身颜色
			String xsqyName = iApplyService.findByProPerties("SSQY",
					ddcHyxhSsdwclsb.getXsqy());
			ddcHyxhSsdwclsb.setXsqyName(xsqyName);// 行驶区域
			// 申报单位
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
					.parseLong(ddcHyxhSsdwclsb.getSsdwId()));
			if (ddcHyxhSsdw != null) {
				ddcHyxhSsdwclsb.setSsdwName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcHyxhSsdwclsb.setSsdwName(null);
			}

		}

		return resultMap;
	}
}
