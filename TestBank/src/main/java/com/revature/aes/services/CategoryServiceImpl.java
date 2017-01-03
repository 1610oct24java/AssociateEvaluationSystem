package com.revature.aes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Category;
import com.revature.aes.daos.CategoryDAO;

@Service("CategoryServiceImpl")
@Transactional
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	@Qualifier("categoryDao")
	private CategoryDAO cdao;

	@Override
	public void addCategory(Category category) {
		cdao.save(category);
	}

	@Override
	public Category getCategoryById(Integer id) {
		return cdao.findOne(id);
	}

	@Override
	public List<Category> getAllCategory() {
		return cdao.findAll();
	}

	@Override
	public void deleteCategoryById(Integer id) {
		cdao.delete(id);
	}

	@Override
	public void deleteCategory(Category category) {
		cdao.delete(category);
	}

	@Override
	public void deleteCategoryByName(String name) {
		cdao.deleteCategoryByName(name);
	}
}
