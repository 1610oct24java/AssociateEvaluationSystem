package com.revature.aes.mail;

import com.revature.aes.beans.MailService;
import com.revature.aes.beans.User;
import com.revature.aes.dao.AssessmentDAO;
import com.revature.aes.dao.UserDAO;
import com.revature.aes.service.UserService;
import com.revature.aes.util.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

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
    protected void postConstruct() {

        configureEmailBody();

    }

    private static String CANDIDATE_LINK_BODY;

    private static String CANDIDATE_NOT_COMPLETE_BODY;

    private static String RECRUITER_COMPLETED_BODY;

    private static String RECRUITER_NOT_DELIVERED_BODY;

    private static String RECRUITER_NOT_COMPLETE_BODY;

    private static String CANDIDATE_LINK_SUBJECT;

    private static String CANDIDATE_NOT_COMPLETE_SUBJECT;

    private static String RECRUITER_COMPLETED_SUBJECT;

    private static String RECRUITER_NOT_DELIVERED_SUBJECT;

    private static String RECRUITER_NOT_COMPLETE_SUBJECT;

    private static String TEMPORARY_PASSWORD_SUBJECT;

    private static String TEMPORARY_PASSWORD_BODY;

    private static String SENDER;

    private void configureEmailBody() {

        Properties properties = propertyReader.propertyRead("emailPrompt.properties");

        CANDIDATE_NOT_COMPLETE_BODY = properties.getProperty("candidate_not_complete_body");
        CANDIDATE_LINK_BODY = properties.getProperty("candidate_link_body");
        RECRUITER_COMPLETED_BODY = properties.getProperty("recruiter_completed_body");
        RECRUITER_NOT_DELIVERED_BODY = properties.getProperty("recruiter_not_delivered_body");
        RECRUITER_NOT_COMPLETE_BODY = properties.getProperty("recruiter_not_complete_body");
        TEMPORARY_PASSWORD_BODY = properties.getProperty("temporary_password_body");
        CANDIDATE_NOT_COMPLETE_SUBJECT = properties.getProperty("candidate_not_complete_body_subject");
        CANDIDATE_LINK_SUBJECT = properties.getProperty("candidate_link_subject");
        RECRUITER_COMPLETED_SUBJECT = properties.getProperty("recruiter_completed_subject");
        RECRUITER_NOT_DELIVERED_SUBJECT = properties.getProperty("recruiter_not_delivered_subject");
        RECRUITER_NOT_COMPLETE_SUBJECT = properties.getProperty("recruiter_not_complete_subject");
        TEMPORARY_PASSWORD_SUBJECT = properties.getProperty("temporary_password_subject");
        SENDER = properties.getProperty("sender");


    }

    public void sendEmail(MailObject m, String email) {
        User candidate = us.findUserByEmail(email);
        User recruiter = null;
        String recruiterEmail;

        if (candidate.getRecruiterId() != null) {
            recruiter = ud.findOne(candidate.getRecruiterId());
            recruiterEmail = recruiter.getEmail();
        }
        else{
            recruiterEmail=email;
        }

        SimpleMailMessage simpleMailMessage = null;

        switch (m.getType()) {

            case "candidateNeedsQuiz":
                simpleMailMessage = ms.setupMessage(email, formatMessage(CANDIDATE_LINK_SUBJECT, candidate, recruiter, m), formatMessage(CANDIDATE_LINK_BODY, candidate, recruiter, m));
                break;

            case "candidateNotCompleted":
                SimpleMailMessage notCompletedCandidateMessage = ms.setupMessage(email, formatMessage(CANDIDATE_NOT_COMPLETE_SUBJECT, candidate, recruiter, m), formatMessage(CANDIDATE_NOT_COMPLETE_BODY, candidate, recruiter, m));
                notCompletedCandidateMessage.setFrom(SENDER);
                ms.sendEmail(notCompletedCandidateMessage);
                simpleMailMessage = ms.setupMessage(recruiterEmail, formatMessage(RECRUITER_NOT_COMPLETE_SUBJECT, candidate, recruiter, m), formatMessage(RECRUITER_NOT_COMPLETE_BODY, candidate, recruiter, m));
                break;

            case "candidateCompleted":
                simpleMailMessage = ms.setupMessage(recruiterEmail, formatMessage(RECRUITER_COMPLETED_SUBJECT, candidate, recruiter, m), formatMessage(RECRUITER_COMPLETED_BODY, candidate, recruiter, m));
                break;

            case "candidateEmailNotDelivered":
                simpleMailMessage = ms.setupMessage(recruiterEmail, formatMessage(RECRUITER_NOT_DELIVERED_SUBJECT, candidate, recruiter, m), formatMessage(RECRUITER_NOT_DELIVERED_BODY, candidate, recruiter, m));
                break;

            case "temporaryPassword":
                simpleMailMessage = ms.setupMessage(email, formatMessage(TEMPORARY_PASSWORD_SUBJECT, candidate, recruiter, m), formatMessage(TEMPORARY_PASSWORD_BODY, candidate, recruiter, m));
                break;

            default:
                break;
        }

        simpleMailMessage.setFrom(SENDER);
        if (!ms.sendEmail(simpleMailMessage) && m.getType().equals("candidateNeedsQuiz")) {

            m.setType("candidateEmailNotDelivered");
            sendEmail(m, email);

        }
    }

    private String formatMessage(String prompt, User candidate, User recruiter, MailObject m) {

        StringBuilder message = new StringBuilder(prompt);

        for (int i = 0; i < message.length(); i++) {

            if (message.charAt(i) == '%' && message.length() > i + 1) {

                message.deleteCharAt(i);

                switch (message.charAt(i)) {

                    case 'c':
                        message.deleteCharAt(i);
                        message.insert(i, candidate.getFirstName() + " " + candidate.getLastName());
                        break;
                    case 'r':
                        message.deleteCharAt(i);
                        message.insert(i, recruiter.getFirstName() + " " + recruiter.getLastName());
                        break;
                    case 'g':
                        message.deleteCharAt(i);
                        message.insert(i, String.valueOf(ad.findAssessmentByAssessmentId(m.getAssessmentId()).getGrade()));
                        break;
                    case 'l':
                        message.deleteCharAt(i);
                        message.insert(i, m.getLink());
                        break;
                    case 'p':
                        message.deleteCharAt(i);
                        message.insert(i, m.getTempPass());
                        break;
                    case 'e':
                        message.deleteCharAt(i);
                        message.insert(i, candidate.getEmail());
                        break;
                    case '%':
                        break;

                }

            }

        }

        return message.toString();

    }
}