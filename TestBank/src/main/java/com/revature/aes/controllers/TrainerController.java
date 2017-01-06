package com.revature.aes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

@Controller
public class TrainerController{
	
	@RequestMapping(value="/trainer", method=RequestMethod.GET)
	public String getIndex(){
		return "redirect:resources/pages/trainerHome.html";
	}
}

