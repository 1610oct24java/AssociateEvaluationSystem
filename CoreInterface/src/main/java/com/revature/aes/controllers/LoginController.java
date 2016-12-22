package com.revature.aes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String getLoginPage(ModelMap modelMap) {
		
		
		return "login";
	}
	
	@RequestMapping(value="/recruiter/login",method = RequestMethod.GET)
	public String doLogin() {
		
		
		return "login";
	}
}
