package com.yeogiya.web.controller;

import com.yeogiya.web.dto.JoinRequestDto;
import com.yeogiya.web.dto.LoginRequestDto;
import com.yeogiya.web.jwt.PrincipalDetails;
import com.yeogiya.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/info")
    public String info(@AuthenticationPrincipal PrincipalDetails principalDetails, Authentication authentication) {
        System.out.println("PrincipalDetails " + principalDetails);
        System.out.println("authentication " + authentication);

        StringBuilder sb = new StringBuilder();
        sb.append("PrincipalDetails ");
        sb.append(principalDetails);
        sb.append("\n\n");
        sb.append("authentication ");
        sb.append(authentication);

        return sb.toString();

    }

    @PostMapping("/api/join")
    public Long join(@RequestBody JoinRequestDto requestDto) {

        return memberService.join(requestDto);
    }

    @PostMapping("/api/login")
    public String login(@RequestBody LoginRequestDto requestDto) {

        return memberService.login(requestDto);
    }
}
