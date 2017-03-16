package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.aes.beans.Security;

public interface SecurityDao extends JpaRepository<Security, Integer> {

	public Security findSecurityByUserId(int id);
}
