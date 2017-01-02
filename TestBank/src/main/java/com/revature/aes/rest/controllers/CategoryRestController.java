package com.revature.aes.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@RequestMapping(value="category", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public void saveCategory(@RequestBody Category category){
		service.addCategory(category);
	}
	
	@RequestMapping(value="category/{name}", method=RequestMethod.DELETE)
	public void deleteCategory(@PathVariable String name){
		service.deleteCategoryByName(name);
	}
}
