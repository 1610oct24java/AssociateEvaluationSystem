package com.revature.aes.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.aes.beans.Assessment;

import com.revature.aes.dao.AssessmentService;

@RestController
@RequestMapping("/rest")
public class GetAssessmentController {
	@Autowired
	private AssessmentService service;

	// @Autowired
	private HttpSession httpSession;

	@RequestMapping(value = "/link", method = RequestMethod.POST)
	public String getAssessmentID(@RequestBody int assessmentId) {
		httpSession.setAttribute("assessmentId", assessmentId);
		return "thisIsALink";
	}
	
	@RequestMapping(value="/submitAssessment",method = RequestMethod.POST)
	public String saveAssessmentAnswers(@RequestBody String JsonUserAnswers, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException
	{
		System.out.println("I'm gonna save the thing!");
		ObjectMapper om = new ObjectMapper();
		//SAVE the answers into the database
		Assessment assessment = om.readValue(JsonUserAnswers,Assessment.class);
		service.updateAssessment(assessment);
		System.out.println("Hopefully I saved the thing!");
		
		response.sendRedirect("/Assessment/goodbye");
		
    	return "Gucci?";
	}

	@RequestMapping(value = "{id}")
	public String getAssessment(@PathVariable("id") int AssessmentId) throws JsonProcessingException {

		System.out.println("I'm here!" + AssessmentId);
		String JSONString;
		ObjectMapper mapper = new ObjectMapper();
		// httpSession.getAttribute("assessmentId");
		Assessment assessment = new Assessment();
		try {
			assessment = service.getAssessmentById(AssessmentId);
		} catch (NullPointerException e) {
			System.out.println("error");
			e.printStackTrace();
		}
		System.out.println(assessment);
		System.out.println(assessment.getMyTemplate().getTemplateQuestion().toString());
		JSONString = mapper.writeValueAsString(assessment);
		return JSONString;
	}
}