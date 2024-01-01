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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class YeogiyaExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handle(BaseException ex) {
        return new ResponseEntity<>(ErrorResponse.of(ex), ex.getHttpStatus());
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorResponse> handle(ClientException ex, HttpServletResponse res) throws IOException {
        if (ex.getResult() == EnumErrorCode.ALREADY_EXISTS_EMAIL.getResult()) {
            res.sendRedirect("http://localhost:8000/notfound/account");
        }

        return handle(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return handle(new ClientException.BadRequest(400, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex, HttpServletRequest req) {
        log.error(req.getRequestURI());
        log.error(ex.getMessage(), ex);
        return handle(new ServerException.InternalServerError(EnumErrorCode.INTERNAL_SERVER_ERROR));
    }
}
