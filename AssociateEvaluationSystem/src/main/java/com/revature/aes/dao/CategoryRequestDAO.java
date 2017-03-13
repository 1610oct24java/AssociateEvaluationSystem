package com.revature.aes.dao;

import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.CategoryRequest;

@Repository("categoryRequestDao")
public interface CategoryRequestDAO {
	
	public CategoryRequest getByCategory(Category cat);

}
