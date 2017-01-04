package com.revature.aes.daos;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;

@Repository("questionDao")
public interface QuestionDAO extends JpaRepository<Question, Integer>{
	public List<Question> findAllQuestionsByFormat(Format format);
	public List<Question> findAllQuestionsByCategory(Category category); 
	//public Question updateQuestionById(Integer id);
}
