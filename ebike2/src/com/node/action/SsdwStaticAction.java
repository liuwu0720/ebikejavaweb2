/**
 * 文件名：SsdwStaticAction.java
 * 版本信息：Version 1.0
 * 日期：2016年4月13日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcSjzd;
import com.node.service.IApplyService;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月13日 下午8:41:16
 */
@Controller
@RequestMapping("/ssdwStaticAction")
@ApiIgnore
public class SsdwStaticAction {
	@Autowired
	IApplyService iApplyService;

	@RequestMapping("/getYwList")
	public String getYwList(HttpServletRequest request) {
		List<DdcSjzd> ywylxs = iApplyService.getSjzdByDmlb("YWLX");// 业务原因
		request.setAttribute("ywylxs", ywylxs);
		return "company/flowList";
	}
}
