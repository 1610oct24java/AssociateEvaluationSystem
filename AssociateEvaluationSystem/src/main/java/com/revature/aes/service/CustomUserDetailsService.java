package com.revature.aes.service;

import com.revature.aes.beans.Security;
import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private Logging log;

	@Autowired
    UserService uService;
	
	@Autowired
	SecurityService sService;
	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		com.revature.aes.beans.User user = uService.findUserByEmail(arg0);
		Security security = sService.findSecurityByUserId(user.getUserId());
		if(!this.checkForValidPassword(user, security)) {
			security.setPassword("");
		}
		return new User(arg0, security.getPassword(), getAuthorities(user.getRole().getRoleTitle()));
	}

    public boolean checkForValidPassword(com.revature.aes.beans.User user, Security security) {
    	if(security.getValid() == 1) {
			String pattern = "dd-MMM-yy";
			SimpleDateFormat fmt = new SimpleDateFormat(pattern);
			String date = fmt.format(new Date());
			Date currentDate = new Date();
			Date passwordDate = new Date();
			try {
				currentDate = fmt.parse(date);
				fmt = new SimpleDateFormat("yyyy-MM-dd");
				passwordDate = fmt.parse(user.getDatePassIssued().substring(0,10));
			} catch (ParseException e) {
				log.info(e.toString());
			}
			long diff = currentDate.getTime() - passwordDate.getTime();
			long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			if("Candidate".equalsIgnoreCase(user.getRole().getRoleTitle()) && days > 7) {
				security.setValid(0);
				sService.updateSecurity(security);
			} else {
				return true;
			}
    	}
    	return false;
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
