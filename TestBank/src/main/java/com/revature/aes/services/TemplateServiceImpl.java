package com.revature.aes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Template;
import com.revature.aes.daos.TemplateDAO;

@Service
public class TemplateServiceImpl implements TemplateService{

	@Autowired
	private TemplateDAO tDao;
	
	@Override
	public Template addTemplate(Template tmpl) {
		return tDao.save(tmpl);
		
	}

}
