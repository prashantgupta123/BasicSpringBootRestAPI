package com.basicSpringBootRestAPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    @ResponseBody
    public String home() {
        return "Home";
    }

    @RequestMapping(value = "/user")
    @ResponseBody
    public String userHome() {
        return "User Home";
    }

    @RequestMapping(value = "/admin")
    @ResponseBody
    public String adminHome() {
        return "Admin Home";
    }

    @RequestMapping(value = "/hacker")
    @ResponseBody
    public String hackerHome() {
        return "Hacker Home";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    @ResponseBody
    public String accessDenied() {
        return "Access Deny";
    }

}
