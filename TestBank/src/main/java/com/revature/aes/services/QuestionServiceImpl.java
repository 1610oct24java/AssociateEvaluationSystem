/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database. Use to evaluate associates
 * 		performance both during and before employment with Revature 
 * 		LLC.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/

package com.revature.aes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;
import com.revature.aes.daos.QuestionDAO;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService
{
	@Autowired
	private QuestionDAO qdao;

	/**
	 * Adds a Question to the Database
	 * @param The Question to be persisted to the database 
	 * @return the Object returned after persisting to a database
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Question addQuestion(Question question)
	{
		return qdao.save(question);
	}

	/**
	 * Retrieves a Question from the database 
	 * @param The unique identifier of a Question to be retrieved to the database, cannot be null 
	 * @return the Object returned from the database
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Question getQuestionById(Integer id)
	{
		return qdao.findOne(id);
	}

	/**
	 * Retrieves all Questions from the database
	 * @return A List of Questions
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Question> getAllQuestions()
	{
		return qdao.findAll();
	}
	
	
	/**
	 * Retrieves all Questions by a specific format from a database
	 * @param the Format to determine the restriction type of SQL query
	 * @return A List of Questions restricted to a specific format
	 * 
	 * @see com.revature.aes.beans.Format
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Question> getAllQuestionsByFormat(Format format)
	{
		return qdao.findAllQuestionsByFormat(format);
	}

	/**
	 * Updates a Question which is stored in a database
	 * @param an Updated Question value
	 * @return the Updated object after being persisted in a database
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Question updateQuestion(Question question)
	{
		return qdao.save(question);
	}
	
	/**
	 * Deletes a question based off an the unique identifier for the question.
	 * @param the unique identifier of a question.
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteQuestionById(Integer id)
	{
		qdao.delete(qdao.findOne(id)); 	
	}


}
