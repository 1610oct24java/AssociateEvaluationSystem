package com.revature.aes.service;

import java.util.List;

import com.revature.aes.beans.User;
import com.revature.aes.beans.UserUpdateHolder;

public interface UserService {
	public User findUserByEmail(String email);
	public User findUserByEmailIgnoreCase(String email);
	public List<User> findAllUsers();
	public List<User> findUsersByRecruiter(String email);
	public User getUserById(int id);
	public User findUserByIndex(int index, String email);
	public boolean createCandidate(User candidate, String recruiterEmail);
	public User updateCandidate(User candidate, String email, int index);
	public void removeCandidate(User candidate);
	public List<User> findUsersByRole(String role);
	public void createAdmin(String email, String lastname, String firstname);
	public String createEmployee(User employee);
	public boolean updateEmployee(User user, UserUpdateHolder userUpdate);
	public void removeEmployee(String email);
	public String setCandidateSecurity(User candidate);
	public String findEmailById(int id);
}
