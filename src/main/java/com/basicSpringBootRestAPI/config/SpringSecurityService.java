package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.entity.User;
import com.basicSpringBootRestAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SpringSecurityService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(user.getUsername());
    }
}