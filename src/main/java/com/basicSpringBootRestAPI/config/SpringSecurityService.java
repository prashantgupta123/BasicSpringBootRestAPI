package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.entity.User;
import com.basicSpringBootRestAPI.entity.UserAuthenticationToken;
import com.basicSpringBootRestAPI.repository.UserAuthenticationTokenRepository;
import com.basicSpringBootRestAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class SpringSecurityService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthenticationTokenRepository userAuthenticationTokenRepository;

    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(user.getUsername());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String saveUserAuthenticationToken(User user) {
        String token = UUID.randomUUID().toString().toUpperCase() + "|" + user.getId();
        UserAuthenticationToken verificationToken = userAuthenticationTokenRepository.findByUser(user);
        if (verificationToken == null) {
            verificationToken = new UserAuthenticationToken();
        }
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        userAuthenticationTokenRepository.save(verificationToken);
        return token;
    }
}