package com.revature.aes.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AES_SECURITY")
public class Security {
	@Id
	@Column(name="USER_ID")
	private int userId;
	@Column(name="PASS_WORD")
	private String password;
	@Column(name="VALID")
	private int valid;
}
