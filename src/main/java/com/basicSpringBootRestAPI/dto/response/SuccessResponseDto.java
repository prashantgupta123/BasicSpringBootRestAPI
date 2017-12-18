package com.basicSpringBootRestAPI.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"code", "status", "message", "data"})
public class SuccessResponseDto extends AbstractResponseDto {

    private Object data;

    public Object getData() {
        return data;
    }

    public SuccessResponseDto setData(Object data) {
        this.data = data;
        return this;
    }

    public SuccessResponseDto body(Object data) {
        this.data = data;
        return this;
    }

}
