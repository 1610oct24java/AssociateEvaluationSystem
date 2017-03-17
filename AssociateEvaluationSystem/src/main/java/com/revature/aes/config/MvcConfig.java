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
		registry.addViewController("/quiz").setViewName("asmt/quiz");
		registry.addViewController("/goodbye").setViewName("asmt/goodbye");
		registry.addViewController("/expired").setViewName("asmt/expired");
		registry.addViewController("/recruit").setViewName("recruiter/recruitCandidate");
		registry.addViewController("/view").setViewName("recruiter/viewCandidates");
		registry.addViewController("/updateUser").setViewName("recruiter/updateUser");
		registry.addViewController("/viewEmployees").setViewName("admin/viewEmployees");
		registry.addViewController("/registerEmployee").setViewName("admin/registerEmployee");
		registry.addViewController("/createAssessment").setViewName("admin/createAssessment");
		registry.addViewController("/updateEmployee").setViewName("admin/updateEmployee");
		/*registry.addViewController("/category").setViewName("bank/trainerHome");
		registry.addViewController("/trainer/**").setViewName("bank/trainerHome");
		registry.addViewController("/hello").setViewName("asmt/hello");
		registry.addViewController("/home").setViewName("asmt/home");aast
		registry.addViewController("/").setViewName("asmt/home");
		registry.addViewController("/category").setViewName("bank/trainerHome");-m
		registry.addViewController("/question").setViewName("bank/trainerHome");
		registry.addViewController("/format").setViewName("bank/trainerHome");
        registry.addViewController("/trainer/").setViewName("bank/trainerHome");*/
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
