package com.revature.aes.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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
import com.revature.aes.beans.AnswerData;
import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.FileUpload;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.SnippetTemplate;
import com.revature.aes.beans.SnippetUpload;
import com.revature.aes.beans.Template;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.config.IpConf;
import com.revature.aes.dao.UserDAO;
import com.revature.aes.grading.CoreEmailClient;
import com.revature.aes.logging.Logging;
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

	@Autowired
	CoreEmailClient coreEmailClient;

	@Autowired
	IpConf ipConf;

	@PostConstruct
	protected void postConstruct(){

		configureRestService();

	}

	private String coreEmailClientEndpointAddress = "http://localhost/aes/";

	private void configureRestService(){

		coreEmailClientEndpointAddress = "http://"+ipConf.getHostName()+"/aes/";

	}

	private Logging log = new Logging();



	@RequestMapping(value = "/link", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON })
	public String getAssessmentID(@RequestBody Assessment assessment, HttpServletRequest request) {

		log.info("Link called " + assessment);

		return coreEmailClientEndpointAddress + "quiz?asmt=" + assessment.getAssessmentId();
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
				
				switch(su.getFileType())
				{
				case "java": 
					key += ".java"; 
					break;
				case "cpp": 
					key += ".cpp"; 
					break;
		        case "c++":
		        	key += ".cpp"; 
					break;
		        case "c":
		        	key += ".c";
		        	break;
				case "cs":
					key += ".cs";
					break;
				default: 
					key += ".java"; 
					break;
				}
				
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
		
		/*int recruiterId = assessment.getUser().getRecruiterId();
		String recruiterEmail = UsersService.findOne(recruiterId).getEmail();*/

		coreEmailClient.setServiceHost(coreEmailClientEndpointAddress);
		log.info("Email sent? " + coreEmailClient.sendEmailAfterGrading(assessment.getUser().getEmail(), assessment.getAssessmentId()));
		
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
			
			// --- This portion of code pulls a snippet template from the S3 bucket --- -RicSmith
			// This list of snippet templates will be added to the response map.
			List<String> codeStarters = new ArrayList<String>();
			// Pull out the TemplateQuestion set from the assessment.
			Set<TemplateQuestion> templateQuestions = assessment.getTemplate().getTemplateQuestion();
			
			for (TemplateQuestion tq : templateQuestions)
			{
				Question question = tq.getQuestion();						// Get each question.
				Format questionFormat = question.getFormat();				// Get each question format.
				
				// Check to see if this question format is a code snippet.
				if (questionFormat.getFormatName().equals("Code Snippet"))	
				{
					// Pull out the SnippetTemplates from the question.
					Set<SnippetTemplate> snippetTemplates = question.getSnippetTemplates();
					
					// Loop through the snippetTemplates to get their url locations in S3 bucket.
					for (SnippetTemplate st : snippetTemplates)
					{
						String snippetTemplateUrl = st.getTemplateUrl();		// SnippetTemplate URL.
						String starterCode = s3.readFromS3(snippetTemplateUrl);	// Read snippet starter from S3 bucket.
						codeStarters.add(starterCode);							// Add snippetTemplate to list.
					}
				}
			}
			
			// If code snippet questions exist in the assessment, this array won't be empty.
			if (!codeStarters.isEmpty())
			{
				// Add code starters for snippets to the responseMap that will be sent with the assessment to AngularJS for parsing.
				responseMap.put("snippets", codeStarters);
			}
<<<<<<< HEAD
			
=======
      
>>>>>>> ac983401c6893402c77bed4e256360c5e13e28e8
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
				responseMap.put("msg", "deny");
			}
			
		} catch (NullPointerException e) {
			System.out.println("error");
			e.printStackTrace();
		}

		// Returns a hashMap object with allow message and assessment object
		// which is automatically converted into JSON objects
		return responseMap;
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
		System.out.println("GetAssessmentController.saveAssessmentAnswers: Assessment state should now be quick saved.");
		
		return "{\"success\":\"ok\"}";
	}
}