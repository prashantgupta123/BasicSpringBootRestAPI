package com.basicSpringBootRestAPI.config;

import com.basicSpringBootRestAPI.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    @Autowired
    private SpringSecurityService springSecurityService;

    public SpringSecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .exceptionHandling().accessDeniedPage("/403").and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl().and().and()
                .authorizeRequests()
                .antMatchers("/rest/token").permitAll()
                .antMatchers(HttpMethod.POST, "/rest/api/login").permitAll()
                .antMatchers("/rest/**").authenticated()
                .antMatchers("/**").denyAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(new StatelessLoginFilter("/rest/api/login", tokenAuthenticationService, springSecurityService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
