package com.revature.aes.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;

@Repository("categoryDao")
public interface CategoryDAO extends JpaRepository<Category, Integer>{

	public Category getByName(String name);
	public void deleteCategoryByName(String name);
	
}