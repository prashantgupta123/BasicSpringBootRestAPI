package com.basicSpringBootRestAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccess loginSuccess;

    @Autowired
    private LogoutSuccess logoutSuccess;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private BasicAuthenticationEntryPoint entryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder
                .inMemoryAuthentication()
                .withUser("user").password("pass").roles("USER").and()
                .withUser("admin").password("pass").roles("ADMIN");
    }*/

    /*create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)*/

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/user").hasAuthority("ROLE_USER")
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .antMatchers("/register/**").permitAll()
                .antMatchers("/rest/api/**").hasAuthority("ROLE_USER")
                .antMatchers("/**").denyAll()
                .anyRequest().authenticated()

                .and()
                .httpBasic().authenticationEntryPoint(entryPoint)

                /*.and()
                .formLogin()
                .successHandler(loginSuccess)
                .loginPage("/login")
                .loginProcessingUrl("/loginAction")
                .permitAll()

                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccess)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login")
                .permitAll()

                .and()
                .rememberMe()
                .tokenValiditySeconds(3600)
                .rememberMeCookieName("rememberMeCookie")
                .rememberMeParameter("rememberMe")
                .tokenRepository(persistentTokenRepository())*/

                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }
}
