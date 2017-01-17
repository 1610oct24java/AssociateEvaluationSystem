package com.revature.aes.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.exception.AikenSyntaxException;
import com.revature.aes.exception.InvalidFileTypeException;
import com.revature.aes.parsers.AikenParser;
import com.revature.aes.parsers.CSVParser;

@Component
public class AssessmentGenService {
	private MultipartFile file;
	
	@Autowired
	private QuestionService service;
	
	@Autowired
	private AikenParser aikenParser;
	
	@Autowired
	private CSVParser csvParser;
	
	public AssessmentGenService() {
		super();
	}

	public AssessmentGenService(MultipartFile file, QuestionService service) {
		super();
		this.file = file;
		this.service = service;
	}
	
	public AssessmentGenService(MultipartFile file){
		this.file = file;
	}
	
	public void uploadAikenFile() throws InvalidFileTypeException, AikenSyntaxException,IOException {
		try {
			aikenParser.parseFile(file);
			// redundant exception invalid file type 
		} catch (InvalidFileTypeException | AikenSyntaxException e) {
			throw e;
			
		} catch (IOException e) {
			throw e;
			
		}
		HashMap<Question, Set<Option>> questionsMap = (HashMap<Question, Set<Option>>) aikenParser.getQuestionsMap();
		
		for(Question q : questionsMap.keySet()){
			// These two lines actually persist the question to the database...
			System.out.println("Ass Question: " + q);
			q.setOption(questionsMap.get(q));
	    	
	    	service.addQuestion(q);
		}
	}
	
	public void uploadCSVFile() throws IOException {
			csvParser.parseCSV(file);	
	}
	
	public void uploadCodeSnippet(){
		//Implement in later sprints, jk.
		
	}


	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public QuestionService getService() {
		return service;
	}

	public void setService(QuestionService service) {
		this.service = service;
	}

}
