package com.revature.aes.service;

import java.util.List;

import com.revature.aes.beans.User;

public interface UserService {
	public User findUserByEmail(String email);
	public User createCandidate(User candidate, String recruiterEmail);
	public List<User> findAllUsers();
}
