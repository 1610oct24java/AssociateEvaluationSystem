package com.revature.aes.locator;

public interface MailServiceLocator {
	public boolean sendPassword(String email, String... contents);
	public void overdueAlert(String email);
}