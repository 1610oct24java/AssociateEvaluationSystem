package com.revature.aes.controllers;

import java.util.Iterator;
import java.util.Set;
import java.lang.Math;

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
import com.revature.aes.service.CategoryRequestService;
import com.revature.aes.service.CategoryService;

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
	
	@Autowired
	CategoryRequestService categoryRequestService;
	
	@Autowired
	CategoryService categoryService;


	/**
	 * Endpoint used to create an Assessment Request object.
	 *
	 * @param id
	 *            The id of the AssessmentRequest object to be created
	 */
	//@Transactional(readOnly = false)
	@RequestMapping(value = "assessmentrequest/{id}/", method = RequestMethod.PUT)
	public void saveAssessmentRequest(@RequestBody AssessmentRequest assessmentRequest, @PathVariable Integer id) {
		
		
		//check for removed categories if editing 
		AssessmentRequest existingAssReq = assessmentRequestService.getAssessmentRequestById(assessmentRequest.getAssessmentRequestId());
		Set<CategoryRequest> newAssReqCategories = assessmentRequest.getCategoryRequestList();
		if(existingAssReq != null){
			for(CategoryRequest catReq : existingAssReq.getCategoryRequestList()){
				if(!newAssReqCategories.contains(catReq)){
					categoryRequestService.deleteCategoryRequest(catReq);
				}
			}
		} 
		
		
		log.info("Creating AssessmentRequest with id: " + id);
		log.info("New AssessmentRequest: " + assessmentRequest);
		assessmentRequestService.saveAssessmentRequest(assessmentRequest);
		log.info("Assessment Request Created");

	}
	
	
	/**
	 * Endpoint used to retrieve an assessment request
	 *
	 * @param id
	 *            The id of the AssessmentRequest object to be received
	 */
	@RequestMapping(value = "assessmentrequest/{id}/", method = RequestMethod.GET)
	public AssessmentRequest getAssessmentRequest(@PathVariable Integer id) {
		
		log.info("Retrieving AssessmentRequest with id: " + id);
		AssessmentRequest assessmentRequest = assessmentRequestService.getAssessmentRequestById(id);
		log.info("Assessment Request Retrieved");
		
		return assessmentRequest;
	}
	
	/* new enpoint to get number of questions of specific category and type
	 * if the category is 'core language' then return the lesser of the core two categories.
	 */
	@RequestMapping(value = "assessmentrequest/{category}/{type}/{numOfQuestions}/", method = RequestMethod.GET)
	public Integer getNumberOfQuestions(@PathVariable Integer category, @PathVariable Integer type, @PathVariable Integer numOfQuestions){
		
		System.out.println(category);
		System.out.println(type);
		System.out.println(numOfQuestions);
		
		Integer num = 0;
		
		int javaCategory = categoryService.getCategoryByName("Java").getCategoryId();
		int dotNetCategory = categoryService.getCategoryByName(".net").getCategoryId();
		
		if(category == 6){		
			Integer javaQuestions = assessmentRequestService.getNumberOfQuestions(javaCategory, type);
			Integer dotNetQuestions = assessmentRequestService.getNumberOfQuestions(dotNetCategory, type); 	
			num = Math.min(javaQuestions, dotNetQuestions);
		} else {
			num = assessmentRequestService.getNumberOfQuestions(category, type);
		}
		
		return num;
	}

	/**
	 * Endpoint used to delete Assessment Request object.
	 */
	@RequestMapping(value = "assessmentrequest/delete", method = RequestMethod.POST)
	public void deleteAssessmentRequest(@RequestBody AssessmentRequest assessmentRequest) {

		log.info("deleting AssessmentRequest with id: " + assessmentRequest.getAssessmentRequestId());
		assessmentRequestService.deleteAssessmentRequest(assessmentRequest);
		log.info("Assessment Request Deleted");
	}

}
