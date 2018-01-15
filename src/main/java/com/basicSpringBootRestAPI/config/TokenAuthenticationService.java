package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.constant.ApplicationConstant;
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
        
        final String accessToken = request.getHeader(ApplicationConstant.TOKEN_HEADER);
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
}
