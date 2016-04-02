/**
 * 文件名：UserAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月1日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.node.model.DdcHyxhBase;
import com.node.service.IUserService;
import com.node.util.AjaxUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：用户首页登录、用户增删改查的一些操作
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月1日 上午12:09:31
 */
@Controller
@RequestMapping("/userAction")
public class UserAction {

	@Autowired
	IUserService iUserService;

	/**
	 * 
	 * 方法描述：登录首页
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月1日 下午4:49:30
	 */
	@RequestMapping("/index")
	public String index() {

		return "index";
	}

	@RequestMapping("/checkUser")
	public void checkUser(HttpServletRequest request,
			HttpServletResponse response, String cuser, String cpassword,
			String ccode) {
		/*
		 * String code = (String) request.getSession().getAttribute("certCode");
		 * 
		 * if (StringUtils.isEmpty(code) || StringUtils.isEmpty(ccode)) {
		 * AjaxUtil.rendJson(response, false, "验证码获取失败，请刷新页面重试"); return; }
		 * 
		 * 
		 * if (!code.equalsIgnoreCase(ccode)) { AjaxUtil.rendJson(response,
		 * false, "验证码不正确"); return; }
		 */

		DdcHyxhBase ddcHyxhBase = iUserService.getByUserAccount(cuser);
		if (ddcHyxhBase == null || !ddcHyxhBase.getHyxhmm().equals(cpassword)) {

			AjaxUtil.rendJson(response, false, "用户名或密码错误！");
			return;
		} else {
			request.getSession().setAttribute("ddcHyxhBase", ddcHyxhBase);
			AjaxUtil.rendJson(response, true, "验证通过");
		}

	}

	/**
	 * 
	 * 方法描述：登录到首页
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月2日 上午9:00:35
	 */
	@RequestMapping("/loginToMain")
	public String loginToMain() {
		return "main";

	}

	/**
	 * 
	 * 方法描述：退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月31日 上午11:29:26
	 */
	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute(SystemConstants.SESSION_USER);
		PrintWriter out = response.getWriter();
		out.println("<script>window.parent.location.replace('"
				+ request.getContextPath() + "/index.jsp')</script>");
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 
	 * 方法描述：修改密码界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月31日 上午11:33:43
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(HttpServletRequest request,
			HttpServletResponse response) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);

		request.setAttribute("ddcHyxhBase", ddcHyxhBase);
		return "main/modifyUser";
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
	 * @version: 2016年3月31日 下午12:13:09
	 * @throws IOException
	 */
	@RequestMapping("/saveModifyUser")
	public void saveModifyUser(HttpServletRequest request, String id,
			String userPassword, HttpServletResponse response)
			throws IOException {
		try {
			DdcHyxhBase ddcHyxhBase = iUserService.getById(Long.parseLong(id));
			ddcHyxhBase.setHyxhmm(userPassword);
			iUserService.update(ddcHyxhBase);
			AjaxUtil.rendJson(response, true, "成功");

		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "失败！");
		}
	}

}
