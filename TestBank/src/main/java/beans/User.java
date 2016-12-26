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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name  = "AES_USERS")
public class User implements Serializable
{

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 2129360053028307874L;
	
	/**
	 * @userId The unique Identifier for the Class
	 */
	@Id
	@SequenceGenerator(name = "AES_USERS_SEQ", sequenceName = "AES_USERS_SEQ")
	@GeneratedValue(generator = "AES_USERS_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "USER_ID")
	private Integer userId;
	
	/**
	 * @email A String representation of the email of the User.
	 */
	@Column(name = "EMAIL")
	private String email;
	
	/**
	 * @firstname A String representation of the first name of the User.
	 */
	@Column(name = "FIRSTNAME")
	private String firstname;
	
	/**
	 * @lastname A String representation of the lasst name of the User.
	 */
	@Column(name = "LASTNAME")
	private String lastname;
	
	/**
	 * @salesForce An identifer for the Saleforce Representive of the User.
	 */
	@Column(name="SALESFORCE")
	private Integer salesForce;
	
	/**
	 * @recruiter The assigned recruiter of the User if applicable.
	 * If null then the User role should be a Trainer or a Recruiter 
	 */
	@Column(name="RECRUITER_ID")
	private Integer recruiter;
	
	/**
	 * @role The role of the User.
	 */
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private Roles role;
	
	/**
	 * @passwordIssueDate The date of the user was assigned a Password.
	 */
	@Column(name="DATE_PASS_ISSUED")
	private LocalDate passwordIssueDate;
	/**
	 * @userTrainers A set of Users that is used to determine the trainer(s) of the user(s)
	 */
	@ManyToMany
	@JoinTable(name="AES_USER_TRAINERS",
				joinColumns=@JoinColumn(name="TRAINER_ID"),
				inverseJoinColumns=@JoinColumn(name="USER_ID"))
	private List<User> usersTrainers;
	
	@OneToMany(mappedBy="user")
	private List<Assessment> assessments;
	
	@OneToMany(mappedBy="creator")
	private List<Template> templates;
	
	/**
	 * @associates The associates related to the trainer
	 */
	@ManyToMany(mappedBy="usersTrainers")
	private List<User> associates;
	
	public User()
	{
		super();
	}

	public User(Integer userId, String email, String firstname, String lastname, Integer salesForce, Integer recruiter,
			Roles role, LocalDate passwordIssueDate, List<User> usersTrainers) {
		super();
		this.userId = userId;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.salesForce = salesForce;
		this.recruiter = recruiter;
		this.role = role;
		this.passwordIssueDate = passwordIssueDate;
		this.usersTrainers = usersTrainers;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getSalesForce() {
		return salesForce;
	}

	public void setSalesForce(Integer salesForce) {
		this.salesForce = salesForce;
	}


	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public LocalDate getPasswordIssueDate() {
		return passwordIssueDate;
	}

	public void setPasswordIssueDate(LocalDate passwordIssueDate) {
		this.passwordIssueDate = passwordIssueDate;
	}

	public List<User> getUsersTrainers() {
		return usersTrainers;
	}

	public void setUsersTrainers(List<User> usersTrainers) {
		this.usersTrainers = usersTrainers;
	}

	public Integer getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Integer recruiter) {
		this.recruiter = recruiter;
	}

	public List<Assessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<Assessment> assessments) {
		this.assessments = assessments;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	public List<User> getAssociates() {
		return associates;
	}

	public void setAssociates(List<User> associates) {
		this.associates = associates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((passwordIssueDate == null) ? 0 : passwordIssueDate.hashCode());
		result = prime * result + ((recruiter == null) ? 0 : recruiter.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((salesForce == null) ? 0 : salesForce.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((usersTrainers == null) ? 0 : usersTrainers.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (passwordIssueDate == null) {
			if (other.passwordIssueDate != null)
				return false;
		} else if (!passwordIssueDate.equals(other.passwordIssueDate))
			return false;
		if (recruiter == null) {
			if (other.recruiter != null)
				return false;
		} else if (!recruiter.equals(other.recruiter))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (salesForce == null) {
			if (other.salesForce != null)
				return false;
		} else if (!salesForce.equals(other.salesForce))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (usersTrainers == null) {
			if (other.usersTrainers != null)
				return false;
		} else if (!usersTrainers.equals(other.usersTrainers))
			return false;
		return true;
	}
	
}
