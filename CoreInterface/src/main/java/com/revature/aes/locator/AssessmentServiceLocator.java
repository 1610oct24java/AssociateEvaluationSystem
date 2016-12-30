package com.revature.aes.locator;

import com.revature.aes.beans.AssessmentRequest;

public interface AssessmentServiceLocator {
	public AssessmentRequest getLink(AssessmentRequest request);
}
