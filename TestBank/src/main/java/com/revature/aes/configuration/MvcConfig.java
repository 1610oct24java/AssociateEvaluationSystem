/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database. Use to evaluate associates
 * 		performance both during and before employment with Revature 
 * 		LLC.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/

package com.revature.aes.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;


/**
 * As of Spring 4.1 this is the recommended way of using static resources instead of the old legacy way of using an XML file.  
 * reference http://www.baeldung.com/spring-mvc-static-resources and https://spring.io/blog/2014/07/24/spring-framework-4-1-handling-static-web-resources
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry
	      .addResourceHandler("/resources/**")
	      .addResourceLocations("/resources/","/other-resources/")
	      .setCachePeriod(3600)
	      .resourceChain(true)
	      .addResolver(new PathResourceResolver());
	}
}
