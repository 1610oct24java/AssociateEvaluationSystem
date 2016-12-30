package com.revature.aes.rest.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.revature.aes.core.CSVParser;

@Controller
public class TestController
{
	@Autowired
	private CSVParser csv;
	
	@RequestMapping(path="/test",method=RequestMethod.GET)
	public String testParse(ModelMap map, HttpSession session)
	{
		csv.parseCSV(session.getServletContext().getRealPath("/")+"test.csv");
		return "resources/pages/RevatureTemplate.html";
	}
}
