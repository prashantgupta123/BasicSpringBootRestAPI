package com.basicSpringBootRestAPI.controller;

import com.basicSpringBootRestAPI.dto.response.AbstractResponseDto;
import com.basicSpringBootRestAPI.util.ResponseUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "rest")
public class RestAPIController {

    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AbstractResponseDto> home() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "ONE");
        map.put("2", "TWO");
        map.put("3", "THREE");
        return ResponseUtil.success().body(map).send(HttpStatus.OK, "Response map fetch successfully");
    }

    @RequestMapping(value = "/apiError", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AbstractResponseDto> homeError() {
        return ResponseUtil.error().send(HttpStatus.INTERNAL_SERVER_ERROR, "Response not found");
    }

}
