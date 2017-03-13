package com.revature.aes.service;

import com.revature.aes.beans.AssessmentRequest;

public interface AssessmentRequestService {

	public AssessmentRequest getAssessmentRequestTemplate();
	public AssessmentRequest saveAssessmentRequest(AssessmentRequest assReq);
	
}
