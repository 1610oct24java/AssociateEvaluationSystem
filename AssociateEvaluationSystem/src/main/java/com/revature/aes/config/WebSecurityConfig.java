package com.revature.aes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/rest/**")
				.permitAll();
		
		http.authorizeRequests().antMatchers("/**").permitAll().and().authorizeRequests().and().csrf().disable();
		
		// http.authorizeRequests().antMatchers("/").permitAll().anyRequest()
		// .authenticated()
		//
		// .and().formLogin().loginPage("/login").permitAll()
		//
		// .and().logout().logoutUrl("/logout").invalidateHttpSession(true)
		// .deleteCookies("remember-me").permitAll()
		//
		// .and().rememberMe().and().exceptionHandling()
		// .accessDeniedPage("/error")
		//
		// .and().authorizeRequests().and().csrf().disable();
		
	}
	
	@Autowired
	private CustomAuthenticationProvider authProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authProvider);
		// auth.inMemoryAuthentication().withUser("user").password("password")
		// .roles("USER");
	}
}
