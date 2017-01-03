package com.revature.aes.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.core.AikenParser;
import com.revature.aes.services.QuestionService;
import com.revature.aes.services.QuestionServiceImpl;

@RestController
public class TrainerController
{
	@Autowired
	private QuestionService qService;
	
	@RequestMapping(value="/parseAiken", method=RequestMethod.POST)
	public void parseAikenFile(@RequestParam("file") MultipartFile file){
		
		AikenParser aikenParser = new AikenParser(file);
		HashMap<Question, ArrayList<Option>> questionsMap = (HashMap<Question, ArrayList<Option>>) aikenParser.getQuestionsMap();
		System.out.println("QUESTIONS: ");
		for(Question q : questionsMap.keySet()){
			System.out.println(q.toString());
		}
	}
	
	@RequestMapping(value="/trainer", method=RequestMethod.GET)
	public String getIndex(){
		return "redirect:resources/pages/trainerHome.html";
	}
}

