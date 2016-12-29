package com.revature.aes.rest.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Question;
import com.revature.aes.core.CSVParser;

@RestController
public class CSVController {

	@Autowired
	private CSVParser csvparser;
	
	@RequestMapping(value="test", method=RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Question> getCSVData(){
		return csvparser.parseCSV("C:\\Users\\ed_cr\\AssociateEvaluationSystem\\TestBank\\test.csv");
	}
}
