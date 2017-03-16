package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.AssessmentRequest;

@Repository("assessmentRequestDao")
public interface AssessmentRequestDAO extends JpaRepository<AssessmentRequest, Integer> {
	
	public AssessmentRequest getByAssessmentRequestId(Integer id);

}