package com.revature.aes.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ValidationController {
	
	@RequestMapping(value="/user")
	public Principal user(Principal user){
		return user;
	}
}