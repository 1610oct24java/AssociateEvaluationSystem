package com.revature.aes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
