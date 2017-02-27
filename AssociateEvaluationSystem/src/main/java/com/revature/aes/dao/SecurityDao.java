package com.revature.aes.dao;

import com.revature.aes.beans.Security;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityDao extends JpaRepository<Security, Integer> {

	public Security findSecurityByUserId(int id);
}
