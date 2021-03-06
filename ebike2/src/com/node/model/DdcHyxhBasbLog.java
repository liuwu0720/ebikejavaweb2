package com.node.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * DdcHyxhBasbLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DDC_HYXH_BASB_LOG")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class DdcHyxhBasbLog implements java.io.Serializable {

	// Fields

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -1212316966381686312L;
	private Long id;
	private String lsh;
	private String hyxhzh;
	private String hyxhmc;
	private Integer hyxhsqpe;
	private String bz;
	private String sqr;
	private Date sqrq;
	private Integer slpejy;
	private String slyj;
	private String slbz;
	private String slr;
	private String slbm;
	private Date slrq;
	private Integer kldpejy;
	private String slyj2;
	private String kldbz;
	private String kld;
	private Date kldsprq;
	private String kldbm;
	private Integer cldpejy;
	private String slyj3;
	private String cldbz;
	private String kld2;
	private Date cldsprq;
	private String cldbm;
	private Integer zdldpejy;
	private String slyj4;
	private String zdldbz;
	private String kld3;
	private Date zdldsprq;
	private String zdldbm;
	private Integer hyxhbcsjpe;
	private String bjjg;
	private String bjbz;
	private String bzjr;
	private String bjbm;
	private Date bjrq;
	private String synFlag;
	private String tranFlag;
	private Date tranDate;
	private String czr;
	private String czbm;
	private Date czrq;
	private String cznr;

	// Constructors

	/** default constructor */
	public DdcHyxhBasbLog() {
	}

	/** minimal constructor */
	public DdcHyxhBasbLog(String lsh) {
		this.lsh = lsh;
	}

	/** full constructor */
	public DdcHyxhBasbLog(String lsh, String hyxhzh, Integer hyxhsqpe,
			String bz, String sqr, Date sqrq, Integer slpejy, String slyj,
			String slbz, String slr, String slbm, Date slrq, Integer kldpejy,
			String slyj2, String kldbz, String kld, Date kldsprq, String kldbm,
			Integer cldpejy, String slyj3, String cldbz, String kld2,
			Date cldsprq, String cldbm, Integer zdldpejy, String slyj4,
			String zdldbz, String kld3, Date zdldsprq, String zdldbm,
			Integer hyxhbcsjpe, String bjjg, String bjbz, String bzjr,
			String bjbm, Date bjrq, String synFlag, String tranFlag,
			Date tranDate, String czr, String czbm, Date czrq, String cznr) {
		this.lsh = lsh;
		this.hyxhzh = hyxhzh;
		this.hyxhsqpe = hyxhsqpe;
		this.bz = bz;
		this.sqr = sqr;
		this.sqrq = sqrq;
		this.slpejy = slpejy;
		this.slyj = slyj;
		this.slbz = slbz;
		this.slr = slr;
		this.slbm = slbm;
		this.slrq = slrq;
		this.kldpejy = kldpejy;
		this.slyj2 = slyj2;
		this.kldbz = kldbz;
		this.kld = kld;
		this.kldsprq = kldsprq;
		this.kldbm = kldbm;
		this.cldpejy = cldpejy;
		this.slyj3 = slyj3;
		this.cldbz = cldbz;
		this.kld2 = kld2;
		this.cldsprq = cldsprq;
		this.cldbm = cldbm;
		this.zdldpejy = zdldpejy;
		this.slyj4 = slyj4;
		this.zdldbz = zdldbz;
		this.kld3 = kld3;
		this.zdldsprq = zdldsprq;
		this.zdldbm = zdldbm;
		this.hyxhbcsjpe = hyxhbcsjpe;
		this.bjjg = bjjg;
		this.bjbz = bjbz;
		this.bzjr = bzjr;
		this.bjbm = bjbm;
		this.bjrq = bjrq;
		this.synFlag = synFlag;
		this.tranFlag = tranFlag;
		this.tranDate = tranDate;
		this.czr = czr;
		this.czbm = czbm;
		this.czrq = czrq;
		this.cznr = cznr;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LSH", nullable = false, length = 20)
	public String getLsh() {
		return this.lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	@Column(name = "HYXHZH", length = 20)
	public String getHyxhzh() {
		return this.hyxhzh;
	}

	public void setHyxhzh(String hyxhzh) {
		this.hyxhzh = hyxhzh;
	}

	@Column(name = "HYXHSQPE", precision = 0)
	public Integer getHyxhsqpe() {
		return this.hyxhsqpe;
	}

	public void setHyxhsqpe(Integer hyxhsqpe) {
		this.hyxhsqpe = hyxhsqpe;
	}

	@Column(name = "BZ", length = 200)
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

	@Column(name = "SLPEJY", precision = 0)
	public Integer getSlpejy() {
		return this.slpejy;
	}

	public void setSlpejy(Integer slpejy) {
		this.slpejy = slpejy;
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

	@Column(name = "SLBM", length = 20)
	public String getSlbm() {
		return this.slbm;
	}

	public void setSlbm(String slbm) {
		this.slbm = slbm;
	}

	@Column(name = "SLRQ", length = 7)
	public Date getSlrq() {
		return this.slrq;
	}

	public void setSlrq(Date slrq) {
		this.slrq = slrq;
	}

	@Column(name = "KLDPEJY", precision = 0)
	public Integer getKldpejy() {
		return this.kldpejy;
	}

	public void setKldpejy(Integer kldpejy) {
		this.kldpejy = kldpejy;
	}

	@Column(name = "SLYJ2", length = 10)
	public String getSlyj2() {
		return this.slyj2;
	}

	public void setSlyj2(String slyj2) {
		this.slyj2 = slyj2;
	}

	@Column(name = "KLDBZ", length = 100)
	public String getKldbz() {
		return this.kldbz;
	}

	public void setKldbz(String kldbz) {
		this.kldbz = kldbz;
	}

	@Column(name = "KLD", length = 20)
	public String getKld() {
		return this.kld;
	}

	public void setKld(String kld) {
		this.kld = kld;
	}

	@Column(name = "KLDSPRQ", length = 7)
	public Date getKldsprq() {
		return this.kldsprq;
	}

	public void setKldsprq(Date kldsprq) {
		this.kldsprq = kldsprq;
	}

	@Column(name = "KLDBM", length = 20)
	public String getKldbm() {
		return this.kldbm;
	}

	public void setKldbm(String kldbm) {
		this.kldbm = kldbm;
	}

	@Column(name = "CLDPEJY", precision = 0)
	public Integer getCldpejy() {
		return this.cldpejy;
	}

	public void setCldpejy(Integer cldpejy) {
		this.cldpejy = cldpejy;
	}

	@Column(name = "SLYJ3", length = 10)
	public String getSlyj3() {
		return this.slyj3;
	}

	public void setSlyj3(String slyj3) {
		this.slyj3 = slyj3;
	}

	@Column(name = "CLDBZ", length = 100)
	public String getCldbz() {
		return this.cldbz;
	}

	public void setCldbz(String cldbz) {
		this.cldbz = cldbz;
	}

	@Column(name = "KLD2", length = 20)
	public String getKld2() {
		return this.kld2;
	}

	public void setKld2(String kld2) {
		this.kld2 = kld2;
	}

	@Column(name = "CLDSPRQ", length = 7)
	public Date getCldsprq() {
		return this.cldsprq;
	}

	public void setCldsprq(Date cldsprq) {
		this.cldsprq = cldsprq;
	}

	@Column(name = "CLDBM", length = 20)
	public String getCldbm() {
		return this.cldbm;
	}

	public void setCldbm(String cldbm) {
		this.cldbm = cldbm;
	}

	@Column(name = "ZDLDPEJY", precision = 0)
	public Integer getZdldpejy() {
		return this.zdldpejy;
	}

	public void setZdldpejy(Integer zdldpejy) {
		this.zdldpejy = zdldpejy;
	}

	@Column(name = "SLYJ4", length = 10)
	public String getSlyj4() {
		return this.slyj4;
	}

	public void setSlyj4(String slyj4) {
		this.slyj4 = slyj4;
	}

	@Column(name = "ZDLDBZ", length = 100)
	public String getZdldbz() {
		return this.zdldbz;
	}

	public void setZdldbz(String zdldbz) {
		this.zdldbz = zdldbz;
	}

	@Column(name = "KLD3", length = 20)
	public String getKld3() {
		return this.kld3;
	}

	public void setKld3(String kld3) {
		this.kld3 = kld3;
	}

	@Column(name = "ZDLDSPRQ", length = 7)
	public Date getZdldsprq() {
		return this.zdldsprq;
	}

	public void setZdldsprq(Date zdldsprq) {
		this.zdldsprq = zdldsprq;
	}

	@Column(name = "ZDLDBM", length = 20)
	public String getZdldbm() {
		return this.zdldbm;
	}

	public void setZdldbm(String zdldbm) {
		this.zdldbm = zdldbm;
	}

	@Column(name = "HYXHBCSJPE", precision = 0)
	public Integer getHyxhbcsjpe() {
		return this.hyxhbcsjpe;
	}

	public void setHyxhbcsjpe(Integer hyxhbcsjpe) {
		this.hyxhbcsjpe = hyxhbcsjpe;
	}

	@Column(name = "BJJG", length = 20)
	public String getBjjg() {
		return this.bjjg;
	}

	public void setBjjg(String bjjg) {
		this.bjjg = bjjg;
	}

	@Column(name = "BJBZ", length = 100)
	public String getBjbz() {
		return this.bjbz;
	}

	public void setBjbz(String bjbz) {
		this.bjbz = bjbz;
	}

	@Column(name = "BZJR", length = 10)
	public String getBzjr() {
		return this.bzjr;
	}

	public void setBzjr(String bzjr) {
		this.bzjr = bzjr;
	}

	@Column(name = "BJBM", length = 50)
	public String getBjbm() {
		return this.bjbm;
	}

	public void setBjbm(String bjbm) {
		this.bjbm = bjbm;
	}

	@Column(name = "BJRQ", length = 7)
	public Date getBjrq() {
		return this.bjrq;
	}

	public void setBjrq(Date bjrq) {
		this.bjrq = bjrq;
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

	@Column(name = "TRAN_DATE", length = 7)
	public Date getTranDate() {
		return this.tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	@Column(name = "CZR", length = 10)
	public String getCzr() {
		return this.czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	@Column(name = "CZBM", length = 20)
	public String getCzbm() {
		return this.czbm;
	}

	public void setCzbm(String czbm) {
		this.czbm = czbm;
	}

	@Column(name = "CZRQ", length = 7)
	public Date getCzrq() {
		return this.czrq;
	}

	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}

	@Column(name = "CZNR", length = 20)
	public String getCznr() {
		return this.cznr;
	}

	public void setCznr(String cznr) {
		this.cznr = cznr;
	}

	@Column(name = "HYXHMC", length = 50)
	public String getHyxhmc() {
		return hyxhmc;
	}

	/**
	 * @param hyxhmc
	 *            : set the property hyxhmc.
	 */
	public void setHyxhmc(String hyxhmc) {
		this.hyxhmc = hyxhmc;
	}
}