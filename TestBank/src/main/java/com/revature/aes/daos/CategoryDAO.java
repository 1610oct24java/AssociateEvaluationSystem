<<<<<<< HEAD
package com.revature.aes.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;

@Repository("categoryDao")
public interface CategoryDAO extends JpaRepository<Category, Integer>{

	public Category getByName(String name);
	public void deleteCategoryByName(String name);
	

}
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
package com.revature.aes.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;

@Repository("categoryDao")
public interface CategoryDAO extends JpaRepository<Category, Integer>{

	public void deleteCategoryByName(String name);
}
>>>>>>> branch 'bank-dev' of https://github.com/1610oct24java/AssociateEvaluationSystem.git
