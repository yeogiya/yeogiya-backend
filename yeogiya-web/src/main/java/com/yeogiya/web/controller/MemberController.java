package com.yeogiya.web.controller;

import com.yeogiya.web.dto.MemberSignUpDto;
import com.yeogiya.web.service.MemberService;
import com.yeogiya.web.validator.CheckEmailValidator;
import com.yeogiya.web.validator.CheckIdValidator;
import com.yeogiya.web.validator.CheckNicknameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    /* 중복 체크 유효성 검사 */
    private final CheckIdValidator checkIdValidator;
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckEmailValidator checkEmailValidator;

    /* 커스텀 유효성 검증 */
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkIdValidator);
        binder.addValidators(checkNicknameValidator);
        binder.addValidators(checkEmailValidator);
    }


    @PostMapping("/sign-up")
    public String signUp(@Valid @RequestBody MemberSignUpDto memberSignUpDto, BindingResult bindingResult, Model model) throws Exception {
        /* 검증 */
        if(bindingResult.hasErrors()) {
            /* 회원가입 실패 시 입력 데이터 값 유지 */
            model.addAttribute("memberSignUpDto", memberSignUpDto);

            /* 유효성 검사를 통과하지 못 한 필드와 메시지 핸들링 */
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_"+error.getField(), error.getDefaultMessage());
                log.info("회원가입 실패 ! error message : "+error.getDefaultMessage());
            }

            /* Model에 담아 view resolve */
            for(String key : errorMap.keySet()) {
                model.addAttribute(key, errorMap.get(key));
            }

            /* 회원가입 페이지로 리턴 */
            return "/user/user-join";
        }

        // 회원가입 성공 시
        memberService.signUp(memberSignUpDto);
        log.info("회원가입 성공");
        return "redirect:/login";
    }


    @PostMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }


    @GetMapping("/sign-up/{id}/exists")
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable String id){
        return ResponseEntity.ok(memberService.checkIdDuplication(id));
    }

    @GetMapping("/sign-up/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname){
        return ResponseEntity.ok(memberService.checkNicknameDuplication(nickname));
    }

    @GetMapping("/sign-up/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email){
        return ResponseEntity.ok(memberService.checkEmailDuplication(email));
    }



}
