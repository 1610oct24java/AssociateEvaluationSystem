package com.revature.aes.testing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.CategoryRequest;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.dao.CategoryDAO;
import com.revature.aes.dao.QuestionDAO;
import com.revature.aes.service.CategoryServiceImpl;
import com.revature.aes.service.SystemTemplate;

public class AssessmentTest {

//	SystemTemplate systemp = new SystemTemplate();
//	CategoryRequest catReq = new CategoryRequest();
	private CategoryRequest invalidCat = new CategoryRequest("yeva",0,3,0,0);
	
	private Category category;
	private CategoryRequest catReq;
	private SystemTemplate systemp;
	private CategoryServiceImpl catserve;
	private CategoryDAO catDao;
	private QuestionDAO qDao;
	
	private CategoryRequest mcCatReq = new CategoryRequest("Java",0,3,0,0);
//	CategoryRequest msCatReq = new CategoryRequest("Java",2,0,0,0);
//	CategoryRequest ddCatReq = new CategoryRequest("Java",0,0,2,0);
//	CategoryRequest csCatReq = new CategoryRequest("Java",0,0,0,2);
//	CategoryRequest fullCatReq = new CategoryRequest("Java",2,3,2,2);
	
	@Before
	public void makeMockThangs(){
		catDao = mock(CategoryDAO.class);
		category = mock(Category.class);
		catReq = mock(CategoryRequest.class);
		systemp = mock(SystemTemplate.class);
		catserve = mock(CategoryServiceImpl.class);
		qDao = mock(QuestionDAO.class);
	}
	
	@After
	public void resetPrivateVariables(){
		category = null;
		catReq = null;
		systemp = null;
		catserve = null;
	}
	
	@Test
	public void noCategoryAssessmentRequestTester(){
		
		Set<TemplateQuestion> emptyList = new HashSet<>();
		catReq = new CategoryRequest();
		systemp = new SystemTemplate();
		
		assertEquals("Test if method completes with no categories picked.", emptyList, systemp.getRandomSelectionFromCategory(catReq));
	}
	
	@Test(expected = NullPointerException.class)
	public void invalidCategoryTest(){
		category = new Category();
		catserve = new CategoryServiceImpl();
		category.setName("Java");
		when(catDao.getByName("Java")).thenReturn(category);
		assertEquals("Wrong category should catch null pointer exception and log it",3,systemp.getRandomSelectionFromCategory(invalidCat).size());
	}
	
	@Test
	public void fullAssessmentRequestTester()throws NullPointerException{
		category = new Category();
		catserve = new CategoryServiceImpl();
		category.setName("Java");
		
		ArrayList<Question> qList = new ArrayList<Question>();
		qList.add(new Question());
		qList.add(new Question());
		qList.add(new Question());
		
		when(catDao.getByName("Java")).thenReturn(category);
		when(qDao.findAllByQuestionCategory(category)).thenReturn(qList);
		assertEquals("Test if method returns 3 questions from multiple choice.",3,systemp.getRandomSelectionFromCategory(mcCatReq).size());
//		assertEquals("Test if method returns 2 questions from multiple select.",2,systemp.getRandomSelectionFromCategory(msCatReq).size());
//		assertEquals("Test if method returns 2 questions from drag and drop.",2,systemp.getRandomSelectionFromCategory(ddCatReq).size());
//		assertEquals("Test if method returns 2 questions from code snippet.",2,systemp.getRandomSelectionFromCategory(csCatReq).size());
		
		
	}

}
