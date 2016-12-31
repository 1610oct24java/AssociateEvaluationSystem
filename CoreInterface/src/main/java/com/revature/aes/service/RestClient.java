package com.revature.aes.service;

import com.revature.aes.beans.User;

public interface RestClient {
	public void finalizeCandidate(User candidate, String email);
}
