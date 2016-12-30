package com.revature.aes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.User;
import com.revature.aes.service.UserService;

@RestController
public class RecruiterController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/candidate", method=RequestMethod.POST)
	public User createCandidate(@RequestBody User candidate){
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return userService.createCandidate(candidate, user.getUsername());
	}
	
	@RequestMapping(value="/candidates", method=RequestMethod.GET)
	public List<User> getCandidates(){
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return userService.findUsersByRecruiter(user.getUsername());
	}
	
	@RequestMapping(value="/candidates/{id}", method=RequestMethod.GET)
	public User getCandidate(@PathVariable int id){
		return userService.findUserById(id);
	}
}
