package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.aes.beans.User;
import com.revature.aes.dao.UsersDao;

public class UsersServiceImpl implements UsersService{

	@Autowired
	UsersDao usersDao;

	@Override
	public User getUserById(int id) {
		
		return usersDao.findOne(id);
	}
}