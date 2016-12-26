/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database. Use to evaluate associates
 * 		performance both during and before employment with Revature 
 * 		LLC.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/

package com.revature.aes.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name  = "AES_ROLES")
public class Roles implements Serializable
{

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 6958302263060604424L;
	
	/**
	 * @roleId the unique Identifier for the Option class
	 */
	@Id
	@SequenceGenerator(name = "AES_ROLES_SEQ", sequenceName = "AES_ROLES_SEQ")
	@GeneratedValue(generator = "AES_ROLES_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "ROLE_ID")
	private Integer roleId;
	/**
	 * @roleTitle A String representation used to describes the role title of a user.
	 */
	@Column(name="ROLE_TITLE")
	private String roleTitle;
	
	@OneToMany(mappedBy="role")
	private List<User> users;

	public Roles()
	{
		super();
	}
	public Roles(Integer roleId, String roleTitle)
	{
		this();
		this.roleId = roleId;
		this.roleTitle = roleTitle;
	}
	public Integer getRoleId()
	{
		return roleId;
	}
	public void setRoleId(Integer roleId)
	{
		this.roleId = roleId;
	}
	public String getRoleTitle()
	{
		return roleTitle;
	}
	public void setRoleTitle(String roleTitle)
	{
		this.roleTitle = roleTitle;
	}
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result + ((roleTitle == null) ? 0 : roleTitle.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Roles other = (Roles) obj;
		if (roleId == null)
		{
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (roleTitle == null)
		{
			if (other.roleTitle != null)
				return false;
		} else if (!roleTitle.equals(other.roleTitle))
			return false;
		return true;
	}

}
