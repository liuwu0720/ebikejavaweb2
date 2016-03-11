package com.node.service;

import java.util.List;

import com.node.model.DdcHyxhBase;
import com.node.model.HyxhMenu;

public interface IUserService {

	/**
	 * 方法描述：
	 * 
	 * @param cuser
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月11日 下午4:05:07
	 */
	DdcHyxhBase getByUserAccount(String cuser);

	/**
	 * 方法描述：
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月11日 下午5:31:24
	 */
	List<HyxhMenu> getAllMenus();

}
