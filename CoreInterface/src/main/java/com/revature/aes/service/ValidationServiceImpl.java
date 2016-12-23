package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.User;

@Service
public class ValidationServiceImpl implements ValidationService {
	
	@Autowired
	private ApplicationContext appContext;

	@Override
	public boolean validate(String username) {
		// 
		User user = (User) appContext.getBean(User.class);
		
		if(user != null && user.checkUsername(username))
			return true;
		return false;
	}
	
	public User register(String username) {
		User u = appContext.getBean(User.class);
		u.setUsername(username);
		
		return u;
	}
}
