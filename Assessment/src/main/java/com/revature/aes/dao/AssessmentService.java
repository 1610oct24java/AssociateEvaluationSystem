package com.revature.aes.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.grading.AssessmentGrader;

@Service
public class AssessmentService implements IAssessmentService {

	@Autowired
	AssessmentDAO assDAO;
	
	@Override
	public Assessment getAssessmentById(int id) {
		System.out.println("I'm servicing things! Poorly!");
		Assessment out = assDAO.findAssessmentByAssessmentId(id);
		return out;
	}

	@Override
	public void saveAssessment(Assessment assessment) {
		System.out.println("I am saving things!! maybe?");
		assDAO.save(assessment);
	}
	
	@Override
	public void updateAssessment(Assessment assessment) {
		Assessment oldAssessment = assDAO.findOne(assessment.getAssessmentId());
		oldAssessment = assessment;
		assDAO.save(oldAssessment);
	}


	public void gradeAssessment(Assessment assessment) {
		assessment.setGrade((int) Math.round(new AssessmentGrader().gradeAssessment(assessment)));
	}
}
