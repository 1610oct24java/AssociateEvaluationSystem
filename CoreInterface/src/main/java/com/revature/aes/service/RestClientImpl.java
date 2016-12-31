package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.User;
import com.revature.aes.loader.AssessmentRequestLoader;
import com.revature.aes.locator.AssessmentServiceLocator;
import com.revature.aes.locator.MailServiceLocator;

@Service
public class RestClientImpl implements RestClient {
	@Autowired
	private AssessmentServiceLocator assessmentService;
	@Autowired
	private MailServiceLocator mailService;
	@Autowired
	private AssessmentAuthService authService;

	@Override
	public void finalizeCandidate(User candidate, String pass) {
		// 
		int userId = candidate.getUserId();
		String email = candidate.getEmail();
		String category = candidate.getFormat();
		
		AssessmentRequest ar = new AssessmentRequestLoader().loadRequest(category);
		
		ar = assessmentService.getLink(ar);
		
		String link = ar.getLink();
		
		AssessmentAuth auth = new AssessmentAuth();
		auth.setUrlAssessment(link);
		auth.setUrlAuth("https://localhost:8443/core/");
		auth.setUserId(userId);
		
		authService.save(auth);
		
		mailService.send(email, pass, link);
	}

}
