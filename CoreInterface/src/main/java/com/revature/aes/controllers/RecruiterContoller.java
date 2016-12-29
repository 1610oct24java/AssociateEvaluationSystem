package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.aes.beans.User;
import com.revature.aes.service.UserService;

@Controller
public class RecruiterContoller {
	
	@Autowired
	UserService service;
	
	@RequestMapping(value="/registerCanidate",method = RequestMethod.POST)
	@ResponseBody
	public User registerCanidate(User canidate) {
		return service.registerCanidate(canidate);
	}
	
}
