/**
  * 文件名：TrafficData.java
  * 版本信息：Version 1.0
  * 日期：2016年8月8日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.obj;

import java.io.Serializable;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年8月8日 下午3:16:57 
 */
public class TrafficData implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1571795353414101020L;
	private String licensePlateNo;
	private String licensePlateType;
	/**
	 * @return licensePlateNo : return the property licensePlateNo.
	 */
	public String getLicensePlateNo() {
		return licensePlateNo;
	}
	/**
	 * @param licensePlateNo : set the property licensePlateNo.
	 */
	public void setLicensePlateNo(String licensePlateNo) {
		this.licensePlateNo = licensePlateNo;
	}
	/**
	 * @return licensePlateType : return the property licensePlateType.
	 */
	public String getLicensePlateType() {
		return licensePlateType;
	}
	/**
	 * @param licensePlateType : set the property licensePlateType.
	 */
	public void setLicensePlateType(String licensePlateType) {
		this.licensePlateType = licensePlateType;
	}
	
}
