package com.revature.aes.services;

import java.util.List;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;

public interface QuestionService
{
	public Question addQuestion(Question question);
	
	public Question getQuestionById(Integer id);
	public List<Question> getAllQuestions();
	public List<Question> getAllQuestionsByFormat(Format format);
	public Question getAllQuestionsByCategory(Category category);
	public List<Question> getAllQuestionsByFormatAndCategory(Format format, List<Category> category);
	
	public Question updateQuestionById(Question question);
	
	public void deleteQuestionById(Integer id);
}
