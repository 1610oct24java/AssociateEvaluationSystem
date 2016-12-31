package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.dao.AssessmentAuthDao;

@Service
@Transactional
public class AssessmentAuthServiceImpl implements AssessmentAuthService {
	
	@Autowired
	private AssessmentAuthDao dao;

	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public AssessmentAuth save(AssessmentAuth link) {
		// 
		return dao.save(link);
	}
}
