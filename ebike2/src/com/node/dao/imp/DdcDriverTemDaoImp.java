/**
 * 文件名：DdcDriverTemDaoImp.java
 * 版本信息：Version 1.0
 * 日期：2016年6月5日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.dao.imp;

import org.springframework.stereotype.Repository;

import com.node.dao.IDdcDriverTempDao;
import com.node.model.DdcDriverTemp;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月5日 上午9:44:49
 */
@Repository
public class DdcDriverTemDaoImp extends
		GenericHibernateDao<DdcDriverTemp, Long> implements
		IDdcDriverTempDao {

}
