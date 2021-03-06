package com.revature.aes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login/index");
        registry.addViewController("/index").setViewName("login/index");
        registry.addViewController("/assessmentLandingPage").setViewName("asmt/assessmentLandingPage");
        registry.addViewController("/quiz").setViewName("asmt/quiz");
        registry.addViewController("/quizReview").setViewName("asmt/quizReview");
        registry.addViewController("/goodbye").setViewName("asmt/goodbye");
		registry.addViewController("/expired").setViewName("asmt/expired");
		registry.addViewController("/missing").setViewName("asmt/missing");
		registry.addViewController("/recruitMenu.html").setViewName("recruiter/recruitMenu");
//		registry.addViewController("/recruitMenu.html").setViewName("admin/recruitMenu");
		registry.addViewController("/recruit").setViewName("recruiter/recruitCandidate");
		registry.addViewController("/view").setViewName("recruiter/viewCandidates");
		registry.addViewController("/updateUser").setViewName("recruiter/updateUser");
		registry.addViewController("/viewEmployees").setViewName("admin/viewEmployees");
		registry.addViewController("/viewAssessment").setViewName("admin/viewAssessment");
		registry.addViewController("/registerEmployee").setViewName("admin/registerEmployee");
		registry.addViewController("/registerEmployee").setViewName("admin/registerEmployee");
		registry.addViewController("/parser").setViewName("admin/xmlParser");
		registry.addViewController("/menu.html").setViewName("admin/menu");
		registry.addViewController("/manageQuestions").setViewName("admin/manageQuestions");
		registry.addViewController("/addQuestions").setViewName("admin/addQuestions");
		registry.addViewController("/createAssessment").setViewName("admin/createAssessment");
        registry.addViewController("/updateCredentials").setViewName("admin/updateCredentials");
        registry.addViewController("/updateEmployee").setViewName("admin/updateEmployee");
		registry.addViewController("/category").setViewName("misc/bank/trainerHome");
		registry.addViewController("/trainer/**").setViewName("misc/bank/trainerHome");
		registry.addViewController("/hello").setViewName("misc/hello");
		registry.addViewController("/home").setViewName("misc/home");
		registry.addViewController("/").setViewName("misc/home");
		registry.addViewController("/category").setViewName("misc/bank/trainerHome");
		registry.addViewController("/question").setViewName("misc/bank/trainerHome");
		//fix this format
		registry.addViewController("/format").setViewName("misc/bank/trainerHome");
        registry.addViewController("/trainer/").setViewName("misc/bank/trainerHome");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/questionTemplate").setViewName("misc/bank/questionTemplate");
        registry.addViewController("/chooseAssessment").setViewName("admin/chooseAssessment");
        
        registry.addViewController("/globalSettings").setViewName("admin/globalSettings");
        
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/templates/trainer/**")
	      .addResourceLocations("/templates/trainer/","/other-resources/")
	      .setCachePeriod(3600)
	      .resourceChain(true)
	      .addResolver(new PathResourceResolver());
	}
}
