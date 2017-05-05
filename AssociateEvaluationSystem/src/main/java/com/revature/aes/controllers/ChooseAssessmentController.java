package com.revature.aes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.AssessmentRequestServiceImpl;

@RestController
public class ChooseAssessmentController {
	
	@Autowired
	private Logging log;
	
	@Autowired
	private AssessmentRequestServiceImpl assReqServ;
	
	@Autowired
	private AssessmentRequest assReq;
	
	//created by: Edward Young
	// on enter view, objective is to call /allAssessments to load initial vaules of assessments.
	
	@RequestMapping(value = "/allAssessments", method = RequestMethod.GET)
	public List<AssessmentRequest> getListOfAssessments(){
		
		List<AssessmentRequest> allAss = assReqServ.findAll();
		
		return allAss;
	}
	
	//sets new default
	@RequestMapping(value = "/selectAssessment", method = RequestMethod.POST)
	public void selectDefaultAssessment(@RequestBody AssessmentRequest newDefault){
	
		AssessmentRequest newDefaultAss = assReqServ.getAssessmentRequestById(newDefault.getAssessmentRequestId());
		assReqServ.newDefaultAssessment(newDefaultAss);
		
	}
	
	//saves newly changed hours
	@RequestMapping(value = "/updateViewableHours", method = RequestMethod.POST)
	public void updateHViewableHours(@RequestBody AssessmentRequest newViewableHours){
		assReqServ.saveAssessmentRequest(newViewableHours);
	}

}
