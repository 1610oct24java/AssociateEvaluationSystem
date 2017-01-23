package com.revature.aes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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