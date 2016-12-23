package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.User;
import com.revature.aes.service.ValidationService;

@RestController
public class ValidationController {
	
	@Autowired
	private ValidationService validationService;
	
	@RequestMapping(value="validate/{userId}", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public Boolean auth(@PathVariable int userId){
		return validationService.validate(userId);
	}
	
	@RequestMapping(value="user/{userId}", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public User register(@PathVariable int userId){
		return validationService.register(userId);
	}
}