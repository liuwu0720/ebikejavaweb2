package com.node.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * HyxhMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HYXH_MENU")
public class HyxhMenu implements java.io.Serializable {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 8279518359997118346L;
	// Fields

	private Integer id;
	private String vcMenu;
	private String vcIcon;
	private String vcUrl;
	private Integer iPid;
	private Integer nIndex;
	private List<HyxhMenu> subhHyxhMenus = new ArrayList<>();

	// Constructors

	/** default constructor */
	public HyxhMenu() {
	}

	/** full constructor */
	public HyxhMenu(String vcMenu, String vcIcon, String vcUrl, Integer iPid,
			Integer NIndex) {
		this.vcMenu = vcMenu;
		this.vcIcon = vcIcon;
		this.vcUrl = vcUrl;
		this.iPid = iPid;
		this.nIndex = nIndex;
	}

	// Property accessors
	@SequenceGenerator(name = "HYXH_MENU", sequenceName = "SEQ_HYXH_MENU", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "HYXH_MENU")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "VC_MENU", length = 50)
	public String getVcMenu() {
		return this.vcMenu;
	}

	public void setVcMenu(String vcMenu) {
		this.vcMenu = vcMenu;
	}

	@Column(name = "VC_ICON", length = 50)
	public String getVcIcon() {
		return this.vcIcon;
	}

	public void setVcIcon(String vcIcon) {
		this.vcIcon = vcIcon;
	}

	@Column(name = "VC_URL", length = 100)
	public String getVcUrl() {
		return this.vcUrl;
	}

	public void setVcUrl(String vcUrl) {
		this.vcUrl = vcUrl;
	}

	@Column(name = "I_PID", precision = 0)
	public Integer getiPid() {
		return this.iPid;
	}

	public void setiPid(Integer iPid) {
		this.iPid = iPid;
	}

	@Column(name = "N_INDEX", precision = 0)
	public Integer getnIndex() {
		return this.nIndex;
	}

	public void setnIndex(Integer nIndex) {
		this.nIndex = nIndex;
	}

	@Transient
	public List<HyxhMenu> getSubhHyxhMenus() {
		return subhHyxhMenus;
	}

	/**
	 * @param subhHyxhMenus
	 *            : set the property subhHyxhMenus.
	 */
	public void setSubhHyxhMenus(List<HyxhMenu> subhHyxhMenus) {
		this.subhHyxhMenus = subhHyxhMenus;
	}

}