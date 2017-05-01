package com.revature.aes.service;

import com.revature.aes.beans.SnippetTemplate;

public interface SnippetTemplateService
{
	public SnippetTemplate addSnippetTemplate(SnippetTemplate snippetTemplate);
	public void removeSnippetTemplate(int id);
	public SnippetTemplate getSnippetTemplate(int id);
}
