package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.service.AssessmentAuthService;

@RestController
public class CandidateController {
	
	@Autowired
	private AssessmentAuthService authService;
	
	@RequestMapping(value="/candidate/{email}/link",method=RequestMethod.GET)
	public AssessmentAuth getLink(@PathVariable String email){
		AssessmentAuth auth = authService.getLink(email);
		//return auth.getUrlAssessment();
		return auth;
	}
}
