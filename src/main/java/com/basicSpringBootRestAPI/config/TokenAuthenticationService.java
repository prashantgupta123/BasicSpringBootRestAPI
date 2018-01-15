package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.entity.Role;
import com.basicSpringBootRestAPI.entity.UserAuthenticationToken;
import com.basicSpringBootRestAPI.repository.UserAuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class TokenAuthenticationService {

    @Autowired
    private UserAuthenticationTokenRepository userAuthenticationTokenRepository;

    public Authentication getAuthentication(HttpServletRequest request) {
        System.out.println("TokenAuthenticationService -> getAuthentication");
        
        final String accessToken = request.getHeader("oauth-token");
        if (accessToken != null) {
            UserAuthenticationToken userAuthenticationToken = null;
            try {
                userAuthenticationToken = userAuthenticationTokenRepository.findByToken(accessToken);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (userAuthenticationToken == null) {
                return null;
            }

            com.basicSpringBootRestAPI.entity.User user = userAuthenticationToken.getUser();

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            }

            final User userSpring = new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    true,
                    true,
                    true,
                    authorities);

            return new UsernamePasswordAuthenticationToken(user, null, userSpring.getAuthorities());
        }
        return null;
    }

    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final com.basicSpringBootRestAPI.entity.User user = authentication.getDetails();
        response.addHeader("oauth-token", "123456789");
    }
}
