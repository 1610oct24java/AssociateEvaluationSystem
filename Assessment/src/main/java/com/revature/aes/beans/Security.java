package com.revature.aes.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "AES_SECURITY")
public class Security {
	@Id
	@Column(name = "USER_ID")
	private int userId;
	@Column(name = "PASS_WORD")
	private String password;
	@Column(name = "VALID")
	private int valid;
	
	public Security() {
		super();
	}
	
	@Override
	public String toString() {
		return "Security [userId="
				+ userId
				+ ", password="
				+ password
				+ ", valid="
				+ valid
				+ "]";
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getValid() {
		return valid;
	}
	
	public void setValid(int valid) {
		this.valid = valid;
	}
}