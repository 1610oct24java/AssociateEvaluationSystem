package com.revature.aes.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {

	@Autowired
	private MailSender mailSender;
	
	public SimpleMailMessage setupMessage(String sender, String recipient, String sub, String body){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(sender);
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
        	e.printStackTrace();
        	
        	try{
        		mailSender.send(msg);
        		return true;
        	}
        	catch(MailException ex){
        		ex.printStackTrace();
        		return false;
        	}
        }
    }
}