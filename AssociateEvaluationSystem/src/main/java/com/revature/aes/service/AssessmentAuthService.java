package com.revature.aes.service;

import com.revature.aes.beans.AssessmentAuth;

public interface AssessmentAuthService {
	
	public AssessmentAuth save(AssessmentAuth link);
	public AssessmentAuth getLink(String email);
	public void remove(int userId);
}
