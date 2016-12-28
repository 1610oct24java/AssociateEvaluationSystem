package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.User;
import com.revature.aes.mail.Mail;

@RestController
public class MailController {
	
	@Autowired
	Mail mail;      
	
	/*Used for when a candidate needs sent a quiz. Takes the candidates Email, the Link to
	 * the login page, and the temporary password.
	 */

	@RequestMapping(value="canidateCompletedEmail", method=RequestMethod.POST)
	//public void candidateCompleted(@RequestBody String candidateEmail, @RequestBody String link, @RequestBody String tempPass){
	public void candidateNeedsQuiz(@RequestBody User user){
		mail.sendQuizEmail(user.getEmail(), link, tempPass);
	}
	
	/*Used for when a candidate did not completed their quiz. Takes the candidates Email*/
	
	@RequestMapping(value="canidateNOTCompletedEmail", method=RequestMethod.POST)
	public void canidateNOTCompleted(@RequestBody String candidateEmail){
		mail.candidateNotCompletedEmail(candidateEmail);
	}
	
	/*Used for when a candidate has completed their quiz and a recruiter needs to be sent an email with the grade.
	 * Takes the candidates Email, candidates name, and their grade for that quiz
	 */
	
	@RequestMapping(value="recruiterReturnGradeEmail", method=RequestMethod.POST)
	public void recruiterReturnGradeEmail(@RequestBody String recruiterEmail, @RequestBody String candidateName, @RequestBody String grade){
		mail.recruiterReturnGradeEmail(recruiterEmail, candidateName, grade);
	}
	
	/*Used for when a candidate did not complete their quiz and alerts the recruiter.
	 * Takes the recruiters Email, and candidates name
	 */
	
	@RequestMapping(value="recruiterNOTCompleted", method=RequestMethod.POST)
	public void recruiterNOTCompleted(@RequestBody String recruiterEmail, @RequestBody String canidateName){
		mail.recruiterNotCompletedEmail(recruiterEmail, canidateName);
	}
}