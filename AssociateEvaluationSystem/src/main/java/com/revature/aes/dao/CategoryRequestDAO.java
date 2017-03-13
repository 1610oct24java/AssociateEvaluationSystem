package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.CategoryRequest;
import com.revature.aes.beans.TemplateQuestion;

@Repository("categoryRequestDao")
public interface CategoryRequestDAO extends JpaRepository<CategoryRequest, Integer>{
	
	public CategoryRequest getByCategory(Category cat);

}
