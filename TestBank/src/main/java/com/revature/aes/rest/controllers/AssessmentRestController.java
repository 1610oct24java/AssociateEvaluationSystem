package com.revature.aes.rest.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.Template;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.core.SystemTemplate;

@RestController
public class AssessmentRestController {
	
	@Autowired
	private SystemTemplate systemp;
	
	/**
	 * Takes in a JSON that requests an assessment.
	 * 
	 * @param category: The category that a question falls into.
	 * @return a list of questions that are of the requested categories.
	 */
	@RequestMapping(value = "user/{email}/RandomAssessment", method=RequestMethod.POST, consumes = 
		{MediaType.APPLICATION_JSON_VALUE})
	public Template createAssessment(@RequestBody AssessmentRequest assReq){
		
		Template tmpl = new Template();
		Set <TemplateQuestion> finalQuestion = systemp.getRandomSelectionFromCategory(assReq);
		for(TemplateQuestion tq : finalQuestion)
		{
			tq.setTemplate(tmpl);
		}
		tmpl.setTemplateQuestion(finalQuestion);
		
		
		
		return tmpl;
	}
	

}
