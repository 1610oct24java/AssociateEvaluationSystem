package com.revature.aes.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UserImpl implements User {
	private String username;

	@Override
	public boolean checkUsername(String username) {
		// 
		
		return username.equals(this.username);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
