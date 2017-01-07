package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.beans.User;
import com.revature.aes.dao.AssessmentAuthDao;

@Service
@Transactional
public class AssessmentAuthServiceImpl implements AssessmentAuthService {
	
	@Autowired
	private AssessmentAuthDao dao;
	@Autowired
	private UserService uService;

	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public AssessmentAuth save(AssessmentAuth link) {
		AssessmentAuth assessmentAuth = dao.findByUserId(link.getUserId());
		
		if(assessmentAuth != null) {
			assessmentAuth.setUrlAssessment(link.getUrlAssessment());
			return dao.save(assessmentAuth);
		} else {
			return dao.save(link);
		}
	}

	@Override
	public AssessmentAuth getLink(String email) {
		// 
		User u = uService.findUserByEmail(email);
		return dao.findByUserId(u.getUserId());
	}
}
