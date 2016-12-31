package com.revature.aes.rest.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.revature.aes.core.CSVParser;
import com.revature.aes.core.TestCategory;

@Controller
public class TestController
{
	@Autowired
	private CSVParser csv;
	
	@Autowired
	private TestCategory cat;
	
	@RequestMapping(path="/test",method=RequestMethod.GET)
	public String testParse(ModelMap map, HttpSession session)
	{
		//cat.testAddCategory();
		cat.testDeleteCategory();
		//System.out.println("get questions by category!");
		//cat.testGetQuestionsByCategory();
		//csv.parseCSV(session.getServletContext().getRealPath("/")+"test.csv");
		return "resources/pages/RevatureTemplate.html";
	}
}
