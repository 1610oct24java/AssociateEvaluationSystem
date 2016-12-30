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

/**
 * Provides an implementation of the UserService
 * interacts with the UserDao as well as the SecurityService 
 * to create/read/update/remove users to/from the database.
 * 
 * Pretty straightforward.
 * 
 * @author Michelle Slay
 * @author Willie Jensen
 */

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
	
	/**
	 * The following method needed a little bit of added complexity by making it 
	 * a transaction. We didn't want it to be possible to add a user
	 * but have the password adding fail.
	 * 
	 *  The pattern is necessary to work with the DATE format in 
	 *  Oracle SQL. It's possible that a different format would be 
	 *  usable with a different kind of database.
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public User createCandidate(User candidate, String recruiterEmail) {
		String pattern = "dd-MMM-yy";
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		
		int recruiterId = dao.findUserByEmail(recruiterEmail).getUserId();
		
		candidate.setRecruiterId(recruiterId);
		candidate.setRole(role.findRoleByRoleTitle("Candidate"));
		candidate.setDatePassIssued(fmt.format(new Date()));
		
		dao.save(candidate);
		
		security.createSecurity(candidate);
		
		return candidate;
	}

	@Override
	public List<User> findUsersByRecruiter(String email) {
		int recruiterId = dao.findUserByEmail(email).getUserId();
		
		return dao.findUsersByRecruiterId(recruiterId);
	}
}
