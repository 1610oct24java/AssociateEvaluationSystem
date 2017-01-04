package com.revature.aes.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
class ErrorConfiguration implements EmbeddedServletContainerCustomizer {
	
	/**
	 * Set error pages for specific error response codes
	 */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
		container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/401"));
		container.addErrorPages(
				new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
	}
	
}
