package com.revature.aes.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.Category;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.beans.User;
import com.revature.aes.daos.AssessmentDAO;
import com.revature.aes.daos.CategoryDAO;
import com.revature.aes.daos.QuestionDAO;
import com.revature.aes.daos.UserDAO;

@Component
public class SystemTemplate {

	@Autowired
	private QuestionDAO qDao;
	@Autowired
	private CategoryDAO cDao;
	@Autowired
	private UserDAO uDao;
	@Autowired
	private AssessmentDAO aDao;

	/**
	 * 
	 * @param assReq
	 *            The AssessmentRequest object being passed in from Core team
	 *            containing the amount of questions for a specific format and
	 *            the category.
	 * @return The list of questions taken from the database for the template.
	 */
	public Set<TemplateQuestion> getRandomSelectionFromCategory(AssessmentRequest assReq) {

		String catName = assReq.getCategory();
		int multiChoice = assReq.getMcQuestions();
		int multiSelect = assReq.getMsQuestions();
		int dragDrop = assReq.getDdQuestions();
		int codeSnip = assReq.getCsQuestions();
		int size;
		String userEmail = assReq.getUserEmail();
		Set<Question> AssessList = new HashSet<Question>();
		List<Question> formatList = null;
		Set<TemplateQuestion> finalList = new HashSet<TemplateQuestion>();
		formatList = new ArrayList<>();
		User user = uDao.findByEmail(userEmail);
		
		Category cat = (Category) cDao.getByName(catName);
		
		List<Question> filteredQuestions = (List<Question>) qDao.findAllQuestionsByCategory(cat);

		if (multiChoice != 0) {

			formatList = multiChoiceQuestionAdder(formatList, filteredQuestions);

			size = formatList.size() - 1; // subtract 1 so that this can be used
											// to get an index for random
											// question

			for (int i = 0; i <= multiChoice; i++) {
				int num = (int) (Math.random() * size);
				AssessList.add(formatList.remove(num));
				size--;
				if (size == 0) {
					break;
				}
			}

		}

		if (multiSelect != 0) {

			formatList = multiSelectQuestionAdder(formatList, filteredQuestions);

			size = formatList.size() - 1; // subtract 1 so that this can be used
											// to get an index for random
											// question

			for (int i = 0; i <= multiChoice; i++) {
				int num = (int) (Math.random() * size);
				AssessList.add(formatList.remove(num));
				size--;
				if (size == 0) {
					break;
				}
			}

		}

		if (dragDrop != 0) {

			formatList = dragDropQuestionAdder(formatList, filteredQuestions);

			size = formatList.size() - 1; // subtract 1 so that this can be used
											// to get an index for random
											// question

			for (int i = 0; i <= multiChoice; i++) {
				int num = (int) (Math.random() * size);
				AssessList.add(formatList.remove(num));
				size--;
				if (size == 0) {
					break;
				}
			}

		}

		if (codeSnip != 0) {

			formatList = codeSnippetQuestionAdder(formatList, filteredQuestions);

			size = formatList.size() - 1; // subtract 1 so that this can be used
											// to get an index for random
											// question

			for (int i = 0; i <= multiChoice; i++) {
				int num = (int) (Math.random() * size);
				AssessList.add(formatList.remove(num));
				size--;
				if (size == 0) {
					break;
				}
			}

		}
		for(Question q : AssessList)
		{
			TemplateQuestion tq = new TemplateQuestion();
			tq.setPatternInquiry(q);
			
			finalList.add(tq);
		}
		
		return finalList;
	}

	public List<Question> multiChoiceQuestionAdder(List<Question> formatList, List<Question> filteredQuestions) {

		for (Question q : filteredQuestions) {

			if (q.getFormat().getFormatName().equals("Multiple Choice")) {
				formatList.add(q);
			}
		}
		return formatList;
	}

	public List<Question> multiSelectQuestionAdder(List<Question> formatList, List<Question> filteredQuestions) {

		for (Question q : filteredQuestions) {

			if (q.getFormat().getFormatName().equals("Multiple Select")) {
				formatList.add(q);
			}
		}
		return formatList;
	}

	public List<Question> dragDropQuestionAdder(List<Question> formatList, List<Question> filteredQuestions) {

		for (Question q : filteredQuestions) {

			if (q.getFormat().getFormatName().equals("Drag and Drop")) {
				formatList.add(q);
			}
		}
		return formatList;
	}

	public List<Question> codeSnippetQuestionAdder(List<Question> formatList, List<Question> filteredQuestions) {

		for (Question q : filteredQuestions) {

			if (q.getFormat().getFormatName().equals("Code Snippet")) {
				formatList.add(q);
			}
		}
		return formatList;
	}

}
