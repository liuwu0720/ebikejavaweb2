/**
 * 文件名：IDataService.java
 * 版本信息：Version 1.0
 * 日期：2016年4月21日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.service;

import java.io.InputStream;

import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.node.model.FileRecord;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月21日 下午4:59:59
 */
public interface IDataService {
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

	/**
	 * 方法描述：
	 * 
	 * @param inputStream
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月21日 下午4:52:28
	 */
	String updateReadExcel(InputStream inputStream);

	
	/**
	  * 方法描述：
	  * @param wcfFC
	  * @param wcfFC2
	  * @param ws7 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月5日 下午5:23:00
	 * @throws WriteException 
	 * @throws RowsExceededException 
	  */
	void createDdcDriver(WritableCellFormat wcfFC, WritableCellFormat wcfFC2,
			WritableSheet ws7) throws RowsExceededException, WriteException;

	
	/**
	  * 方法描述：
	  * @param wcfFC
	  * @param wcfFC2
	  * @param ws9 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月11日 下午6:15:06
	  */
	void createDdcDriverDaxxb(WritableCellFormat wcfFC,
			WritableCellFormat wcfFC2, WritableSheet ws9) throws RowsExceededException, WriteException;
}
