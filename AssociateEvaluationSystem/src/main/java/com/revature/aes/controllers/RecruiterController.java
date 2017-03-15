package com.revature.aes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;
import com.revature.aes.locator.MailServiceLocator;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.AssessmentService;
import com.revature.aes.service.RestServices;
import com.revature.aes.service.RoleService;
import com.revature.aes.service.UserService;

/**
 * 
 * Controller for recruiters. Provides restful 
 * CRUD methods that can create, retrieve, update
 * or delete candidates from the database. 
 * 
 * Access is limited to recruiters.
 * 
 * @author Michelle Slay
 *
 */
@RestController
public class RecruiterController {

	@Autowired
	Logging log;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AssessmentService aService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MailServiceLocator mailService;
	@Autowired
	private RestServices client;
	
	/**
	 * This method adds a candidate to the system.
	 * 
	 * Edit (by Ric Smith)
	 * 		This method now only creates candidates, but doesn't send links to an assessment.
	 * 		Assessment assignments happen in the following method 'sendAssessment'
	 * 
	 * @param candidate
	 * 		the candidate that is to be added to the database
	 * @param email
	 * 		the email of this recruiter
	 * @return 
	 * 		the newly saved user object
	 */
	@RequestMapping(value="/recruiter/{email}/candidates", method= RequestMethod.POST)
	public void createCandidate(@RequestBody User candidate, @PathVariable String email){
		userService.createCandidate(candidate, email);
	}
	
	/**
	 * This method will make an assessment and send a link and temp password in an email
	 * to the candidate.
	 * 
	 * @author Ric S
	 * 
	 * @param email
	 * 		email of the candidate
	 * 
	 * @param type
	 * 		type of assessment such as 'java', 'csharp', etc
	 */
	@RequestMapping(value="/recruiter/sendAssessment/{email}/{type}", method=RequestMethod.POST)
	public void sendAssessment(@PathVariable String email, @PathVariable String type){
		User candidate = userService.findUserByEmail(email);
		String pass = userService.setCandidateSecurity(candidate);
		candidate.setFormat(type);
		String link = client.finalizeCandidate(candidate, pass);
		mailService.sendPassword(candidate.getEmail(), link, pass);
	}
	
	/**
	 * This method gets a list of candidates who reference
	 * this recruiter.
	 * 
	 * @param email
	 * 		the email of this recruiter
	 * @return 
	 * 		the list of users this recruiter added
	 */
	@RequestMapping(value="/recruiter/{email}/candidates", method= RequestMethod.GET)
	public List<User> getCandidates(@PathVariable String email){
		return userService.findUsersByRecruiter(email);
	}
	
	/**
	 * Gets the assessments of the selected user
	 * @param email
	 * @return The list of assessments
	 */
	@RequestMapping(value="/recruiter/{email}/assessments", method= RequestMethod.GET)
	public List<Assessment> getAssessment(@PathVariable String email){
		List<Assessment> a = aService.findAssessmentsByUser(userService.findUserByEmail(email));
		return a;
	}
	
	/**
	 * This method retrieves a particular candidate 
	 * at an index of the list returned by getCandidates
	 * 
	 * @param email
	 * 		The email of this recruiter
	 * @param index
	 * 		The index of the list returned by getCandidates
	 * @return
	 * 		the indexed User
	 */
	@RequestMapping(value="/recruiter/{email}/candidates/{index}", method= RequestMethod.GET)
	public User getCandidate(@PathVariable String email, @PathVariable int index){
		return userService.findUserByIndex(index, email);
	}
	
	/**
	 * This method changes details about a user in the database.
	 * 
	 * @param email
	 * 		The email of this recruiter
	 * @param index
	 * 		The candidate at the index of the list returned
	 * 		by getCandidates that needs updating 
	 * @param candidate
	 * 		The updated user object
	 * @return
	 * 		The newly saved User object
	 */
	@RequestMapping(value="/recruiter/{email}/candidates/{index}", method= RequestMethod.PUT)
	public User updateCandidate(@PathVariable String email, @PathVariable int index, @RequestBody User candidate){
		return userService.updateCandidate(candidate, email, index);
	}
	
	/**
	 * This method removes the indexed user from the database
	 * 
	 * @param email
	 * 		The email of this recruiter
	 * @param index
	 * 		The index of this user in the list returned by
	 * getCandidates
	 */
	@RequestMapping(value="/recruiter/{email}/candidates/{index}", method= RequestMethod.DELETE)
	public void deleteCandidate(@PathVariable String email, @PathVariable int index){
		userService.removeCandidate(email, index);
	}
	
	@RequestMapping(value="roles/init",method = RequestMethod.GET)
	public void initRoles() {
		roleService.initRoles();
	}
}
