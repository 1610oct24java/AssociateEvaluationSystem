package com.revature.aes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Role;
import com.revature.aes.dao.RoleDao;
import com.revature.aes.logging.Logging;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	Logging log;
	
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
		Role candidate = dao.findRoleByRoleTitle("Candidate");
		log.debug("CANDIDATE: " + candidate);
		if(candidate == null){
			candidate = new Role();
			candidate.setRoleTitle("Candidate");
			candidate.setRoleId(50);
			dao.save(candidate);
		}
		Role trainer = dao.findRoleByRoleTitle("Trainer");
		log.debug("TRAINER: " + trainer);
		if(trainer == null){
			trainer = new Role();
			trainer.setRoleTitle("Trainer");
			trainer.setRoleId(100);
			dao.save(trainer);
		}
		Role recruiter = dao.findRoleByRoleTitle("Recruiter");
		log.debug("RECRUITER: " + recruiter);
		if(recruiter == null){
			recruiter = new Role();
			recruiter.setRoleTitle("Recruiter");
			recruiter.setRoleId(150);
			dao.save(recruiter);
		}
	}
	
	/**
	 * Retrieves all roles that are in the database.
	 * 
	 * @return
	 */
	public List<Role> getRoles() {
		return dao.findAll();
	}
	
}
