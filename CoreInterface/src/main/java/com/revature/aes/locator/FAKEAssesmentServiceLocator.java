package com.revature.aes.locator;

import com.revature.aes.beans.AssessmentRequest;

public class FAKEAssesmentServiceLocator implements AssessmentServiceLocator {
	
	public AssessmentRequest getLink(AssessmentRequest assessment) {
		//TODO Remove @Service annotation when we get the real impl working
		assessment.setLink("www.google.com");
		System.out.println(assessment.getUserEmail());
		return assessment;
	}

}
