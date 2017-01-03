package com.revature.aes.rest.controllers;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController
{
	@RequestMapping(path="/test",method=RequestMethod.GET)
	public String testParse(ModelMap map, HttpSession session)
	{
		return "resources/pages/RevatureTemplate.html";
	}
}
