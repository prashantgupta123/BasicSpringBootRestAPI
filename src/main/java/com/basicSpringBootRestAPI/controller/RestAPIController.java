package com.basicSpringBootRestAPI.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "rest")
public class RestAPIController {

    @Secured("ROLE_USER")
    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map home() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "ONE");
        map.put("2", "TWO");
        map.put("3", "THREE");
        map.put("4", "ROLE_USER");
        return map;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/api1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map home1() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "ONE");
        map.put("2", "TWO");
        map.put("3", "THREE");
        map.put("4", "ROLE_ADMIN");
        return map;
    }

}
