package com.revature.aes.locator;

@FunctionalInterface
public interface MailServiceLocator {
	public boolean send(String email, String... contents);
}