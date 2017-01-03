package com.revature.aes.core;

import java.util.ArrayList;
import java.util.List;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.Category;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;
import com.revature.aes.daos.CategoryDAO;
import com.revature.aes.daos.QuestionDAO;

public class SystemTemplate {

	private QuestionDAO qDao;
	private CategoryDAO cDao;

	/**
	 * 
	 * @param assReq
	 *            The AssessmentRequest object being passed in from Core team
	 *            containing the amount of questions for a specific format and
	 *            the category.
	 * @return The list of questions taken from the database for the template.
	 */
	public List<Question> getRandomSelectionFromCategory(AssessmentRequest assReq) {

		String catName = assReq.getCategory();
		int multiChoice = assReq.getMcQuestions();
		int multiSelect = assReq.getMsQuestions();
		int dragDrop = assReq.getDdQuestions();
		int codeSnip = assReq.getCsQuestions();
		List<Question> AssessList = new ArrayList<Question>();

		// set instead of list
		System.out.println("The category name: " + catName);
		Category cat = (Category) cDao.getByName(catName);
		System.out.println("The category object: " + cat.toString());
		List<Question> filteredQuestions = (List<Question>) qDao.findAllQuestionsByCategory(cat);
		List<Question> formatList = null;

		// Make a separate method instead of repeating this four times.

		formatList = new ArrayList<>();
		int size;

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

		return AssessList;
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
