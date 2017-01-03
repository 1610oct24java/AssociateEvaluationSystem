package com.revature.aes.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
 * @author Michelle Slay
 * @author Willie Jensen
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {
	Logger log = Logger.getRootLogger();
	
	@Autowired
	private UserDao dao;
	@Autowired 
	private SecurityService security;
	@Autowired
	private RoleService role;
	@Autowired
	private RestServices client;

	@Override
	public User findUserByEmail(String email) {
		return dao.findUserByEmail(email);
	}

	@Override
	public List<User> findAllUsers() {
		return dao.findAll();
	}
	
	/**
	 * createCandidate creates a candidate and adds them to the database.
	 * 
	 * The following method needed a little bit of added complexity by making it 
	 * a transaction. We didn't want it to be possible to add a user but have 
	 * the password adding fail.
	 * 
	 *  The pattern is necessary to work with the DATE format in Oracle SQL. 
	 *  It's possible that a different format is required for a different kind 
	 *  of database but that's your problem.
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
		
		String pass = security.createSecurity(candidate);
		
		client.finalizeCandidate(candidate, pass);
		
		return candidate;
	}

	@Override
	public List<User> findUsersByRecruiter(String email) {
		int recruiterId = dao.findUserByEmail(email).getUserId();
		
		return dao.findUsersByRecruiterId(recruiterId);
	}

	@Override
	public User findUserById(int id) {
		return dao.getOne(id);
	}

	@Override
	public User findUserByIndex(int index, String email) {
		// 
		List<User> users = findUsersByRecruiter(email);
		if(index >= users.size())
			return null;
		return users.get(index);
	}

	@Override
	public User updateCandidate(User updates, String email, int index) {
		// 
		User candidate = findUserByIndex(index, email);
		if(candidate == null)
			return null;
		
		Date passIssued;

		String inFormat = "yyyy-MM-dd HH:mm:ss.S";
		String outFormat = "dd-MMM-yy";
		SimpleDateFormat inFmt = new SimpleDateFormat(inFormat);
		SimpleDateFormat outFmt = new SimpleDateFormat(outFormat);
		
		try {
			passIssued = inFmt.parse(candidate.getDatePassIssued());
			candidate.setDatePassIssued(outFmt.format(passIssued));
		} catch (ParseException e) {
			log.error(e);
			return null;
		}

		candidate.setFirstName(updates.getFirstName());
		candidate.setLastName(updates.getLastName());
		candidate.setFormat(updates.getFormat());
		candidate.setEmail(updates.getEmail());
		
		return candidate;
	}

	@Override
	public void removeCandidate(String email, int index) {
		// 
		User candidate = findUsersByRecruiter(email).get(index);
		
		dao.delete(candidate);
	}
}
