package com.revature.aes.core;

import java.util.List;

import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;
import com.revature.aes.daos.QuestionDAO;

public class SystemTemplate {
	
	private QuestionDAO qDao;

	/**
	 * List of questions that will make up the test. Take in an amount of questions, a format, and a category, and add them to the list.
	 * 
	 * @param amount: The number of questions that is being requested.
	 * @param format: The format of the questions being requested.
	 * @param category: The category of the question that is being requested.
	 * @param AssessList: The either empty, or partially filled list of questions for an assessment. 
	 * @return The same list that is taken in, but with more questions added to it.
	 */
	public List<Question> getRandomSelectionFromCategory(int amount, Format format, List category, List<Question> AssessList){
		
		//set instead of list
		List<Question> filteredQuestions = qDao.findByFormatAndCategory(format, category);
		int n =filteredQuestions.size();
		/*
		 * loop through all the questions a pick out 'amount' number of them.
		 */
		int randomizedId = 0;
		for(int i = 0;i<amount;i++)
			randomizedId = (int)(n*Math.random());
			AssessList.add(filteredQuestions.get(randomizedId));
		
		return AssessList;
	}

}
