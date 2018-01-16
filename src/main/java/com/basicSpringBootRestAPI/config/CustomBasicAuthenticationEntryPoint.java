package com.basicSpringBootRestAPI.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        super.commence(request, response, authException);
        System.out.println("CustomBasicAuthenticationEntryPoint -> commence");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String REALM = "Restful Spring Security Application";
        setRealmName(REALM);
        super.afterPropertiesSet();
        System.out.println("CustomBasicAuthenticationEntryPoint -> afterPropertiesSet");
    }
}