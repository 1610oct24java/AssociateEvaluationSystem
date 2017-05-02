package com.revature.aes.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
	
	@Override
	public User findUserByEmailIgnoreCase(String email) {
		return dao.findByEmailIgnoreCase(email);
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

		User existingCandidate = dao.findUserByEmailIgnoreCase(candidate.getEmail());
		if(existingCandidate != null) {
			candidate = existingCandidate;
		}

		candidate.setFirstName(usr.getFirstName());
		candidate.setLastName(usr.getLastName());
		candidate.setFormat(usr.getFormat());

		int recruiterId = dao.findUserByEmailIgnoreCase(recruiterEmail).getUserId();

		candidate.setRecruiterId(recruiterId);
		candidate.setRole(role.findRoleByRoleTitle("candidate"));
		
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);
		candidate.setDatePassIssued(fmt.format(new Date()));
		
		User userCheck = dao.save(candidate);
		
		if (userCheck != null && userCheck.getEmail().equalsIgnoreCase(candidate.getEmail()))
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
		int recruiterId = dao.findByEmailIgnoreCase(email).getUserId();
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
		
		List<User> users = dao.findUsersByRole_RoleTitle(role);
		
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
		if (employee.getEmail() == null || dao.findUserByEmailIgnoreCase(employee.getEmail()) != null) {

			return null;

		}
		
		System.out.println("////////////////////////////////////////////////////////////\nRole object passed: " + employee.getRole() + "//////////////////////////////\n");

		User user = new User();
		user.setEmail(employee.getEmail());
		user.setFirstName(employee.getFirstName());
		user.setLastName(employee.getLastName());
		//user.setRole(role.findRoleByRoleTitle("recruiter")); //sledgehammer's hardcode
		user.setRole(employee.getRole());
		user.setDatePassIssued(fmt.format(new Date()));
		
		dao.save(user);
		
		String pass = security.createSecurity(user);
		return pass;
	}
	
	@Override
	public List<User> updateCandidatesOnRecruiter(String userEmail, List<User> newCandidates){
		Boolean b = true;
		List <User> oldCandidatesList = this.findUsersByRecruiter(userEmail);
		User recruiter = this.findUserByEmail(userEmail);
		//gets a list of candidates that no longer has this recruiter
		List <User> candidatesToRemoveRecruiter = new LinkedList<>(oldCandidatesList);
		candidatesToRemoveRecruiter.removeAll(newCandidates);
		
		boolean addCommaToLog = false;
		
		//get a list of candidates that now get this recruiter
		newCandidates.removeAll(oldCandidatesList);
		
		//creates the log message for candidates being removed
		StringBuilder logMessage1 = new StringBuilder();
		logMessage1.append("Users being removed from recruiter id # ");
		logMessage1.append(recruiter.getUserId());
		logMessage1.append(" : ");
		
		
		
		//removes recruiter from candidate
		for(User c : candidatesToRemoveRecruiter){
			if (addCommaToLog){
				logMessage1.append(", ");
			}
			
			//removes recruiter from candidates
			UserUpdateHolder updateHolder = new UserUpdateHolder();
			updateHolder.setNoOldPasswordCheck(true);
			updateHolder.setNewRecruiterId(0);
			b = this.updateEmployee(c, updateHolder) && b;
			
			logMessage1.append(c.getUserId());
			addCommaToLog = true;
		}
		
		//creates the log message for new candidates being added
		StringBuilder logMessage2 = new StringBuilder();
		logMessage2.append("Users being added to recruiter id # ");
		logMessage2.append(recruiter.getUserId());
		logMessage2.append(" : ");

		addCommaToLog = false;
		
		//adds recruiter to candidate
		for (User c : newCandidates){
			if (addCommaToLog){
				logMessage2.append(", ");
			}
			
			UserUpdateHolder updateHolder = new UserUpdateHolder();
			updateHolder.setNewRecruiterId(recruiter.getUserId());
			updateHolder.setNoOldPasswordCheck(true);
			
			logMessage2.append(c.getUserId());
			addCommaToLog = true;
			
			b =  this.updateEmployee(c, updateHolder) && b;
		}
		

		if (!candidatesToRemoveRecruiter.isEmpty()){
			log.info(logMessage1.toString());
		}
		if (!newCandidates.isEmpty()){
			log.info(logMessage2.toString());
		}
		
		return this.findUsersByRecruiter(userEmail);
	}
	
	
	@Override
	public boolean updateEmployee(User currentUser, UserUpdateHolder updatedUser) {
		
		SimpleDateFormat fmt = new SimpleDateFormat(PATTERN);
		Security userSecure = security.findSecurityByUserId(currentUser.getUserId());
		
		//checks to see if it needs to checks the old password, if so compares the old password to check if it is valid.
		boolean correctPassword = updatedUser.isNoOldPasswordCheck() || security.checkCorrectPassword(updatedUser.getOldPassword(), userSecure);
		
		if (correctPassword)
		{
			//handles updating candidates for recruiters
			if("recruiter".equals(currentUser.getRole().getRoleTitle())){
				List<User> candidates = updatedUser.getCandidates();
				candidates = this.updateCandidatesOnRecruiter(currentUser.getEmail(), candidates);
				int i = 1;
			}
			
			if (updatedUser.getNewEmail() != null && !updatedUser.getNewEmail().isEmpty())
			{	currentUser.setEmail(updatedUser.getNewEmail()); }
			if (updatedUser.getFirstName() != null && !updatedUser.getFirstName().isEmpty())
			{	currentUser.setFirstName(updatedUser.getFirstName()); }
			if (updatedUser.getLastName() != null && !updatedUser.getLastName().isEmpty())
			{	currentUser.setLastName(updatedUser.getLastName()); }
			if (updatedUser.getNewRecruiterId() != null){
				// if new Recruiter id is 0 or belows, it will reflect null in the database
				if (updatedUser.getNewRecruiterId() <= 0){
					currentUser.setRecruiterId(null);
				} else {
					currentUser.setRecruiterId(updatedUser.getNewRecruiterId());
				}
			}
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

	@Override
	public String findEmailById(int id) {
		// TODO Auto-generated method stub
		String email = dao.findByUserId(id).getEmail();
		return email;
	}

}
