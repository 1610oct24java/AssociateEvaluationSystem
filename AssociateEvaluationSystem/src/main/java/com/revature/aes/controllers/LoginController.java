package com.revature.aes.controllers;

import com.revature.aes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controler for redirecting users to the login page
 * 
 * @author Willy Jensen
 *
 */
@Controller
public class LoginController {

	@Autowired
    UserService service;
	
	@RequestMapping(value="/",method = RequestMethod.GET)
	public String login() {
		return "redirect:index";
	}
}