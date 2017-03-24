package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.SnippetTemplate;
import com.revature.aes.dao.SnippetTemplateDAO;

@Service
public class SnippetTemplateServiceImpl implements SnippetTemplateService
{
	@Autowired
	SnippetTemplateDAO stDao;
	
	
	@Override
	public SnippetTemplate addSnippetTemplate(SnippetTemplate snippetTemplate)
	{		
		return stDao.save(snippetTemplate);
	}

	@Override
	public void removeSnippetTemplate(int id)
	{
		stDao.delete(id);
	}

	@Override
	public SnippetTemplate getSnippetTemplate(int id)
	{
		// TODO Auto-generated method stub
		return stDao.findOne(id);
	}

}
