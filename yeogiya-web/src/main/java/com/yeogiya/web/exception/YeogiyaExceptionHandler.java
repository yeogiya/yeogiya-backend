package com.yeogiya.web.exception;

import com.yeogiya.dto.response.ErrorResponse;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.BaseException;
import com.yeogiya.exception.ClientException;
import com.yeogiya.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class YeogiyaExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handle(BaseException ex) {
        return new ResponseEntity<>(ErrorResponse.of(ex), ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return handle(new ClientException.BadRequest(400, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        log.error("Internal Server Error. message = {}", ex.getMessage());
        return handle(new ServerException.InternalServerError(EnumErrorCode.INTERNAL_SERVER_ERROR));
    }
}
