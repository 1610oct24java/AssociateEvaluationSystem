<<<<<<< HEAD
=======
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

>>>>>>> branch 'bank-dev' of https://github.com/1610oct24java/AssociateEvaluationSystem.git
package com.revature.aes.daos;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;

@Repository("questionDao")
public interface QuestionDAO extends JpaRepository<Question, Integer>{
	public List<Question> findAllQuestionsByFormat(Format format);
<<<<<<< HEAD
	public List<Question> findCategoryByName(String name);
	public Set<Question> findAllQuestionsByCategory(Category category); 
	public Question updateQuestionById(Integer id);

=======
	public Set<Question> findAllQuestionsByCategory(Category category);
	public Question updateQuestionById(Integer id);
>>>>>>> branch 'bank-dev' of https://github.com/1610oct24java/AssociateEvaluationSystem.git
}
