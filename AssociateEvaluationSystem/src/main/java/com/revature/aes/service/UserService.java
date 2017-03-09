package com.revature.aes.service;

import com.revature.aes.beans.User;

import java.util.List;

public interface UserService {
	public User findUserByEmail(String email);
	public String createCandidate(User candidate, String recruiterEmail);
	public List<User> findAllUsers();
	public List<User> findUsersByRecruiter(String email);
	public User getUserById(int id);
	public User findUserByIndex(int index, String email);
	public User updateCandidate(User candidate, String email, int index);
	public void removeCandidate(String email, int index);
	public void createRecruiter(String email, String lastname, String firstname);
	public void createTrainer(String email, String lastname, String firstname);
	public List<User> findUsersByRole(String role);
	public void createAdmin(String email, String lastname, String firstname);
	public User updateEmployee(User user, String email);
	void removeEmployee(String email);
	//void updateEmployee(User user, String email, String lastname, String firstname);
	void updateEmployee(String email, String lastname, String firstname);
}
