package com.revature.aes.service;

import com.revature.aes.beans.User;

public interface UserService {
	
	public User getUserByEmail(String email);
	public User getUserById(int id);

}
