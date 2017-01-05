package com.revature.aes.config;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan({ "com.revature.aes.controllers", "com.revature.aes.beans",
		"com.revature.aes.aop", "com.revature.aes.dao",
		"com.revature.aes.config" })
public class Application extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(String[] args) throws Throwable {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		return jdbcTemplate;
	}
	
	@Bean
	NamedParameterJdbcTemplate namedParameterJdbcTemplate(
			DataSource dataSource) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate =
				new NamedParameterJdbcTemplate(dataSource);
		
		return namedParameterJdbcTemplate;
	}
}
