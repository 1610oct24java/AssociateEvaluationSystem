package com.revature.aes.locator;

import org.apache.log4j.Logger;

import com.revature.aes.beans.AssessmentRequest;

public class FAKEAssesmentServiceLocator implements AssessmentServiceLocator {
	Logger log = Logger.getRootLogger();
	
	public AssessmentRequest getLink(AssessmentRequest assessment) {
		//
		assessment.setLink("www.google.com");
		log.debug(assessment.getUserEmail());
		return assessment;
	}

}