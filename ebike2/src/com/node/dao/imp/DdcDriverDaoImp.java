/**
 * 文件名：DdcDriverDaoImp.java
 * 版本信息：Version 1.0
 * 日期：2016年4月16日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.node.dao.IDdcDriverDao;
import com.node.model.DdcDriver;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月16日 下午7:15:51
 */
@Repository
public class DdcDriverDaoImp extends GenericHibernateDao<DdcDriver, Long>
		implements IDdcDriverDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
		/* (non-Javadoc)
		 * @see com.node.dao.IDdcDriverDao#updateBySql(java.lang.String)
		 */
	@Override
	public void updateBySql(String sql2) {
		// TODO Auto-generated method stub
		  int row=	jdbcTemplate.update(sql2);
		  System.out.println("row="+row);
		
	}

}
