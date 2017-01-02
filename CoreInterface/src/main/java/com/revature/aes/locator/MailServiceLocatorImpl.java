package com.revature.aes.locator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.User;

public class MailServiceLocatorImpl implements MailServiceLocator {

	@Override
	public boolean send(String email, String... contents) {
		// TODO find url for rest call
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://localhost:8443/core";
		
		// TODO send message through secure post request
		ResponseEntity<String[]> responseEntity = restTemplate.postForEntity(url+"/candidateNeedsQuiz", new User(), String[].class);
		
		if(responseEntity.getStatusCode() != HttpStatus.OK)
			return false;
		
		return true;
	}

}

class MailerEntity{
	
}