package com.revature.aes.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Michelle Slay
 *
 */
@RestController
public class ValidationController {
	
	/**
	 * This returns the currently logged in User
	 * as handled by Spring Security.
	 * 
	 * @param user
	 * 		Spring security injects the currently logged
	 * 		in user whenever this method is called
	 * @return
	 * 		The currently logged in user
	 */
	@RequestMapping(value="/security/auth")
	public Principal user(Principal user){
		return user;
	}
}