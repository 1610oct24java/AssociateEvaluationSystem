/****************************************************************
 * Project Name: Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/
package com.revature.aes.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer>
{
	
	public List<Question> findAllQuestionsByFormat(Format format);
	public List<Question> findByCategories(List<Category> category);
	public List<Question> findByFormatAndCategories(Format format, String category);
	public List<Question> findAllQuestions();

}
