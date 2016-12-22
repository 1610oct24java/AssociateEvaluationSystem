package com.revature.aes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.revature.aes.beans.User;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@RequestMapping(method = RequestMethod.GET)
	public String getLoginPage(ModelMap modelMap) {
		
		
		return null;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String doLogin() {
		
		
		return null;
	}
}
