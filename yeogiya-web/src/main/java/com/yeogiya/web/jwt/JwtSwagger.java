package com.yeogiya.web.jwt;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "토큰")
public interface JwtSwagger {

    ResponseEntity<Void> reissue(
            @Parameter(in = ParameterIn.HEADER,
                    name = "Authorization-refresh",
                    description = "리프레시 토큰",
                    required = true) String refreshToken);
}
