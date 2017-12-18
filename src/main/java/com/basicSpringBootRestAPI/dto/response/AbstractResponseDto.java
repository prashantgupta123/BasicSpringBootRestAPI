package com.basicSpringBootRestAPI.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractResponseDto {

    private int code = HttpStatus.OK.value();

    private String status = HttpStatus.OK.getReasonPhrase();

    private String message = "";

    public int getCode() {
        return code;
    }

    public AbstractResponseDto setCode(int code) {
        this.code = code;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AbstractResponseDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AbstractResponseDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntity<AbstractResponseDto> send(HttpStatus status, String message) {
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        return new ResponseEntity<AbstractResponseDto>(this, status);
    }

}
