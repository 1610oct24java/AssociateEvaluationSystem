package com.revature.aes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.daos.TemplateQuestionDAO;

@Service
public class TemplateQuestionServiceImpl implements TemplateQuestionService{

	@Autowired
	private TemplateQuestionDAO tqDao;
	
	@Override
	public List<TemplateQuestion> addTemplateQuestion(List<TemplateQuestion> tq) {
		
		return tqDao.save(tq);
	}

}
