package com.revature.aes.beans;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserUpdateHolder implements Serializable{

	private static final long serialVersionUID = -4757047255053843520L;

	private String newEmail;
	private String firstName;
	private String lastName;
	private String oldPassword;
	private String newPassword;
	private Integer newRecruiterId;
	private List<User> candidates;
	public Integer getNewRecruiterId() {
		return newRecruiterId;
	}

	public void setNewRecruiterId(Integer newRecruiterId) {
		this.newRecruiterId = newRecruiterId;
	}

	transient private boolean noOldPasswordCheck;
	
	public boolean isNoOldPasswordCheck() {
		return noOldPasswordCheck;
	}

	public void setNoOldPasswordCheck(boolean noOldPasswordCheck) {
		this.noOldPasswordCheck = noOldPasswordCheck;
	}

	public UserUpdateHolder() {
		super();
	}

	public UserUpdateHolder(String newEmail, String firstName, String lastName, String oldPassword,
			String newPassword) {
		super();
		this.newEmail = newEmail;
		this.firstName = firstName;
		this.lastName = lastName;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}



	@Override
	public String toString() {
		return "UserUpdateHolder [newEmail=" + newEmail + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", newRecruiterId=" + newRecruiterId
				+ "]";
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((newEmail == null) ? 0 : newEmail.hashCode());
		result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((oldPassword == null) ? 0 : oldPassword.hashCode());
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
		UserUpdateHolder other = (UserUpdateHolder) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (newEmail == null) {
			if (other.newEmail != null)
				return false;
		} else if (!newEmail.equals(other.newEmail))
			return false;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} else if (!newPassword.equals(other.newPassword))
			return false;
		if (oldPassword == null) {
			if (other.oldPassword != null)
				return false;
		} else if (!oldPassword.equals(other.oldPassword))
			return false;
		return true;
	}

	public List<User> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<User> candidates) {
		this.candidates = candidates;
	}
	
	
	
}
