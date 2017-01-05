package com.revature.aes.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.Format;

@Service
public class AssessmentService implements IAssessmentService {

	@Autowired
	AssessmentDAO assDAO;
	
	@Override
	public Assessment getAssessmentById(int id) {
		System.out.println("I'm servicing things! Poorly!");
		Assessment out = assDAO.findAssesmentByAssessmentId(id);
		return out;
	}

	@Override
	public void saveAssessmentById() {
		// TODO Auto-generated method stub
		
	}

}
