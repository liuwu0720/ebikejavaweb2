/**
 * 文件名：MainAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月2日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.node.model.HyxhMenu;
import com.node.service.IUserService;

/**
 * 类描述：主页的页面加载
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月2日 上午8:34:59
 */
@Controller
@RequestMapping("/mainAction")
public class MainAction {

	@Autowired
	private IUserService iUserService;

	/**
	 * 
	 * 方法描述：展示主页的显示隐藏按钮
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月2日 上午9:22:36
	 */
	@RequestMapping("/getControl")
	public String getControl() {
		return "main/control";
	}

	/**
	 * 
	 * 方法描述：展示主页的页头
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月2日 上午9:24:07
	 */
	@RequestMapping("/getHeader")
	public String getHeader() {
		return "main/header";
	}

	/**
	 * 
	 * 方法描述：主页的欢迎页面
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月2日 上午9:36:01
	 */
	@RequestMapping("/getWelcome")
	public String getWelcomePage() {
		return "main/welcome";
	}

	/**
	 * 
	 * 方法描述：加载左侧菜单
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月2日 上午10:33:56
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSidebar")
	public String getSidebar(HttpServletRequest request) {
		List<HyxhMenu> hyxhMenus = iUserService.getAllMenus();
		List<HyxhMenu> nodeHyxhMenus = new ArrayList<HyxhMenu>();

		for (HyxhMenu hyxhMenu : hyxhMenus) {
			if (hyxhMenu.getiPid().equals(0)) {
				nodeHyxhMenus.add(hyxhMenu);
			}
		}
		for (HyxhMenu hyxhMenu : nodeHyxhMenus) {
			for (HyxhMenu subMenu : hyxhMenus) {
				if (subMenu.getiPid().equals(hyxhMenu.getId())) {
					hyxhMenu.getSubhHyxhMenus().add(subMenu);
				}
			}
		}
		request.setAttribute("nodeResources", nodeHyxhMenus);

		return "main/getSlidebar";
	}
}
