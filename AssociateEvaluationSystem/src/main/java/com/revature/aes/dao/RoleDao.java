package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.aes.beans.Role;

/**
 * This is a repository that checks the role
 * of a particular user.
 * 
 * @author Willie Jensen
 *
 */
public interface RoleDao extends JpaRepository<Role, Integer> {
	public Role findRoleByRoleTitle(String roleTitle);
}