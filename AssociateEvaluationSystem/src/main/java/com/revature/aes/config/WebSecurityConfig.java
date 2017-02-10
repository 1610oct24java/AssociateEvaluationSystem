package com.revature.aes.config;

import com.revature.aes.service.CustomUserDetailsService;
import com.revature.aes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
//@ImportResource("classpath*:com/revature/aes/config/security-context.xml")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/rest/**", "/images/**", "/site-images/**", "/user/**")
				.permitAll();
		
		http.authorizeRequests().antMatchers("/*/recruit", "/*/view").hasRole("RECRUITER").and().authorizeRequests().and().csrf().csrfTokenRepository(csrfTokenRepository());//.permitAll().and().authorizeRequests().and().csrf().disable();
		
		http.authorizeRequests().antMatchers("/").permitAll().anyRequest()
		.authenticated()
				.and().formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
				.and().httpBasic()
		.and().logout().logoutUrl("/logout").invalidateHttpSession(true)
		.deleteCookies("remember-me").permitAll()
		.and().rememberMe().and().exceptionHandling()
		.accessDeniedPage("/error")
				.and().authorizeRequests().and().csrf().disable();

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

	private CsrfTokenRepository csrfTokenRepository(){

		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;

	}
}
