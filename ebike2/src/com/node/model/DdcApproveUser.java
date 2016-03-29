package com.node.model;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * 类描述：审批人及意见表
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月28日 下午4:34:08
 */
@Entity
@Table(name = "DDC_APPROVE_USER")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class DdcApproveUser implements java.io.Serializable {

	// Fields

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -878478886411664702L;
	private Long id;
	private String userName;
	private String userOrgname;
	private String userRoleName;
	private Integer approveIndex;
	private String approveNote;
	private String approveTable;
	private Integer approveTableid;

	// Constructors

	/** default constructor */
	public DdcApproveUser() {
	}

	/** full constructor */
	public DdcApproveUser(String userName, String userOrgname,
			String userRoleName, Integer approveIndex, String approveNote,
			String approveTable, Integer approveTableid) {
		this.userName = userName;
		this.userOrgname = userOrgname;
		this.userRoleName = userRoleName;
		this.approveIndex = approveIndex;
		this.approveNote = approveNote;
		this.approveTable = approveTable;
		this.approveTableid = approveTableid;
	}

	@SequenceGenerator(name = "DDC_APPROVE_USER", sequenceName = "SEQ_DDC_APPROVE_USER", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "DDC_APPROVE_USER")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_NAME", length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_ORGNAME", length = 50)
	public String getUserOrgname() {
		return this.userOrgname;
	}

	public void setUserOrgname(String userOrgname) {
		this.userOrgname = userOrgname;
	}

	@Column(name = "USER_ROLE_NAME", length = 200)
	public String getUserRoleName() {
		return this.userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	@Column(name = "APPROVE_INDEX", precision = 0)
	public Integer getApproveIndex() {
		return this.approveIndex;
	}

	public void setApproveIndex(Integer approveIndex) {
		this.approveIndex = approveIndex;
	}

	@Column(name = "APPROVE_NOTE", length = 500)
	public String getApproveNote() {
		return this.approveNote;
	}

	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}

	@Column(name = "APPROVE_TABLE", length = 100)
	public String getApproveTable() {
		return this.approveTable;
	}

	public void setApproveTable(String approveTable) {
		this.approveTable = approveTable;
	}

	@Column(name = "APPROVE_TABLEID", precision = 0)
	public Integer getApproveTableid() {
		return this.approveTableid;
	}

	public void setApproveTableid(Integer approveTableid) {
		this.approveTableid = approveTableid;
	}

}