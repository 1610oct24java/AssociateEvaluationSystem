package com.revature.aes.mail;

import com.revature.aes.beans.Assessment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.MailService;
import com.revature.aes.beans.User;
import com.revature.aes.dao.AssessmentDAO;
import com.revature.aes.dao.UserDAO;
import com.revature.aes.service.UserService;

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