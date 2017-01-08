package com.revature.aes.service;

import com.revature.aes.beans.Role;

public interface RoleService {
	public Role findRoleById(int roleId);
	public Role findRoleByRoleTitle(String roleTitle);
	public void initRoles();
}
