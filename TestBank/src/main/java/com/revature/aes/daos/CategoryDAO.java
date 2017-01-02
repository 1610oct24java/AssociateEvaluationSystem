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
package com.revature.aes.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;

@Repository("categoryDao")
public interface CategoryDAO extends JpaRepository<Category, Integer>{
	
	@Query(value="insert into aes_question_category(question_id, category_id) values (?1, ?2)", nativeQuery=true)
	public void insertLinkToQuestion(Integer questionId, Integer categoryId);
	public void deleteCategoryByName(String name);
}