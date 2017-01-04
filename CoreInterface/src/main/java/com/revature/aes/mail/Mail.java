package com.revature.aes.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.MailService;
import com.revature.aes.beans.User;
import com.revature.aes.dao.AssessmentDao;
import com.revature.aes.dao.UserDao;
import com.revature.aes.service.UserService;

@Component
public class Mail {
		
		@Autowired
		private MailService ms;
		
		@Autowired
		private UserService us;
		
		@Autowired
		private UserDao ud;
		
		@Autowired
		private AssessmentDao ad;
		
		static final String CANIDATE_COMPLETED_BODY = "Please click the link below and complete the quiz within one week.\n"
				+ "If you can not click the link please copy and paste it into your URL bar\n\n";
		
		static final String CANDIDATE_NOT_COMPLETE_BODY = "The time to complete your quiz has passed."
				+ " Your temporary password is no longer valid";
		
		static final String RECRUITER_COMPLETED_BODY = " has finished their quiz and recieved a score: ";
		
		public void sendEmail(MailObject m, String email){
			User candidate = us.findUserByEmail(email);
			User recruiter = ud.findOne(candidate.getRecruiterId());
			
			String recruiterEmail = recruiter.getEmail();
			String candidateName = candidate.getFirstName() + " " + candidate.getLastName();
			
			switch (m.getType()){
			
			case "candidateNeedsQuiz":
				ms.sendEmail(ms.setupMessage(email, "Revature Quiz", CANIDATE_COMPLETED_BODY + "Link: " + m.getLink() 
						+ "\nTemporary Pass: " + m.getTempPass()));
				break;
				
			case "candidateNotCompleted":
				ms.sendEmail(ms.setupMessage(email, "Quiz Timer Expired", CANDIDATE_NOT_COMPLETE_BODY));
				ms.sendEmail(ms.setupMessage(recruiterEmail, candidateName + " did not complete their quiz", "The timer for: " + candidateName + " has expired and their temporary password is no longer invalid."));
				break;
			
			case "candidateCompleted":
				int grade = ad.findByUser(candidate).getGrade(); 
				ms.sendEmail(ms.setupMessage(recruiterEmail, candidateName + " has completed quiz", candidateName
						+RECRUITER_COMPLETED_BODY+String.valueOf(grade)));
				break;
			default:
				break;
			}
		}
}