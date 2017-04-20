package com.revature.aes.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;

import com.revature.aes.beans.Security;
import com.revature.aes.beans.User;
import com.revature.aes.beans.UserUpdateHolder;
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

	/**
	 * Edit (by Ric Smith)
	 * 
	 * This method creates candidates and saves them.
	 */
	@org.springframework.transaction.annotation.Transactional(propagation= Propagation.REQUIRED)
	public boolean createCandidate(User usr, String recruiterEmail) {

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
		
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);
		candidate.setDatePassIssued(fmt.format(new Date()));
		
		User userCheck = dao.save(candidate);
		
		if (userCheck != null && userCheck.getEmail().equals(candidate.getEmail()))
		{
			return true;
		}else {
			return false;
		}
	}

	/**
	 * This method resets security for a candidate so that an 
	 * assessment will be processed and sent to them.
	 */
	@Override
	public String setCandidateSecurity(User candidate){
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);
		
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
	public void removeCandidate(User candidate) {
		dao.delete(candidate);
	}

	@Override
	public List<User> findUsersByRole(String role) {
		
		List<User> users = dao.findUsersByRole(role);
		
		return users;
	}

	@Override
	public void createAdmin(String email, String lastname, String firstname) {
		//dao.save(user);
	}

	
	//GENERATES A RANDOM PASSWORD STRING
	@Override
	public String createEmployee(User employee){
		
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);

		//check if employee did not supply email, or if email is already registered to another user
		if (employee.getEmail() == null || dao.findUserByEmail(employee.getEmail()) != null) {

			return null;

		}

		User user = new User();
		user.setEmail(employee.getEmail());
		user.setFirstName(employee.getFirstName());
		user.setLastName(employee.getLastName());
		user.setRole(role.findRoleByRoleTitle("recruiter"));
		user.setDatePassIssued(fmt.format(new Date()));
		
		dao.save(user);
		
		String pass = security.createSecurity(user);
		return pass;
	}
	
	@Override
	public boolean updateEmployee(User currentUser, UserUpdateHolder updatedUser) {
		
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);
		Security userSecure = security.findSecurityByUserId(currentUser.getUserId());
		boolean correctPassword = security.checkCorrectPassword(updatedUser.getOldPassword(), userSecure);
		
		if (correctPassword)
		{
			if (updatedUser.getNewEmail() != null && !updatedUser.getNewEmail().isEmpty())
			{	currentUser.setEmail(updatedUser.getNewEmail()); }
			if (updatedUser.getFirstName() != null && !updatedUser.getFirstName().isEmpty())
			{	currentUser.setFirstName(updatedUser.getFirstName()); }
			if (updatedUser.getLastName() != null && !updatedUser.getLastName().isEmpty())
			{	currentUser.setLastName(updatedUser.getLastName()); }
			if (updatedUser.getNewPassword() != null && !updatedUser.getNewPassword().isEmpty())
			{
				currentUser.setDatePassIssued(fmt.format(new Date()));
				
				userSecure.setPassword(updatedUser.getNewPassword());
				security.updateSecurity(userSecure);
			
			}else {
				try {
					Date oldPassDate = fmt.parse(currentUser.getDatePassIssued());
					currentUser.setDatePassIssued(fmt.format(oldPassDate));
				}catch (ParseException e) {
					e.printStackTrace();
				}finally {
					currentUser.setDatePassIssued(fmt.format(new Date()));
				}
			}
			dao.save(currentUser);
		}
		return correctPassword;
	}

	@Override
	public void removeEmployee(String email) {
		dao.delete(dao.findUserByEmail(email));
	}

}