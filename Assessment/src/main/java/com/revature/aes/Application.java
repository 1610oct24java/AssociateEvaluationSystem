package com.revature.aes.config;

import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.Assessment;
import com.revature.aes.dao.AssessmentDAO;

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
	

	/*@Bean
	public CommandLineRunner demo(AssessmentDAO repository) {
		return (args) -> {
			// save a couple of customers
			// repository.save(new User());
			
			System.out.println("Running");
			
			// fetch all customers
			System.out.println("Customers found with findAll():");
			System.out.println("-------------------------------");
			for (Assessment customer : repository.findAll()) {
				System.out.println(customer.toString());
			}
			System.out.println("");
			
			// fetch an individual customer by ID
			// User customer = repository.findOne(1L);
			// System.out.println("User found with findOne(1L):");
			// System.out.println("--------------------------------");
			// System.out.println(customer.toString());
			// System.out.println("");
			
			// fetch customers by last name
			// System.out.println("User found with findByLastName('Bauer'):");
			// System.out.println("--------------------------------------------");
			// for (User bauer : repository.findByLastName("Bauer")) {
			// System.out.println(bauer.toString());
			// }
			// System.out.println("");
		};
	}*/
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	
}
