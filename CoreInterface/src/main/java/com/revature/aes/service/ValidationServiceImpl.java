package com.revature.aes.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.User;

@Service
@Scope("session")
public class ValidationServiceImpl implements ValidationService {

	@Override
	public User validate(User u) {
		// 
		return null;
	}

}
