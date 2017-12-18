package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.entity.User;
import com.basicSpringBootRestAPI.entity.UserAuthenticationToken;
import com.basicSpringBootRestAPI.repository.UserAuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private UserAuthenticationTokenRepository userAuthenticationTokenRepository;

    public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        User user = (User) authResult.getPrincipal();
        //Generate Token
        String token = UUID.randomUUID().toString().toUpperCase() + "|" + user.getId();
        //Save the token for the logged in user
        UserAuthenticationToken verificationToken = new UserAuthenticationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        userAuthenticationTokenRepository.save(verificationToken);
        //send token in the response
        response.setHeader("oauth-token", "token");
    }
}
