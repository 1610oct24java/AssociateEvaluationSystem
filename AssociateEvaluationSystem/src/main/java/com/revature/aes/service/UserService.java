package com.revature.aes.service;

import java.util.List;

import com.revature.aes.beans.User;
import com.revature.aes.beans.UserUpdateHolder;

public interface UserService {
	public User findUserByEmail(String email);
	public String createCandidate(User candidate, String recruiterEmail);
	public List<User> findAllUsers();
	public List<User> findUsersByRecruiter(String email);
	public User getUserById(int id);
	public User findUserByIndex(int index, String email);
	public User updateCandidate(User candidate, String email, int index);
	public void removeCandidate(String email, int index);
	public String createRecruiter(String email, String lastname, String firstname);
	public String createTrainer(String email, String lastname, String firstname);
	public List<User> findUsersByRole(String role);
	public void createAdmin(String email, String lastname, String firstname);
	public boolean updateEmployee(User user, UserUpdateHolder userUpdate);
	public void removeEmployee(String email);

}
