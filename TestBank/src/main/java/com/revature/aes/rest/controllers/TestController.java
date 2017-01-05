package com.revature.aes.rest.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.Category;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.Template;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.beans.User;
import com.revature.aes.core.SystemTemplate;
import com.revature.aes.services.AssessmentService;
import com.revature.aes.services.CategoryService;
import com.revature.aes.services.QuestionService;
import com.revature.aes.services.TemplateQuestionService;
import com.revature.aes.services.TemplateService;
import com.revature.aes.services.UserService;

@RestController
public class TestController
{
	
	@Autowired
	private SystemTemplate systemp;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CategoryService catService;
	@Autowired
	private UserService userService;
	@Autowired
	private AssessmentService assServ;
	@Autowired
	private TemplateService templateService;
	@Autowired
	private TemplateQuestionService tempQuestServ;
	
	@RequestMapping(path="/test",method=RequestMethod.GET,produces = 
		{ MediaType.APPLICATION_JSON_VALUE })
	public String testParse(ModelMap map, HttpSession session)
	{
		return "resources/pages/RevatureTemplate.html";
	}
	
	@RequestMapping(path="test/question", method=RequestMethod.GET,produces = 
		{ MediaType.APPLICATION_JSON_VALUE })
	public List<Question> getAllQuestions(ModelMap map, HttpSession session){
		return questionService.getAllQuestions();
	}
	
	@RequestMapping(path="test/category", method=RequestMethod.GET,produces = 
		{ MediaType.APPLICATION_JSON_VALUE })
	public List<Category> getAllCategories(ModelMap map, HttpSession session){
		return catService.getAllCategory();
	}
	
	@RequestMapping(path="test/assess",method=RequestMethod.GET,produces = 
		{ MediaType.APPLICATION_JSON_VALUE })
	public Assessment getAssessment(ModelMap map, HttpSession session){
		Template tmpl = new Template();
		User user = new User();
//		ObjectMapper mapper = new ObjectMapper();
		AssessmentRequest assReq = new AssessmentRequest("Java",2,2,0,0,null,"person@place.com");
		Set <TemplateQuestion> finalQuestion = systemp.getRandomSelectionFromCategory(assReq);
		
		for(TemplateQuestion tq : finalQuestion)
		{
			tq.setTemplate(tmpl);
			
		}
		
		//tqList.addAll(finalQuestion);
		//return tempQuestServ.addTemplateQuestion(tqList);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		tmpl.setTemplateQuestion(finalQuestion);
		tmpl.setCreateTimeStamp(timestamp);	
		tmpl.setCreator(userService.getUserById(1));
		
		 System.out.println("Before save==================================================");
		 System.out.println(tmpl);
		 System.out.println("=============================================================");		 
		 
		user = userService.getUserByEmail(assReq.getUserEmail());
		
		Assessment assessment = new Assessment(user,0,30,null,null,tmpl);
		
		System.out.println("assessment: " + assessment);
		
		//mapper.writeValueAsString(assServ.addNewAssessment(assessment).getAssessmentId());
		 		
		return assServ.addNewAssessment(assessment); 
	}
}
