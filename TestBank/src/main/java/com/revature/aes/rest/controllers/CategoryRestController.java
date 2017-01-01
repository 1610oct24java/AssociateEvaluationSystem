package com.revature.aes.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Category;
import com.revature.aes.services.CategoryService;

@RestController
public class CategoryRestController {
	
	@Autowired
	CategoryService service;
	
	@RequestMapping(value="category", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Category> getCategories(){
		return service.getAllCategory();
	}
}
