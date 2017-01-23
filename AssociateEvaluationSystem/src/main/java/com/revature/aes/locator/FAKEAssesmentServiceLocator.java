package com.revature.aes.locator;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;


//@Service
public class FAKEAssesmentServiceLocator implements AssessmentServiceLocator {
	@Autowired
	Logging log;
	
	public AssessmentRequest getLink(AssessmentRequest assessment) {
		
		assessment.setLink("http://www.google.com");
		log.debug(assessment.getUserEmail());
		return assessment;
	}

}
