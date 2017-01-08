package com.revature.aes.dao;

import com.revature.aes.beans.User;

@FunctionalInterface
public interface IUsersService {

	public User getUserById(int id);
}
