package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.revature.aes.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService service;
	
	@RequestMapping(value="/",method = RequestMethod.GET)
	public String login() {
		return "index";
	}
	@RequestMapping(value="/home",method = RequestMethod.GET)
	public void getLoginPage(ModelMap modelMap) {
		//For security purposes
	}
	
	@RequestMapping(value="/admin/private",method = RequestMethod.GET)
	public String getPrivatePage(ModelMap modelMap) {
		
		return "private";
	}

	//Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}
}