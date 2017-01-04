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
	@Deprecated
	public boolean validate(int userId) {
		// 
		User user = appContext.getBean(User.class);
		
		if(user != null && user.getUserId() == userId)
				return true;
		
		return false;
	}
	
	@Deprecated
	public User register(int userId) {
		User u = appContext.getBean(User.class);
		u.setUserId(userId);
		
		return u;
	}
}
