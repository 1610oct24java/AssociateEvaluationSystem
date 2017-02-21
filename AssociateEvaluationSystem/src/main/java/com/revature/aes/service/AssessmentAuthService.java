package com.revature.aes.service;

import com.revature.aes.beans.AssessmentAuth;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AssessmentAuthService {


	public AssessmentAuth save(AssessmentAuth link);
	public AssessmentAuth getLink(String email);
}
