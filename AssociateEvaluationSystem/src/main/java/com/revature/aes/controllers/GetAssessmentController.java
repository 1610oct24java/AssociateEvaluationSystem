package com.revature.aes.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.revature.aes.beans.*;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.*;

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
import com.revature.aes.dao.UserDAO;
import com.revature.aes.grading.CoreEmailClient;

import com.revature.aes.service.AssessmentServiceImpl;
import com.revature.aes.service.S3Service;

@RestController
@RequestMapping("/rest")
public class GetAssessmentController {
	
	@Autowired
	private AssessmentServiceImpl service;
	
	@Autowired
	UserDAO UsersService;
	
	@Autowired
	S3Service s3;

	@Autowired
	DragDropService ddService;

	@Autowired
	OptionService optService;

	@Autowired
	QuestionService questService;

	@Inject
	private org.springframework.boot.autoconfigure.web.ServerProperties serverProperties;

	private static int port;

	private static String ip;

	@PostConstruct
	protected void postConstruct(){

		configureRestService();

	}

	private void configureRestService(){

		port = serverProperties.getPort();

		try{

			ip = InetAddress.getLocalHost().getHostAddress();

		} catch (UnknownHostException e) {
			ip = "localhost";
		}

	}

	private Logging log = new Logging();

	private String coreEmailClientEndpointAddress = "http://localhost/aes/";

	@RequestMapping(value = "/link", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON })
	public String getAssessmentID(@RequestBody Assessment assessment, HttpServletRequest request) {

		log.info("Link called " + assessment);

		ProcessBuilder pb = new ProcessBuilder("curl -s http://169.254.169.254/latest/meta-data/public-hostname");

		String localHostname;

		try {
			localHostname = new BufferedReader(new InputStreamReader((pb.start().getInputStream()))).readLine();
		} catch (IOException e) {
			localHostname = "localhost";
			log.warn("Could not execute command to get hostname");
		}



		return localHostname + ":" + port + "/aes/quiz?asmt=" + assessment.getAssessmentId();
	}
	
	@RequestMapping(value = "/submitAssessment", method = RequestMethod.POST)
	public String saveAssessmentAnswers(@RequestBody AnswerData answerData,
			HttpServletResponse response)
			throws JsonParseException, JsonMappingException, IOException {
		System.out.println("I'm gonna save the thing!");
/*		ObjectMapper om = new ObjectMapper();
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		om.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));*/
		//System.out.println("Info sent: " + answerData);

		//Separate the incoming data
		/*Packet incoming = om.readValue(JsonUserAnswers, Packet.class);
		Assessment assessment = incoming.getAssessment();
		List<SnippetUpload> lstSnippetUploads = incoming.getSnippetUpload();*/

		Assessment assessment = answerData.getAssessment();

		List<SnippetUpload> lstSnippetUploads = answerData.getSnippetUploads();

		Set<Option> optList = new HashSet<>();

		for (Option opts : assessment.getOptions()){

			optList.add(optService.getOptionById(opts.getOptionId()));

		}

		assessment.setOptions(optList);

		for(Option opt : assessment.getOptions()){

			System.out.println(opt);

		}

		for (AssessmentDragDrop add : assessment.getAssessmentDragDrop()){

			add.setDragDrop(ddService.getDragDropById(add.getDragDrop().getDragDropId()));

		}



		//System.out.println(answerData.getSnippetUploads());

		assessment.setFileUpload(new HashSet<FileUpload>());

		if(lstSnippetUploads!=null) {

			for (SnippetUpload su : lstSnippetUploads) {
				// userAnswer_assID_qID
				String key = "";
				key += "Take1_userAnswer_";
				key += assessment.getAssessmentId() + "_";
				key += su.getQuestionId();
				key += ".cpp";        //TODO Not hardcode this?
				System.out.println(su);
				System.out.println("Key: " + key);
				System.out.println(su.getCode());
				s3.uploadToS3(su.getCode(), key);
				FileUpload fu = new FileUpload();
				fu.setAssessment(assessment);
				fu.setFileUrl(key);
				fu.setQuestion(questService.getQuestionById(su.getQuestionId()));
				assessment.getFileUpload().add(fu);
			}
		}

		//SAVE the answers into the database
		service.gradeAssessment(assessment);
		service.updateAssessment(assessment);
		System.out.println("Hopefully I saved the thing!");
		
		int recruiterId = assessment.getUser().getRecruiterId();
		String recruiterEmail = UsersService.findOne(recruiterId).getEmail();
		
		new CoreEmailClient(coreEmailClientEndpointAddress ).sendEmailAfterGrading(recruiterEmail, assessment.getAssessmentId());
		
		return "Gucci?";
	}
	
	@RequestMapping(value = "{id}")
	public String getAssessment(@PathVariable("id") int AssessmentId)
			throws JsonProcessingException {
		
		System.out.println("I'm here!" + AssessmentId);
		String JSONString;
		ObjectMapper mapper = new ObjectMapper();
		Assessment assessment = new Assessment();
		try {
			assessment = service.getAssessmentById(AssessmentId);
		} catch (NullPointerException e) {
			System.out.println("error");
			e.printStackTrace();
		}
		System.out.println(assessment);
		JSONString = mapper.writeValueAsString(assessment);
		return JSONString;
	}
}