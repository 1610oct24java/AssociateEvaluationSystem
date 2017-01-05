package com.revature.aes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.User;
import com.revature.aes.daos.UserDAO;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO uDao;
	
	@Override
	public User getUserByEmail(String email) {

		return uDao.findByEmail(email);
		
	}

	@Override
	public User getUserById(int id) {
		System.out.println(id);
		return uDao.findByUserId(id);
	}
	
	

	
	
}
