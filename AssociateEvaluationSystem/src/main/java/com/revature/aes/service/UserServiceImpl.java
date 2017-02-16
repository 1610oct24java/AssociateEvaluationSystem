package com.revature.aes.service;

import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.aes.beans.User;
import com.revature.aes.dao.UserDAO;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	public void createRecruiter(String email) {

		createAdmin(email, "recruiter");

	}

	@Override
	public void createTrainer(String email) {

		createAdmin(email, "trainer");

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