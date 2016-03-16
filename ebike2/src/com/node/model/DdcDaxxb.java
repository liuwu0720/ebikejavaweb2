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
 * 
 * 类描述：电动车档案信息表
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月11日 下午5:47:46
 */
@Entity
@Table(name = "DDC_DAXXB", uniqueConstraints = @UniqueConstraint(columnNames = "DABH"))
public class DdcDaxxb implements java.io.Serializable {

	// Fields

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 243629761815051390L;
	private Long id;
	private String dabh;// 新进入的数据，自动生成档案编号4403 00000001；老数据去更新
	private String ywlx;// 业务类型
	private String ywyy;// 业务原因
	private String hyxhzh;//
	private String zzjgdmzh;// 组织机构代码证号
	private String zzjgdmzhName;
	private String cphm;// 车牌号码，办结后，以流水转入本表的车牌号码数据填充本表
	private String ppxh;// 品牌型号
	private String cysy;// 车身验色
	private String djh;// 电机号
	private String jtzz;// 脚踏装置（有、无）
	private String jsrxm1;// 驾驶人姓名1
	private String xb1;// 性别1
	private String sfzmhm1;// 身份证号码1
	private String lxdh1;// 联系电话1
	private String jsrxm2;// 驾驶人姓名2
	private String xb2;// 性别2
	private String sfzmhm2;// 身份证号码2
	private String lxdh2;// 联系电话2
	private String jsrxm3;// 驾驶人姓名3
	private String xb3;// 性别3
	private String sfzmhm3;// 身份证号码3
	private String lxdh3;// 联系电话3
	private String xsqy;// 行驶区域(福田区、罗湖区.....)
	private String bz;// 备注
	private String zt;// 车辆状态 填字典
	private String ztName;
	private Date syrq;// 审验日期：新车归档日期+2年或年审验车合格后的归档日期+2年
	private String slzl;// 受理资料，分割 字典表
	private String slyj;// 受理意见 0 同意 1 不同意 特别注意 档案更正是要填写本表记录
	private String slbz;// 受理备注
	private String slr;// 受理人
	private Date slrq;// 受理日期
	private String slbm;// 受理部门
	private String gdyj;// 归档意见 0 办结 1 退办 成功办结的信息才会进入本表
	private String tbyy;// 退办原因
	private String gdbz;// 归档备注
	private String gdr;// 归档人
	private Date gdrq;// 归档日期
	private String gdbm;// 归档部门
	private String synFlag;// 同步标志,填写规则详看其他文档
	private String tranFlag;// 传送标志(该字段必须有索引)
	private Date tranDate;// 传送时间

	// Constructors

	/** default constructor */
	public DdcDaxxb() {
	}

	/** minimal constructor */
	public DdcDaxxb(String dabh) {
		this.dabh = dabh;
	}

	public DdcDaxxb(Long id, String dabh, String cphm, String djh, Date slrq,
			String zzjgdmzh, String zt) {
		this.id = id;
		this.dabh = dabh;
		this.cphm = cphm;
		this.djh = djh;
		this.slrq = slrq;
		this.zzjgdmzh = zzjgdmzh;
		this.zt = zt;

	}

	/** full constructor */
	public DdcDaxxb(String dabh, String ywlx, String ywyy, String hyxhzh,
			String zzjgdmzh, String cphm, String ppxh, String cysy, String djh,
			String jtzz, String jsrxm1, String xb1, String sfzmhm1,
			String lxdh1, String jsrxm2, String xb2, String sfzmhm2,
			String lxdh2, String jsrxm3, String xb3, String sfzmhm3,
			String lxdh3, String xsqy, String bz, String zt, Date syrq,
			String slzl, String slyj, String slbz, String slr, Date slrq,
			String slbm, String gdyj, String tbyy, String gdbz, String gdr,
			Date gdrq, String gdbm, String synFlag, String tranFlag,
			Date tranDate) {
		this.dabh = dabh;
		this.ywlx = ywlx;
		this.ywyy = ywyy;
		this.hyxhzh = hyxhzh;
		this.zzjgdmzh = zzjgdmzh;
		this.cphm = cphm;
		this.ppxh = ppxh;
		this.cysy = cysy;
		this.djh = djh;
		this.jtzz = jtzz;
		this.jsrxm1 = jsrxm1;
		this.xb1 = xb1;
		this.sfzmhm1 = sfzmhm1;
		this.lxdh1 = lxdh1;
		this.jsrxm2 = jsrxm2;
		this.xb2 = xb2;
		this.sfzmhm2 = sfzmhm2;
		this.lxdh2 = lxdh2;
		this.jsrxm3 = jsrxm3;
		this.xb3 = xb3;
		this.sfzmhm3 = sfzmhm3;
		this.lxdh3 = lxdh3;
		this.xsqy = xsqy;
		this.bz = bz;
		this.zt = zt;
		this.syrq = syrq;
		this.slzl = slzl;
		this.slyj = slyj;
		this.slbz = slbz;
		this.slr = slr;
		this.slrq = slrq;
		this.slbm = slbm;
		this.gdyj = gdyj;
		this.tbyy = tbyy;
		this.gdbz = gdbz;
		this.gdr = gdr;
		this.gdrq = gdrq;
		this.gdbm = gdbm;
		this.synFlag = synFlag;
		this.tranFlag = tranFlag;
		this.tranDate = tranDate;
	}

	// Property accessors
	@SequenceGenerator(name = "DDC_DAXXBID", sequenceName = "SEQ_DDC_DAXXB", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "DDC_DAXXBID")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DABH", unique = true, nullable = false, length = 20)
	public String getDabh() {
		return this.dabh;
	}

	public void setDabh(String dabh) {
		this.dabh = dabh;
	}

	@Column(name = "YWLX", length = 20)
	public String getYwlx() {
		return this.ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	@Column(name = "YWYY", length = 20)
	public String getYwyy() {
		return this.ywyy;
	}

	public void setYwyy(String ywyy) {
		this.ywyy = ywyy;
	}

	@Column(name = "HYXHZH", length = 20)
	public String getHyxhzh() {
		return this.hyxhzh;
	}

	public void setHyxhzh(String hyxhzh) {
		this.hyxhzh = hyxhzh;
	}

	@Column(name = "ZZJGDMZH", length = 200)
	public String getZzjgdmzh() {
		return this.zzjgdmzh;
	}

	public void setZzjgdmzh(String zzjgdmzh) {
		this.zzjgdmzh = zzjgdmzh;
	}

	@Column(name = "CPHM", length = 20)
	public String getCphm() {
		return this.cphm;
	}

	public void setCphm(String cphm) {
		this.cphm = cphm;
	}

	@Column(name = "PPXH", length = 20)
	public String getPpxh() {
		return this.ppxh;
	}

	public void setPpxh(String ppxh) {
		this.ppxh = ppxh;
	}

	@Column(name = "CYSY", length = 20)
	public String getCysy() {
		return this.cysy;
	}

	public void setCysy(String cysy) {
		this.cysy = cysy;
	}

	@Column(name = "DJH", length = 30)
	public String getDjh() {
		return this.djh;
	}

	public void setDjh(String djh) {
		this.djh = djh;
	}

	@Column(name = "JTZZ", length = 20)
	public String getJtzz() {
		return this.jtzz;
	}

	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}

	@Column(name = "JSRXM1", length = 20)
	public String getJsrxm1() {
		return this.jsrxm1;
	}

	public void setJsrxm1(String jsrxm1) {
		this.jsrxm1 = jsrxm1;
	}

	@Column(name = "XB1", length = 20)
	public String getXb1() {
		return this.xb1;
	}

	public void setXb1(String xb1) {
		this.xb1 = xb1;
	}

	@Column(name = "SFZMHM1", length = 20)
	public String getSfzmhm1() {
		return this.sfzmhm1;
	}

	public void setSfzmhm1(String sfzmhm1) {
		this.sfzmhm1 = sfzmhm1;
	}

	@Column(name = "LXDH1", length = 20)
	public String getLxdh1() {
		return this.lxdh1;
	}

	public void setLxdh1(String lxdh1) {
		this.lxdh1 = lxdh1;
	}

	@Column(name = "JSRXM2", length = 20)
	public String getJsrxm2() {
		return this.jsrxm2;
	}

	public void setJsrxm2(String jsrxm2) {
		this.jsrxm2 = jsrxm2;
	}

	@Column(name = "XB2", length = 20)
	public String getXb2() {
		return this.xb2;
	}

	public void setXb2(String xb2) {
		this.xb2 = xb2;
	}

	@Column(name = "SFZMHM2", length = 20)
	public String getSfzmhm2() {
		return this.sfzmhm2;
	}

	public void setSfzmhm2(String sfzmhm2) {
		this.sfzmhm2 = sfzmhm2;
	}

	@Column(name = "LXDH2", length = 20)
	public String getLxdh2() {
		return this.lxdh2;
	}

	public void setLxdh2(String lxdh2) {
		this.lxdh2 = lxdh2;
	}

	@Column(name = "JSRXM3", length = 20)
	public String getJsrxm3() {
		return this.jsrxm3;
	}

	public void setJsrxm3(String jsrxm3) {
		this.jsrxm3 = jsrxm3;
	}

	@Column(name = "XB3", length = 20)
	public String getXb3() {
		return this.xb3;
	}

	public void setXb3(String xb3) {
		this.xb3 = xb3;
	}

	@Column(name = "SFZMHM3", length = 20)
	public String getSfzmhm3() {
		return this.sfzmhm3;
	}

	public void setSfzmhm3(String sfzmhm3) {
		this.sfzmhm3 = sfzmhm3;
	}

	@Column(name = "LXDH3", length = 20)
	public String getLxdh3() {
		return this.lxdh3;
	}

	public void setLxdh3(String lxdh3) {
		this.lxdh3 = lxdh3;
	}

	@Column(name = "XSQY", length = 20)
	public String getXsqy() {
		return this.xsqy;
	}

	public void setXsqy(String xsqy) {
		this.xsqy = xsqy;
	}

	@Column(name = "BZ", length = 200)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name = "ZT", length = 10)
	public String getZt() {
		return this.zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	@Column(name = "SYRQ", length = 7)
	public Date getSyrq() {
		return this.syrq;
	}

	public void setSyrq(Date syrq) {
		this.syrq = syrq;
	}

	@Column(name = "SLZL", length = 100)
	public String getSlzl() {
		return this.slzl;
	}

	public void setSlzl(String slzl) {
		this.slzl = slzl;
	}

	@Column(name = "SLYJ", length = 20)
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

	@Column(name = "GDYJ", length = 20)
	public String getGdyj() {
		return this.gdyj;
	}

	public void setGdyj(String gdyj) {
		this.gdyj = gdyj;
	}

	@Column(name = "TBYY", length = 10)
	public String getTbyy() {
		return this.tbyy;
	}

	public void setTbyy(String tbyy) {
		this.tbyy = tbyy;
	}

	@Column(name = "GDBZ", length = 100)
	public String getGdbz() {
		return this.gdbz;
	}

	public void setGdbz(String gdbz) {
		this.gdbz = gdbz;
	}

	@Column(name = "GDR", length = 20)
	public String getGdr() {
		return this.gdr;
	}

	public void setGdr(String gdr) {
		this.gdr = gdr;
	}

	@Column(name = "GDRQ", length = 7)
	public Date getGdrq() {
		return this.gdrq;
	}

	public void setGdrq(Date gdrq) {
		this.gdrq = gdrq;
	}

	@Column(name = "GDBM", length = 20)
	public String getGdbm() {
		return this.gdbm;
	}

	public void setGdbm(String gdbm) {
		this.gdbm = gdbm;
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

	@Transient
	public String getZzjgdmzhName() {
		return zzjgdmzhName;
	}

	/**
	 * @param zzjgdmzhName
	 *            : set the property zzjgdmzhName.
	 */
	public void setZzjgdmzhName(String zzjgdmzhName) {
		this.zzjgdmzhName = zzjgdmzhName;
	}

	@Transient
	public String getZtName() {
		return ztName;
	}

	/**
	 * @param ztName
	 *            : set the property ztName.
	 */
	public void setZtName(String ztName) {
		this.ztName = ztName;
	}

}