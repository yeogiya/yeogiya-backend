package com.yeogiya.web.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/public/v1.0.0/tokens")
public class JwtController implements JwtSwagger {

    private final JwtService jwtService;

    @PostMapping("/refresh")
    public ResponseEntity<Void> reissue(@RequestHeader(name = "Authorization-refresh") String refreshToken) {
        return new ResponseEntity<>(jwtService.reissue(refreshToken), HttpStatus.CREATED);
    }
}
