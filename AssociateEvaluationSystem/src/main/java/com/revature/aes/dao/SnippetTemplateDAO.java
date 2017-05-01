package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.aes.beans.SnippetTemplate;

public interface SnippetTemplateDAO extends JpaRepository<SnippetTemplate, Integer>
{

}
