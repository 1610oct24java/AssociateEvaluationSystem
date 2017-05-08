package com.revature.aes.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.revature.aes.beans.*;
import com.revature.aes.config.IpConf;
import com.revature.aes.dao.UserDAO;
import com.revature.aes.grading.CoreEmailClient;
import com.revature.aes.logging.Logging;
import com.revature.aes.mail.MailObject;
import com.revature.aes.service.*;
import com.revature.aes.util.PropertyReader;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
//Importing a class for hashing the assessment id in this controller
//Author: Nicholas Perez	Date: 4/20/2017


@RestController
@RequestMapping("/rest")
public class GetAssessmentController {
	
	@Autowired
	private Logging log;
	
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
	GlobalSettingService globalSettingService;

	@Autowired
	CoreEmailClient coreEmailClient;

	@Autowired
	IpConf ipConf;
	
	@Autowired
    private PropertyReader propertyReader;


	@PostConstruct
	protected void postConstruct(){

		configureRestService();

	}

	private String coreEmailClientEndpointAddress = "http://localhost/aes/";

	private void configureRestService(){

		coreEmailClientEndpointAddress = "http://"+ipConf.getHostName()+"/aes/";

	}

	//possible hash code insertion should go here????
	//Author: Nicholas Perez
	@RequestMapping(value = "/link", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON })
	public String getAssessmentID(@RequestBody Assessment assessment, HttpServletRequest request) {
		
		log.info("Link called " + assessment);

		Hashids hashids = new Hashids();
		//System.out.print(hashids.encode(assessment.getAssessmentId()));
		//System.out.println(hashids.decode(hashids.encode(assessment.getAssessmentId())));
		return coreEmailClientEndpointAddress + "quiz?asmt=" + hashids.encode(assessment.getAssessmentId());

	}
	
	/**
	 * Returns the encoded id of an assessment.
	 * 
	 * Used in bringing up the quiz review of the assessment. 
	 * 
	 * @param assessment the assessment whose id will be encoded
	 * @return the encoded id of the assessment
	 */
	@RequestMapping(value="/encode", method=RequestMethod.POST)
	public String getEncodedAssessmentId(@RequestBody Assessment assessment) {
		// encode the assessment's id.
		Hashids hashids = new Hashids();
		String encodedId = hashids.encode(assessment.getAssessmentId());
		
		// return the encoded id as a (JSON) object so it can be accessed in AngularJS.
		return "{\"data\": \"" + encodedId + "\"}";
	}
	
	@RequestMapping(value = "/submitAssessment", method = RequestMethod.POST)
	public String saveAssessmentAnswers(@RequestBody AnswerData answerData)
			throws JsonParseException, JsonMappingException, IOException {
		
		log.debug("GetAssessmentController.saveAssessmentAnswers: Entered, start saving assessment.");
		
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

/*		for(Option opt : assessment.getOptions()){

			System.out.println(opt);

		}
*/
		/*SA-CHANGES STARTED*/
		for (AssessmentDragDrop addD : assessment.getAssessmentDragDrop()){
			for(AssessmentDragDrop addE : tempAssessment.getAssessmentDragDrop()){
				if((addE.getDragDrop().getDragDropId())==(addD.getDragDrop().getDragDropId())){
					addD.setAssessmentDragDropId(addE.getAssessmentDragDropId());
					break;
				}
			}
			addD.setDragDrop(ddService.getDragDropById(addD.getDragDrop().getDragDropId()));
		}

		//assessment.setFileUpload(new HashSet<FileUpload>());
		/*SA-CHANGES END*/
		if(lstSnippetUploads!=null) {

			for (SnippetUpload su : lstSnippetUploads) {
				// userAnswer_assID_qID
				String key = "";
				key += "Take1_userAnswer_";
				key += assessment.getAssessmentId() + "_";
				key += su.getQuestionId();
				
				switch(su.getFileType())
				{
				case "cpp": 
				case "c++":
					key += ".cpp"; 
					break;
		        case "c":
		        	key += ".c";
		        	break;
				case "cs":
					key += ".cs";
					break;
				case "java":
				default: 
					key += ".java"; 
					break;
				}
				
				s3.uploadToS3(su.getCode(), key);
				
				/*SA-CHANGES STARTED*/
				boolean bIsExists = false;
				for(FileUpload fUpload : tempAssessment.getFileUpload()){
					if((fUpload.getFileUrl()).equals(key)){
						bIsExists=true;
					}
				}
				
				if(bIsExists==false){
					FileUpload fu = new FileUpload();
					fu.setAssessment(assessment);
					fu.setFileUrl(key);
					fu.setQuestion(questService.getQuestionById(su.getQuestionId()));
					assessment.getFileUpload().add(fu);
				}
				/*SA-CHANGES END*/
			}
		}

		//SAVE the answers into the database
		service.gradeAssessment(assessment);
		service.updateAssessment(assessment);
		log.debug("GetAssessmentController.saveAssessmentAnswers: Assessment should now be saved.");
		
		/*int recruiterId = assessment.getUser().getRecruiterId();
		String recruiterEmail = UsersService.findOne(recruiterId).getEmail();*/

		coreEmailClient.setServiceHost(coreEmailClientEndpointAddress);
		log.info("Email sent? " + coreEmailClient.sendEmailAfterGrading(assessment.getUser().getEmail(), assessment.getAssessmentId()));
		
		return "{\"success\":\"ok\"}";
	}

	/**
	 * In this function the id will be decoded so that the assesment will show with the questions
	 * @param AssessmentId - passes in the id of the test currently being taken
	 * @return
	 * @throws IOException
	 * Author: Nicholas Perez
	 */
	@RequestMapping(value = "{id}")
	public Map<String, Object> getAssessment(@PathVariable("id") String AssessmentId) throws IOException {

		Hashids otherHash = new Hashids();
		long[] hashIdnum = otherHash.decode(AssessmentId);
		log.debug("Requesting assessment with ID=" + AssessmentId);
		
		Assessment assessment = new Assessment();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		try {
			assessment = service.getAssessmentById((int) hashIdnum[0]);
			System.out.println(assessment.toString());
			
			// --- This portion of code pulls a snippet template from the S3 bucket --- -RicSmith
			// This list of snippet templates will be added to the response map.
			List<String> codeStarters = new ArrayList<>();
			List<String> codeStartersInd = new ArrayList<>();
			
			Set<TemplateQuestion> templateQuestions = assessment.getTemplate().getTemplateQuestion();
			Set<FileUpload> tempUploads = assessment.getFileUpload();

			int numQ = 1;



			for (TemplateQuestion tq : templateQuestions)
			{
				Question question = tq.getQuestion();						// Get each question.
				Format questionFormat = question.getFormat();				// Get each question format.
			
				// Check to see if this question format is a code snippet.
				if ("Code Snippet".equals(questionFormat.getFormatName()))	
				{
					// Pull out the SnippetTemplates from the question.
					Set<SnippetTemplate> snippetTemplates = question.getSnippetTemplates();

					int questionID = question.getQuestionId();
					

					int in = questionID;
					String ind = String.valueOf(in);

					boolean addedS = false;
					String starterCode="";
					if(!tempUploads.isEmpty()){
						for(FileUpload f : tempUploads){
							starterCode = s3.readFromS3(f.getFileUrl());
							if(f.getQuestion().getQuestionId() == question.getQuestionId()){
								
								codeStarters.add(starterCode);
								codeStartersInd.add(ind);
								addedS=true;
								break;
							}
						}

					}
					
					if(addedS==false){
						// Loop through the snippetTemplates to get their url locations in S3 bucket.
						for (SnippetTemplate st : snippetTemplates)
						{
							String snippetTemplateUrl = st.getTemplateUrl();		// SnippetTemplate URL.
							starterCode = s3.readFromS3(snippetTemplateUrl);	// Read snippet starter from S3 bucket.
							codeStarters.add(starterCode);	
							// Add snippetTemplate to list.
							codeStartersInd.add(ind);
						}
					}
				}
				numQ++;
				
			}
			
			// If code snippet questions exist in the assessment, this array won't be empty.
			if (!codeStarters.isEmpty())
			{
				
				// Add code starters for snippets to the responseMap that will be sent with the assessment to AngularJS for parsing.
				responseMap.put("snippets", codeStarters);
				responseMap.put("snippetIndexes", codeStartersInd);
			}

			// Get Date where password issued to user
			String strPassIssuedTime = assessment.getUser().getDatePassIssued();
			Timestamp expireDate = Timestamp.valueOf(strPassIssuedTime);
			
			// Get review boolean from service
			boolean reviewBool = globalSettingService.getCanCandidatesReview();
			// Put review boolean into responseMap
			responseMap.put("reviewBool", reviewBool);
			
			// Calculating timestamp to expired date
			Calendar cal = Calendar.getInstance();
			cal.setTime(expireDate);
			cal.add(Calendar.DAY_OF_WEEK, 7); // Add a week to the date to reach expired time
			expireDate = new Timestamp(cal.getTime().getTime());
			
			if (expireDate.after(new Timestamp(System.currentTimeMillis())))
			{	// Allow assessment (expiration date not yet reached)
				// Check to see if the user has already taken this assessment
				if (assessment.getGrade() < 0)
				{	// Assessment not taken yet
					if (assessment.getCreatedTimeStamp() == null)
					{
						Timestamp serverQuizStartTime = new Timestamp(System.currentTimeMillis());
						assessment.setCreatedTimeStamp(serverQuizStartTime);
						service.updateAssessment(assessment);
						
						// Add assessment's full time limit to the response TODO fix
						responseMap.put("timeLimit", assessment.getTimeLimit());
						responseMap.put("newTime", -1);
						responseMap.put("msg", "allow");
						responseMap.put("assessment", assessment);
						
					}else {
						responseMap.put("timeLimit", assessment.getTimeLimit());
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
							responseMap.put("newTime", modifiedTimelimit);
							responseMap.put("msg", "allow");
							responseMap.put("assessment", assessment);
						}
					}
					
				}else {
					// Assessment taken, this message will redirect to expired page
					responseMap.put("assessment", assessment);
					responseMap.put("msg", "deny");
				}
				
			}else {
				// Expiration date passed (deny assessment)
				responseMap.put("msg", "deny");
			}
			
		}catch(NullPointerException e)
		{
			log.error(Logging.errorMsg("\nin class: GetAssessmentController\nin method: getAssessment \nexcept", e));
		}

		// Returns a hashMap object with allow message and assessment object
		// which is automatically converted into JSON objects
		
		return responseMap;
	}
	
	@RequestMapping(value = "/quickSaveAssessment", method = RequestMethod.POST)
	public String quickSaveAssessment(@RequestBody AnswerData answerData)
			throws JsonParseException, JsonMappingException, IOException {
		
		log.debug("GetAssessmentController.quickSaveAssessment: Entered, quick saving assessment.");
		
		Assessment assessment = answerData.getAssessment();
		
		/*SA-CHANGES STARTED*/
		int assID = assessment.getAssessmentId();
		Assessment currAssessment =  service.getAssessmentById(assID);
		
		List<SnippetUpload> lstSnippetUploads = answerData.getSnippetUploads();

		Set<Option> optList = new HashSet<>();

		for (Option opts : assessment.getOptions()){
			
			optList.add(optService.getOptionById(opts.getOptionId()));
			
		}

		assessment.setOptions(optList);
		for (AssessmentDragDrop addD : assessment.getAssessmentDragDrop()){
			for(AssessmentDragDrop addE : currAssessment.getAssessmentDragDrop()){
				if((addE.getDragDrop().getDragDropId())==addD.getDragDrop().getDragDropId()){
					addD.setAssessmentDragDropId(addE.getAssessmentDragDropId());
					break;
				}
			}
			
			addD.setDragDrop(ddService.getDragDropById(addD.getDragDrop().getDragDropId()));
		}

		if(lstSnippetUploads!=null) {

			for (SnippetUpload su : lstSnippetUploads) {
				String key = "";
				key += "Take1_userAnswer_";
				key += assessment.getAssessmentId() + "_";
				key += su.getQuestionId();
				
				switch(su.getFileType())
				{
				case "cpp": 
				case "c++":
					key += ".cpp"; 
					break;
		        case "c":
		        	key += ".c";
		        	break;
				case "cs":
					key += ".cs";
					break;
				case "java":
				default: 
					key += ".java"; 
					break;
				}
				
				s3.uploadToS3(su.getCode(), key);
				
				boolean bIsExists = false;
				for(FileUpload fUpload : currAssessment.getFileUpload()){
					if((fUpload.getFileUrl()).equals(key)){
						bIsExists=true;
					}
				}
				
				if(bIsExists==false){
					FileUpload fu = new FileUpload();
					fu.setAssessment(assessment);
					fu.setFileUrl(key);
					fu.setQuestion(questService.getQuestionById(su.getQuestionId()));
					assessment.getFileUpload().add(fu);
				}/*SA-CHANGES ENDED*/
			}
		}

		//SAVE the answers into the database (grader not called yet)
		service.updateAssessment(assessment);
		log.debug("GetAssessmentController.saveAssessmentAnswers: Assessment state should now be quick saved.");
		
		return "{\"success\":\"ok\"}";
	}
	
	// Landing page method to get candidate first & last name, time limit, and landing page dialog box
	@RequestMapping(value = "/landing/{id}")
	public Map<String, Object> getLanding(@PathVariable("id") String AssessmentId) throws IOException {
		Hashids otherHash = new Hashids();
		long[] hashIdnum = otherHash.decode(AssessmentId);
		log.debug("Requesting assessment with ID=" + AssessmentId);
		
		// Declaring a new assessment and map 
		Assessment assessment = new Assessment();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		try {
						
			assessment = service.getAssessmentById((int) hashIdnum[0]);
			
			// Get Date where password issued to user
			String strPassIssuedTime = assessment.getUser().getDatePassIssued();
			Timestamp expireDate = Timestamp.valueOf(strPassIssuedTime);
			
			// Calculating timestamp to expired date
			Calendar cal = Calendar.getInstance();
			cal.setTime(expireDate);
			cal.add(Calendar.DAY_OF_WEEK, 7); // Add a week to the date to reach expired time
			expireDate = new Timestamp(cal.getTime().getTime());
			
			//Getting the landing page dialog box from file
			Properties properties = propertyReader.propertyRead("landingPage.properties");
			
			String landingPageScript = properties.getProperty("landing");
			String landingPageScript2 = properties.getProperty("continue");
			
			//Formatting the landing page script to enter in the time limit of assessment
			landingPageScript = formatMessage(landingPageScript, assessment.getTimeLimit());
			
			if (expireDate.after(new Timestamp(System.currentTimeMillis())))
			{	// Allow assessment (expiration date not yet reached)
				// Check to see if the user has already taken this assessment
				if (assessment.getGrade() < 0)
				{	// Assessment not taken yet
					if (assessment.getCreatedTimeStamp() == null)
					{
						responseMap.put("firstName", assessment.getUser().getFirstName());
						responseMap.put("lastName", assessment.getUser().getLastName());
						responseMap.put("landingScript", landingPageScript);
						responseMap.put("timestamp", assessment.getCreatedTimeStamp());
						responseMap.put("msg", "allow");
						System.out.println("my asmt: " + assessment);
						
					}else {
						responseMap.put("timeLimit", assessment.getTimeLimit());
						//System.out.println("timeLimit " + assessment.getTimeLimit()+"\n\n\n\n\n");
						Timestamp serverNowTime = new Timestamp(System.currentTimeMillis());
						long serverNowTimeInMillis = serverNowTime.getTime();
						long createdTimestampInMillis = assessment.getCreatedTimeStamp().getTime();
						
						long modifiedTimelimit = (serverNowTimeInMillis/1000) - (createdTimestampInMillis/1000);
						modifiedTimelimit = ((assessment.getTimeLimit()*60) - modifiedTimelimit) / 60;
						
						if (modifiedTimelimit < 0)
						{
							responseMap.put("msg", "deny");
						} else {
							
							responseMap.put("firstName", assessment.getUser().getFirstName());
							responseMap.put("lastName", assessment.getUser().getLastName());
							responseMap.put("landingScript", landingPageScript2);
							responseMap.put("timestamp", assessment.getCreatedTimeStamp());
							responseMap.put("msg", "allow");
						}
					}
					
				} else {
					// Assessment taken, this message will redirect to expired page
					responseMap.put("msg", "deny");
				}
				
			} else {
				// Expiration date passed (deny assessment)
				responseMap.put("msg", "deny");
			}
			
		}catch(NullPointerException e)
		{
			log.error(Logging.errorMsg("\nin class: GetAssessmentController\nin method: getAssessment \nexcept", e));
		}
	
		// Returns a hashMap object with allow message and assessment object
		// which is automatically converted into JSON objects
		return responseMap;
	}
	
	// Function that formats the string and inserts
	// the assessment time into the landing page dialog box
	private String formatMessage(String prompt, int time) {

        StringBuilder message = new StringBuilder(prompt);

        for (int i = 0; i < message.length(); i++) {

        	// Searches for % in the landingPage string and deletes it
            if (message.charAt(i) == '%' && message.length() > i + 1) {

                message.deleteCharAt(i);

                switch (message.charAt(i)) {
                	// Searches for t after % in the landingPage string and deletes it
                    case 't':
                        message.deleteCharAt(i);
                        message.insert(i, time); //adds the time integer to string
                        break;
                   case '%':
                        break;
                }
            }
        }
        return message.toString();
    }
}
