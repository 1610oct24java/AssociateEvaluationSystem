package com.revature.aes.controllers;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.CategoryRequest;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.AssessmentRequestService;

/**
 *
 * Rest Controller to submit a new assessment request template. Add any
 * functionality here that would be related to the AssessmentRequest and should
 * be exposed.
 *
 * Access is limited to admin.
 *
 * @author Nick "LURCH" Jurczak
 *
 */
@RestController
public class AssessmentRequestController {

	@Autowired
	Logging log;

	@Autowired
	AssessmentRequestService assessmentRequestService;

	/**
	 * Endpoint used to update and Assessment Request object.
	 *
	 * @param id
	 *            The id of the AssessmentRequest object to be altered
	 */
	//@Transactional(readOnly = false)
	@RequestMapping(value = "assessmentrequest/{id}/", method = RequestMethod.PUT)
	public void updateEmployee(@RequestBody AssessmentRequest assessmentRequest, @PathVariable Integer id) {
		
		Iterator<CategoryRequest> catIt = assessmentRequest.getCategoryRequestList().iterator();	
		while (catIt.hasNext()) {
			catIt.next().setAssessmentRequest(assessmentRequest);
		}

		log.info("Updating AssessmentRequest with id: " + id);
		log.info("New AssessmentRequest: " + assessmentRequest);
		assessmentRequestService.saveAssessmentRequest(assessmentRequest);
		log.info("Assessment Request Updated");

	}
	
	//new enpoint to get number of questions of specific category and type
	@RequestMapping(value = "assessmentrequest/{category}/{type}/{numOfQuestions}/", method = RequestMethod.GET)
	public Integer getNumberOfQuestions(@PathVariable Integer category, @PathVariable Integer type, @PathVariable Integer numOfQuestions){
		
		System.out.println(category);
		System.out.println(type);
		System.out.println(numOfQuestions);
		
		Integer num = assessmentRequestService.getNumberOfQuestions(category, type);

		return num;
	}

}
