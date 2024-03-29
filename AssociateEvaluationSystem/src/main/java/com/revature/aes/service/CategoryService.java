package com.revature.aes.service;

import java.util.List;

import com.revature.aes.beans.Category;

public interface CategoryService {
	public void addCategory(Category category);
	public Category getCategoryById(Integer id);
	public Category getCategoryByName(String name);
	public List<Category> getAllCategory();
	public void deleteCategoryById(Integer id);
	public void deleteCategory(Category category);
	public void deleteCategoryByName(String name);
}