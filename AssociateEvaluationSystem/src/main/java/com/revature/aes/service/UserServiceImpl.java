package com.revature.aes.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;

import com.revature.aes.beans.User;
import com.revature.aes.dao.UserDAO;
import com.revature.aes.logging.Logging;

@org.springframework.stereotype.Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final String PATTERN = "dd-MMM-yy";

	@Autowired
	Logging log;
	@Autowired
	UserDAO dao;
	@Autowired
	private SecurityService security;
	@Autowired
	private AssessmentService asmt;
	@Autowired
	private RoleService role;

	@Override
	public User findUserByEmail(String email) {
		return dao.findUserByEmail(email);
	}

	@org.springframework.transaction.annotation.Transactional(propagation= Propagation.REQUIRED)
	public String createCandidate(User usr, String recruiterEmail) {
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
		candidate.setRole(role.findRoleByRoleTitle("candidate"));
		candidate.setDatePassIssued(fmt.format(new Date()));
		dao.save(candidate);

		String pass = security.createSecurity(candidate);

		return pass;
	}

	@Override
	public List<User> findAllUsers() {
		return dao.findAll();
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

		log.debug(users.toString());
		return users;
	}

	@Override
	public User getUserById(int id) {
		
		return dao.findOne(id);
	}

	@Override
	public User findUserByIndex(int index, String email) {
		List<User> users = findUsersByRecruiter(email);
		if(index >= users.size())
			return null;

		return users.get(index);
	}

	@Override
	public User updateCandidate(User updates, String email, int index) {
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
			log.error(e.toString());
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

		User candidate = findUsersByRecruiter(email).get(index);

		dao.delete(candidate);

	}

	@Override
	public String createRecruiter(String email, String lastname, String firstname) {

		return createEmployee(email, lastname, firstname, "recruiter");

	}

	@Override
	public String createTrainer(String email, String lastname, String firstname) {
  
		return createEmployee(email, lastname, firstname, "trainer");
    
	}

	private String createEmployee(String email, String lastname, String firstname, String adminRole){
		
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setRole(role.findRoleByRoleTitle(adminRole));
		user.setDatePassIssued(fmt.format(new Date()));
		
		dao.save(user);
		
		String pass = security.createAdminRoleSecurity(user);
		return pass;
	}

	@Override
	public List<User> findUsersByRole(String role) {
		
		List<User> users = dao.findUsersByRole(role);
		
		return users;
	}

	@Override
	public void createAdmin(String email, String lastname, String firstname) {
		
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setRole(role.findRoleByRoleTitle("admin"));
		user.setDatePassIssued(fmt.format(new Date()));
		
		dao.save(user);
		security.createKnownSecurity(user);
	}

	@Override
	public User updateEmployee(User updatedUser, String email) {
		
		User currentUser = dao.findByEmail(email);
		currentUser.setEmail(updatedUser.getEmail());
		currentUser.setFirstName(updatedUser.getFirstName());
		currentUser.setLastName(updatedUser.getLastName());
		dao.save(currentUser);
		return null;
	}

	@Override
	public void removeEmployee(String email) {
		dao.delete(dao.findByEmail(email));
	}

}