/**
 * 文件名：UserServiceImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.node.dao.IDdcHyxhBaseDao;
import com.node.dao.IDdcHyxhSsdwDao;
import com.node.dao.IHyxhMenuDao;
import com.node.dao.IRoleMenuDao;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.HyxhMenu;
import com.node.model.RoleMenu;
import com.node.service.IUserService;
import com.node.util.SystemConstants;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月11日 下午2:27:12
 */
@Service
public class UserServiceImp implements IUserService {
	@Autowired
	IDdcHyxhBaseDao iDdcHyxhBaseDao;

	@Autowired
	IHyxhMenuDao iHyxhMenuDao;

	@Autowired
	IRoleMenuDao iRoleMenuDao;

	@Autowired
	IDdcHyxhSsdwDao iDdcHyxhSsdwDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IUserService#getByUserAccount(java.lang.String)
	 */
	@Override
	public DdcHyxhBase getByUserAccount(String cuser) {
		List<DdcHyxhBase> ddcHyxhBases = iDdcHyxhBaseDao.findByProperty(
				"hyxhzh", cuser);
		if (CollectionUtils.isEmpty(ddcHyxhBases)) {
			return null;
		} else {
			return ddcHyxhBases.get(0);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IUserService#getAllMenus()
	 */
	@Override
	public List<HyxhMenu> getAllMenus(HttpServletRequest request) {
		Object object = request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		if (object.getClass().getSimpleName()
				.equals(SystemConstants.CLASS_NAME_DDC_HYXHBASE)) {
			DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
					.getAttribute(SystemConstants.SESSION_USER);

			if (ddcHyxhBase != null) {
				RoleMenu roleMenu = iRoleMenuDao.findByProperty("role",
						Integer.parseInt(SystemConstants.ROLE_HYXH)).get(0);
				String[] roleArray = roleMenu.getMenu().split(",");
				List<HyxhMenu> hyxhMenus = new ArrayList<HyxhMenu>();
				for (String role : roleArray) {
					HyxhMenu hyxhMenu = iHyxhMenuDao
							.get(Integer.parseInt(role));
					if (hyxhMenu != null) {
						hyxhMenus.add(hyxhMenu);
					}

				}

				return hyxhMenus;
			}
		} else {
			RoleMenu roleMenu = iRoleMenuDao.findByProperty("role",
					Integer.parseInt(SystemConstants.ROLE_SSDW)).get(0);
			String[] roleArray = roleMenu.getMenu().split(",");
			List<HyxhMenu> hyxhMenus = new ArrayList<HyxhMenu>();
			for (String role : roleArray) {
				HyxhMenu hyxhMenu = iHyxhMenuDao.get(Integer.parseInt(role));
				if (hyxhMenu != null) {
					hyxhMenus.add(hyxhMenu);
				}
			}

			return hyxhMenus;
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IUserService#save(com.node.model.DdcHyxhBase)
	 */
	@Override
	public void save(DdcHyxhBase ddcHyxhBase) {
		// TODO Auto-generated method stub
		iDdcHyxhBaseDao.save(ddcHyxhBase);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IUserService#update(com.node.model.DdcHyxhBase)
	 */
	@Override
	public void update(DdcHyxhBase ddcHyxhBase) {
		// TODO Auto-generated method stub
		iDdcHyxhBaseDao.updateCleanBefore(ddcHyxhBase);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IUserService#getById(java.lang.Long)
	 */
	@Override
	public DdcHyxhBase getById(Long id) {
		// TODO Auto-generated method stub
		return iDdcHyxhBaseDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.node.service.IUserService#getSsdwByUserCode(java.lang.String)
	 */
	@Override
	public DdcHyxhSsdw getSsdwByUserCode(String cuser) {
		List<DdcHyxhSsdw> ddcHyxhSsdws = iDdcHyxhSsdwDao.findByProperty(
				"userCode", cuser);
		if (CollectionUtils.isEmpty(ddcHyxhSsdws)) {
			return null;
		} else {
			return ddcHyxhSsdws.get(0);
		}
	}
}
