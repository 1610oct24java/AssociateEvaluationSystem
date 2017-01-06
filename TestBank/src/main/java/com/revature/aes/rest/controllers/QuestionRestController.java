package com.revature.aes.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.QuestionOptionsJSONHandler;
import com.revature.aes.services.QuestionService;

/**
 * The Rest Controller that handles HTTP Requests and Response for the Question
 * object.
 */
@RestController
public class QuestionRestController
{
	/**
	 * @questionService The service used to handle HTTPs Requests and Response
	 *                  for the Question object.
	 */
	@Autowired
	private QuestionService questionService;

	/**
	 * Stores a Question into a database
	 * 
	 * @param question the Question to be persisted into the database
	 * @return a List of Questions
	 * 
	 */
	@RequestMapping(value = "question", method = RequestMethod.POST, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public Question addQuestion(@RequestBody Question question)
	{
		return questionService.addQuestion(question);
	}

	/**
	 * Retrieves a Question from the Database
	 *
	 * @param questionId the unique identifier of a Question.  Cannot be null or Negative and must be
	 * @return a Question
	 * a Integer
	 */
	@RequestMapping(value = "question/{questionId}", method = RequestMethod.GET, produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	public Question getQuestionById(@PathVariable Integer questionId)
	{
		return questionService.getQuestionById(questionId);
	}

	/**
	 * Retrieves all Questions from the database
	 * 
	 * @return The List of All Questions
	 */
	@RequestMapping(value = "question", method = RequestMethod.GET, produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	public List<Question> getAllQuestions()
	{
		return questionService.getAllQuestions();
	}
	
	/**
	 * Retrieves all Questions by Format form the database
	 * 
	 * @param format the format of a question
	 * @return a List of Questions by Format
	 */
	@RequestMapping(value = "question/{format}", method = RequestMethod.GET, produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	public List<Question> getAllQuestionsByFormat(@PathVariable Format format )
	{
		return questionService.getAllQuestionsByFormat(format);
	}
	
	/**
	 * Modifies the a question in the database by its unique identifier
	 * 
	 * @param id the Id of question, cannot be null or less than 0
	 * @return the updated Question 
	 */
	@RequestMapping(value ="question", method = RequestMethod.PUT, produces = 
	{ MediaType.APPLICATION_JSON_VALUE })
	public Question updateQuestionById(@RequestBody Question question)
	{
		return questionService.updateQuestion(question);		
	}
	
	
	/**
	 * Deletes a question from the database
	 * 
	 * @param id the unique identifier of a question, cannot be null or less than 0
	 */
	@RequestMapping(value ="question/{id}", method = RequestMethod.DELETE)
	public void deleteQuestionById(@PathVariable Integer id)
	{
		questionService.deleteQuestionById(id);
	}
	
	@RequestMapping(value ="fullQuestion", method = RequestMethod.POST, produces = 
		{ MediaType.APPLICATION_JSON_VALUE })
	public Question addFullQuestion(@RequestBody QuestionOptionsJSONHandler question ){
		return questionService.addFullQuestion(question);
	}
}
