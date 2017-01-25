package com.revature.aes.locator;

import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * This is a Service Locator meant to send an email through a rest
 * call. 
 * 
 * @author Michelle Slay
 * 
 * 
 *
 */
@Service
public class MailServiceLocatorImpl implements MailServiceLocator {
	@Autowired
	Logging log;
	RestTemplate restTemplate = new RestTemplate();

//	static AssessmentRequestLoader loader = new AssessmentRequestLoader();
//	private static final String URL = loader.loadAddress() + "/core";

	private static final String URL = "http://localhost:8080/core";
	
	@Override
	/**
	 * 
	 * 
	 * 
	 */
	public boolean sendPassword(String email, String... contents) {
		// 
		MailerEntity requestEntity = new MailerEntity();
		
		requestEntity.setLink(contents[0]);
		requestEntity.setTempPass(contents[1]);
		requestEntity.setType("candidateNeedsQuiz");
		
		return send(requestEntity, email);
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