package com.revature.aes.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/*  Anything of type MailSender is imported from springframework. Not to be 
 *  confused with mailService which is our service.
 */

@Service("mailService")
public class MailService {

	@Autowired
	private MailSender mailService;
	private String recipient;
	private String subject;
	private String body;
	private final String sender = "rev.thompson.noreply";
	
	MailService(){
		super();
	}
	
	MailService(String recipient, String subject, String body){
		super();
		this.recipient=recipient;
		this.subject=subject;
		this.body=body;
	}
	
	public void setMailService(MailSender mailService)
	{
		this.mailService = mailService;
	}
	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public String getRecipient(){
		return this.recipient;
	}
	
	public void setSubject(String subject){
		this.subject = subject;
	}
	
	public String getSubject(){
		return this.subject;
	}
	
	public void setBody(String body){
		this.body = body;
	}
	
	public String getBody(){
		return this.body;
	}
	
	public String getSender(){
		return this.sender;
	}
	
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