package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.mail.Mail;
import com.revature.aes.mail.MailObject;

/**
 * Controller for an endpoint to the mail service
 * 
 * @author Wes
 *
 */
@RestController
public class MailController {   
	
	@Autowired
	private Mail mail;

	@RequestMapping(value="user/{userEmail}/mail", method=RequestMethod.POST)
	public void candidateNeedsQuiz(@RequestBody MailObject obj, @PathVariable String userEmail){
		mail.sendEmail(obj, userEmail);
	}
}