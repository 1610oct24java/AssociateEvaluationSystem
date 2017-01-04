package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Assessment;

@Repository
public interface AssessmentService extends JpaRepository<Assessment, Integer> {
	
	public Assessment findAssesmentByAssessmentId(int id);
	
	public void saveAssessmentByAssessment(Assessment assessment);
}
