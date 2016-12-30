package com.revature.aes.locator;

public interface MailServiceLocator {
	public boolean send(String email, String... contents);
}