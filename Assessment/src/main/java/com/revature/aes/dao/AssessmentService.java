package com.revature.aes.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.grading.AssessmentGrader;
import com.revature.aes.logging.Logging;


@Service
public class AssessmentService implements IAssessmentService {

	private Logging log = new Logging();
	
	@Autowired
	AssessmentDAO assDAO;
	
	@Override
	public Assessment getAssessmentById(int id) {
		log.info("I'm servicing things! Poorly!");
		return assDAO.findAssessmentByAssessmentId(id);
	}

	@Override
	public void saveAssessment(Assessment assessment) {
		log.info("I am saving things!! maybe?");
		assDAO.save(assessment);
	}
	
	@Override
	public void updateAssessment(Assessment assessment) {
		assDAO.save(assDAO.findOne(assessment.getAssessmentId()));
	}


	public void gradeAssessment(Assessment assessment) {
		assessment.setGrade((int) Math.round(new AssessmentGrader().gradeAssessment(assessment)));
	}
}
