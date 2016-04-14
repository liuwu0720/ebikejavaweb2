/**
 * 文件名：SsdwStaticAction.java
 * 版本信息：Version 1.0
 * 日期：2016年4月13日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月13日 下午8:41:16
 */
@Controller
@RequestMapping("/ssdwStaticAction")
public class SsdwStaticAction {

	@RequestMapping("/getYwList")
	public String getYwList(HttpServletRequest request) {
		return "company/flowList";
	}
}
