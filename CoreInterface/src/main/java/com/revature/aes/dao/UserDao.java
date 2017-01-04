package com.revature.aes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>{
	public User findUserByEmail(String email);
	public String findAssesmentByUserId(int id);
	public List<User> findUsersByRecruiterId(int recruiterId);
}