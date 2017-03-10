package com.revature.aes.dao;

import java.util.List;
import java.util.Set;

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

	@Query("SELECT questionId FROM Question WHERE questionId = ?1")
	List<Question> findQuestionById(int id);
}
