package com.revature.aes.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.Packet;
import com.revature.aes.beans.SnippetUpload;
import com.revature.aes.dao.AssessmentService;
import com.revature.aes.dao.UsersDao;
import com.revature.aes.grading.CoreEmailClient;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.S3Service;
import com.revature.aes.util.Error;

@RestController
@RequestMapping("/rest")
public class GetAssessmentController {

	@Autowired
	private AssessmentService service;

	@Autowired
	UsersDao usersService;

	@Autowired
	S3Service s3;

	private HttpSession httpSession;

	private Logging log = new Logging();

	private String coreEmailClientEndpointAddress = "http://localhost:8080/core/";

	@RequestMapping(value = "/link", method = RequestMethod.POST)
	public String getAssessmentID(@RequestBody int assessmentId) {
		httpSession.setAttribute("assessmentId", assessmentId);
		return "thisIsALink";
	}

	@RequestMapping(value = "/submitAssessment", method = RequestMethod.POST)
	public String saveAssessmentAnswers(@RequestBody String jsonUserAnswers, HttpServletResponse response)
			throws JsonMappingException, IOException {
		log.info("I'm gonna save the thing!");
		ObjectMapper om = new ObjectMapper();

		// Separate the incoming data
		Packet incoming = om.readValue(jsonUserAnswers, Packet.class);
		Assessment assessment = incoming.getAssessment();
		List<SnippetUpload> lstSnippetUploads = incoming.getSnippetUpload();

		for (SnippetUpload su : lstSnippetUploads) {
			// userAnswer_assID_qID
			String key = "";
			key += "Take1_userAnswer_";
			key += assessment.getAssessmentId() + "_";
			key += su.getQuestionId();
			key += ".java";
			log.info(su.toString());
			log.info("Key: " + key);
			log.info(su.getCode());
			s3.uploadToS3(su.getCode(), key);
		}

		// SAVE the answers into the database
		service.gradeAssessment(assessment);
		service.updateAssessment(assessment);
		log.info("Hopefully I saved the thing!");

		int recruiterId = assessment.getUser().getRecruiterId();
		String recruiterEmail = usersService.findOne(recruiterId).getEmail();

		new CoreEmailClient(coreEmailClientEndpointAddress).sendEmailAfterGrading(recruiterEmail,
				assessment.getAssessmentId());

		return "Gucci?";
	}

	@RequestMapping(value = "{id}")
	public String getAssessment(@PathVariable("id") int AssessmentId) throws JsonProcessingException {

		log.info("I'm here!" + AssessmentId);
		String jsonString;
		ObjectMapper mapper = new ObjectMapper();
		Assessment assessment = new Assessment();
		try {
			assessment = service.getAssessmentById(AssessmentId);
		} catch (NullPointerException e) {
			StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
			Error.error("\nat Line:\t" + thing.getLineNumber() + "\nin Method:\t" + thing.getMethodName()
					+ "\nin Class:\t" + thing.getClassName(), e);
			e.printStackTrace();
		}
		log.info(assessment.toString());
		log.info(assessment.getMyTemplate().getTemplateQuestion().toString());
		jsonString = mapper.writeValueAsString(assessment);
		return jsonString;
	}
}