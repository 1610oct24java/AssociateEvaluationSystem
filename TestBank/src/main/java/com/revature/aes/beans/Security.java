package com.revature.aes.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the AES_SECURITY database table.
 * 
 */
@Entity
@Table(name="AES_SECURITY")
public class Security implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private long userId;

	@Column(name="PASS_WORD")
	private String passWord;

	private BigDecimal valid;

	//bi-directional one-to-one association to AesUser
	@OneToOne
	@JoinColumn(name="USER_ID")
	private User user;

	public Security() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPassWord() {
		return this.passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public BigDecimal getValid() {
		return this.valid;
	}

	public void setValid(BigDecimal valid) {
		this.valid = valid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}