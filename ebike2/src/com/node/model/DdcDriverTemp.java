/**
  * 文件名：DdcDriverTemp.java
  * 版本信息：Version 1.0
  * 日期：2016年6月5日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.model;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月5日 上午9:28:48 
 */
@Entity
@Table(name = "DDC_DRIVER_TEMP")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class DdcDriverTemp  implements java.io.Serializable {

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 3277865891284287329L;
	private Long id;
	private String vcUserName;
	private String vcUserCard;
	private String vcTelPhone;
	@SequenceGenerator(name = "DDC_DRIVER_TEMP", sequenceName = "SEQ_DDC_DRIVER_TEMP", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "DDC_DRIVER_TEMP")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}
	/**
	 * @param id : set the property id.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "VC_USERNAME", length = 50)
	public String getVcUserName() {
		return vcUserName;
	}
	/**
	 * @param vcUserName : set the property vcUserName.
	 */
	public void setVcUserName(String vcUserName) {
		this.vcUserName = vcUserName;
	}
	@Column(name = "VC_USERCARD", unique = true, nullable = false, length = 50)
	public String getVcUserCard() {
		return vcUserCard;
	}
	/**
	 * @param vcUserCard : set the property vcUserCard.
	 */
	public void setVcUserCard(String vcUserCard) {
		this.vcUserCard = vcUserCard;
	}
	@Column(name = "VC_TELPHONE", length = 50)
	public String getVcTelPhone() {
		return vcTelPhone;
	}
	/**
	 * @param vcTelPhone : set the property vcTelPhone.
	 */
	public void setVcTelPhone(String vcTelPhone) {
		this.vcTelPhone = vcTelPhone;
	}
	
}
