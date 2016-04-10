/**
 * 文件名：RoleMenuDaoImp.java
 * 版本信息：Version 1.0
 * 日期：2016年4月8日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.dao.imp;

import org.springframework.stereotype.Repository;

import com.node.dao.IRoleMenuDao;
import com.node.model.RoleMenu;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月8日 下午6:03:36
 */
@Repository
public class RoleMenuDaoImp extends GenericHibernateDao<RoleMenu, Integer>
		implements IRoleMenuDao {

}
