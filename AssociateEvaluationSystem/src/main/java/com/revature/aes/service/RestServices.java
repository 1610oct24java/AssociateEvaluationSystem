package com.revature.aes.service;

import com.revature.aes.beans.User;

@FunctionalInterface
public interface RestServices {
	public String finalizeCandidate(User candidate, String email);
}
