package com.revature.aes.parsers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.exception.AikenSyntaxException;
import com.revature.aes.exception.InvalidFileTypeException;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.FormatService;

/**
 * A parser for the Moodle Aiken format. Generates an map of Questions and their Options.
 * 
 * Aiken Format Guide:
 * The Question must be all on one line. 
 * Each Option must start with a single uppercase letter, followed by a '.' or ')', then a space. 
 * The answer line must immediately follow, starting with "ANSWER: " and ending with the appropriate letter.
 * NOTE: The space after the colon.
 * NOTE: The answer letters (A,B,C etc.) and the word "ANSWER" must be capitalized as shown, otherwise the import will fail.
 * Note: Only one correct Answer can be made per Question using Aiken.
 * 
 * @author Connor Anderson
 */
@Service
public class AikenParser {
	private HashMap<Question, Set<Option>> questionMap;
	private String line;
	
	private FormatService formatService;
	
	/**
	 * Default constructor to please Spring.
	 */
	public AikenParser() {
		super();
	}
	
	@Autowired
	Logging log;
	
	@Autowired
	public AikenParser(FormatService fs) {
		this.formatService=fs;
		questionMap = new HashMap<>();
	}
	

	public void parseFile(MultipartFile mpFile) throws InvalidFileTypeException, AikenSyntaxException, IOException{
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(mpFile.getInputStream()))) {
			// Read first line of the file
		    line = br.readLine();
		    log.debug("Questions: " + line);
		    // Each loop is a new question being read
		    while (line != null) {
		    	Question question = setQuestion();
		    	
		    	Set<Option> optionsList = getOptionsList(br, question);
		    	setCorrectAnswer(optionsList);
		    	
		    	// Add the question and its options to the map
		    	questionMap.put(question, optionsList);
		    	
		    	// Reads an extra line to skip \n between questions
		    	line = br.readLine();

		    	if(line==null){
		    		break;
		    	}
		    	log.debug("Blank line " + line);
		    	if(line.length() != 0){
		    		throw new AikenSyntaxException("Questions must be seperated by a blank line!");
		    	}
		    	
		    	// Sets line to read next Question
		    	line = br.readLine();
		    	log.debug("next question: "+ line );
		    }
		}
	}

	
	/**
	 * Creates a Question object based on the current question being parsed.
	 * 
	 * @param br to continue parsing lines
	 * @return the Question generated from the parsed line
	 * @throws IOException
	 */
	private Question setQuestion() throws IOException{
		// First line will be a question
    	Question question = new Question();
    	question.setQuestionText(line);
    	question.setFormat(formatService.getFormatByName("Multiple Choice"));
    	return question;
	}
	
	/**
	 * Populates the optionsList with options for the current question being parsed.
	 * 
	 * @param br to continue reading lines
	 * @return	the list of options for the current question
	 * @throws IOException
	 */
	private Set<Option> getOptionsList(BufferedReader br, Question optionQuestion) throws IOException{	
    	
		line = br.readLine();
		log.debug("Line 112: " + line);
    	
		// Create new options list for each Question
		Set<Option> optionsList = new HashSet<Option>();
		
    	// Parse lines until "ANSWER:" to retrieve Options
    	while(!line.startsWith("ANSWER:")){
    		Option option = new Option();
    		option.setOptionText(line);
    		option.setOptionId(0);
    		option.setQuestion(optionQuestion);
    		optionsList.add(option);
    		
    		line = br.readLine();
    		log.debug("Options: " + line);
    	}
    	return  optionsList;
	}
	
	/**
	 * For a given Question, sets the appropriate Option as the correct answer.
	 * "ANSWER:" line indicates correct answer
	 */
	private void setCorrectAnswer(Set<Option> optionsList){
		// Gets the correct character from the "ANSWER" line
		Character correctLetter = line.trim().charAt(line.length()-1);
		
		for(Option option : optionsList){
			if(option.getOptionText().startsWith(correctLetter.toString())){
				option.setCorrect(1);
			}else{
				option.setCorrect(0);
			}
		}
	}
	
	/**
	 * Returns the HashMap of questions and options.
	 * 
	 * @return 	the map of questions and their options
	 * @see com.revature.aes.beans.Option
	 * @see com.revature.aes.beans.Question
	 */
	public Map<Question, Set<Option>> getQuestionsMap(){
		return questionMap;
	}

	public FormatService getFormatService() {
		return formatService;
	}

	public void setFormatService(FormatService formatService) {
		this.formatService = formatService;
	}
	
}