package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.AssessmentRequest;

@Repository("assessmentRequestDao")
public interface AssessmentRequestDAO extends JpaRepository<AssessmentRequest, Integer> {
	
	
	public AssessmentRequest getByAssessmentRequestId(Integer id);
/*	
//	@Query(value="Select a from AssessmentRequest a where rownum =1 order by a.assessmentRequestId desc")
	@Query(value="Select * from AES_ASSESSMENT_REQUEST where rownum =1 order by assessment_Request_Id desc", nativeQuery=true)
	public AssessmentRequest getLastInsertedAssessmentRequest();*/
	
}
