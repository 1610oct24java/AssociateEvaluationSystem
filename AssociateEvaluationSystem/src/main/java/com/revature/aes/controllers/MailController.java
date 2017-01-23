package com.revature.aes.controllers;

import com.revature.aes.mail.Mail;
import com.revature.aes.mail.MailObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping(value="user/{userEmail}/mail", method= RequestMethod.POST)
	public void candidateNeedsQuiz(@RequestBody MailObject obj, @PathVariable String userEmail){
		mail.sendEmail(obj, userEmail);
	}
}