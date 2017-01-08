package com.revature.aes.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Role;
import com.revature.aes.dao.RoleDao;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	private static final String CANDIDATE = "Candidate";
	private static final String RECRUITER = "Recruiter";
	private static final String TRAINER = "Trainer";
	Logger log = Logger.getRootLogger();
	
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
			if(CANDIDATE.equals(roleTitle))
				r.setRoleId(100);
			else if (TRAINER.equals(roleTitle))
				r.setRoleId(50);
			
			
			dao.save(r);
		}
		
		return r;
	}
	
	public void initRoles() {
		log.debug("here");
		Role candidate = dao.findRoleByRoleTitle(CANDIDATE);
		log.debug("CANDIDATE: " + candidate);
		if(candidate == null){
			candidate = new Role();
			candidate.setRoleTitle(CANDIDATE);
			candidate.setRoleId(50);
			dao.save(candidate);
		}
		Role trainer = dao.findRoleByRoleTitle(TRAINER);
		log.debug("TRAINER: " + trainer);
		if(trainer == null){
			trainer = new Role();
			trainer.setRoleTitle(TRAINER);
			trainer.setRoleId(100);
			dao.save(trainer);
		}
		Role recruiter = dao.findRoleByRoleTitle(RECRUITER);
		log.debug("RECRUITER: " + recruiter);
		if(recruiter == null){
			recruiter = new Role();
			recruiter.setRoleTitle(RECRUITER);
			recruiter.setRoleId(150);
			dao.save(recruiter);
		}
	}
}
