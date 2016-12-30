package com.revature.aes.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.revature.aes.mail.Mail;
import com.revature.aes.mail.MailObject;

@RestController
public class MailController {     
	
	@RequestMapping(value="user/{userEmail}/mail", method=RequestMethod.POST)
	public void candidateNeedsQuiz(@RequestBody MailObject obj, @PathVariable String userEmail){
		Mail.sendEmail(obj, userEmail);
	}
}