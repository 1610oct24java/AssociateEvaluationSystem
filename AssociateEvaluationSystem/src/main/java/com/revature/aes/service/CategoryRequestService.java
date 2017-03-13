package com.revature.aes.service;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.CategoryRequest;

public interface CategoryRequestService {

	public CategoryRequest getCategoryRequestByCategory(Category cat);
	
	public void saveCategoryRequest(CategoryRequest catReq);
	
}
