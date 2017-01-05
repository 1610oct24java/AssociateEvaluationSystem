package com.revature.aes.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.aes.beans.Assessment;
import com.revature.aes.dao.AssessmentService;

@RestController
public class GetAssessmentController 
{
	//@Autowired
	private AssessmentService service;
	
	//@Autowired
	private HttpSession httpSession;
		 
	@RequestMapping(value="/link", method=RequestMethod.POST)
	public String getAssessmentID(@RequestBody int assessmentId)
	{
		httpSession.setAttribute("assessmentId", assessmentId);
		return "thisIsALink";
	}
	
	@RequestMapping(value="/view/{id}", method=RequestMethod.GET)
	public String getAssessment(@PathVariable int AssessmentId) throws JsonProcessingException
	{
		System.out.println("I'm here!");
		String JSONString;
		ObjectMapper mapper = new ObjectMapper();
		//httpSession.getAttribute("assessmentId"); 
		//Assessment assessment = service.findAssesmentByAssessmentId(AssessmentId);
		Assessment assessment = service.findOne(AssessmentId);
		System.out.println(assessment);
		JSONString = mapper.writeValueAsString(assessment);
		return JSONString;
	}
}