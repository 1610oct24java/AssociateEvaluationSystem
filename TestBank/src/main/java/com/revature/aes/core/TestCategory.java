package com.revature.aes.core;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Question;
import com.revature.aes.services.QuestionService;

@Component
public class TestCategory {

	@Autowired
	private QuestionService service;
	
	public void testGetQuestionsByCategory(){
		Category category = new Category();
		category.setCategoryId(1);
		category.setName("Java");
		Set<Question> questions = service.getAllQuestionsByCategory(category);
		System.out.println("questions!");
		for (Question question : questions){
			System.out.print(question.getQuestionId());
			System.out.println(": " + question.getQuestionText());
		}
	}
}
