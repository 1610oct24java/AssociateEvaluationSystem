package com.revature.aes.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Tag;
import com.revature.aes.services.TagService;

/**
 * The Rest Controller that handles HTTP Requests and Response for the Tag
 * object.
 */
@RestController
public class TagRestController {
	
	@Autowired
	TagService service;
	
	@RequestMapping(value="tag", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Tag> getCategories(){
		return service.getAllTags();
	}
}