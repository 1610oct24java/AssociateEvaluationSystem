package com.revature.aes.locator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.AssessmentRequest;

@Service
public class AssessmentServiceLocatorImpl implements AssessmentServiceLocator {
	private final String url = "http://192.168.60.55:8080/TestBank";
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public AssessmentRequest getLink(AssessmentRequest request) {
		//Change the URL to whatever Matthew's thingy will be
		String lines = "=============================================";
		System.out.println(lines);
		System.out.println(request);
		System.out.println(lines);
		
		ResponseEntity<AssessmentRequest> responseEntity = restTemplate.postForEntity(url + "/user/RandomAssessment", request, AssessmentRequest.class);
		
		System.out.println(lines);
		System.out.println(request);
		System.out.println(lines);
		
		AssessmentRequest response = responseEntity.getBody();
		HttpStatus httpStatus = responseEntity.getStatusCode();
		
		if(httpStatus != HttpStatus.OK)
			return null;
		
		return response;
	}

}
