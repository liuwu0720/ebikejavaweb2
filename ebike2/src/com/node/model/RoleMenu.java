package com.node.model;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * RoleMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROLE_MENU")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class RoleMenu implements java.io.Serializable {

	// Fields

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer role;
	private String menu;

	// Constructors

	/** default constructor */
	public RoleMenu() {
	}

	/** full constructor */
	public RoleMenu(Integer role, String menu) {
		this.role = role;
		this.menu = menu;
	}

	// Property accessors
	@SequenceGenerator(name = "ROLE_MENU", sequenceName = "SEQ_ROLE_MENU", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "ROLE_MENU")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ROLE", precision = 0)
	public Integer getRole() {
		return this.role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	@Column(name = "MENU", length = 50)
	public String getMenu() {
		return this.menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

}