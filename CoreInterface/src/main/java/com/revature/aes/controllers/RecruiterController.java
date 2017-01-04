package com.revature.aes.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.User;
import com.revature.aes.locator.MailServiceLocator;
import com.revature.aes.service.RestServices;
import com.revature.aes.service.UserService;

@RestController
public class RecruiterController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailServiceLocator mailService;
	
	@RequestMapping(value="/recruiter/{email}/candidates", method=RequestMethod.POST)
	public User createCandidate(@RequestBody User candidate, @PathVariable String email){
		Map<String,String> map = userService.createCandidate(candidate, email);
		User u = userService.findUserByEmail(candidate.getEmail());
		mailService.sendPassword(map.get("email"), map.get("link"), map.get("pass"));
		return u;
	}
	
	@RequestMapping(value="/recruiter/{email}/candidates", method=RequestMethod.GET)
	public List<User> getCandidates(@PathVariable String email){
		return userService.findUsersByRecruiter(email);
	}
	
	@RequestMapping(value="/recruiter/{email}/candidates/{index}", method=RequestMethod.GET)
	public User getCandidate(@PathVariable String email, @PathVariable int index){
		return userService.findUserByIndex(index, email);
	}
	
	@RequestMapping(value="/recruiter/{email}/candidates/{index}", method=RequestMethod.PUT)
	public User updateCandidate(@PathVariable String email, @PathVariable int index, @RequestBody User candidate){
		return userService.updateCandidate(candidate, email, index);
	}
	
	@RequestMapping(value="/recruiter/{email}/candidates/{index}", method=RequestMethod.DELETE)
	public void deleteCandidate(@PathVariable String email, @PathVariable int index){
		userService.removeCandidate(email, index);
	}
}
