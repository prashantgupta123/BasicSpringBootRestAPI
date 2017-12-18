package com.basicSpringBootRestAPI.config;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void afterPropertiesSet() throws Exception {
        String REALM = "Restful Spring Security Application";
        setRealmName(REALM);
        super.afterPropertiesSet();
    }
}