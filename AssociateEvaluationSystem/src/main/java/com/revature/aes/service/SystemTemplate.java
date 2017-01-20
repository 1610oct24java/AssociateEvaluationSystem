package com.revature.aes.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.xspec.S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.Category;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.dao.CategoryDAO;
import com.revature.aes.dao.QuestionDAO;

@Component
public class SystemTemplate {

	@Autowired
	private QuestionDAO qDao;
	@Autowired
	private CategoryDAO cDao;

	/**
	 * 
	 * @param assReq: The AssessmentRequest object being sent from Core team. See bean for content.
	 * @return	Returns the same object with the link to the assessment that Assessment team generates.
	 */
	public Set<TemplateQuestion> getRandomSelectionFromCategory(AssessmentRequest assReq) {

		String catName = assReq.getCategory();
		int multiChoice = assReq.getMcQuestions();
		int multiSelect = assReq.getMsQuestions();
		int dragDrop = assReq.getDdQuestions();
		int codeSnip = assReq.getCsQuestions();
		int size;

		Set<Question> assessList = new HashSet<>();
		List<Question> formatList = new ArrayList<>();
		Set<TemplateQuestion> finalList = new HashSet<>();
		Random rando = new Random();
		
		Category cat = (Category) cDao.getByName(catName);
		
		List<Question> filteredQuestions = (List<Question>) qDao.findAllByQuestionCategory(cat);

		System.out.println("Questions for category " + cat);

		for(Question q : filteredQuestions){

			System.out.println(q);

		}

		if (multiChoice != 0) {

			formatList = multiChoiceQuestionAdder(formatList, filteredQuestions);
			System.out.println("Hello, this is Richard.");
			System.out.println(formatList);
			size = formatList.size(); // subtract 1 so that this can be used
											// to get an index for random
											// question

			for (int i = 0; i < multiChoice; i++) {
				
				int num = rando.nextInt(size);
				assessList.add(formatList.remove(num));
				size--;
				if (size == 1) {
					break;
				}
			}

		}
		formatList.clear();

		if (multiSelect != 0) {

			formatList = multiSelectQuestionAdder(formatList, filteredQuestions);

			size = formatList.size(); // subtract 1 so that this can be used
											// to get an index for random
											// question

			for (int i = 0; i < multiSelect; i++) {
				int num = rando.nextInt(size);
				assessList.add(formatList.remove(num));
				size--;
				if (size == 1) {
					break;
				}
			}

		}
		formatList.clear();
		if (dragDrop != 0) {

			formatList = dragDropQuestionAdder(formatList, filteredQuestions);

			size = formatList.size(); // subtract 1 so that this can be used
											// to get an index for random
											// question

			for (int i = 0; i < dragDrop; i++) {
				int num = rando.nextInt(size);
				assessList.add(formatList.remove(num));
				size--;
				if (size == 1) {
					break;
				}
			}

		}
		formatList.clear();
		if (codeSnip != 0) {

			formatList = codeSnippetQuestionAdder(formatList, filteredQuestions);

			size = formatList.size(); // subtract 1 so that this can be used
											// to get an index for random
											// question

			for (int i = 0; i < codeSnip; i++) {
				int num = rando.nextInt(size);
				assessList.add(formatList.remove(num));
				size--;
				if (size == 1) {
					break;
				}
			}

		}
		for(Question q : assessList)
		{
			System.out.println("Question: " + q);
			TemplateQuestion tq = new TemplateQuestion();
			tq.setQuestion(q);
			System.out.println("Template Question " + q);
			finalList.add(tq);
		}

		System.out.println("Final Question List");

		for(TemplateQuestion q : finalList){

			System.out.println(q.getQuestion());

		}

		return finalList;
	}

	public List<Question> multiChoiceQuestionAdder(List<Question> formatList, List<Question> filteredQuestions) {

		for (Question q : filteredQuestions) {
			System.out.println(q+" "+"Multiple Choice".equals(q.getFormat().getFormatName()));
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