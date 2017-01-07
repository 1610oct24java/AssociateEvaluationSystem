package com.revature.aes.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.aes.beans.User;

public class UsersService implements IUsersService{

	@Autowired
	UsersDao usersDao;

	@Override
	public User getUserById(int id) {
		
		return usersDao.findOne(id);
	}
}