package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.revature.aes.mail.Mail;
import com.revature.aes.mail.MailObject;

@RestController
public class MailController {
	
	@Autowired
	Mail mail;
	
	/*Used for when a candidate needs sent a quiz. Takes the candidates Email, the Link to
	 * the login page, and the temporary password.
	 */
	
	@RequestMapping(value="candidiateNeedsQuiz", method=RequestMethod.POST)
	public void candidateNeedsQuiz(@RequestBody MailObject obj){
		mail.sendQuizEmail(obj.getCandidateEmail(), obj.getLink(), obj.getTempPass());
	}
	
	/*Used for when a candidate did not completed their quiz. Takes the candidates Email, RecruiterEmail, and Candidate Name*/
	
	@RequestMapping(value="canidateNOTCompletedEmail", method=RequestMethod.POST)
	public void canidateNOTCompleted(@RequestBody MailObject obj){
		mail.candidateNotCompletedEmail(obj.getCandidateEmail());
		mail.recruiterNotCompletedEmail(obj.getRecruiterEmail(), obj.getCandidateName());
	}
	
	/*Used for when a candidate has completed their quiz and a recruiter needs to be sent an email with the grade.
	 * Takes the candidates Email, candidates name, and their grade for that quiz
	 */
	
	@RequestMapping(value="recruiterReturnGradeEmail", method=RequestMethod.POST)
	public void recruiterReturnGradeEmail(@RequestBody MailObject obj){
		mail.recruiterReturnGradeEmail(obj.getRecruiterEmail(), obj.getCandidateName(), obj.getGrade());
	}
}