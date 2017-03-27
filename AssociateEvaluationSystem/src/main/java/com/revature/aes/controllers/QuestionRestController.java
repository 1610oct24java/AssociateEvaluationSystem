package com.revature.aes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.revature.aes.beans.*;
import org.junit.experimental.categories.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.logging.Logging;
import com.revature.aes.service.DragDropService;
import com.revature.aes.service.OptionService;
import com.revature.aes.service.QuestionService;

/**
 * The Rest Controller that handles HTTP Requests and Response for the Question
 * object.
 */
@RestController
public class QuestionRestController
{
	
	@Autowired
	Logging log;
	
	/**
	 * @questionService The service used to handle HTTPs Requests and Response
	 *                  for the Question object.
	 */
	@Autowired
	private QuestionService questionService;
	
	/**
	 * @questionService The service used to handle HTTPs Requests and Responses
	 * 					for the Question's Options.
	 */
	@Autowired
	private OptionService optionService;
	
	/**
	 * @ddService 		The service used to handle HTTPs Requests and Responses
	 * 					for the Drag and Drop Options
	 */
	@Autowired
	private DragDropService ddService;

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
	 * @param optionId the unique identifier of a Question.  Cannot be null or Negative and must be
	 * @return a Question
	 * a Integer
	 */
	@RequestMapping(value = "question/refresh/{optionId}", method = RequestMethod.POST, produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	public Question getQuestionByOptionId(@PathVariable Integer optionId, @RequestBody Integer questionId)
	{
		optionService.removeOptionById(optionId);
		Question question = questionService.getQuestionById(questionId);
		return question;
	}
	
	/**
	 * 
	 * @param dragDropId	The id of the option for a drag and drop question to be
	 * 				deleted.
	 */
	@RequestMapping(value="question/deleteDragDrop/{dragDropId}", method = RequestMethod.POST, produces = 
			{MediaType.APPLICATION_JSON_VALUE})
	public Question deleteDragDropById(@PathVariable Integer dragDropId, @RequestBody Integer questionId){
		ddService.removeDragDropById(dragDropId);
		Question question = questionService.getQuestionById(questionId);
		return question;
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
	 * @param question the Id of question, cannot be null or less than 0
	 * @return the updated Question 
	 */
	@RequestMapping(value ="question", method = RequestMethod.PUT, produces = 
	{ MediaType.APPLICATION_JSON_VALUE })
	public Question updateQuestionById(@RequestBody Question question)
	{
		if (question.getOption() != null){
			for (Option o : question.getOption()) {
				o.setQuestion(question);
			}
		}

		if(question.getDragdrop() != null){
			for (DragDrop d : question.getDragdrop()) {
				d.setQuestion(question);
			}
		}
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
	
	/**
	 * 
	 * @param question This is a method that Ed did to add questions.
	 * 					I think it is done this way to add the options, category, and 
	 * 					format of the question correctly.
	 * @return
	 */
	@RequestMapping(value ="fullQuestion", method = RequestMethod.POST, produces = 
		{ MediaType.APPLICATION_JSON_VALUE })
	public Question addFullQuestion(@RequestBody QuestionOptionsJSONHandler question ){
		return questionService.addFullQuestion(question);
	}
	
	
	@RequestMapping(value="question/addOption/{questionId}", method = RequestMethod.POST, produces = 
			{ MediaType.APPLICATION_JSON_VALUE })
	public Question addOption(@RequestBody Option opt, @PathVariable Integer questionId){
		
		Question question = questionService.getQuestionById(questionId);
		Option option = new Option();
		option.setOptionText(opt.getOptionText());
		option.setCorrect(opt.getCorrect());
		option.setQuestion(question);
		optionService.addOption(option);
		question.getOption().add(option);
		return question;
	}
	
	@RequestMapping(value="question/addDragDrop/{questionId}", method = RequestMethod.POST, produces = 
		{ MediaType.APPLICATION_JSON_VALUE })
	public Question addDragDrop(@RequestBody String dragDropText, @PathVariable Integer questionId){
		Question question = questionService.getQuestionById(questionId);
		DragDrop dragdrop = new DragDrop();
		dragdrop.setDragDropText(dragDropText);
		dragdrop.setQuestion(question);
		ddService.addDragDrop(dragdrop);
		question.getDragdrop().add(dragdrop);
		return question;	
	}
	
	@RequestMapping(value="question/mcCorrect/{questionId}", method = RequestMethod.POST, produces = 
		{MediaType.APPLICATION_JSON_VALUE })
	public Question mcReset(@RequestBody Integer optionId, @PathVariable Integer questionId){
		Question question = questionService.getQuestionById(questionId);
		Option option = optionService.getOptionById(optionId);
		Set<Option> options = question.getOption();
		for(Option opt : options){
			if(opt.equals(option)){
				opt.setCorrect(1);
			}else{
			opt.setCorrect(0);
			}
		}
		question.setOption(options);
		return questionService.updateQuestion(question);
	}
	
	@RequestMapping(value="question/changeCorrect/{optionId}", method = RequestMethod.POST, produces=
			{MediaType.APPLICATION_JSON_VALUE})
	public Question changeCorrect(@PathVariable Integer optionId){
		Option option = optionService.getOptionById(optionId);
		if(option.getCorrect() == 1){
			option.setCorrect(0);
			optionService.addOption(option);
		}
		else{
			option.setCorrect(1);
			optionService.addOption(option);
		}
		return option.getQuestion();
	}
	
	@RequestMapping(value="question/{category}/count", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody int getQuestionCount(@PathVariable String category, @RequestBody String format) {
		return questionService.findQuestionCountByFormatandCategory(category, format).intValue();	
	}

	@RequestMapping(value = "questions", method = RequestMethod.POST)
	public List<Question> createQuestions(@RequestBody List<Question> questions){

		questions.forEach(question -> {
			final Set<Option> options = question.getOption();
			final Set<Category> cats = question.getQuestionCategory();
			question.setOption(null);
			question.setQuestionCategory(null);
			question = questionService.addQuestion(question);
			for(Option option : options){
				option.setQuestion(question);
			}
			question.setOption(options);
			question.setQuestionCategory(cats);
			question = questionService.updateQuestion(question);
			System.out.println(question);
		});

		return questions;
	}
}
