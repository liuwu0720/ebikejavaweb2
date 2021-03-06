package com.node.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * DdcHyxhSsdwclsb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DDC_HYXH_SSDWCLSB", uniqueConstraints = @UniqueConstraint(columnNames = "LSH"))
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class DdcHyxhSsdwclsb implements java.io.Serializable {

	// Fields

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 204700208717659645L;
	private Long id;
	private String lsh;
	private String hyxhzh;
	private String hyxhzhName;
	private String ssdwId;// 所属单位ID,对应 DDC_HYXH_SSDW的记录ID
	private String ssdwName;// 申报单位名称
	private String cphm;
	private String ppxh;
	private String cysy;// 车身颜色code
	private String cysyName;// 车身颜色
	private String djh;
	private String jtzz;
	private String jsrxm1;
	private String xb1;
	private String sfzmhm1;
	private String lxdh1;
	private String jsrxm2;
	private String xb2;
	private String sfzmhm2;
	private String lxdh2;
	private String xsqy;// 行驶区域
	private String xsqyName;
	private String bz;
	private String sqr;
	private Date sqrq;
	private String slzl;
	private String slyj;
	private String slbz;
	private String slr;
	private Date slrq;
	private String slbm;
	private String synFlag;
	private String tranFlag;
	private Date tranDate;
	private String sqip;
	private String vcEbikeImg;// 车身图片
	private String vcUser1Img;// 驾驶人1图片
	private String vcUser2Img;// 驾驶人2图片
	private String vcShowEbikeImg;
	private String vcShowUser1Img;
	private String vcShowUser2Img;
	private Integer slIndex;// 受理审批顺序 0-等待行业协会审批 1-民敬审批
	private String tbyy;// 退办原因
	private String vcUser1CardImg1;// 驾驶人1身份证照片正面
	private String vcUser1CardImg2;// 驾驶人1身份证照片反面
	private String vcUser2CardImg1;// 驾驶人2身份证照片正面
	private String vcUser2CardImg2;// 驾驶人2身份证照片反面
	private String vcEbikeInvoiceImg;// 购车发票
	private String vcUser1CardImg1Show;
	private String vcUser1CardImg2Show;// 驾驶人1身份证照片反面
	private String vcUser2CardImg1Show;// 驾驶人2身份证照片正面
	private String vcUser2CardImg2Show;// 驾驶人2身份证照片反面
	private String vcEbikeInvoiceImgShow;
	private Integer nEnable;
	private String note;
	

	// 201606增加
	private String vcUser1WorkImg;//驾驶人1 在职证明或居住证
	private String vcUser2WorkImg;//驾驶人2 在职证明或居住证
	private String vcQualifiedImg;//车辆合格证
	private String vcEbikeInsuranceImg;//投保凭证
	private String vcUser1WorkImgShow;//驾驶人1 在职证明或居住证
	private String vcUser2WorkImgShow;//驾驶人2 在职证明或居住证
	private String vcQualifiedImgShow;//车辆合格证
	private String vcEbikeInsuranceImgShow;//投保凭证
	private String vcJsr1State;
	private String vcJsr2State;
	
	

	@Transient
	public String getVcJsr1State() {
		return vcJsr1State;
	}

	/**
	 * @param vcJsr1State : set the property vcJsr1State.
	 */
	public void setVcJsr1State(String vcJsr1State) {
		this.vcJsr1State = vcJsr1State;
	}

	@Transient
	public String getVcJsr2State() {
		return vcJsr2State;
	}

	/**
	 * @param vcJsr2State : set the property vcJsr2State.
	 */
	public void setVcJsr2State(String vcJsr2State) {
		this.vcJsr2State = vcJsr2State;
	}

	@Transient
	public String getVcUser1WorkImgShow() {
		return vcUser1WorkImgShow;
	}

	/**
	 * @param vcUser1WorkImgShow : set the property vcUser1WorkImgShow.
	 */
	public void setVcUser1WorkImgShow(String vcUser1WorkImgShow) {
		this.vcUser1WorkImgShow = vcUser1WorkImgShow;
	}

	@Transient
	public String getVcUser2WorkImgShow() {
		return vcUser2WorkImgShow;
	}

	/**
	 * @param vcUser2WorkImgShow : set the property vcUser2WorkImgShow.
	 */
	public void setVcUser2WorkImgShow(String vcUser2WorkImgShow) {
		this.vcUser2WorkImgShow = vcUser2WorkImgShow;
	}

	@Transient
	public String getVcQualifiedImgShow() {
		return vcQualifiedImgShow;
	}

	/**
	 * @param vcQualifiedImgShow : set the property vcQualifiedImgShow.
	 */
	public void setVcQualifiedImgShow(String vcQualifiedImgShow) {
		this.vcQualifiedImgShow = vcQualifiedImgShow;
	}

	@Transient
	public String getVcEbikeInsuranceImgShow() {
		return vcEbikeInsuranceImgShow;
	}

	/**
	 * @param vcEbikeInsuranceImgShow : set the property vcEbikeInsuranceImgShow.
	 */
	public void setVcEbikeInsuranceImgShow(String vcEbikeInsuranceImgShow) {
		this.vcEbikeInsuranceImgShow = vcEbikeInsuranceImgShow;
	}

	@Column(name = "VC_USER1WORKIMG", length = 100)
	public String getVcUser1WorkImg() {
		return vcUser1WorkImg;
	}

	/**
	 * @param vcUser1WorkImg : set the property vcUser1WorkImg.
	 */
	public void setVcUser1WorkImg(String vcUser1WorkImg) {
		this.vcUser1WorkImg = vcUser1WorkImg;
	}

	@Column(name = "VC_USER2WORKIMG", length = 100)
	public String getVcUser2WorkImg() {
		return vcUser2WorkImg;
	}

	/**
	 * @param vcUser2WorkImg : set the property vcUser2WorkImg.
	 */
	public void setVcUser2WorkImg(String vcUser2WorkImg) {
		this.vcUser2WorkImg = vcUser2WorkImg;
	}

	@Column(name = "VC_EBIKE_QUALIFIEDIMG", length = 100)
	public String getVcQualifiedImg() {
		return vcQualifiedImg;
	}

	/**
	 * @param vcQualifiedImg : set the property vcQualifiedImg.
	 */
	public void setVcQualifiedImg(String vcQualifiedImg) {
		this.vcQualifiedImg = vcQualifiedImg;
	}

	@Column(name = "VC_EBIKE_INSURANCEIMG", length = 100)
	public String getVcEbikeInsuranceImg() {
		return vcEbikeInsuranceImg;
	}

	/**
	 * @param vcEbikeInsuranceImg : set the property vcEbikeInsuranceImg.
	 */
	public void setVcEbikeInsuranceImg(String vcEbikeInsuranceImg) {
		this.vcEbikeInsuranceImg = vcEbikeInsuranceImg;
	}

	// Constructors

	/** default constructor */
	public DdcHyxhSsdwclsb() {
	}

	/** minimal constructor */
	public DdcHyxhSsdwclsb(String lsh) {
		this.lsh = lsh;
	}

	@Id
	@SequenceGenerator(name = "DDC_HYXH_SSDWCLSB", sequenceName = "SEQ_HYXH_SSDWCLSB_XH", allocationSize = 1)
	@GeneratedValue(strategy = SEQUENCE, generator = "DDC_HYXH_SSDWCLSB")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LSH", unique = true, nullable = false, length = 50)
	public String getLsh() {
		return this.lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	@Column(name = "HYXHZH", length = 50)
	public String getHyxhzh() {
		return this.hyxhzh;
	}

	public void setHyxhzh(String hyxhzh) {
		this.hyxhzh = hyxhzh;
	}

	@Column(name = "SSDWID", length = 200)
	public String getSsdwId() {
		return this.ssdwId;
	}

	public void setSsdwId(String ssdwId) {
		this.ssdwId = ssdwId;
	}

	@Column(name = "CPHM", length = 50)
	public String getCphm() {
		return this.cphm;
	}

	public void setCphm(String cphm) {
		this.cphm = cphm;
	}

	@Column(name = "PPXH", length = 50)
	public String getPpxh() {
		return this.ppxh;
	}

	public void setPpxh(String ppxh) {
		this.ppxh = ppxh;
	}

	@Column(name = "CYSY", length = 50)
	public String getCysy() {
		return this.cysy;
	}

	public void setCysy(String cysy) {
		this.cysy = cysy;
	}

	@Column(name = "DJH", length = 50)
	public String getDjh() {
		return this.djh;
	}

	public void setDjh(String djh) {
		this.djh = djh;
	}

	@Column(name = "JTZZ", length = 50)
	public String getJtzz() {
		return this.jtzz;
	}

	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}

	@Column(name = "JSRXM1", length = 50)
	public String getJsrxm1() {
		return this.jsrxm1;
	}

	public void setJsrxm1(String jsrxm1) {
		this.jsrxm1 = jsrxm1;
	}

	@Column(name = "XB1", length = 50)
	public String getXb1() {
		return this.xb1;
	}

	public void setXb1(String xb1) {
		this.xb1 = xb1;
	}

	@Column(name = "SFZMHM1", length = 50)
	public String getSfzmhm1() {
		return this.sfzmhm1;
	}

	public void setSfzmhm1(String sfzmhm1) {
		this.sfzmhm1 = sfzmhm1;
	}

	@Column(name = "LXDH1", length = 50)
	public String getLxdh1() {
		return this.lxdh1;
	}

	public void setLxdh1(String lxdh1) {
		this.lxdh1 = lxdh1;
	}

	@Column(name = "JSRXM2", length = 50)
	public String getJsrxm2() {
		return this.jsrxm2;
	}

	public void setJsrxm2(String jsrxm2) {
		this.jsrxm2 = jsrxm2;
	}

	@Column(name = "XB2", length = 50)
	public String getXb2() {
		return this.xb2;
	}

	public void setXb2(String xb2) {
		this.xb2 = xb2;
	}

	@Column(name = "SFZMHM2", length = 50)
	public String getSfzmhm2() {
		return this.sfzmhm2;
	}

	public void setSfzmhm2(String sfzmhm2) {
		this.sfzmhm2 = sfzmhm2;
	}

	@Column(name = "LXDH2", length = 50)
	public String getLxdh2() {
		return this.lxdh2;
	}

	public void setLxdh2(String lxdh2) {
		this.lxdh2 = lxdh2;
	}


	@Column(name = "XSQY", length = 50)
	public String getXsqy() {
		return this.xsqy;
	}

	public void setXsqy(String xsqy) {
		this.xsqy = xsqy;
	}

	@Column(name = "BZ", length = 500)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name = "SQR", length = 50)
	public String getSqr() {
		return this.sqr;
	}

	public void setSqr(String sqr) {
		this.sqr = sqr;
	}

	@Column(name = "SQRQ", length = 7)
	public Date getSqrq() {
		return this.sqrq;
	}

	public void setSqrq(Date sqrq) {
		this.sqrq = sqrq;
	}

	@Column(name = "SLZL", length = 100)
	public String getSlzl() {
		return this.slzl;
	}

	public void setSlzl(String slzl) {
		this.slzl = slzl;
	}

	@Column(name = "SLYJ", length = 10)
	public String getSlyj() {
		return this.slyj;
	}

	public void setSlyj(String slyj) {
		this.slyj = slyj;
	}

	@Column(name = "SLBZ", length = 100)
	public String getSlbz() {
		return this.slbz;
	}

	public void setSlbz(String slbz) {
		this.slbz = slbz;
	}

	@Column(name = "SLR", length = 20)
	public String getSlr() {
		return this.slr;
	}

	public void setSlr(String slr) {
		this.slr = slr;
	}

	@Column(name = "SLRQ", length = 7)
	public Date getSlrq() {
		return this.slrq;
	}

	public void setSlrq(Date slrq) {
		this.slrq = slrq;
	}

	@Column(name = "SLBM", length = 20)
	public String getSlbm() {
		return this.slbm;
	}

	public void setSlbm(String slbm) {
		this.slbm = slbm;
	}

	@Column(name = "SYN_FLAG", length = 10)
	public String getSynFlag() {
		return this.synFlag;
	}

	public void setSynFlag(String synFlag) {
		this.synFlag = synFlag;
	}

	@Column(name = "TRAN_FLAG", length = 10)
	public String getTranFlag() {
		return this.tranFlag;
	}

	public void setTranFlag(String tranFlag) {
		this.tranFlag = tranFlag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRAN_DATE", length = 7)
	public Date getTranDate() {
		return this.tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	@Column(name = "SQIP", length = 50)
	public String getSqip() {
		return this.sqip;
	}

	public void setSqip(String sqip) {
		this.sqip = sqip;
	}

	@Transient
	public String getSsdwName() {
		return ssdwName;
	}

	/**
	 * @param ssdwName
	 *            : set the property ssdwName.
	 */
	public void setSsdwName(String ssdwName) {
		this.ssdwName = ssdwName;
	}

	@Transient
	public String getCysyName() {
		return cysyName;
	}

	/**
	 * @param cysyName
	 *            : set the property cysyName.
	 */
	public void setCysyName(String cysyName) {
		this.cysyName = cysyName;
	}

	@Transient
	public String getXsqyName() {
		return xsqyName;
	}

	/**
	 * @param xsqyName
	 *            : set the property xsqyName.
	 */
	public void setXsqyName(String xsqyName) {
		this.xsqyName = xsqyName;
	}

	@Column(name = "VC_EBIKE_IMG", length = 100)
	public String getVcEbikeImg() {
		return vcEbikeImg;
	}

	/**
	 * @param vcEbikeImg
	 *            : set the property vcEbikeImg.
	 */
	public void setVcEbikeImg(String vcEbikeImg) {
		this.vcEbikeImg = vcEbikeImg;
	}

	@Column(name = "VC_USER1IMG", length = 100)
	public String getVcUser1Img() {
		return vcUser1Img;
	}

	/**
	 * @param vcUser1Img
	 *            : set the property vcUser1Img.
	 */
	public void setVcUser1Img(String vcUser1Img) {
		this.vcUser1Img = vcUser1Img;
	}

	@Column(name = "VC_USER2IMG", length = 100)
	public String getVcUser2Img() {
		return vcUser2Img;
	}

	/**
	 * @param vcUser2Img
	 *            : set the property vcUser2Img.
	 */
	public void setVcUser2Img(String vcUser2Img) {
		this.vcUser2Img = vcUser2Img;
	}

	@Transient
	public String getVcShowEbikeImg() {
		return vcShowEbikeImg;
	}

	/**
	 * @param vcShowEbikeImg
	 *            : set the property vcShowEbikeImg.
	 */
	public void setVcShowEbikeImg(String vcShowEbikeImg) {
		this.vcShowEbikeImg = vcShowEbikeImg;
	}

	@Transient
	public String getVcShowUser1Img() {
		return vcShowUser1Img;
	}

	/**
	 * @param vcShowUser1Img
	 *            : set the property vcShowUser1Img.
	 */
	public void setVcShowUser1Img(String vcShowUser1Img) {
		this.vcShowUser1Img = vcShowUser1Img;
	}

	@Transient
	public String getVcShowUser2Img() {
		return vcShowUser2Img;
	}

	/**
	 * @param vcShowUser2Img
	 *            : set the property vcShowUser2Img.
	 */
	public void setVcShowUser2Img(String vcShowUser2Img) {
		this.vcShowUser2Img = vcShowUser2Img;
	}

	@Column(name = "TBYY", length = 100)
	public String getTbyy() {
		return tbyy;
	}

	/**
	 * @param tbyy
	 *            : set the property tbyy.
	 */
	public void setTbyy(String tbyy) {
		this.tbyy = tbyy;
	}

	@Column(name = "SL_INDEX")
	public Integer getSlIndex() {
		return slIndex;
	}

	/**
	 * @param slIndex
	 *            : set the property slIndex.
	 */
	public void setSlIndex(Integer slIndex) {
		this.slIndex = slIndex;
	}

	@Column(name = "VC_USER1_CARDIMG1", length = 100)
	public String getVcUser1CardImg1() {
		return vcUser1CardImg1;
	}

	/**
	 * @param vcUser1CardImg1
	 *            : set the property vcUser1CardImg1.
	 */
	public void setVcUser1CardImg1(String vcUser1CardImg1) {
		this.vcUser1CardImg1 = vcUser1CardImg1;
	}

	@Column(name = "VC_USER1_CARDIMG2", length = 100)
	public String getVcUser1CardImg2() {
		return vcUser1CardImg2;
	}

	/**
	 * @param vcUser1CardImg2
	 *            : set the property vcUser1CardImg2.
	 */
	public void setVcUser1CardImg2(String vcUser1CardImg2) {
		this.vcUser1CardImg2 = vcUser1CardImg2;
	}

	@Column(name = "VC_USER2_CARDIMG1", length = 100)
	public String getVcUser2CardImg1() {
		return vcUser2CardImg1;
	}

	/**
	 * @param vcUser2CardImg1
	 *            : set the property vcUser2CardImg1.
	 */
	public void setVcUser2CardImg1(String vcUser2CardImg1) {
		this.vcUser2CardImg1 = vcUser2CardImg1;
	}

	@Column(name = "VC_USER2_CARDIMG2", length = 100)
	public String getVcUser2CardImg2() {
		return vcUser2CardImg2;
	}

	/**
	 * @param vcUser2CardImg2
	 *            : set the property vcUser2CardImg2.
	 */
	public void setVcUser2CardImg2(String vcUser2CardImg2) {
		this.vcUser2CardImg2 = vcUser2CardImg2;
	}

	@Transient
	public String getVcUser1CardImg1Show() {
		return vcUser1CardImg1Show;
	}

	/**
	 * @param vcUser1CardImg1Show
	 *            : set the property vcUser1CardImg1Show.
	 */
	public void setVcUser1CardImg1Show(String vcUser1CardImg1Show) {
		this.vcUser1CardImg1Show = vcUser1CardImg1Show;
	}

	@Transient
	public String getVcUser1CardImg2Show() {
		return vcUser1CardImg2Show;
	}

	/**
	 * @param vcUser1CardImg2Show
	 *            : set the property vcUser1CardImg2Show.
	 */
	public void setVcUser1CardImg2Show(String vcUser1CardImg2Show) {
		this.vcUser1CardImg2Show = vcUser1CardImg2Show;
	}

	@Transient
	public String getVcUser2CardImg1Show() {
		return vcUser2CardImg1Show;
	}

	/**
	 * @param vcUser2CardImg1Show
	 *            : set the property vcUser2CardImg1Show.
	 */
	public void setVcUser2CardImg1Show(String vcUser2CardImg1Show) {
		this.vcUser2CardImg1Show = vcUser2CardImg1Show;
	}

	@Transient
	public String getVcUser2CardImg2Show() {
		return vcUser2CardImg2Show;
	}

	/**
	 * @param vcUser2CardImg2Show
	 *            : set the property vcUser2CardImg2Show.
	 */
	public void setVcUser2CardImg2Show(String vcUser2CardImg2Show) {
		this.vcUser2CardImg2Show = vcUser2CardImg2Show;
	}

	@Transient
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            : set the property note.
	 */
	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "ENABLE")
	public Integer getnEnable() {
		return nEnable;
	}

	/**
	 * @param nEnable
	 *            : set the property nEnable.
	 */
	public void setnEnable(Integer nEnable) {
		this.nEnable = nEnable;
	}

	@Column(name = "VC_EBIKE_INVOICE", length = 100)
	public String getVcEbikeInvoiceImg() {
		return vcEbikeInvoiceImg;
	}

	/**
	 * @param vcEbikeInvoiceImg
	 *            : set the property vcEbikeInvoiceImg.
	 */
	public void setVcEbikeInvoiceImg(String vcEbikeInvoiceImg) {
		this.vcEbikeInvoiceImg = vcEbikeInvoiceImg;
	}

	@Transient
	public String getVcEbikeInvoiceImgShow() {
		return vcEbikeInvoiceImgShow;
	}

	/**
	 * @param vcEbikeInvoiceImgShow
	 *            : set the property vcEbikeInvoiceImgShow.
	 */
	public void setVcEbikeInvoiceImgShow(String vcEbikeInvoiceImgShow) {
		this.vcEbikeInvoiceImgShow = vcEbikeInvoiceImgShow;
	}

	@Transient
	public String getHyxhzhName() {
		return hyxhzhName;
	}

	/**
	 * @param hyxhzhName : set the property hyxhzhName.
	 */
	public void setHyxhzhName(String hyxhzhName) {
		this.hyxhzhName = hyxhzhName;
	}
	
	
}