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

import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.QuestionOptionsJSONHandler;

public interface QuestionService {
	public Question addQuestion(Question question);

	public Question getQuestionById(Integer id);

	public List<Question> getAllQuestions();

	public List<Question> getAllQuestionsByFormat(Format format);

	public Question updateQuestion(Question question);

	public void deleteQuestionById(Integer id);

	public Question addFullQuestion(QuestionOptionsJSONHandler question);

}
