package com.revature.aes.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

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
import com.revature.aes.beans.Packet;
import com.revature.aes.beans.SnippetUpload;
import com.revature.aes.dao.AssessmentService;
import com.revature.aes.dao.UsersDao;
import com.revature.aes.grading.CoreEmailClient;
import com.revature.aes.service.S3Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("/rest")
public class GetAssessmentController {
	
	@Autowired
	private AssessmentService service;
	
	@Autowired
	UsersDao UsersService;
	
	@Autowired
	S3Service s3;
	
	private HttpSession httpSession;
	
	private String coreEmailClientEndpointAddress =
			"http://54.201.37.54:8080/core/";
	
	@RequestMapping(value = "/link", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON })
	public String getAssessmentID(@RequestBody String JSONData,
			HttpSession event) {
		int assessmentId;
		
		System.out.println("Link called " + JSONData);
		
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(JSONData).getAsJsonObject();
		
		assessmentId = Integer.parseInt(obj.get("assessmentId").getAsString());
		
		httpSession = event;
		
		httpSession.setAttribute("assessmentId", assessmentId);
		System.out.println("Attrtibute set");
		
		System.out.println(httpSession.getAttribute("assessmentId"));
		
		return "http://localhost:8090/asmt/quiz";
	}
	
	@RequestMapping(value = "/submitAssessment", method = RequestMethod.POST)
	public String saveAssessmentAnswers(@RequestBody String JsonUserAnswers,
			HttpServletResponse response)
			throws JsonParseException, JsonMappingException, IOException {
		System.out.println("I'm gonna save the thing!");
		ObjectMapper om = new ObjectMapper();
		
		// Separate the incoming data
		Packet incoming = om.readValue(JsonUserAnswers, Packet.class);
		Assessment assessment = incoming.getAssessment();
		List<SnippetUpload> lstSnippetUploads = incoming.getSnippetUpload();
		
		for (SnippetUpload su : lstSnippetUploads) {
			// userAnswer_assID_qID
			String key = "";
			key += "Take1_userAnswer_";
			key += assessment.getAssessmentId() + "_";
			key += su.getQuestionId();
			key += ".java"; // TODO Not hardcode this?
			System.out.println(su);
			System.out.println("Key: " + key);
			System.out.println(su.getCode());
			s3.uploadToS3(su.getCode(), key);
		}
		
		// SAVE the answers into the database
		service.gradeAssessment(assessment);
		service.updateAssessment(assessment);
		System.out.println("Hopefully I saved the thing!");
		
		int recruiterId = assessment.getUser().getRecruiterId();
		String recruiterEmail = UsersService.findOne(recruiterId).getEmail();
		
		new CoreEmailClient(coreEmailClientEndpointAddress)
				.sendEmailAfterGrading(recruiterEmail,
						assessment.getAssessmentId());
		
		return "Gucci?";
	}
	
	@RequestMapping(value = "{id}")
	public String getAssessment(@PathVariable("id") int AssessmentId)
			throws JsonProcessingException {
		
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
		System.out.println(
				assessment.getMyTemplate().getTemplateQuestion().toString());
		JSONString = mapper.writeValueAsString(assessment);
		return JSONString;
	}
}