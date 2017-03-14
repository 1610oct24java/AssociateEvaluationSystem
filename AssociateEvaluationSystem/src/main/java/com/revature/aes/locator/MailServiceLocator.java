package com.revature.aes.locator;

public interface MailServiceLocator {
	public boolean sendPassword(String email, String... contents);
	
	// Sends temp password to newly registered recruiters and trainers
	public boolean sendTempPassword(String email, String pass);
	
	public void overdueAlert(String email);
}