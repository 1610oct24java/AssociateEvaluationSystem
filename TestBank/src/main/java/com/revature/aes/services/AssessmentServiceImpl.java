package com.revature.aes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.daos.AssessmentDAO;

@Service
public class AssessmentServiceImpl implements AssessmentService{

	@Autowired
	private AssessmentDAO aDao;
	@Autowired
	private TemplateService tServ;
	
	@Override
	public Assessment addNewAssessment(Assessment assess) {
		
		//assess.setTemplate(tServ.addTemplate(assess.getTemplate()));
		return aDao.save(assess);
	}
	
	

}
