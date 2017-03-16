package com.revature.aes.locator;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.MailService;
import com.revature.aes.logging.Logging;

/**
 * 
 * This is a Service Locator meant to send an email through a rest
 * call. 
 * 
 * @author Michelle Slay
 */
@Service
public class MailServiceLocatorImpl implements MailServiceLocator {

	@Autowired
	Logging log;
	
	@Autowired
	private MailService mailService;
	
	RestTemplate restTemplate = new RestTemplate();

	@Inject
	private org.springframework.boot.autoconfigure.web.ServerProperties serverProperties;

	private static int port;

	private static String ip;

	private static String URL;

	@PostConstruct
	protected void postConstruct(){

		configureRestService();

	}

	private void configureRestService(){

		port = serverProperties.getPort();

		try{

			ip = InetAddress.getLocalHost().getHostAddress();

		} catch (UnknownHostException e) {
			log.error("UnknownHostException; setting ip to localhost");
			log.stackTraceLogging(e);
			ip = "localhost";
		}

		URL = "http://" + ip + ":" + port + "/aes";

	}

//	static AssessmentRequestLoader loader = new AssessmentRequestLoader();
//	private static final String URL = loader.loadAddress() + "/core";

	/**
	 * 
	 * 
	 * 
	 */
	@Override
	public boolean sendPassword(String email, String... contents) {
		// 
		MailerEntity requestEntity = new MailerEntity();
		
		requestEntity.setLink(contents[0]);
		requestEntity.setTempPass(contents[1]);
		requestEntity.setType("candidateNeedsQuiz");
		
		return send(requestEntity, email);
	}

	/**
	 * This method will be called by the AdminController after getting
	 * a temporary password made and emails it to the recruiter/trainer.
	 * 
	 * @param email
	 * 		String, email of the recruiter or trainer
	 * @param pass
	 * 		String, generated temporary password
	 */
	@Override
	public boolean sendTempPassword(String email, String pass) {
		// 
		StringBuilder msg = new StringBuilder();
		msg.append("\n\n***** DO NOT REPLY *****\n\n");
		msg.append("You have been registered into the Associate Evaluation System (AES).\n\n");
		msg.append("Please use the following temporary password to log in along with your email as username.\n\n");
		msg.append(pass + " \n\n");
		msg.append("After logging in, you should change your password.\n\n");
		msg.append("Should you run into any issues, please notify Revature\n\n");
		msg.append("***** DO NOT REPLY *****");
		
		System.out.println("Email being sent to 2: " + email);
		
		SimpleMailMessage mail = mailService.setupMessage(email, "Revature: Temporary Password Issued for AES", msg.toString());
		mail.setFrom("rev.thompson.noreply@gmail.com");
		
		return mailService.sendEmail(mail);
	}
	
	@Override
	public void overdueAlert(String email) {
		// 
		MailerEntity requestEntity = new MailerEntity();
		requestEntity.setType("canidateNotCompleted");
		
		String success = send(requestEntity, email) ? "successful!" : "a failure...";
		
		log.info("The send attempt was " + success);
	}
	
	private boolean send(MailerEntity requestEntity, String email){
		ResponseEntity<MailerEntity> responseEntity = restTemplate.postForEntity(URL+"/user/"+ email +"/mail", requestEntity, MailerEntity.class);
		log.debug("url: " + responseEntity.getHeaders().getLocation() + " body: " + responseEntity.getBody()+": Status="+responseEntity.getStatusCode());
		if(responseEntity.getStatusCode() == HttpStatus.OK)
			return true;
		
		return false;
	}
}

@Component
class MailerEntity{
	private String link;
	private String tempPass;
	private String type;
	private int assessmentId;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTempPass() {
		return tempPass;
	}
	public void setTempPass(String tempPass) {
		this.tempPass = tempPass;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(int assessmentId) {
		this.assessmentId = assessmentId;
	}
}