package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "AES_ROLES")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 6033234014682351342L;
	@Id
	@Column(name = "ROLE_ID")
	private int roleId;
	@Column(name = "ROLE_TITLE")
	private String roleTitle;
	
	public Role() {
		super();
	}
	
	public Role(int roleId, String roleTitle) {
		super();
		this.roleId = roleId;
		this.roleTitle = roleTitle;
	}
	
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleTitle=" + roleTitle + "]";
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleTitle() {
		return roleTitle;
	}
	
	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
	}
}