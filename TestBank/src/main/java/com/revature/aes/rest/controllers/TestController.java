package com.revature.aes.rest.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value = "user/link", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public String createLink(@RequestBody int assessmentId) {
		return "thisTotallyWorks.gov";
	}
}