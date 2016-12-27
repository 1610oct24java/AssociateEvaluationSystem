/****************************************************************
 * Project Name: Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/
package com.revature.aes.services;

import java.util.List;

import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;

public interface QuestionService
{
	public Question addQuestion(Question question);
	
	public Question getQuestionById(Integer id);
	public List<Question> getAllQuestions();
	public List<Question> getAllQuestionsByFormat(Format format);
	
	public Question updateQuestionById(Question question);
	
	public void deleteQuestionById(Integer id);
}
