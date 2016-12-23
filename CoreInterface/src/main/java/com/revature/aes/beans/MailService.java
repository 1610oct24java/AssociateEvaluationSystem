package com.revature.aes.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {

	@Autowired
	private MailSender mailService;
	
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
        	mailService.send(msg);
        	return true;
        }
        catch(MailException e){
        	e.printStackTrace();
        	
        	try{
        		mailService.send(msg);
        		return true;
        	}
        	catch(MailException ex){
        		ex.printStackTrace();
        		return false;
        	}
        }
    }
}