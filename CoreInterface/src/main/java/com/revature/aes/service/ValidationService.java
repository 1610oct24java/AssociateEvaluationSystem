package com.revature.aes.service;

import com.revature.aes.beans.User;

public interface ValidationService {
	public boolean validate(int userid);

	public User register(int userid);
}