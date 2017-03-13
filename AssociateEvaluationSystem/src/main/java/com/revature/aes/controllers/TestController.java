package com.revature.aes.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.Option;
import com.revature.aes.dao.QuestionDAO;

@RestController
public class TestController
{
	@Autowired
	private QuestionDAO qDao;
	
	@RequestMapping(value = "/testQuestion", method = RequestMethod.GET)
	public List<Question> testQuestion()
	{
		Question q = new Question();
		List<Question> rQuestion;
		Set<Option> questionOptions = new HashSet<>();
		
		q.setQuestionText("What is a WSDL?");
		q.setFormat(new Format(1,"True/False"));
		q.addQuestionCategory(new Category(1,"Java"));

		questionOptions.add(new Option("Something",1,q));
		questionOptions.add(new Option("Something Else",0,q));
		
		q.setOption(questionOptions);
		
		qDao.save(q);
		
		rQuestion = qDao.findAll();
		
		return rQuestion;
	}
}
