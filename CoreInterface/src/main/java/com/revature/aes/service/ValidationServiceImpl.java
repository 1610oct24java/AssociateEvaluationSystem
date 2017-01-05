package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.User;

/**
 * 
 * @author Michelle Slay
 *
 * @deprecated this was how we handled session validation before it turned out
 * Spring Security handles it all.
 * 
 * Don't use this. It's just here to find the other places it might have been 
 * used.
 *
 */
@Service
@Deprecated
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
