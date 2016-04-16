package com.node.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.HyxhMenu;
import com.node.util.HqlHelper;

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
	List<HyxhMenu> getAllMenus(HttpServletRequest request);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhBase
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午6:56:36
	 */
	void save(DdcHyxhBase ddcHyxhBase);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhBase
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午6:57:03
	 */
	void update(DdcHyxhBase ddcHyxhBase);

	/**
	 * 方法描述：
	 * 
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午7:00:17
	 */
	DdcHyxhBase getById(Long id);

	/**
	 * 方法描述：
	 * 
	 * @param cuser
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月8日 下午2:24:06
	 */
	DdcHyxhSsdw getSsdwByUserCode(String cuser);

	/**
	 * 方法描述：
	 * 
	 * @param hql
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月14日 下午4:57:34
	 */
	Map<String, Object> queryFileRecordByHql(HqlHelper hql);

}
