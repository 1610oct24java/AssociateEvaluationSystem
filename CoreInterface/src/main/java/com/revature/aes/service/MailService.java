package com.revature.aes.service;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {
//	
//	@Autowired
//	private MailSender mailSender;
	

	//user/pass
	static final String FROM="rev.thompson.noreply@gmail.com";
	static final String SMTP_USERNAME = "AKIAI2RGLQWVBVB4Q2LA";
	static final String SMTP_PASSWORD = "At1Ccq/b7PK7guC3u2t6GgWilTWElpwiSsHyZP+MXuS5";
	static final String HOST = "email-smtp.us-west-2.amazonaws.com";
	static final int PORT = 25;
	
	
	/*public SimpleMailMessage setupMessage(String recipient, String sub, String body){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(recipient);
        msg.setSubject(sub);
        msg.setText(body);
        
        return msg;
	}
	
    public Boolean sendEmail(SimpleMailMessage msg) {	
    	Logger logger = Logger.getAnonymousLogger();

        try{
        	mailSender.send(msg); 
        	return true;
        }
        catch(MailException e){
        	
        	logger.info("error" + e);
        	
        	try{
        		mailSender.send(msg);
        		return true;
        	}
        	catch(MailException ex){
        		logger.info("error" + ex);
        		return false;
        	}
        }
    }*/

	public boolean sendEmail(String recipient, String subject, String body) {
		Logger logger = Logger.getAnonymousLogger();
		Transport transport = null;
		try{
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtps");
			props.put("mail.smtp.port", PORT);
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.starttls.required", "true");
			Session session = Session.getDefaultInstance(props);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			msg.setContent(body,"text/plain");
			
			transport = session.getTransport();
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
			
			transport.sendMessage(msg, msg.getAllRecipients());
			
		}	
		catch(Exception e)
		{
			logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				transport.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
}