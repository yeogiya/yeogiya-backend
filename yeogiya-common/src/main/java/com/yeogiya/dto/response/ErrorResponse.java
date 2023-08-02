package com.yeogiya.dto.response;

import com.yeogiya.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int result;
    private final String message;

    private ErrorResponse(int result, String message) {
        this.result = result;
        this.message = message;
    }

    public static ErrorResponse of(BaseException ex) {
        return new ErrorResponse(ex.getResult(), ex.getMessage());
    }
}
