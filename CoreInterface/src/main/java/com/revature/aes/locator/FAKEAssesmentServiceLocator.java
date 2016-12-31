package com.revature.aes.locator;

import org.springframework.stereotype.Service;

import com.revature.aes.beans.AssessmentRequest;

@Service
public class FAKEAssesmentServiceLocator implements AssessmentServiceLocator {
	
	@Override
	public AssessmentRequest getLink(AssessmentRequest assessment) {
		// TODO remove the @Service annotation when we get the real impl working
		assessment.setLink("www.google.com");
		return assessment;
	}

}
