package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AES_API_TOKEN")
public class Token implements Serializable{
	
	private static final long serialVersionUID = -8167297380057309451L;

	@Id
	@Column(name="API_ID")
	private int apiTokenId;
	
	@Column(name="TOKEN")
	private String token;
	
	@Column(name="VALID")
	private int valid;

	public Token() {
		super();
	}

	public int getApiTokenId() {
		return apiTokenId;
	}

	public void setApiTokenId(int apiTokenId) {
		this.apiTokenId = apiTokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}
}
