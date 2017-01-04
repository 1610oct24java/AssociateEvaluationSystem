package com.revature.aes.service;

import java.util.Map;

import com.revature.aes.beans.User;

@FunctionalInterface
public interface RestServices {
	public Map<String,String> finalizeCandidate(User candidate, String email);
}
