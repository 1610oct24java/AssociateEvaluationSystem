/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/
package com.revature.aes.beans;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User recruiter;
	
	/**
	 * @role The role of the User.
	 */
	@OneToOne
	@JoinColumn(name="ROLE_ID")
	private Roles role;
	
	/**
	 * @passwordIssueDate The date of the user was assigned a Password.
	 */
	@Column(name="DATE_PASS_ISSUED")
	private LocalDate passwordIssueDate;
	
	public User()
	{
		super();
	}

	public User(Integer userId, String email, String firstname, String lastname, Integer salesForce, User recruiter,
			Roles role, LocalDate passIssuedDate)
	{
		super();
		this.userId = userId;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.salesForce = salesForce;
		this.recruiter = recruiter;
		this.role = role;
		this.passwordIssueDate = passIssuedDate;
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public Integer getSalesForce()
	{
		return salesForce;
	}

	public void setSalesForce(Integer salesForce)
	{
		this.salesForce = salesForce;
	}

	public User getRecruiter()
	{
		return recruiter;
	}

	public void setRecruiter(User recruiter)
	{
		this.recruiter = recruiter;
	}

	public Roles getRole()
	{
		return role;
	}

	public void setRole(Roles role)
	{
		this.role = role;
	}

	public LocalDate getPasswordIssueDate()
	{
		return passwordIssueDate;
	}

	public void setPasswordIssueDate(LocalDate passwordIssueDate)
	{
		this.passwordIssueDate = passwordIssueDate;
	}

	@Override
	public int hashCode()
	{
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
		User other = (User) obj;
		if (email == null)
		{
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null)
		{
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null)
		{
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (passwordIssueDate == null)
		{
			if (other.passwordIssueDate != null)
				return false;
		} else if (!passwordIssueDate.equals(other.passwordIssueDate))
			return false;
		if (recruiter == null)
		{
			if (other.recruiter != null)
				return false;
		} else if (!recruiter.equals(other.recruiter))
			return false;
		if (role == null)
		{
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (salesForce == null)
		{
			if (other.salesForce != null)
				return false;
		} else if (!salesForce.equals(other.salesForce))
			return false;
		if (userId == null)
		{
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "User [userId=" + userId + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", salesForce=" + salesForce + ", recruiter=" + recruiter + ", role=" + role + ", passIssuedDate="
				+ passwordIssueDate + "]";
	}

	
	
}
