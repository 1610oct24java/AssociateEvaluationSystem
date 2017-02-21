/*
package com.revature.aes.filter;

import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

*/
/*
 * Created by Nick on 2/16/2017.
*//*



@Component
public class CORSRESTFilter extends GenericFilterBean {

*/
/*    @Autowired
    Logging log;*//*


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

*/
/*        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        ((HttpServletResponse)response).setHeader("Access-Control-Max-Age", "3600");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Header", "Content-Type, Accept, X-Requested-With, remember-me");

        Map<String, String[]> params = request.getParameterMap();

        for(String s : params.keySet()){

            log.info("Param key: " + s);

            for(String s1 : params.get(s)){

                log.info("  value: " + s1);

            }

        }*//*


        chain.doFilter(request, response);

    }
}
*/
