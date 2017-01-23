package com.revature.aes.controllers;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.service.AssessmentAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * This is the controller the candidates access to find the link to their
 * assessment.
 * 
 * The email path variable should be the candidate's email.
 * 
 * @author Michelle Slay
 *
 */
@RestController
public class CandidateController {
	
	@Autowired
	private AssessmentAuthService authService;
	
	/**
	 * 
	 * getLink retrieves the link associated with the candidate
	 * whose "username" is the same as the provided email parameter.
	 * The link is a field inside the AssessmentAuth class.
	 * 
	 * @param email
	 * @return AssessmentAuth
	 */
	@RequestMapping(value="/candidate/{email}/link",method= RequestMethod.GET)
	public AssessmentAuth getLink(@PathVariable String email){
		return authService.getLink(email);
		
	}
}
