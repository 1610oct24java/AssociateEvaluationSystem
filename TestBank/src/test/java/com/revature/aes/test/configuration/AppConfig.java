package com.revature.aes.test.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.revature.aes.aspects.Logging;
import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.Category;
import com.revature.aes.beans.DragDrop;
import com.revature.aes.beans.FileUpload;
import com.revature.aes.beans.Format;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.beans.Role;
import com.revature.aes.beans.Security;
import com.revature.aes.beans.SnippetTemplate;
import com.revature.aes.beans.Tag;
import com.revature.aes.beans.Template;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.beans.User;
import com.revature.aes.controllers.TrainerController;
import com.revature.aes.core.CSVParser;
import com.revature.aes.rest.controllers.FormatRestController;
import com.revature.aes.rest.controllers.QuestionRestController;
import com.revature.aes.rest.controllers.TestController;
import com.revature.aes.services.FormatService;
import com.revature.aes.services.FormatServiceImpl;
import com.revature.aes.services.QuestionService;
import com.revature.aes.services.QuestionServiceImpl;


@Configuration
public class AppConfig {
	
	@Bean
	public Logging getLogging(){
		return new Logging();
	}

	@Bean
	public Assessment getAssessment(){
		return new Assessment();
	}
	
	@Bean
	public AssessmentAuth getAssessmentAuth(){
		return new AssessmentAuth();
	}
	
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
	public Tag getTag(){
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
	
	@Bean
	public TrainerController getTrainerController(){
		return new TrainerController();
	}
	
	@Bean
	public CSVParser getCSVParser(){
		return new CSVParser();
	}
	
	@Bean
	public FormatRestController getFormatRestController(){
		return new FormatRestController();
	}
	
	@Bean
	public QuestionRestController getQuestionRestController(){
		return new QuestionRestController();
	}
	
	@Bean
	public TestController getTestController(){
		return new TestController();
	}
	
	@Bean
	public FormatService getFormatService(){
		return getFormatServiceImpl();
	}
	
	@Bean
	public QuestionService getQuestionService(){
		return getQuestionServiceImpl();
	}
	
	@Bean
	public FormatServiceImpl getFormatServiceImpl(){
		return new FormatServiceImpl();
	}
	
	@Bean QuestionServiceImpl getQuestionServiceImpl(){
		return new QuestionServiceImpl();
	}
	
}
