package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.entity.Role;
import com.basicSpringBootRestAPI.entity.User;
import com.basicSpringBootRestAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (user == null) {
            throw new UsernameNotFoundException("User does not exists");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }
}
