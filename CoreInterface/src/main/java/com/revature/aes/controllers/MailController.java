package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.mail.Mail;

@RestController
public class MailController {
	@Autowired
	Mail ms;
	
	@RequestMapping("canidateCompletedEmail")
	public void canidateCompleted(){
		ms.canidateCompletedEmail(canidateEmail, link, tempPass);
	}
	
	@RequestMapping("canidateNOTCompletedEmail")
	public void canidateNOTCompleted(){
		ms.canidateNotCompletedEmail(canidateEmail);
	}
	
	@RequestMapping("recruiterReturnGradeEmail")
	public void recruiterReturnGradeEmail(){
		ms.recruiterReturnGradeEmail(recruiterEmail, canidateName, grade);
	}
	
	@RequestMapping("recruiterNOTCompleted")
	public void recruiterNOTCompleted(){
		ms.recruiterNotCompletedEmail(recruiterEmail, canidateName);
	}
}