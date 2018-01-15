package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private SpringSecurityService springSecurityService;

    public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        System.out.println("CustomBasicAuthenticationFilter -> constructor");
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        System.out.println("CustomBasicAuthenticationFilter -> onSuccessfulAuthentication");
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        User user1 = springSecurityService.findByUsername(user.getUsername());
        String token = springSecurityService.saveUserAuthenticationToken(user1);
        response.setHeader("oauth-token", token);
    }
}
