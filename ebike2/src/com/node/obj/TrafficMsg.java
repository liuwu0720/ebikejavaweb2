/**
  * 文件名：TrafficMsg.java
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
 * @version: 2016年8月8日 下午2:17:58 
 */
public class TrafficMsg implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 8955851617101962068L;

	private String billNo;
	private String carOwner;
	private String licensePlateNo;
	private String licensePlateType;
	private String illegalTime;
	private String illegalAddr;
	private String illegalDesc;
	private String punishAmt;
	private Integer punishScore;
	private String isNeedClaim;
	/**
	 * @return billNo : return the property billNo.
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * @param billNo : set the property billNo.
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * @return carOwner : return the property carOwner.
	 */
	public String getCarOwner() {
		return carOwner;
	}
	/**
	 * @param carOwner : set the property carOwner.
	 */
	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}
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
	/**
	 * @return illegalTime : return the property illegalTime.
	 */
	public String getIllegalTime() {
		return illegalTime;
	}
	/**
	 * @param illegalTime : set the property illegalTime.
	 */
	public void setIllegalTime(String illegalTime) {
		this.illegalTime = illegalTime;
	}
	/**
	 * @return illegalAddr : return the property illegalAddr.
	 */
	public String getIllegalAddr() {
		return illegalAddr;
	}
	/**
	 * @param illegalAddr : set the property illegalAddr.
	 */
	public void setIllegalAddr(String illegalAddr) {
		this.illegalAddr = illegalAddr;
	}
	/**
	 * @return illegalDesc : return the property illegalDesc.
	 */
	public String getIllegalDesc() {
		return illegalDesc;
	}
	/**
	 * @param illegalDesc : set the property illegalDesc.
	 */
	public void setIllegalDesc(String illegalDesc) {
		this.illegalDesc = illegalDesc;
	}
	/**
	 * @return punishAmt : return the property punishAmt.
	 */
	public String getPunishAmt() {
		return punishAmt;
	}
	/**
	 * @param punishAmt : set the property punishAmt.
	 */
	public void setPunishAmt(String punishAmt) {
		this.punishAmt = punishAmt;
	}
	/**
	 * @return punishScore : return the property punishScore.
	 */
	public Integer getPunishScore() {
		return punishScore;
	}
	/**
	 * @param punishScore : set the property punishScore.
	 */
	public void setPunishScore(Integer punishScore) {
		this.punishScore = punishScore;
	}
	/**
	 * @return isNeedClaim : return the property isNeedClaim.
	 */
	public String getIsNeedClaim() {
		return isNeedClaim;
	}
	/**
	 * @param isNeedClaim : set the property isNeedClaim.
	 */
	public void setIsNeedClaim(String isNeedClaim) {
		this.isNeedClaim = isNeedClaim;
	}
	
}
