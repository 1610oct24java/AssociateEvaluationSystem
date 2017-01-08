package com.revature.aes.controllers;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.User;
import com.revature.aes.locator.MailServiceLocator;
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
	Logger log = Logger.getRootLogger();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MailServiceLocator mailService;
	
	
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
	@RequestMapping(value="/recruiter/{email}/candidates", method=RequestMethod.POST)
	public User createCandidate(@RequestBody User candidate, @PathVariable String email){
		Map<String,String> map = userService.createCandidate(candidate, email);
		User u = userService.findUserByEmail(candidate.getEmail());
		mailService.sendPassword(map.get("email"), map.get("link"), map.get("pass"));
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
	@RequestMapping(value="/recruiter/{email}/candidates", method=RequestMethod.GET)
	public List<User> getCandidates(@PathVariable String email){
		return userService.findUsersByRecruiter(email);
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
	@RequestMapping(value="/recruiter/{email}/candidates/{index}", method=RequestMethod.GET)
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
	@RequestMapping(value="/recruiter/{email}/candidates/{index}", method=RequestMethod.PUT)
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
	@RequestMapping(value="/recruiter/{email}/candidates/{index}", method=RequestMethod.DELETE)
	public void deleteCandidate(@PathVariable String email, @PathVariable int index){
		userService.removeCandidate(email, index);
	}

	
	@RequestMapping(value="recruiter/{email}/init",method = RequestMethod.POST)
	public void initRecruiter(@PathVariable String email) {
		userService.createRecruiter(email);
	}
	
	@RequestMapping(value="trainer/{email}/init",method = RequestMethod.POST)
	public void initTrainer(@PathVariable String email) {
		userService.createTrainer(email);
	}
	
	@RequestMapping(value="roles/init",method = RequestMethod.GET)
	public void initRoles() {
		roleService.initRoles();
	}
}
