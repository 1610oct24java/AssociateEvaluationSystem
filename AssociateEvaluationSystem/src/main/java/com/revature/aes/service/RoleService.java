package com.revature.aes.service;

import com.revature.aes.beans.Role;
import java.util.List;

public interface RoleService {
	public Role findRoleById(int roleId);
	public Role findRoleByRoleTitle(String roleTitle);
	public void initRoles();
	public List<Role> getRoles();
}
