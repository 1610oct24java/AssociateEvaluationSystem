package com.revature.aes.controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;

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
@RequestMapping("/rest")
public class SubmitAssessmentController {
	
	// @Autowired
	AssessmentService service;
	
	// method: receiveAssessmentID from Core()
	// httpSession.save(ID);
	//
	// method: getAssessment()
	// id = session.get(ID);
	// questions.getDataById(id);
	// return questionsJSON;
	// }
	private static String TBurl = "http://localhost:1993/TestBank/";
	private static String Aurl = "http://localhost:1993/Assessment/";
	
	@RequestMapping(value = "/getAssessmentId", method = RequestMethod.GET)
	public String getAssessment()
			throws JsonParseException, JsonMappingException, IOException {
		System.out.println("getAssessment");
		int HashId = 5;
		// GET ID, GENERTE HASH, RETURN URL WITH HASH
		
		return Aurl + HashId;/// ??
	}
	
	@RequestMapping(value = "/getAssessmentData", method = RequestMethod.GET)
	public String getAssessmentQuestions(HttpSession sessionObj)
			throws JsonParseException, JsonMappingException, IOException {
		
		// GET LINK WITH HASH_ID AND TURN IT BACK TO ASSESMENT ID AND SAVE ITNTO
		// THE SESSION??
		int id = 0; // we need a hash for the id
		String JSONString;
		Assessment assessment = service.findAssesmentByAssessmentId(id);
		
		JSONString = assessment.toString();
		
		return JSONString;/// ??
	}
	
	@RequestMapping(value = "/submitAssessment", method = RequestMethod.POST)
	public String saveAssessmentAnswers(@RequestBody String JsonUserAnswers)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		// SAVE the answers into the database
		Assessment assessment = om.readValue(JsonUserAnswers, Assessment.class);
		service.saveAssessmentByAssessment(assessment);
		
		return "redirect:pages/GoodBye.html";
	}
}
