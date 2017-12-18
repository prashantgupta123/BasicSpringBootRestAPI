package com.basicSpringBootRestAPI.util;

import com.basicSpringBootRestAPI.dto.response.ErrorResponseDto;
import com.basicSpringBootRestAPI.dto.response.SuccessResponseDto;

public class ResponseUtil {

    public static SuccessResponseDto success() {
        return new SuccessResponseDto();
    }

    public static ErrorResponseDto error() {
        return new ErrorResponseDto();
    }

}
