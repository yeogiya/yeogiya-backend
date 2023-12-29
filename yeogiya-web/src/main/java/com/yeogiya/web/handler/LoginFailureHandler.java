package com.yeogiya.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeogiya.dto.response.ErrorResponse;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 로그인 실패 시 처리하는 핸들러
 * SimpleUrlAuthenticationFailureHandler를 상속받아서 구현
 */
@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        ErrorResponse errorResponse;

        if (exception.getCause() instanceof ClientException.BadRequest) {
            errorResponse = ErrorResponse.of(new ClientException.BadRequest(EnumErrorCode.INVALID_MEMBER_STATUS));
        } else {
            errorResponse = ErrorResponse.of(new ClientException.Unauthorized(EnumErrorCode.LOGIN_FAILED));
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        log.info("로그인에 실패했습니다. 메시지 : {}", exception.getMessage());
    }
}