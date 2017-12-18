package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.entity.Role;
import com.basicSpringBootRestAPI.entity.UserAuthenticationToken;
import com.basicSpringBootRestAPI.repository.UserAuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private UserAuthenticationTokenRepository userAuthenticationTokenRepository;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        //extract token from header
        final String accessToken = httpRequest.getHeader("oauth-token");
        System.out.println("accessToken: " + accessToken);

        if (null != accessToken) {
            //get and check whether token is valid (from DB or file wherever you are storing the token)
            UserAuthenticationToken userAuthenticationToken = null;
            try {
                userAuthenticationToken = userAuthenticationTokenRepository.findByToken(accessToken);
                System.out.println("userAuthenticationToken" + userAuthenticationToken.getId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (userAuthenticationToken == null) {
                throw new UsernameNotFoundException("User does not exists");
            }

            com.basicSpringBootRestAPI.entity.User user = userAuthenticationToken.getUser();

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            }

            //Populate SecurityContextHolder by fetching relevant information using token
            final User userSpring = new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    true,
                    true,
                    true,
                    authorities);

            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, userSpring.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}