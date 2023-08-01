package com.yeogiya.web.exception;

import com.yeogiya.dto.response.ErrorResponse;
import com.yeogiya.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class YeogiyaExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handle(BaseException ex) {
        return new ResponseEntity<>(ErrorResponse.of(ex), ex.getHttpStatus());
    }
}
