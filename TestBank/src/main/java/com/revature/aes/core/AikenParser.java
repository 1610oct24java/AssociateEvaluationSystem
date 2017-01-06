package com.revature.aes.core;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.exception.AikenSyntaxException;
import com.revature.aes.exception.InvalidFileTypeException;

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
	private HashMap<Question, ArrayList<Option>> questionMap;
	private String line;
	
	/**
	 * Default constructor to please Spring.
	 */
	public AikenParser() {
		super();
	}
	
	/**
	 * Constructor takes in a String representing the path to the Aiken file relative to the root to parse Questions and Options.
	 * @param url of the file to be parsed
	 */
	public AikenParser(MultipartFile mpFile){			
		Map<Question, ArrayList<Option>> questionMap = new HashMap<>();
		try {
			parseFile(mpFile);
		} catch (InvalidFileTypeException e) {
			e.printStackTrace();
		} catch (AikenSyntaxException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Parses an Aiken formatted text file for Questions and Options. 
	 * @param url String of the file to be parsed
	 * @throws InvalidFileTypeException 
	 * @throws AikenSyntaxException 
	 */
	private void parseFile(MultipartFile mpFile) throws InvalidFileTypeException, AikenSyntaxException{
		//checkFileType(mpFile);
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(mpFile.getInputStream()))) {
			// Read first line of the file
		    line = br.readLine();

		    // Each loop is a new question being read
		    while (line != null) {
		    	Question question = getQuestion();
		    	ArrayList<Option> optionsList = getOptionsList(br);
		    	
		    	setCorrectAnswer(optionsList);
		    	// Add the question and its options to the map
		    	questionMap.put(question, optionsList);

		    	// Reads an extra line to skip \n between questions
		    	line = br.readLine();
		    	if(line==null){
		    		break;
		    	}
		    	if(line.length() != 0){
		    		throw new AikenSyntaxException("Questions must be seperated by a blank line!");
		    	}
		    	
		    	// Sets line to read next Question
		    	line = br.readLine();
		    }
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Checks if the file type is of type .txt and throws an exception if not.
	 * @param url String of the file to be parsed
	 * @throws InvalidFileTypeException
	 */
	private void checkFileType(String url) throws InvalidFileTypeException{

		if(!url.endsWith(".txt")){
			throw new InvalidFileTypeException("Aiken files must be of type \".txt\" ");
		}
	}
	
	/**
	 * Creates a Question object based on the current question being parsed.
	 * 
	 * @param br to continue parsing lines
	 * @return the Question generated from the parsed line
	 * @throws IOException
	 */
	private Question getQuestion() throws IOException{
		// First line will be a question
    	Question question = new Question();
    	question.setQuestionText(line);
    	return question;
	}
	
	/**
	 * Populates the optionsList with options for the current question being parsed.
	 * 
	 * @param br to continue reading lines
	 * @return	the list of options for the current question
	 * @throws IOException
	 */
	private ArrayList<Option> getOptionsList(BufferedReader br) throws IOException{	
    	
		line = br.readLine();
    	
		// Create new options list for each Question
		ArrayList<Option> optionsList = new ArrayList<>();
		
    	line = br.readLine();
    			
    	// Parse lines until "ANSWER:" to retrieve Options
    	while(!line.startsWith("ANSWER:")){
    		Option option = new Option();
    		option.setOptionText(line);
    		optionsList.add(option);
    		
    		String optionString = "Option: " + option.getOptionText();
    		System.out.println(optionString);
    		
    		line = br.readLine();
    	}
    	return  optionsList;
	}
	
	/**
	 * For a given Question, sets the appropriate Option as the correct answer.
	 * "ANSWER:" line indicates correct answer
	 */
	private void setCorrectAnswer(ArrayList<Option> optionsList){
		// Gets the correct character from the "ANSWER" line
		Character correctLetter = line.trim().charAt(line.length()-1);
		System.out.println("Correct Option: " + correctLetter);
		
		for(Option option : optionsList){
			if(option.getOptionText().startsWith(correctLetter.toString())){
				option.setCorrect(1);
				System.out.println("Correct Option is: " + option.getOptionText());
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
	public Map<Question, ArrayList<Option>> getQuestionsMap(){
		return questionMap;
	}
}
