package com.revature.aes.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
	public User findByUserId(int userId);

}
