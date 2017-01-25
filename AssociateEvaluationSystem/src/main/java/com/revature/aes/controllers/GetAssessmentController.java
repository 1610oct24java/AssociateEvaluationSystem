package com.revature.aes.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
<<<<<<< HEAD
import java.util.Calendar;
=======
>>>>>>> 595e71de62536bc6e34ff2836ccd4e4ae91e1cd1
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
<<<<<<< HEAD

=======
import com.revature.aes.logging.Logging;
>>>>>>> 595e71de62536bc6e34ff2836ccd4e4ae91e1cd1
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
<<<<<<< HEAD
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.aes.beans.AnswerData;
import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.FileUpload;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.SnippetUpload;
import com.revature.aes.dao.UsersDao;
import com.revature.aes.grading.CoreEmailClient;
import com.revature.aes.logging.Logging;
=======
import com.revature.aes.dao.UserDAO;
import com.revature.aes.grading.CoreEmailClient;
import com.revature.aes.beans.AnswerData;
import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.FileUpload;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.SnippetUpload;
>>>>>>> 595e71de62536bc6e34ff2836ccd4e4ae91e1cd1
import com.revature.aes.service.AssessmentServiceImpl;
import com.revature.aes.service.DragDropService;
import com.revature.aes.service.OptionService;
import com.revature.aes.service.QuestionService;
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
	public String saveAssessmentAnswers(@RequestBody AnswerData answerData)
			throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println("GetAssessmentController.saveAssessmentAnswers: Entered, start saving assessment.");
		
		Assessment assessment = answerData.getAssessment();
		
		// Pull out previously saved server start time for quiz and assign
		// it to the submitted assessment.
		// Prevents user from tampering with TimeStamp on front-end.
		Assessment tempAssessment = service.getAssessmentById(assessment.getAssessmentId());
		assessment.setCreatedTimeStamp(tempAssessment.getCreatedTimeStamp());
		
		// Check submitted server time against server retrieved time to make sure time limit not abused.
		Timestamp quizSubmittedTime = new Timestamp(System.currentTimeMillis());
		assessment.setFinishedTimeStamp(quizSubmittedTime);
		
<<<<<<< HEAD
=======
		System.out.println("Server received assessment submission:"
				+ "\nStarted: " + assessment.getCreatedTimeStamp()
				+ "\nFinished: " + assessment.getFinishedTimeStamp()
				+ "\nTime difference in millis: "
				+ (assessment.getFinishedTimeStamp().getTime() - assessment.getCreatedTimeStamp().getTime()) );
		System.out.println("TimeLimit=" + assessment.getTimeLimit());
		
>>>>>>> 595e71de62536bc6e34ff2836ccd4e4ae91e1cd1
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
		System.out.println("GetAssessmentController.saveAssessmentAnswers: Assessment should now be saved.");
		
		int recruiterId = assessment.getUser().getRecruiterId();
		String recruiterEmail = UsersService.findOne(recruiterId).getEmail();
		
		new CoreEmailClient(coreEmailClientEndpointAddress ).sendEmailAfterGrading(recruiterEmail, assessment.getAssessmentId());
		
		return "{\"success\":\"ok\"}";
	}
	
	@RequestMapping(value = "{id}")
	public Map<String, Object> getAssessment(@PathVariable("id") int AssessmentId)
			throws JsonProcessingException {
		
		System.out.println("Requesting assessment with ID=" + AssessmentId);
		
		Assessment assessment = new Assessment();
		Map<String, Object> responseMap = new HashMap<String, Object>();
			
		try {
			assessment = service.getAssessmentById(AssessmentId);
			
<<<<<<< HEAD
			// Get Date where password issued to user
			String strPassIssuedTime = assessment.getUser().getDatePassIssued();
			Timestamp expireDate = Timestamp.valueOf(strPassIssuedTime);
			
			// Calculating timestamp to expired date
			Calendar cal = Calendar.getInstance();
			cal.setTime(expireDate);
			cal.add(Calendar.DAY_OF_WEEK, 7); // Add a week to the date to reach expired time
			expireDate = new Timestamp(cal.getTime().getTime());
			
			System.out.println(expireDate.after(new Timestamp(System.currentTimeMillis()))); // if true, allow
			
			if (expireDate.after(new Timestamp(System.currentTimeMillis())))
			{	// Allow assessment (expiration date not yet reached)
				// Check to see if the user has already taken this assessment
				if (assessment.getGrade() < 0)
				{	// Assessment not taken yet
					System.out.println("Created Timestamp test= " + assessment.getCreatedTimeStamp());
					if (assessment.getCreatedTimeStamp() == null)
					{
						Timestamp serverQuizStartTime = new Timestamp(System.currentTimeMillis());
						assessment.setCreatedTimeStamp(serverQuizStartTime);
						service.updateAssessment(assessment);
						
						// Add assessment's full time limit to the response
						responseMap.put("timeLimit", assessment.getTimeLimit());
						responseMap.put("msg", "allow");
						responseMap.put("assessment", assessment);
						
					}else {
						Timestamp serverNowTime = new Timestamp(System.currentTimeMillis());
						long serverNowTimeInMillis = serverNowTime.getTime();
						long createdTimestampInMillis = assessment.getCreatedTimeStamp().getTime();
						
						long modifiedTimelimit = (serverNowTimeInMillis/1000) - (createdTimestampInMillis/1000);
						modifiedTimelimit = ((assessment.getTimeLimit()*60) - modifiedTimelimit) / 60;
						
						if (modifiedTimelimit < 0)
						{
							responseMap.put("msg", "deny");
						}else {
							// Add modified time limit since assessment is still in progress
							responseMap.put("timeLimit", modifiedTimelimit);
							responseMap.put("msg", "allow");
							responseMap.put("assessment", assessment);
						}
					}
					
				}else {
					// Assessment taken, this message will redirect to expired page
					responseMap.put("msg", "deny");
				}
				
			}else {
				// Expiration date passed (deny assessment)
=======
			// Check to see if the user has already taken this assessment
			if (assessment.getGrade() < 0)
			{	// Assessment not taken yet
				Timestamp serverQuizStartTime = new Timestamp(System.currentTimeMillis());
				System.out.println("Server time for quiz start: " + serverQuizStartTime);
				System.out.println("Timestamp in millis (getTime()): " + serverQuizStartTime.getTime());
				assessment.setCreatedTimeStamp(serverQuizStartTime);
				service.updateAssessment(assessment);
				
				System.out.println(assessment);
				responseMap.put("msg", "allow");
				responseMap.put("assessment", assessment);
			}else {
				// Assessment taken, this message will redirect to expired page
>>>>>>> 595e71de62536bc6e34ff2836ccd4e4ae91e1cd1
				responseMap.put("msg", "deny");
			}
			
		} catch (NullPointerException e) {
			System.out.println("error");
			e.printStackTrace();
		}

		// Returns a hashMap object with allow message and assessment object
		// which is automatically converted into JSON objects
		return responseMap;
<<<<<<< HEAD
	}
	
	@RequestMapping(value = "/quickSaveAssessment", method = RequestMethod.POST)
	public String quickSaveAssessment(@RequestBody AnswerData answerData)
			throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println("GetAssessmentController.quickSaveAssessment: Entered, quick saving assessment.");
		
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

		//SAVE the answers into the database (grader not called yet)
		service.updateAssessment(assessment);
		System.out.println("GetAssessmentController.saveAssessmentAnswers: Assessment state should now be saved.");
		
		return "{\"success\":\"ok\"}";
=======
>>>>>>> 595e71de62536bc6e34ff2836ccd4e4ae91e1cd1
	}
}