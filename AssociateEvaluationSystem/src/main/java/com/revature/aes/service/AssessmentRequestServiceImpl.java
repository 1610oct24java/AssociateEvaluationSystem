package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.dao.AssessmentRequestDAO;

@Service
public class AssessmentRequestServiceImpl implements AssessmentRequestService {

	private static final Integer AssessmentRequestTemplateId = 1;
	
	@Autowired
	private AssessmentRequestDAO assReqDao;
	
	@Override
	public AssessmentRequest getAssessmentRequestTemplate() {
		return assReqDao.getByAssessmentRequestId(AssessmentRequestTemplateId);
	}

}
