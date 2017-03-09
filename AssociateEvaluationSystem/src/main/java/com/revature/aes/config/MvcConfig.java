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
		registry.addViewController("/home").setViewName("asmt/home");
		registry.addViewController("/").setViewName("asmt/home");
		registry.addViewController("/hello").setViewName("asmt/hello");
		registry.addViewController("/quiz").setViewName("asmt/quiz");
		registry.addViewController("/login").setViewName("core/index");
		registry.addViewController("/goodbye").setViewName("asmt/goodbye");
		registry.addViewController("/trainer/**").setViewName("bank/trainerHome");
		registry.addViewController("/expired").setViewName("asmt/expired");
		registry.addViewController("/index").setViewName("core/index");
		registry.addViewController("/recruit").setViewName("core/recruitCandidate");
		registry.addViewController("/view").setViewName("core/viewCandidates");
		registry.addViewController("/viewEmployees").setViewName("admin/viewEmployees");
		registry.addViewController("/registerEmployee").setViewName("admin/registerEmployee");
		registry.addViewController("/updateCredentials").setViewName("admin/updateCredentials");
		/*registry.addViewController("/category").setViewName("bank/trainerHome");
		registry.addViewController("/question").setViewName("bank/trainerHome");
		registry.addViewController("/format").setViewName("bank/trainerHome");*/
		
		/*registry.addViewController("/trainer/").setViewName("bank/trainerHome");*/
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
