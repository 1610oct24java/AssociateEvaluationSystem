package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.CategoryRequest;
import com.revature.aes.dao.CategoryRequestDAO;

@Service("CategoryRequestServiceImpl")
public class CategoryRequestServiceImpl implements CategoryRequestService {

	@Autowired
	@Qualifier("categoryRequestDao")
	private CategoryRequestDAO catDao;
	 
	@Override
	public CategoryRequest getCategoryRequestByCategory(Category cat) {
		return catDao.getByCategory(cat);
	}

	@Override
	public void saveCategoryRequest(CategoryRequest catReq) {
		catDao.save(catReq);
		
	}

}
