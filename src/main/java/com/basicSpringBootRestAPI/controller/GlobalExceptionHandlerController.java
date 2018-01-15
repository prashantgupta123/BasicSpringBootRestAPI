package com.basicSpringBootRestAPI.controller;

import com.basicSpringBootRestAPI.dto.response.AbstractResponseDto;
import com.basicSpringBootRestAPI.util.ResponseUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public HttpEntity<AbstractResponseDto> handleResourceNotFoundException() {
        return ResponseUtil.error().send(HttpStatus.BAD_REQUEST, "Response not found");
    }
}
