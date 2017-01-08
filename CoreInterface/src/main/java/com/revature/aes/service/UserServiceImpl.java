package com.revature.aes.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private static final String PATTERN = "dd-MMM-yy";
	
	@Autowired
	private UserDao dao;
	@Autowired 
	private SecurityService security;
	@Autowired
	private AssessmentService asmt;
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
	public Map<String,String> createCandidate(User usr, String recruiterEmail) {
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);
		
		User candidate = new User();
		candidate.setEmail(usr.getEmail());
		
		User existingCandidate = dao.findUserByEmail(candidate.getEmail());
		if(existingCandidate != null) {
			candidate = existingCandidate;
		}

		candidate.setFirstName(usr.getFirstName());
		candidate.setLastName(usr.getLastName());
		candidate.setFormat(usr.getFormat());
		
		int recruiterId = dao.findUserByEmail(recruiterEmail).getUserId();
		
		candidate.setRecruiterId(recruiterId);
		candidate.setRole(role.findRoleByRoleTitle("Candidate"));
		candidate.setDatePassIssued(fmt.format(new Date()));
		dao.save(candidate);
		
		String pass = security.createSecurity(candidate);
		
		return client.finalizeCandidate(candidate, pass);
	}

	@Override
	public List<User> findUsersByRecruiter(String email) {
		int recruiterId = dao.findUserByEmail(email).getUserId();
		List<User> users = dao.findUsersByRecruiterId(recruiterId); 
		
		for(User u : users){
			Integer grade = asmt.findGradeByUser(u);
			if(grade!=null)
				u.setGrade(grade);
			else
				u.setGrade(-1);
		}
		
		log.debug(users);	
		return users;
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

	@Override
	public void createRecruiter(String email) {
		// 
		createAdmin(email, "Recruiter");
	}

	@Override
	public void createTrainer(String email) {
		// 
		createAdmin(email, "Trainer");
	}
	
	private void createAdmin(String email, String adminRole){
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);
		User recruiter = new User();
		recruiter.setEmail(email);
		recruiter.setFirstName("John");
		recruiter.setLastName(adminRole);
		
		recruiter.setRole(role.findRoleByRoleTitle(adminRole));
		recruiter.setDatePassIssued(fmt.format(new Date()));
		dao.save(recruiter);
		
		security.createKnownSecurity(recruiter);
	}
}
