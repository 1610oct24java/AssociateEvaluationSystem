package com.revature.aes.locator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.User;

public class MailServiceLocatorImpl implements MailServiceLocator {

	@Override
	public boolean send(String email, String... contents) {
		// TODO find url for rest call
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://localhost:8080/core";
		
		// TODO send password through secure post request
		ResponseEntity<User> responseEntity = restTemplate.postForEntity(url+"/candidateNeedsQuiz", new User(), User.class);
		
		// TODO return true if the send is successful, false if not
		return false;
	}

}
