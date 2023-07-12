package com.yeogiya.web.controller;

import com.yeogiya.web.dto.JoinRequestDto;
import com.yeogiya.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/join")
    public Long join(@RequestBody JoinRequestDto requestDto) {

        return memberService.join(requestDto);
    }
}
