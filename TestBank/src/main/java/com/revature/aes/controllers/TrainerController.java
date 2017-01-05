package com.revature.aes.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.stereotype.Controller;

import org.springframework.web.multipart.MultipartFile;

import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.core.AikenParser;


@Controller
public class TrainerController
{

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

