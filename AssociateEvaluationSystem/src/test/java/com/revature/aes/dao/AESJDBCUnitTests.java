/****************************************	
 * 			AESJDBC Unit Test			*
 ****************************************										
 *										*
 * 		-Created by Eric "DK" Biehl		*
 * 		-5/25/17						*
 *                                      *
 ****************************************/
package com.revature.aes.dao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class AESJDBCUnitTests {

	private AESJDBCImpl imp;
	private DriverManagerDataSource dmds;

	@Before
	public void setUp() throws Exception {
		imp = new AESJDBCImpl();
		dmds = new DriverManagerDataSource();
		dmds.setUrl("jdbc:oracle:thin:@associate-evaluation-system.cgbbs6xdwjwh.us-west-2.rds.amazonaws.com:1521:AES");
		dmds.setUsername("aes_user");
		dmds.setPassword("r3vatur3!");
		dmds.setDriverClassName("oracle.jdbc.OracleDriver");
	}
	@Test
	public void testSetDataSource() {
		DataSource ds = dmds;
		imp.setJdbc(new JdbcTemplate(ds));
	}
	@Test
	public void testGetNumOfJavaQuestionsForEachType() {

		testSetDataSource();

		int numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(1, 1);
		Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
		numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(1, 2);
		Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
//		numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(1, 3);
//		Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
		numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(1, 4);
		Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
	}
	@Test
	public void testGetNumOfOOPQuestionsForEachType() {

		testSetDataSource();

		int numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(2, 1);
		Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
		numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(2, 2);
		Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
		// numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(2, 3);
		// Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
		// numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(2, 4);
		// Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
	}
	@Test
	public void testGetNumOfDataStructuresQuestionsForEachType() {

		testSetDataSource();

		int numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(3, 1);
		Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
		numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(3, 2);
		Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
		//numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(3, 3);
		//Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
		//numberOfQuestionByTypeAndCatergory = imp.getNumOfQuestions(3, 4);
		//Assert.assertTrue(numberOfQuestionByTypeAndCatergory > 0);
	}
}
