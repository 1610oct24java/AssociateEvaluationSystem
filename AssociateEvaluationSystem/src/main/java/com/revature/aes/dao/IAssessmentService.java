package com.revature.aes.dao;

import com.revature.aes.beans.Assessment;

public interface IAssessmentService {
	public Assessment getAssessmentById(int id);
	
	public void saveAssessment(Assessment assessment);
	
	public void updateAssessment(Assessment assessment);
}
