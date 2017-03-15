package com.revature.aes.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.CategoryRequest;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.dao.CategoryDAO;
import com.revature.aes.dao.QuestionDAO;
import com.revature.aes.logging.Logging;

@Component
public class SystemTemplate {

	@Autowired
	Logging log;
	
	@Autowired
	//@Qualifier("questionDao")
	private QuestionDAO qDao;

	@Autowired
	private QuestionService qService;

	@Autowired
	//@Qualifier("categoryDAO")
	private CategoryDAO cDao;

	/**
	 * 
	 * @param assReq: The AssessmentRequest object being sent from Core team. See bean for content.
	 * @return	Returns the same object with the link to the assessment that Assessment team generates.
	 */
	public Set<TemplateQuestion> getRandomSelectionFromCategory(CategoryRequest assReq) {

		String catName = assReq.getCategory().getName();
		int multiChoice = assReq.getMcQuestions();
		int multiSelect = assReq.getMsQuestions();
		int dragDrop = assReq.getDdQuestions();
		int codeSnip = assReq.getCsQuestions();

		Set<Question> assessList = new HashSet<>();
		Set<TemplateQuestion> finalList = new HashSet<>();
		
		Category cat = (Category) cDao.getByName(catName);
		
		if(multiChoice != 0) {
			assessList = addQuestionsToList("Multiple Choice", cat.getName(), multiChoice, assessList);
		}
		if(multiSelect != 0) {
			assessList = addQuestionsToList("Multiple Select", cat.getName(), multiSelect, assessList);
		}
		if(dragDrop != 0) {
			assessList = addQuestionsToList("Drag and Drop", cat.getName(), dragDrop, assessList);
		}
		if(codeSnip != 0) {
			assessList = addQuestionsToList("Code Snippet", cat.getName(), codeSnip, assessList);
		}
		
		for(Question q : assessList) {
				log.debug("Question: " + q);
				TemplateQuestion tq = new TemplateQuestion();
				tq.setQuestion(q);
				log.debug("Template Question " + q);
				finalList.add(tq);
		}
		return finalList;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		List<Question> filteredQuestions = (List<Question>) qDao.findAllByQuestionCategory(cat);
//
//		log.debug("Questions for category " + cat);
//
//		if (multiChoice != 0) {
//
//			formatList = multiChoiceQuestionAdder(formatList, filteredQuestions);
//			//System.out.println(formatList);
//			size = formatList.size(); // subtract 1 so that this can be used
//											// to get an index for random
//											// question
//
//			for (int i = 0; i < multiChoice; i++) {
//				
//				int num = rando.nextInt(size);
//				assessList.add(formatList.remove(num));
//				size--;
//				if (size == 1) {
//					break;
//				}
//			}
//
//		}
//		formatList.clear();
//
//		if (multiSelect != 0) {
//
//			formatList = multiSelectQuestionAdder(formatList, filteredQuestions);
//
//			size = formatList.size(); // subtract 1 so that this can be used
//											// to get an index for random
//											// question
//
//			for (int i = 0; i < multiSelect; i++) {
//				int num = rando.nextInt(size);
//				assessList.add(formatList.remove(num));
//				size--;
//				if (size == 1) {
//					break;
//				}
//			}
//
//		}
//		formatList.clear();
//		if (dragDrop != 0) {
//
//			formatList = dragDropQuestionAdder(formatList, filteredQuestions);
//
//			size = formatList.size(); // subtract 1 so that this can be used
//											// to get an index for random
//											// question
//
//			for (int i = 0; i < dragDrop; i++) {
//				int num = rando.nextInt(size);
//				assessList.add(formatList.remove(num));
//				size--;
//				if (size == 1) {
//					break;
//				}
//			}
//
//		}
//		formatList.clear();
//		if (codeSnip != 0) {
//
//			formatList = codeSnippetQuestionAdder(formatList, filteredQuestions);
//
//			size = formatList.size(); // subtract 1 so that this can be used
//											// to get an index for random
//											// question
//
//			for (int i = 0; i < codeSnip; i++) {
//				int num = rando.nextInt(size);
//				assessList.add(formatList.remove(num));
//				size--;
//				if (size == 1) {
//					break;
//				}
//			}
//
//		}
//		for(Question q : assessList)
//		{
//			log.debug("Question: " + q);
//			TemplateQuestion tq = new TemplateQuestion();
//			tq.setQuestion(q);
//			log.debug("Template Question " + q);
//			finalList.add(tq);
//		}
//
//		log.debug("Final Question List");
//
///*		for(TemplateQuestion q : finalList){
//
//			System.out.println(q.getQuestion());
//
//		}*/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}

	private Set<Question> addQuestionsToList(String format, String category, int questionCount, Set<Question> assessmentQuestions) {
		List<BigDecimal> list = qService.findIdsByFormatAndCategory(category, format);
		Random rando = new Random();
		for (int i = 0; i < questionCount; i++) {
			int num = rando.nextInt(list.size());
			Question q = qService.getQuestionById(list.get(num).intValue());
			list.remove(num).intValue();
			assessmentQuestions.add(q);
			if (list.isEmpty()) {
				break;
			}
		}
		return assessmentQuestions;
	}
	
	
	public List<Question> multiChoiceQuestionAdder(List<Question> formatList, List<Question> filteredQuestions) {

		for (Question q : filteredQuestions) {
			log.debug(q+" "+"Multiple Choice".equals(q.getFormat().getFormatName()));
			if ("Multiple Choice".equals(q.getFormat().getFormatName())) {
				formatList.add(q);
			}
		}
		return formatList;
	}

	public List<Question> multiSelectQuestionAdder(List<Question> formatList, List<Question> filteredQuestions) {

		for (Question q : filteredQuestions) {

			if ("Multiple Select".equals(q.getFormat().getFormatName())) {
				formatList.add(q);
			}
		}
		return formatList;
	}

	public List<Question> dragDropQuestionAdder(List<Question> formatList, List<Question> filteredQuestions) {

		for (Question q : filteredQuestions) {

			if ("Drag and Drop".equals(q.getFormat().getFormatName())) {
				formatList.add(q);
			}
		}
		return formatList;
	}

	public List<Question> codeSnippetQuestionAdder(List<Question> formatList, List<Question> filteredQuestions) {

		for (Question q : filteredQuestions) {

			if ("Code Snippet".equals(q.getFormat().getFormatName())) {
				formatList.add(q);
			}
		}
		return formatList;
	}

}
