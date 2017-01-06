package com.revature.aes.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {
	
	@RequestMapping(value="/security/auth")
	public Principal user(Principal user){
		System.out.println("VALIDATING");
		return user;
	}
}