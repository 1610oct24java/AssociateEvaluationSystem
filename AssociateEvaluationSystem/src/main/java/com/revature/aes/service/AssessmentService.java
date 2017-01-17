package com.revature.aes.service;

import com.revature.aes.beans.Assessment;

public interface AssessmentService {
	public Assessment getAssessmentById(int id);
	
	public Assessment saveAssessment(Assessment assessment);
	
	public void updateAssessment(Assessment assessment);
}
