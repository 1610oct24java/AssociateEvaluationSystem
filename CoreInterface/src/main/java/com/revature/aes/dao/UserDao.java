package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>{
	public User findUserByEmail(String email);
	public User registerCanidate(User canidate);
}
