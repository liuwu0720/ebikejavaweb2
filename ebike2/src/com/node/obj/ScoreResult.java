/**
  * 文件名：ScoreResult.java
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
 * @version: 2016年8月8日 上午11:01:15 
 */
public class ScoreResult implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -6857069290900179052L;
	private String ddts;
	private String dtjg;
	private String dtpc;
	private String dtrq;
	/**
	 * @return ddts : return the property ddts.
	 */
	public String getDdts() {
		return ddts;
	}
	/**
	 * @param ddts : set the property ddts.
	 */
	public void setDdts(String ddts) {
		this.ddts = ddts;
	}
	/**
	 * @return dtjg : return the property dtjg.
	 */
	public String getDtjg() {
		return dtjg;
	}
	/**
	 * @param dtjg : set the property dtjg.
	 */
	public void setDtjg(String dtjg) {
		this.dtjg = dtjg;
	}
	/**
	 * @return dtpc : return the property dtpc.
	 */
	public String getDtpc() {
		return dtpc;
	}
	/**
	 * @param dtpc : set the property dtpc.
	 */
	public void setDtpc(String dtpc) {
		this.dtpc = dtpc;
	}
	/**
	 * @return dtrq : return the property dtrq.
	 */
	public String getDtrq() {
		return dtrq;
	}
	/**
	 * @param dtrq : set the property dtrq.
	 */
	public void setDtrq(String dtrq) {
		this.dtrq = dtrq;
	}
	

}
