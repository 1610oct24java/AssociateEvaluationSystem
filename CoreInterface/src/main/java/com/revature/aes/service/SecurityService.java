package com.revature.aes.service;

import com.revature.aes.beans.Security;
import com.revature.aes.beans.User;

public interface SecurityService {
	public Security findSecurityByUserId(int id);
	
	public Security createSecurity(User user);
}
