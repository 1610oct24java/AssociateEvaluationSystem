package com.revature.aes.core;

import java.util.List;

import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;
import com.revature.aes.daos.QuestionDAO;

public class SystemTemplate {
	
	List<Question> AssessList = null;
	QuestionDAO qDao = null;
	
	/*
	 * List of questions that will make up the test. Take in an amount of questions, a format, and a category, and add them to the list.
	 */
	public List<Question> randomSelectFromCategory(int amount, Format format, String category, List<Question> AssessList){
		
		List<Question> filteredQuestions = qDao.findByFormatAndCategory(format, category);
		int n =filteredQuestions.size();
		/*
		 * loop through all the questions a pick out 'amount' number of them.
		 */
		int randomizedId = 0;
		for(int i = 0;i<amount;i++)
			randomizedId = (int)(n*Math.random());
			AssessList.add(qDao.findOne(randomizedId));
		
		return AssessList;
	}

}
