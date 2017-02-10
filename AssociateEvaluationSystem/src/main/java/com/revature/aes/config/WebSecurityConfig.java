package com.revature.aes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.revature.aes.filter.AfterFilter;
import com.revature.aes.filter.BeforeFilter;

@Configuration
@EnableWebSecurity
//@ImportResource("classpath*:com/revature/aes/config/security-context.xml")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/rest/**")
				.permitAll();
			
		http.authorizeRequests().antMatchers("/**").permitAll().and().authorizeRequests().and().csrf().disable();
		
		http.authorizeRequests().antMatchers("/").permitAll().anyRequest()
		.authenticated()
				.and().formLogin().loginPage("/login").permitAll()
		.and().logout().logoutUrl("/logout").invalidateHttpSession(true)
		.deleteCookies("remember-me").permitAll()
		.and().rememberMe().and().exceptionHandling()
		.accessDeniedPage("/error")
				.and().authorizeRequests().and().csrf().disable();
		
		
//		http.antMatcher("/rest/**").authorizeRequests()
//      .anyRequest().authenticated()
//      .and()
//      .addFilterBefore(new BeforeFilter(), BasicAuthenticationFilter.class);
//	
//		http.authorizeRequests().antMatchers("/rest/**").permitAll().and().authorizeRequests().and().csrf().disable()
//		.addFilterBefore(new BeforeFilter(), BasicAuthenticationFilter.class).addFilterAfter(new AfterFilter(), BasicAuthenticationFilter.class).cors();
//
//		http.addFilterBefore(new BeforeFilter(), AnonymousAuthenticationFilter.class).antMatcher("rest/**");
//	
//		http.antMatcher("/rest/**").addFilterBefore(new BeforeFilter(), BasicAuthenticationFilter.class);
//		
//		http.authorizeRequests().antMatchers("/user").permitAll().and().authorizeRequests().and().csrf().disable()
//		.addFilterBefore(new BeforeFilter(), BasicAuthenticationFilter.class).addFilterAfter(new AfterFilter(), BasicAuthenticationFilter.class).cors();
	}

	@Autowired
	private UserDetailsService customUserDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
		// auth.inMemoryAuthentication().withUser("user").password("password")
		// .roles("USER");
	}
}
