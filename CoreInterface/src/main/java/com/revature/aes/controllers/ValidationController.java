package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.User;
import com.revature.aes.service.ValidationService;

@RestController
@Scope("session")
public class ValidationController {
	
	@Autowired
	private ValidationService validationService;
	
	@RequestMapping(value="user", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public User auth(@RequestBody User user){
		return validationService.validate(user);
	}
}
