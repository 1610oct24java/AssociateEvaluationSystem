package com.revature.aes.locator;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.AssessmentRequest;


public class AssessmentServiceLocatorImpl implements AssessmentServiceLocator {
	private static final String URL = "http://192.168.60.55:8080/TestBank";

	private RestTemplate restTemplate = new RestTemplate();
	Logger log = Logger.getRootLogger();
	
	@Override
	public AssessmentRequest getLink(AssessmentRequest request) {
		//Change the URL to whatever Matthew's thingy will be
		String lines = "=============================================";
		log.debug(lines);
		log.debug(request);
		log.debug(lines);
		
		ResponseEntity<AssessmentRequest> responseEntity = restTemplate.postForEntity(URL + "/user/RandomAssessment", request, AssessmentRequest.class);	
		AssessmentRequest response = responseEntity.getBody();
		log.debug(lines);
		log.debug(response);
		log.debug(lines);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		
		if(httpStatus != HttpStatus.OK)
			return null;
		
		return response;
	}
}