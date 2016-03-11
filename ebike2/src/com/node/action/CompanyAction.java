/**
 * 文件名：CompanyAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@RequestMapping("/getAll")
	public String getAll() {
		return "company/companyInfos";

	}

}
