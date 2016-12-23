package com.revature.aes.service;

import com.revature.aes.beans.User;

public interface ValidationService {
	public boolean validate(String username);

	public User register(String username);
}