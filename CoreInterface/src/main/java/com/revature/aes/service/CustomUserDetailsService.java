package com.revature.aes.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Security;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		System.out.println("INPUT USER: "+arg0);
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		UserService uService =(UserService) ac.getBean("userServiceImpl");
		SecurityService sService = (SecurityService) ac.getBean("securityServiceImpl");
		com.revature.aes.beans.User user = uService.findUserByEmail(arg0);
		System.out.println(user);
		Security security = sService.findSecurityByUserId(user.getUserId());
		System.out.println(security);
		return new User(arg0, security.getPassword(), getAuthorities(1));
	}

	
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<String> getRoles(Integer role) {
        List<String> roles = new ArrayList<String>();

        if (role.intValue() == 1) {
            roles.add("ROLE_USER");
            roles.add("ROLE_ADMIN");

        } else if (role.intValue() == 2) {
            roles.add("ROLE_USER");
        }

        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
