package com.revature.aes.service;

import java.util.List;

import com.revature.aes.beans.AssessmentRequest;

public interface AssessmentRequestService {

	public AssessmentRequest getAssessmentRequestTemplate();
	public AssessmentRequest saveAssessmentRequest(AssessmentRequest assReq);
	public List<AssessmentRequest> findAll();
	public AssessmentRequest getAssessmentRequestById(Integer id);
	public void newDefaultAssessment(AssessmentRequest assReq);
	
}
