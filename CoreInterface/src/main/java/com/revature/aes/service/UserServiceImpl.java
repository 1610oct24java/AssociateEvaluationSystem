package com.revature.aes.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.User;
import com.revature.aes.dao.UserDao;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao dao;
	@Autowired 
	private SecurityService security;
	@Autowired
	private RoleService role;

	@Override
	public User findUserByEmail(String email) {
		return dao.findUserByEmail(email);
	}

	@Override
	public List<User> findAllUsers() {
		return dao.findAll();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public User createCandidate(User candidate, String recruiterEmail) {
		String pattern = "dd-MMM-yy";
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		
		User recruiter = dao.findUserByEmail(recruiterEmail);
		candidate.setRecruiterId(recruiter.getUserId());
		candidate.setRole(role.findRoleByRoleTitle("Candidate"));
		candidate.setDatePassIssued(fmt.format(new Date()));
		
		candidate = dao.save(candidate);
		
		
		security.createSecurity(candidate);
		
		return candidate;
	}
}
