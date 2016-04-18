/**
 * 文件名：IEbikeService.java
 * 版本信息：Version 1.0
 * 日期：2016年3月16日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service;

import java.util.List;
import java.util.Map;

import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;

import com.node.model.DdcDaxxb;
import com.node.model.DdcDaxxbLog;
import com.node.model.DdcFlow;
import com.node.model.DdcHyxhSsdw;
import com.node.model.FileRecord;
import com.node.util.HqlHelper;
import com.node.util.Page;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月16日 下午5:02:00
 */
public interface IEbikeService {

	/**
	 * 方法描述：
	 * 
	 * @param hql
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午5:03:42
	 */
	Map<String, Object> queryByHql(HqlHelper hql);

	/**
	 * 方法描述：
	 * 
	 * @param zzjgdmzh
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午5:16:44
	 */
	String findByProPerties(String zzjgdmzh);

	/**
	 * 方法描述：
	 * 
	 * @param sql
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午6:28:00
	 */
	Map<String, Object> queryBySpringSql(String sql, Page page);

	/**
	 * 方法描述：
	 * 
	 * @param sql
	 * @param p
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午6:36:16
	 */
	Map<String, Object> queryBySpringHql(String sql, Page p);

	/**
	 * 方法描述：
	 * 
	 * @param sbId
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 上午10:02:03
	 */
	DdcDaxxb getById(long sbId);

	/**
	 * 方法描述：
	 * 
	 * @param daxxb
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午7:51:13
	 */
	void update(DdcDaxxb daxxb);

	/**
	 * 方法描述：
	 * 
	 * @param daxxbLog
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午8:11:46
	 */
	void saveDdcDaxxbLog(DdcDaxxbLog daxxbLog);

	/**
	 * 方法描述：
	 * 
	 * @param ddcFlow
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午10:06:10
	 */
	void saveDdcFlow(DdcFlow ddcFlow);

	/**
	 * 方法描述：
	 * 
	 * @param flowId
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 下午6:27:07
	 */
	DdcFlow getFlowById(long flowId);

	/**
	 * 方法描述：
	 * 
	 * @param ddcFlow
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月13日 下午8:03:41
	 */
	void updateDdcFlow(DdcFlow ddcFlow);

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdw
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月13日 下午8:04:05
	 */
	void updateDdcHyxhSsdw(DdcHyxhSsdw ddcHyxhSsdw);

	/**
	 * 方法描述：
	 * 
	 * @param string
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月14日 下午5:30:53
	 */
	List<DdcDaxxb> findDdcDaxxbsByFlag(String flag);

	/**
	 * 方法描述：
	 * 
	 * @param wcfFC
	 * @param wcfFC2
	 * @param ws
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月14日 下午7:13:28
	 * @throws Exception
	 */
	void createDaxxbExcel(WritableCellFormat wcfFC, WritableCellFormat wcfFC2,
			WritableSheet ws) throws Exception;

	/**
	 * 方法描述：
	 * 
	 * @param wcfFC
	 * @param wcfFC2
	 * @param ws2
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月14日 下午7:23:08
	 * @throws Exception
	 */
	void createApproveUsers(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws2) throws Exception;

	/**
	 * 方法描述：
	 * 
	 * @param wcfFC
	 * @param wcfFC2
	 * @param ws3
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月17日 下午9:28:20
	 * @throws Exception
	 */
	void createDdcflows(WritableCellFormat wcfFC, WritableCellFormat wcfFC2,
			WritableSheet ws3) throws Exception;

	/**
	 * 方法描述：
	 * 
	 * @param wcfFC
	 * @param wcfFC2
	 * @param ws4
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月18日 下午12:05:45
	 */
	void createDdcHyxhBasb(WritableCellFormat wcfFC, WritableCellFormat wcfFC2,
			WritableSheet ws4) throws Exception;

	/**
	 * 方法描述：
	 * 
	 * @param wcfFC
	 * @param wcfFC2
	 * @param ws5
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月18日 下午12:37:54
	 */
	void createDdcHyxhBase(WritableCellFormat wcfFC, WritableCellFormat wcfFC2,
			WritableSheet ws5) throws Exception;

	/**
	 * 方法描述：
	 * 
	 * @param wcfFC
	 * @param wcfFC2
	 * @param ws6
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月18日 下午1:59:21
	 */
	void createDdcHyxhSsdw(WritableCellFormat wcfFC, WritableCellFormat wcfFC2,
			WritableSheet ws6) throws Exception;

	/**
	 * 方法描述：
	 * 
	 * @param wcfFC
	 * @param wcfFC2
	 * @param ws7
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月18日 下午2:34:59
	 * @throws Exception
	 */
	void createDdcHyxhSsdwClSb(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws7) throws Exception;

	/**
	 * 方法描述：
	 * 
	 * @param fileRecord
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月18日 下午3:25:23
	 */
	void saveFileRecord(FileRecord fileRecord);

}
