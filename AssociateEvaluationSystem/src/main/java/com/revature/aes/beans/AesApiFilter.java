package com.revature.aes.beans;

import com.revature.aes.dao.ApiTokenDao;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.ApiTokenService;
import com.revature.aes.service.ApiTokenServiceImpl;
import com.revature.aes.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by mpski on 2/17/17.
 */

//@Component
public class AesApiFilter extends GenericFilterBean {


    ApiTokenService apiTokenService;

    public AesApiFilter(ApiTokenService apiserv) {
        this.apiTokenService = apiserv;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        //Principal user = (Principal) ((SecurityContextImpl)req.getSession().getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal();

        //Authentication restUser = SecurityContextHolder.getContext().s

        //restUser.setAuthenticated(true);

        //Collection<GrantedAuthority> authorities = CustomUserDetailsService.getGrantedAuthorities();

//        System.out.println("PRINCIPAL  : " + restUser.getPrincipal());
//        System.out.println("AUTHORITIES: " + restUser.getAuthorities());
//        System.out.println("CREDENTIALS: " + restUser.getCredentials());
//        System.out.println("DETAILS    : " + restUser.getDetails());

        //AuthorityUtils.createAuthorityList("TRUSTED_REST_USER");

        Enumeration<String> headerNames = req.getHeaderNames();
        System.out.println("\n\nCHUNT >>>>>> ");
        while(headerNames.hasMoreElements()) {
            String elem = headerNames.nextElement();
            System.out.println("HEADER NAME : " + elem);
            System.out.println("HEADER VALUE: " + req.getHeader(elem));
        }
        System.out.println("\n\n");
        String api_token = req.getHeader("api-token");
        System.out.println(api_token);
        //apiTokenService = WebApplicationContextUtils.getRequiredWebApplicationContext(getFilterConfig().getServletContext()).getBean(ApiTokenService.class);
        ApiToken token1 = apiTokenService.findApiTokenById(1);
        System.out.println(token1);
        ApiToken token2 = apiTokenService.findApiTokenByToken(api_token);
        System.out.println(token2);
        if ("123qweasd".equals(api_token)) {
            System.out.println("VALID");
            User sgtPoopDick = new User();
            chain.doFilter(request,response);
        } else {
            System.out.println("INVALID");
        }

    }

}
