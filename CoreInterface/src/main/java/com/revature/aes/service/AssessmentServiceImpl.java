package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;
import com.revature.aes.dao.AssessmentDao;

@Service
public class AssessmentServiceImpl implements AssessmentService {
	
	@Autowired
	private AssessmentDao dao;

	@Override
	public Assessment findByUser(User user) {
		// 
		//return dao.findAssesmentByUser(user);
		return dao.findByUser(user);
	}

}
