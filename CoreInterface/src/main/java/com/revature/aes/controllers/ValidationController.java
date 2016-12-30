package com.revature.aes.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {
	
	@CrossOrigin
	@RequestMapping(value="/user")
	public Principal user(Principal user){
		System.out.println("user: " + user);
		return user;
	}
}