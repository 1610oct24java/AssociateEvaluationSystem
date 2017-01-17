package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Template;
import com.revature.aes.dao.TemplateDAO;

@Service
public class TemplateServiceImpl implements TemplateService{

	@Autowired
	private TemplateDAO tDao;
	
	@Override
	public Template addTemplate(Template tmpl) {
		return tDao.save(tmpl);
		
	}

}
