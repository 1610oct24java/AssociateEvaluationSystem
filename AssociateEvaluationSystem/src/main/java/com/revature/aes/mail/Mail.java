package com.revature.aes.mail;

import com.revature.aes.config.IpConf;
import com.revature.aes.util.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.MailService;
import com.revature.aes.beans.User;
import com.revature.aes.dao.AssessmentDAO;
import com.revature.aes.dao.UserDAO;
import com.revature.aes.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
public class Mail {
		
		@Autowired
		private MailService ms;
		
		@Autowired
		private UserService us;
		
		@Autowired
		private UserDAO ud;
		
		@Autowired
		private AssessmentDAO ad;

		@Autowired
		private PropertyReader propertyReader;


	@PostConstruct
	protected void postConstruct(){

		configureEmailBody();

	}
		
		static String CANIDATE_COMPLETED_BODY;
		
		static String CANDIDATE_NOT_COMPLETE_BODY;
		
		static String RECRUITER_COMPLETED_BODY;

	private void configureEmailBody(){

		Properties properties = propertyReader.propertyRead("emailPrompt.properties");

		CANDIDATE_NOT_COMPLETE_BODY = properties.getProperty("candidate_not_complete_body");
		CANIDATE_COMPLETED_BODY = properties.getProperty("candidate_complete_body");
		RECRUITER_COMPLETED_BODY = properties.getProperty("recruiter_completed_body");

	}

		public void sendEmail(MailObject m, String email){
			User candidate = us.findUserByEmail(email);
			User recruiter = ud.findOne(candidate.getRecruiterId());
			
			String recruiterEmail = recruiter.getEmail();
			String candidateName = candidate.getFirstName() + " " + candidate.getLastName();
			
			switch (m.getType()){
			
			case "candidateNeedsQuiz":

				SimpleMailMessage needQuizMessage = ms.setupMessage(email, "Revature Quiz", CANIDATE_COMPLETED_BODY + "Link: " + m.getLink()
						+ "\nTemporary Pass: " + m.getTempPass());
						needQuizMessage.setFrom("rev.thompson.noreply@gmail.com");
				ms.sendEmail(needQuizMessage);
				break;
				
			case "candidateNotCompleted":
				SimpleMailMessage notCompletedCandidateMessage = ms.setupMessage(email, "Quiz Timer Expired", CANDIDATE_NOT_COMPLETE_BODY);
				notCompletedCandidateMessage.setFrom("rev.thompson.noreply@gmail.com");
				ms.sendEmail(notCompletedCandidateMessage);
				SimpleMailMessage notCompletedRecruiterMessage = ms.setupMessage(recruiterEmail, candidateName + " did not complete their quiz", "The timer for: " + candidateName + " has expired and their temporary password is no longer invalid.");
				notCompletedCandidateMessage.setFrom("rev.thompson.noreply@gmail.com");
				ms.sendEmail(notCompletedCandidateMessage);
				break;
			
			case "candidateCompleted":
				int grade = ad.findAssessmentByAssessmentId(m.getAssessmentId()).getGrade();

				SimpleMailMessage completedMessage = ms.setupMessage(recruiterEmail, candidateName + " has completed quiz", candidateName
						+RECRUITER_COMPLETED_BODY+String.valueOf(grade));
				completedMessage.setFrom("rev.thompson.noreply@gmail.com");
				ms.sendEmail(completedMessage);
				break;
			default:
				break;
			}
		}
}