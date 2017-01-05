package com.revature.aes.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Template;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.daos.TemplateDAO;

@Service
public class TemplateServiceImpl implements TemplateService{

	@Autowired
	private TemplateDAO tDao;
	@Autowired
	private TemplateQuestionService qService;
	
	@Override
	public Template addTemplate(Template tmpl) {
		/*Set<TemplateQuestion> tSet;
		List<TemplateQuestion> tList = new ArrayList<TemplateQuestion>();
		tSet = tmpl.getTemplateQuestion();
		tList.addAll(tSet);
		tList = qService.addTemplateQuestion(tList);
		tSet = new HashSet<TemplateQuestion>();
		tSet.addAll(tList);
		tmpl.setTemplateQuestion(tSet);*/
		return tDao.save(tmpl);
		
	}

}
