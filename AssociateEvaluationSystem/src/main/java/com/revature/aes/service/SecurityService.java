package com.revature.aes.service;

import com.revature.aes.beans.Security;
import com.revature.aes.beans.User;

public interface SecurityService {
	public Security findSecurityByUserId(int id);
	public String createSecurity(User user);
	public void updateSecurity(Security security);
	public void invalidatePassword(User u);
	public String createKnownSecurity(User superuser);
	public String createAdminRoleSecurity(User admin);
	public void removeSecurity(int userId);
	public boolean checkCorrectPassword(String enteredPass, Security security);
}
