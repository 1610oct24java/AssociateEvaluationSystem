package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.aes.beans.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {
	public Role findRoleByRoleTitle(String roleTitle);
}