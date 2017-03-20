package com.revature.aes.controllers;

import java.util.List;

import com.revature.aes.beans.UserUpdateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	 * This method tries to add a candidate to the system.
	 * 
	 * @param candidate
	 * 		the candidate that is to be added to the database
	 * @param email
	 * 		the email of this recruiter
	 * @return 
	 * 		the newly saved user object
	 */
	@RequestMapping(value="/recruiter/{email}/candidates", method= RequestMethod.POST)
	public User createCandidate(@RequestBody User candidate, @PathVariable String email){
		String pass = userService.createCandidate(candidate, email);
		User u = userService.findUserByEmail(candidate.getEmail());
		String link = client.finalizeCandidate(u, pass);
		mailService.sendPassword(u.getEmail(), link, pass);
		log.debug("USER: " + u);
		return u;
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

	@RequestMapping(value="recruiter/update/{email}/", method= RequestMethod.PUT)
	public @ResponseBody boolean updateEmployee(@RequestBody UserUpdateHolder userUpdate, @PathVariable String email){
		User currentUser = userService.findUserByEmail(email);
		if (currentUser != null){
			if (!userService.updateEmployee(currentUser, userUpdate)){
				return false;
			}
			return true;
		}
		else {
			return false;
		}
	}

//	THESE ARE NOW IMPLEMENTED IN com.revature.aes.controllers.AdminController.java
	@RequestMapping(value="recruiter/{email}/init",method = RequestMethod.POST)
	public void initRecruiter(@PathVariable String email) {
		userService.createRecruiter(email, "Recruiter", "Tim");
	}
//	
//	@RequestMapping(value="trainer/{email}/init",method = RequestMethod.POST)
//	public void initTrainer(@PathVariable String email) {
//		userService.createTrainer(email);
//	}
	
	@RequestMapping(value="roles/init",method = RequestMethod.GET)
	public void initRoles() {
		roleService.initRoles();
	}
}
