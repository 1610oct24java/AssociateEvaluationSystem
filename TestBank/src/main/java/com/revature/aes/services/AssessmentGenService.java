package com.revature.aes.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.core.AikenParser;
import com.revature.aes.core.CSVParser;
import com.revature.aes.exception.AikenSyntaxException;
import com.revature.aes.exception.InvalidFileTypeException;

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
	
	public void uploadAikenFile(){
		try {
			aikenParser.parseFile(file);
			// redundant exception invalid file type 
		} catch (InvalidFileTypeException | AikenSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashMap<Question, ArrayList<Option>> questionsMap = (HashMap<Question, ArrayList<Option>>) aikenParser.getQuestionsMap();
		
		for(Question q : questionsMap.keySet()){
			// These two lines actually persist the question to the database...
			q.setMultiChoice(questionsMap.get(q));
	    	
	    	service.addQuestion(q);
		}
	}
	
	public void uploadCSVFile() throws IOException {
			csvParser.parseCSV(file);	
	}
	
	public void uploadCodeSnippet(){
		
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
