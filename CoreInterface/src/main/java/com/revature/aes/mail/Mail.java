package com.revature.aes.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.MailService;


@Component
public class Mail {
		
		@Autowired
		MailService ms;
		
		static final String CANIDATE_COMPLETED_BODY = "Please click the link below and complete the quiz within one week.\n"
				+ "If you can not click the link please copy and paste it into your URL bar\n\n";
		
		static final String RECRUITER_COMPLETED_BODY = " has finished their quiz and recieved a score: ";
		
		static final String CANDIDATE_NOT_COMPLETE_BODY = "The time to complete your quiz has passed."
				+ " Your temporary password is no longer valid";
		
		
		public void sendQuizEmail(String candidateEmail, String link, String tempPass){
			ms.sendEmail(ms.setupMessage(candidateEmail, "Revature Quiz", CANIDATE_COMPLETED_BODY + "Link: " + link 
					+ "\nTemporary Pass: " + tempPass));
		}
		
		public void recruiterReturnGradeEmail(String recruiterEmail, String candidateName, String grade){
			ms.sendEmail(ms.setupMessage(recruiterEmail, candidateName + " has completed quiz",candidateName
					+RECRUITER_COMPLETED_BODY+grade));
		}
		
		public void candidateNotCompletedEmail(String candidateEmail){
			ms.sendEmail(ms.setupMessage(candidateEmail, "Quiz Timer Expired", CANDIDATE_NOT_COMPLETE_BODY));
		}
		
		public void recruiterNotCompletedEmail(String recruiterEmail, String candidateName){
			ms.sendEmail(ms.setupMessage(recruiterEmail, candidateName + " did not complete their quiz", "The timer for: " 
					+ candidateName + " has expired and their temporary password is no longer invalid."));
		}
}