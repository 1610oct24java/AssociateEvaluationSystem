package com.revature.aes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.aes.beans.User;

@Controller
public class LoginController {

	private static User user = new User();
	static {
		user.setEmail("asd");
		//user.setPassword("$2a$12$z13JLN67h0bJaqNCwQzS1OKzT30hKXdxmCyiXR5e.CKPEsRCfwMWu");
	}
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String getLoginPage(ModelMap modelMap) {
		
		System.out.println("LOGIN");
		return "login";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public @ResponseBody User login(@RequestBody User user) {
		
		System.out.println(user);
		return user;
	}
}
