package com.revature.aes.restcontroller;

import static org.mockito.Matchers.anySetOf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.DragDrop;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.QuestionOptionsJSONHandler;
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
	 * @param questionId the unique identifier of a Question.  Cannot be null or Negative and must be
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
	 * @param id	The id of the option for a drag and drop question to be 
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
	 * @param id the Id of question, cannot be null or less than 0
	 * @return the updated Question 
	 */
	@RequestMapping(value ="question", method = RequestMethod.PUT, produces = 
	{ MediaType.APPLICATION_JSON_VALUE })
	public Question updateQuestionById(@RequestBody Question question)
	{
		for (Option o : question.getOption())
		{
			o.setQuestion(question);
		}
		for (DragDrop d : question.getDragdrop())
		{
			d.setQuestion(question);
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
	public Question addOption(@RequestBody String optionText, @PathVariable Integer questionId){
		
		Question question = questionService.getQuestionById(questionId);
		Option option = new Option();
		option.setOptionText(optionText);
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
	
	@RequestMapping(value="question/changeCorrect", method = RequestMethod.POST, produces=
			{MediaType.APPLICATION_JSON_VALUE})
	public Option changeCorrect(@RequestBody Option option){
		if(option.getCorrect()==1){
			option.setCorrect(0);
		}
		else{
			option.setCorrect(1);
		}
		return option;
	}
	
}
