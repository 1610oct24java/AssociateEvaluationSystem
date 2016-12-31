package com.revature.aes.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Question;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer>
{
	
	public List<Question> findByFormat(Format format);
	public Question findByCategory(Category category);
	public List<Question> findByFormatAndCategory(Format format, List<Category> category);
	public List<Question> findAllQuestions();

}
