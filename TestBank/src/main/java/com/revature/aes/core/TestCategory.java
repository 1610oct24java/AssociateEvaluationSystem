package com.revature.aes.core;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Question;
import com.revature.aes.services.CategoryService;
import com.revature.aes.services.QuestionService;

@Component
public class TestCategory {

	@Autowired
	private QuestionService qservice;
	
	@Autowired
	private CategoryService cservice;
	
	public void testGetQuestionsByCategory(){
		Category category = new Category();
		category.setCategoryId(1);
		category.setName("Java");
		Set<Question> questions = qservice.getAllQuestionsByCategory(category);
		System.out.println("questions!");
		for (Question question : questions){
			System.out.print(question.getQuestionId());
			System.out.println(": " + question.getQuestionText());
		}
	}
	
	public void testAddCategory(){
		Category category = new Category();
		category.setName("Python");
		
		cservice.addCategory(category);
	}
	
	public void testDeleteCategory(){
		cservice.deleteCategoryById(-45);
		
		Category category = new Category();
		category.setName("Python");
		
		cservice.addCategory(category);
		cservice.deleteCategory(category);
	}

}