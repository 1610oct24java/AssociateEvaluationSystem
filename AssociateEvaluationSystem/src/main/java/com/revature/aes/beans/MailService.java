package com.revature.aes.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.revature.aes.logging.Logging;

@Service("mailService")
public class MailService {
	
	@Autowired
	private MailSender mailSender;

	@Autowired
	Logging logger;
	
	public SimpleMailMessage setupMessage(String recipient, String sub, String body){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(recipient);
        msg.setSubject(sub);
        msg.setText(body);
        
        return msg;
	}
	
    public Boolean sendEmail(SimpleMailMessage msg) {	

    	
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
    }
}