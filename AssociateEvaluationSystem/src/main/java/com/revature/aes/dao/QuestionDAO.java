package com.revature.aes.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;

@Repository("questionDao")
public interface QuestionDAO extends JpaRepository<Question, Integer> {
	public List<Question> findAllByFormat(Format format);
	public List<Question> findAllByQuestionCategory(Category category); 
	public Question findQuestionByOption(Option opt);
//	public Question findQuestionByOptionId(Integer id);

	@Query("SELECT questionText FROM Question WHERE questionId = ?1")
	List<Question> findQuestionById(int id);
	
	//@Query("select AES_QUESTION.QUESTION_ID from 
	//(AES_QUESTION inner join AES_QUESTION_CATEGORY on AES_QUESTION.QUESTION_ID = AES_QUESTION_CATEGORY.QUESTION_ID) 
	//where CATEGORY_ID = (select CATEGORY_ID from AES_CATEGORY where CATEGORY_NAME = ?1) 
	//and QUESTION_FORMAT_ID = (Select FORMAT_ID from AES_FORMATS where FORMAT_NAME = ?2)")
	//@Query("SELECT questionId FROM Question q WHERE q.format = (SELECT Format from Format where formatName = ?2)")
	@Query(value = "select AES_QUESTION.QUESTION_ID from "
			+ "(AES_QUESTION inner join AES_QUESTION_CATEGORY on AES_QUESTION.QUESTION_ID = AES_QUESTION_CATEGORY.QUESTION_ID) "
			+ "where CATEGORY_ID = (select CATEGORY_ID from AES_CATEGORY where CATEGORY_NAME = ?1) "
			+ "and QUESTION_FORMAT_ID = (Select FORMAT_ID from AES_FORMATS where FORMAT_NAME = ?2)", nativeQuery = true)
	List<BigDecimal> findQuestionIdsByFormatandCategory(String category, String format);
}
