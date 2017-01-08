package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Role;
import com.revature.aes.dao.RoleDao;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao dao;

	@Override
	public Role findRoleById(int roleId) {
		// 
		return dao.getOne(roleId);
	}

	@Override
	public Role findRoleByRoleTitle(String roleTitle) {
		//
		Role r = dao.findRoleByRoleTitle(roleTitle);
		
		if(r == null){
			r = new Role();
			r.setRoleTitle(roleTitle);
			
			dao.save(r);
		}
		return r;
	}
}
