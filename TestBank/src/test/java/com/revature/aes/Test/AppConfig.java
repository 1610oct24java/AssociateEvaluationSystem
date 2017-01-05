package com.revature.aes.Test;

import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.revature.aes.aspects.Logging;
import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.Category;
import com.revature.aes.beans.DragDrop;
import com.revature.aes.beans.FileUpload;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.QuestionOptionsJSONHandler;
import com.revature.aes.beans.Role;
import com.revature.aes.beans.Security;
import com.revature.aes.beans.SnippetTemplate;
import com.revature.aes.beans.Tag;
import com.revature.aes.beans.Template;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.beans.User;
import com.revature.aes.controllers.TrainerController;
import com.revature.aes.core.AikenParser;
import com.revature.aes.core.CSVParser;
import com.revature.aes.daos.CategoryDAO;
import com.revature.aes.daos.FormatDAO;
import com.revature.aes.daos.OptionsDAO;
import com.revature.aes.daos.QuestionDAO;
import com.revature.aes.rest.controllers.CategoryRestController;
import com.revature.aes.rest.controllers.FormatRestController;
import com.revature.aes.rest.controllers.QuestionRestController;
import com.revature.aes.services.CategoryService;
import com.revature.aes.services.CategoryServiceImpl;
import com.revature.aes.services.FormatServiceImpl;
import com.revature.aes.services.QuestionService;
import com.revature.aes.services.QuestionServiceImpl;

@Configuration
public class AppConfig {
	
	//Aspect
	@Bean
	public Logging getLogging(){
		return new Logging();
	}
	
	//Beans
	@Bean
	public Assessment getAssessment(){
		return new Assessment();
	}
	
	//AssessmentAuth bean not created as it isn't an annotated bean.
	
	@Bean
	public AssessmentDragDrop getAssessmentDragDrop(){
		return new AssessmentDragDrop();
	}
	
	@Bean
	public Category getCategory(){
		return new Category();
	}
	
	@Bean
	public DragDrop getDragDrop(){
		return new DragDrop();
	}
	
	@Bean
	public FileUpload getFileUpload(){
		return new FileUpload();
	}
	
	@Bean
	public Format getFormat(){
		return new Format();
	}
	
	@Bean
	public Option getOption(){
		return new Option();
	}
	
	@Bean
	public Question getQuestion(){
		return new Question();
	}
	
	@Bean
	public QuestionOptionsJSONHandler getQuestionOptionsJSONHandler(){
		return new QuestionOptionsJSONHandler();
	}
	
	@Bean
	public Role getRole(){
		return new Role();
	}
	
	@Bean
	public Security getSecurity(){
		return new Security();
	}
	
	@Bean
	public SnippetTemplate getSnippetTemplate(){
		return new SnippetTemplate();
	}
	
	@Bean
	public Tag getTag (){
		return new Tag();
	}
	
	@Bean
	public Template getTemplate(){
		return new Template();
	}
	
	@Bean
	public TemplateQuestion getTemplateQuestion(){
		return new TemplateQuestion();
	}
	
	@Bean
	public User getUser(){
		return new User();
	}
	
	//Controllers
	@Bean
	public TrainerController getTrainerController(){
		return new TrainerController();
	}
	//Core
	@Bean
	public AikenParser getAikenParser(){
		return new AikenParser();
	}
	
	@Bean
	public CSVParser getCSVParser(){
		return new CSVParser();
	}
	
	//DAOs
	@Bean
	@Qualifier("categoryDao")
	public CategoryDAO getCategoryDAO(){
		return mock(CategoryDAO.class);
	}
	
	@Bean
	public FormatDAO getFormatDAO(){
		return mock(FormatDAO.class);
	}
	
	@Bean
	@Qualifier("optionDao")
	public OptionsDAO getOptionsDAO(){
		return mock(OptionsDAO.class);
	}
	@Bean 
	@Qualifier("questionDao")
	public QuestionDAO getQuestionDAO(){
		return mock(QuestionDAO.class);
	}
	
	//Exception
	//RestControllers
	@Bean
	public CategoryRestController getCategoryRestController(){
		return new CategoryRestController();
	}
	@Bean
	public FormatRestController getFormatRestController(){
		return new FormatRestController();
	}
	@Bean
	public QuestionRestController getQuestionRestController(){
		return new QuestionRestController();
	}
	
	//Services
	@Bean
	public CategoryService getCategoryService(){
		return new CategoryServiceImpl();
	}
	
	@Bean
	public FormatServiceImpl getFormatServiceImpl(){
		return new FormatServiceImpl();
	}
	
	@Bean
	public QuestionService getQuestionService(){
		 return new QuestionServiceImpl();
	}
}
