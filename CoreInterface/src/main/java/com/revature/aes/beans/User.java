package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AES_USERS")
public class User implements Serializable{

	private static final long serialVersionUID = 6104022944061620088L;
	@Id
	@Column(name="USER_ID")
	private int userId;
	@Column(name="EMAIL")
	private String email;
	@Column(name="FIRSTNAME")
	private String firstName;
	@Column(name="LASTNAME")
	private String lastName;
	@Column(name="SALESFORCE")
	private Integer salesforce;
	@Column(name="RECRUITER_ID")
	private Integer recruiterId;
	@Column(name="ROLE_ID")
	private int roleId;
	@Column(name="DATE_PASS_ISSUED")
	private String datePassIssued;
	
	public User(int userId, String email, String firstName, String lastName, Integer salesforce, Integer recruiterId,
			int roleId) {
		super();
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salesforce = salesforce;
		this.recruiterId = recruiterId;
		this.roleId = roleId;
		this.datePassIssued = datePassIssued;
	}
	public User() {
		super();
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getSalesforce() {
		return salesforce;
	}
	public void setSalesforce(Integer salesforce) {
		this.salesforce = salesforce;
	}
	public Integer getRecruiterId() {
		return recruiterId;
	}
	public void setRecruiterId(Integer recruiterId) {
		this.recruiterId = recruiterId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getDatePassIssued() {
		return datePassIssued;
	}
	public void setDatePassIssued(String datePassIssued) {
		this.datePassIssued = datePassIssued;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", salesforce=" + salesforce + ", recruiterId=" + recruiterId + ", roleId=" + roleId
				+ ", datePassIssued=" + datePassIssued + "]";
	}
}