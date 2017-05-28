package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="AES_USERS")
public class User implements Serializable{

	private static final long serialVersionUID = 6104022944061620088L;
	@Id
	@GeneratedValue(generator = "AES_USERS_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_USERS_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_USERS_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
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
	
	@ManyToOne(fetch=FetchType.EAGER,targetEntity = Role.class)
	@JoinColumn(name="ROLE_ID")
	private Role role;
	
	@Column(name="DATE_PASS_ISSUED")
	private String datePassIssued;
	
	@Transient
    private String format;

	@Transient
	private Integer grade;
	
	public User() {
		super();
	}

	public String getFormat() { return format; }
	public void setFormat(String format) { this.format = format; }
	public Integer getGrade() { return grade; }
	public void setGrade(Integer grade) { this.grade = grade; }
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
				+ ", salesforce=" + salesforce + ", recruiterId=" + recruiterId + ", role=" + role + ", datePassIssued="
				+ datePassIssued + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datePassIssued == null) ? 0 : datePassIssued.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((recruiterId == null) ? 0 : recruiterId.hashCode());
		result = prime * result + ((salesforce == null) ? 0 : salesforce.hashCode());
		result = prime * result + userId;
		return result;
	}
}