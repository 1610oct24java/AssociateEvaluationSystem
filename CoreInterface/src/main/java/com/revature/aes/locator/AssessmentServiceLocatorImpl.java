package com.revature.aes.locator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.AssessmentRequest;

public class AssessmentServiceLocatorImpl implements AssessmentServiceLocator {
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public AssessmentRequest getLink(AssessmentRequest request) {
		// TODO Change the URL to whatever Matthew's thingy will be
		
		String url = "http://www.google.com";
		ResponseEntity<AssessmentRequest> responseEntity = restTemplate.postForEntity(url + "/assessment", request, AssessmentRequest.class);
		
		AssessmentRequest response = responseEntity.getBody();
		HttpStatus httpStatus = responseEntity.getStatusCode();
		
		if(httpStatus != HttpStatus.OK)
			return null;
		
		return response;
	}

}
