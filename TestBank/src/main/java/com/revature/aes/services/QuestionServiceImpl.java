package com.revature.aes.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Category;
import com.revature.aes.beans.DragDrop;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.QuestionOptionsJSONHandler;
import com.revature.aes.beans.Tag;
import com.revature.aes.daos.OptionsDAO;
import com.revature.aes.daos.QuestionDAO;

@Service("QuestionServiceImpl")
@Transactional
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	@Qualifier("questionDao")
	private QuestionDAO qdao;

	@Autowired
	private OptionsDAO odao;
	/**
	 * Adds a Question to the Database
	 * @param The Question to be persisted to the database 
	 * @return the Object returned after persisting to a database
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Question addQuestion(Question question)
	{
		//ensures question text isn't null or an empty string.
		if(question.getQuestionText().trim() == "" || question.getQuestionText() == null  )
		{
			return null;
		}
		return qdao.saveAndFlush(question);
	}

	/**
	 * Retrieves a Question from the database 
	 * @param The unique identifier of a Question to be retrieved to the database, cannot be null 
	 * @return the Object returned from the database
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Question getQuestionById(Integer id)
	{
		return qdao.findOne(id);
	}

	/**
	 * Retrieves all Questions from the database
	 * @return A List of Questions
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Question> getAllQuestions()
	{
		return qdao.findAll();
	}
	
	
	/**
	 * Retrieves all Questions by a specific format from a database
	 * @param the Format to determine the restriction type of SQL query
	 * @return A List of Questions restricted to a specific format
	 * 
	 * @see com.revature.aes.beans.Format
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Question> getAllQuestionsByFormat(Format format)
	{
		return qdao.findAllQuestionsByFormat(format);
	}
	
	/**
	 * Retrieves all Questions by a specific category from a database
	 * @param the Category to determine the restriction type of SQL query
	 * @return A List of Category restricted to a specific format
	 * 
	 * @see com.revature.aes.beans.Category
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Question> getAllQuestionsByCategory(Category category)
	{
		return qdao.findAllQuestionsByCategory(category);
	}

	/**
	 * Updates a Question which is stored in a database
	 * @param an Updated Question value
	 * @return the Updated object after being persisted in a database
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Question updateQuestion(Question question)
	{
		return qdao.save(question);
	}
	
	/**
	 * Deletes a question based off an the unique identifier for the question.
	 * @param the unique identifier of a question.
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteQuestionById(Integer id)
	{
		qdao.delete(qdao.findOne(id)); 	
	}

	/**
	 * This method fixes the inconsistency of JSON object return an array while
	 * our beans use various collection like Set and List.
	 */
	@Override
	public Question addFullQuestion(QuestionOptionsJSONHandler question) {
		
		Question baseQuestion = addQuestion(question.getQuestion());	
		Option[] multiChoice = question.getMultiChoice();
		Set<Category> categorySet = new HashSet<>();
		Category[] categories = question.getCategories();
		Set<DragDrop> dragDropSet = new HashSet<>();
		DragDrop[] dragDrops = question.getDragDrops();
		Tag[] tags = question.getTags();
		Set<Tag> tagSet = new HashSet<>();
		
		baseQuestion = qdao.saveAndFlush(baseQuestion);
		if(multiChoice != null){
			for(Option option : multiChoice){
				option.setQuestion(baseQuestion);
				odao.saveAndFlush(option);
			}
		}
		
		if(categories != null){
			for(Category cat : categories){
				categorySet.add(cat);
			}
		}
		
		if(dragDrops != null){
			for(DragDrop dragDrop: dragDrops){
				dragDropSet.add(dragDrop);
			}
		}
		
		if(tags != null){
			for(Tag tag: tags){
				tagSet.add(tag);
			}
		}
		
		return baseQuestion;
	}


}
