package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.dao.AssessmentDragDropDAO;
import com.revature.aes.dao.CategoryRequestDAO;

@Service("AssessmentDragDropServiceImp")
public class AssessmentDragDropServiceImp {

	@Autowired
	@Qualifier("assessmentDDrop")
	private AssessmentDragDropDAO AssDDao;
	
	public AssessmentDragDrop getByassessmentDragDropId(Integer assessmentDragDropId){
		return null;
	}
	
	
	public void updateDD(AssessmentDragDrop aDD){
		AssDDao.save(aDD);
	}
	
}
