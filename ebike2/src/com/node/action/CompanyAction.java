/**
 * 文件名：CompanyAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.node.model.DdcHyxhSsdw;
import com.node.service.ICompanyService;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.ServiceUtil;

/**
 * 类描述：单位信息管理
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月11日 下午6:47:17
 */
@Controller
@RequestMapping("/companyAction")
public class CompanyAction {

	@Autowired
	ICompanyService iCompanyService;

	/**
	 * 
	 * 方法描述：页面跳转
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月11日 下午10:07:14
	 */
	@RequestMapping("/getAll")
	public String getAll() {
		return "company/companyInfos";

	}

	/**
	 * 
	 * 方法描述：查询行业所属单位的信息
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月11日 下午10:16:16
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request) {
		Page p = ServiceUtil.getcurrPage(request);
		HqlHelper hql = new HqlHelper(DdcHyxhSsdw.class);

		hql.setQueryPage(p);
		Map<String, Object> resultMap = iCompanyService.queryByHql(hql);

		return resultMap;
	}

}
