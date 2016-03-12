/**
 * 文件名：HyxhMenuDaoImp.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.dao.imp;

import org.springframework.stereotype.Repository;

import com.node.dao.IHyxhMenuDao;
import com.node.model.HyxhMenu;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月11日 下午5:33:22
 */
@Repository
public class HyxhMenuDaoImp extends GenericHibernateDao<HyxhMenu, Long>
		implements IHyxhMenuDao {

}
