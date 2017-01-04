package com.revature.aes.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

public class ContainerCustomizer {
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		
		return (container -> {
			ErrorPage error401Page =
					new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
			ErrorPage error404Page =
					new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
			ErrorPage error500Page = new ErrorPage(
					HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
			
			container.addErrorPages(error401Page, error404Page, error500Page);
		});
	}
	
}
