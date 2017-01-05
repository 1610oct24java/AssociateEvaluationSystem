package com.revature.aes.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.fileTester.CodeTester;

@RestController
public class SnippetRestController
{
	@RequestMapping(value="submit", method=RequestMethod.POST)
	public boolean test(@RequestBody SnippetRequestObject sro){
		System.out.println(sro.getSnippet() + sro.getSolution());
		return new CodeTester().executeCodeTest(sro.getSolution(), sro.getSnippet());
	}
}