package com.revature.aes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.aes.services.QuestionService;
import com.revature.aes.services.QuestionServiceImpl;

@Controller
public class TrainerController
{
	@Autowired
	private QuestionService qService;
	
	/**
	 * Controller mapping to handle parsing Aiken files.
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/parseAiken", method=RequestMethod.POST)
	public @ResponseBody String parseAikenFile(@RequestBody String file){
		qService.getClass();
		return null;
	}
	
	@RequestMapping(value="/trainer", method=RequestMethod.GET)
	public String getIndex(){
		return "redirect:resources/pages/trainerHome.html";
	}
}

