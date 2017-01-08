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
		
		Role r = dao.findRoleByRoleTitle(roleTitle);
		if(r == null){
			r = new Role();
			r.setRoleTitle(roleTitle);
			if("Candidate".equals(roleTitle))
				r.setRoleId(100);
			else if ("Trainer".equals(roleTitle))
				r.setRoleId(50);
			
			
			dao.save(r);
		}
		
		return r;
	}
	
	public void initRoles() {
		System.out.println("here");
		Role candidate = dao.findRoleByRoleTitle("Candidate");
		System.out.println("CANDIDATE: " + candidate);
		if(candidate == null){
			candidate = new Role();
			candidate.setRoleTitle("Candidate");
			candidate.setRoleId(50);
			dao.save(candidate);
		}
		Role trainer = dao.findRoleByRoleTitle("Trainer");
		System.out.println("TRAINER: " + trainer);
		if(trainer == null){
			trainer = new Role();
			trainer.setRoleTitle("Trainer");
			trainer.setRoleId(100);
			dao.save(trainer);
		}
		Role recruiter = dao.findRoleByRoleTitle("Recruiter");
		System.out.println("RECRUITER: " + recruiter);
		if(recruiter == null){
			recruiter = new Role();
			recruiter.setRoleTitle("Recruiter");
			recruiter.setRoleId(150);
			dao.save(recruiter);
		}
	}
}
