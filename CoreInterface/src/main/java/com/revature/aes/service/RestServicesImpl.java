package com.revature.aes.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.User;
import com.revature.aes.loader.AssessmentRequestLoader;
import com.revature.aes.locator.AssessmentServiceLocator;
import com.revature.aes.locator.MailServiceLocator;

@Service
public class RestServicesImpl implements RestServices {
	@Autowired
	private AssessmentServiceLocator assessmentService;
	@Autowired
	private MailServiceLocator mailService;
	@Autowired
	private AssessmentAuthService authService;

	@Override
	public Map<String,String> finalizeCandidate(User candidate, String pass) {
		Map<String,String> map = new HashMap<String,String>();
		int userId = candidate.getUserId();
		String email = candidate.getEmail();
		String category = candidate.getFormat();
		
		AssessmentRequest ar = new AssessmentRequestLoader().loadRequest(category);
		
		ar = assessmentService.getLink(ar);
		
		String link = ar.getLink();
		
		AssessmentAuth auth = new AssessmentAuth();
		auth.setUrlAssessment(link);
		auth.setUrlAuth("http://localhost:8080/core/");
		auth.setUserId(userId);
		
		authService.save(auth);
		
		//mailService.sendPassword(email, link, pass);
		map.put("email", email);
		map.put("link", auth.getUrlAuth());
		map.put("pass", pass);
		return map;
	}

}
