package com.revature.aes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.daos.AssessmentDAO;

@Service
public class AssessmentServiceImpl implements AssessmentService{

	@Autowired
	private AssessmentDAO aDao;
	
	@Override
	public Assessment addNewAssessment(Assessment assess) {
		return aDao.save(assess);
	}
	
	

}
