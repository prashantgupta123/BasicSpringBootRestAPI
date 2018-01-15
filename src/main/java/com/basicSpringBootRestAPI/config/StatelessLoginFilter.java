package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.constant.ApplicationConstant;
import com.basicSpringBootRestAPI.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final SpringSecurityService springSecurityService;

    public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService, SpringSecurityService springSecurityService, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(urlMapping));
        System.out.println("StatelessLoginFilter -> constructor");
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.springSecurityService = springSecurityService;
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        System.out.println("StatelessLoginFilter -> attemptAuthentication");
        final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());
        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                         Authentication authentication) throws IOException, ServletException {
        System.out.println("StatelessLoginFilter -> successfulAuthentication");
        // Lookup the complete User object from the database and create an
        // Authentication for it
        final User authenticatedUser = springSecurityService.findByUsername(authentication.getName());
        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

        // Add the custom token as HTTP header to the response
        tokenAuthenticationService.addAuthentication(response, userAuthentication);
        addUserDataInResponse(response, authenticatedUser);

        // Add the authentication to the Security context
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }

    private void addUserDataInResponse(HttpServletResponse response, User user) throws IOException {
        System.out.println("StatelessLoginFilter -> addUserDataInResponse");
        response.getWriter().write(new ObjectMapper().writeValueAsString(user));
        response.setHeader(ApplicationConstant.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
}