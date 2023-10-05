package com.yeogiya.web.controller;

import com.yeogiya.web.dto.MemberSignUpDTO;
import com.yeogiya.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;



    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody MemberSignUpDTO memberSignUpDto) throws Exception {

        memberService.signUp(memberSignUpDto);
        log.info("회원가입 성공");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }


    @GetMapping("/sign-up/id-exist")
    public ResponseEntity<Boolean> checkIdDuplicate(String id){
        return ResponseEntity.ok(memberService.checkIdDuplication(id));
    }

    @GetMapping("/sign-up/nickname-exist")
    public ResponseEntity<Boolean> checkNicknameDuplicate(String nickname){
        return ResponseEntity.ok(memberService.checkNicknameDuplication(nickname));
    }

    @GetMapping("/sign-up/email-exist")
    public ResponseEntity<Boolean> checkEmailDuplicate(String email){
        return ResponseEntity.ok(memberService.checkEmailDuplication(email));
    }



}
