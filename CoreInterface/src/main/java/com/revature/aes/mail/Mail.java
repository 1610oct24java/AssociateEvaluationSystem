package com.revature.aes.mail;

import org.springframework.beans.factory.annotation.Autowired;
import com.revature.aes.beans.MailService;

public class Mail {
		
		@Autowired
		MailService ms;
		
		final String canidateCompletedBody = "Please click the link below and complete the quiz within one week.\n"
				+ "If you can not click the link please copy and paste it into your URL bar\n\n";
		
		final String recruiterCompletedBody = " has finished their quiz and recieved a score: ";
		
		final String canidateNotCompleteBody = "Your time to complete your quiz has now expired and your temporary password is now expired.";
		
		public void canidateCompletedEmail(String canidateEmail, String link, String tempPass){
			ms.sendEmail(ms.setupMessage(canidateEmail, "Revature Quiz", canidateCompletedBody + "Link: " + link + "\nTemporary Pass: " + tempPass));
		}
		
		public void recruiterReturnGradeEmail(String recruiterEmail, String canidateName, String grade){
			ms.sendEmail(ms.setupMessage(recruiterEmail, canidateName + "has completed quiz",canidateName+recruiterCompletedBody+grade));
		}
		
		public void canidateNotCompletedEmail(String canidateEmail){
			ms.sendEmail(ms.setupMessage(canidateEmail, "Quiz Timer Expired", canidateNotCompleteBody));
		}
		
		public void recruiterNotCompletedEmail(String recruiterEmail, String canidateName){
			ms.sendEmail(ms.setupMessage(recruiterEmail, canidateName + " did not complete their quiz", "The timer for: " + canidateName + " has expired and their temporary password is not invalid."));
		}
}