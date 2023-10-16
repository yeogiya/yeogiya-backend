package com.yeogiya.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponse<T> {

    private HttpStatus status;
    private T body;

    public CommonResponse(HttpStatus status) {
        this.status = status;
    }

    public CommonResponse(HttpStatus status, T body) {
        this.status = status;
        this.body = body;
    }
}
