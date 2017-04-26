package com.revature.aes.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.aes.beans.UserUpdateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;
import com.revature.aes.locator.MailServiceLocator;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.AssessmentAuthService;
import com.revature.aes.service.AssessmentService;
import com.revature.aes.service.RestServices;
import com.revature.aes.service.RoleService;
import com.revature.aes.service.SecurityService;
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
	private AssessmentAuthService authService;
	
	@Autowired
	private SecurityService secService;
	
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
	@RequestMapping(value="/recruiter/{username}/candidates", method=RequestMethod.POST)
	public String createCandidate(@RequestBody User candidate, @PathVariable String username) 
			throws JsonParseException, JsonMappingException, IOException {
		
		boolean success = userService.createCandidate(candidate, username);
		
		if (success)
		{
			return "{\"msg\":\"success\"}";
		}else {
			return "{\"msg\":\"fail\"}";
		}
	}
	
	/**
	 * This method will make an assessment and send a link and temp password in an email
	 * to the candidate.
	 * 
	 * @author Ric Smith
	 * 
	 * @param user
	 *		User object with candidate's email and format for assessment
	 */
	@RequestMapping(value="/recruiter/candidate/assessment", method=RequestMethod.POST)
	public ResponseEntity<Map> sendAssessment(@RequestBody User user){
		Map<String, String> response = new HashMap<>();

		User candidate = userService.findUserByEmail(user.getEmail());

		String pass = userService.setCandidateSecurity(candidate);
		candidate.setFormat(user.getFormat());
		String link = client.finalizeCandidate(candidate, pass);
		if(mailService.sendPassword(candidate.getEmail(), link, pass)){

			response.put("msg", "success");
			return ResponseEntity.ok(response);

		}
		else{

			response.put("msg", "failed to send assessment");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
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
	 * This method removes a candidate
	 * 
	 * @author (Edited by Ric)
	 * 
	 * @param email  (String: candidate email)
	 */
	@RequestMapping(value="/recruiter/candidate/{email}/delete", method= RequestMethod.DELETE)
	public void deleteCandidate(@PathVariable String email){
		
		User candidate = userService.findUserByEmail(email);
		
		if (candidate != null)
		{
			List<Assessment> assList = aService.findByUser(candidate);
			if (!assList.isEmpty())
			{
				for (Assessment ass : assList)
				{
					aService.deleteAssessment(ass);
				}
			}
			
			authService.remove(candidate.getUserId());
			secService.removeSecurity(candidate.getUserId());
			
			userService.removeCandidate(candidate);
		}
	}

	@RequestMapping(value="/recruiter/{currentEmail}/update", method= RequestMethod.PUT)
	public ResponseEntity<Map> updateEmployee(@RequestBody UserUpdateHolder userUpdate, @PathVariable String currentEmail){
		User currentUser = userService.findUserByEmail(currentEmail);
		Map<String,String> response = new HashMap<>();

		if (userService.updateEmployee(currentUser, userUpdate)) {
			response.put("msg", "success");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		else {
			response.put("msg", "invalid credentials");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@RequestMapping(value="roles/init",method = RequestMethod.GET)
	public void initRoles() {
		roleService.initRoles();
	}
	
	// this obtains all the emails in the database
	@RequestMapping(value="recruiter/emails")
	public List<String> getEmails() {
		List<String> allEmails = new ArrayList<String>();
		for (User user : userService.findAllUsers()){
			allEmails.add(user.getEmail());
		}
		return allEmails;
	}
	
	// updating a candidate's info
	@RequestMapping(value="recruiter/update/candidate")
	public ResponseEntity<User> updateCandidate(@RequestBody User candidate) {
		// logic goes here to update info
		return new ResponseEntity<User>(candidate, HttpStatus.OK);
	}
}
