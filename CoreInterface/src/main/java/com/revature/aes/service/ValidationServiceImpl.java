package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.User;

@Service
@Deprecated
public class ValidationServiceImpl implements ValidationService {
	
	@Autowired
	private ApplicationContext appContext;

	@Override
	public boolean validate(int userId) {
		// 
		User user = appContext.getBean(User.class);
		
		if(user != null && user.getUserId() == userId)
				return true;
		
		return false;
	}
	
	public User register(int userId) {
		User u = appContext.getBean(User.class);
		u.setUserId(userId);
		
		return u;
	}
}
