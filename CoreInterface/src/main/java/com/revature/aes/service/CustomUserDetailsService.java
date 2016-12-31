package com.revature.aes.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	UserService uService;
	
	@Autowired
	SecurityService sService;
	
	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		com.revature.aes.beans.User user = uService.findUserByEmail(arg0);
		Security security = sService.findSecurityByUserId(user.getUserId());
		return new User(arg0, security.getPassword(), getAuthorities(user.getRole().getRoleTitle()));
	}

    public Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return getGrantedAuthorities(getRoles(role));
    }

    public List<String> getRoles(String role) {
        List<String> roles = new ArrayList<>();

        if ("Recruiter".equalsIgnoreCase(role)) {
            roles.add("ROLE_RECRUITER");
        } else if ("Candidate".equalsIgnoreCase(role)) {
            roles.add("ROLE_CANDIDATE");
        } else if ("Trainer".equalsIgnoreCase(role)) {
            roles.add("ROLE_TRAINER");
        }
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
