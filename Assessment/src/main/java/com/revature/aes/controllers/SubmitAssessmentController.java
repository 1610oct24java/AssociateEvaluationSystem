package com.revature.aes.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.aes.beans.Assessment;
import com.revature.aes.dao.AssessmentService;

@Controller
public class SubmitAssessmentController {

	 //@Autowired
	  AssessmentService service;
	 
	@RequestMapping(value="/submitAssessment",method = RequestMethod.POST)
	public String saveAssessmentAnswers(@RequestBody String JsonUserAnswers) throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper om = new ObjectMapper();
		//SAVE the answers into the database
		Assessment assessment = om.readValue(JsonUserAnswers,Assessment.class);
		service.saveAssessmentByAssessment(assessment);

    	return "redirect:pages/GoodBye.html";
	}
}
