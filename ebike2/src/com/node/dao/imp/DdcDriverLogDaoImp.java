/**
  * 文件名：DdcDriverLogDaoImp.java
  * 版本信息：Version 1.0
  * 日期：2016年6月21日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.dao.imp;

import org.springframework.stereotype.Repository;

import com.node.dao.IDdcDriverLogDao;
import com.node.model.DdcDriverLog;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月21日 下午10:31:16 
 */
@Repository
public class DdcDriverLogDaoImp extends GenericHibernateDao<DdcDriverLog, Long>
implements IDdcDriverLogDao {

}
