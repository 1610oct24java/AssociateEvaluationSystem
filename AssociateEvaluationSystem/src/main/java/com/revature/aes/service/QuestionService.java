package com.revature.aes.service;

import java.math.BigDecimal;
import java.util.List;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.QuestionOptionsJSONHandler;

public interface QuestionService {
	public Question addQuestion(Question question);

	public Question getQuestionById(Integer id);

	public List<Question> getAllQuestions();

	public List<Question> getAllQuestionsByFormat(Format format);
	
	public Question updateQuestion(Question question);

	public List<Question> getAllQuestionsByCategory(Category category);
	
	public void deleteQuestionById(Integer id);

	public Question addFullQuestion(QuestionOptionsJSONHandler question);
	
	public Question getQuestionByOption(Option opt);
	
	public List<Question> findQuestionByQuestionId(int id);

	public List<BigDecimal> findIdsByFormatAndCategory(String category, String format);

}
