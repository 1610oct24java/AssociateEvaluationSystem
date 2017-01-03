package com.revature.aes.main;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.core.SystemTemplate;
import com.revature.aes.daos.CategoryDAO;
import com.revature.aes.daos.QuestionDAO;

public class testingDriver {
	
	private static QuestionDAO qDao;
	private static CategoryDAO cDao;
	
	public static void main(String[] args) {
		
		AssessmentRequest test = new AssessmentRequest("Java", 2, 2, 0, 0, null, null);
		
		System.out.println(cDao.findAll());
		
		/*SystemTemplate systemp = new SystemTemplate();
		System.out.println(systemp.getRandomSelectionFromCategory(test));*/
		
		
	}

}
