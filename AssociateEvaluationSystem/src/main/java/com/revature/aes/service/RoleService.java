package com.revature.aes.service;

import java.util.List;

import com.revature.aes.beans.Role;

public interface RoleService {
	public Role findRoleById(int roleId);
	public Role findRoleByRoleTitle(String roleTitle);
	public void initRoles();
	public List<Role> findAllRoles();
}
