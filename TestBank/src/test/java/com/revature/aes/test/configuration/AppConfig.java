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
import com.revature.aes.daos.FormatDAO;;
import com.revature.aes.daos.QuestionDAO;;
import com.revature.aes.rest.controllers.FormatRestController;
import com.revature.aes.rest.controllers.QuestionRestController;
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
	public 
}
