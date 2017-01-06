package com.revature.aes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

/**
 * The Class Application, starting point for SpringBoot.
 */
@SpringBootApplication
@EnableJpaRepositories("com.revature.aes.dao")
public class Application extends SpringBootServletInitializer {
	
	/**
	 * The main method, boot uses this to start.
	 *
	 * @param args
	 *            the arguments
	 * @throws Throwable
	 *             the throwable
	 */
	public static void main(String[] args) throws Throwable {
		SpringApplication.run(Application.class, args);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.web.support.SpringBootServletInitializer#
	 * configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	/**
	 * Rest template, builds rest calls.
	 *
	 * @param builder
	 *            the builder
	 * @return the rest template
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
}
