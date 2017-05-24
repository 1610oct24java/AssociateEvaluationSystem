package com.revature.aes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.dao.AESJDBCImpl;
import com.revature.aes.dao.AssessmentRequestDAO;

@Service("AssessmentRequestServiceImpl")
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class AssessmentRequestServiceImpl implements AssessmentRequestService {

    @Autowired
    @Qualifier("assessmentRequestDao")
    private AssessmentRequestDAO assReqDao;
    
    @Autowired
    private AESJDBCImpl aesJdbc;

	@Override
	public void deleteAssessmentRequestTemplate(AssessmentRequest assReq){
		assReqDao.delete(assReq);
	}
    @Override
    public AssessmentRequest getAssessmentRequestTemplate() {
    	AssessmentRequest defAss = assReqDao.getDefaultAssessment().get(0);
        return defAss;
    }

    @Override
    public AssessmentRequest saveAssessmentRequest(AssessmentRequest assReq) {
        return assReqDao.save(assReq);
    }

	@Override
	public List<AssessmentRequest> findAll() {
		return assReqDao.findAll();
	}


	@Override
	public AssessmentRequest getAssessmentRequestById(Integer id) {
		// TODO Auto-generated method stub
		return assReqDao.getByAssessmentRequestId(id);
	}

	@Override
	public void newDefaultAssessment(AssessmentRequest assReq) {
		// TODO Auto-generated method stub
		
		List<AssessmentRequest> defAss = assReqDao.getDefaultAssessment();
		defAss.get(0).setIsDefault(0);
		assReqDao.save(defAss.get(0));
		
		assReq.setIsDefault(1);
		assReqDao.save(assReq);
		
	}
	
	@Override
	public Integer getNumberOfQuestions(Integer category, Integer type){
		return aesJdbc.getNumOfQuestions(category, type);
	}


/*	@Override
	public AssessmentRequest getLastInsertedAssessmentRequest() {
		// TODO Auto-generated method stub
		return assReqDao.getLastInsertedAssessmentRequest();
	}*/

}
