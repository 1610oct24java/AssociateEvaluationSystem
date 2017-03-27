package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.mail.Mail;
import com.revature.aes.mail.MailObject;

import java.util.HashMap;
import java.util.Map;

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
	public ResponseEntity<Map> candidateNeedsQuiz(@RequestBody MailObject obj, @PathVariable String userEmail){

		Map<String, String> response = new HashMap<>();
		if(mail.sendEmail(obj, userEmail)){
			response.put("msg", "success");
			return ResponseEntity.ok(response);
		}
		else{
			response.put("msg", "Failed to send message");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}