package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.revature.aes.beans.User;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
	public User findUserByEmail(String email);
	public User findByUserId(int userId);
	public List<User> findUsersByRecruiterId(int recruiterId);
	public List<User> findUsersByRole(String role);

	// Kevin Langhoff added code
	public List<User> findAll();
	


}
