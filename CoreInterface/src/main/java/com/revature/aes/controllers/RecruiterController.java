package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.User;
import com.revature.aes.service.UserService;

@RestController
public class RecruiterController {
	
	@Autowired
	UserService service;
	
    @RequestMapping(value="canidates",method = RequestMethod.POST)
    public void registerCanidate(@RequestBody String canidate) {
		System.out.println("*************");
		System.out.println(canidate);
		System.out.println("*************");
//		User u = service.registerCanidate(canidate);
//		System.out.println("=============");
//		System.out.println(canidate);
//		System.out.println("=============");
		//return u;
	}
	
}
