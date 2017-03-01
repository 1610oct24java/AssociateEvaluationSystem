package com.revature.aes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.dao.TemplateQuestionDAO;

@Service
public class TemplateQuestionServiceImpl implements TemplateQuestionService{

	@Autowired
	private TemplateQuestionDAO tqDao;
	
	@Override
	public List<TemplateQuestion> addTemplateQuestion(List<TemplateQuestion> tq) {
		
		return tqDao.save(tq);
	}

}
