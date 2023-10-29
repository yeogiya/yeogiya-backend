package com.yeogiya.web.controller;

import com.yeogiya.dto.response.CommonResponse;
import com.yeogiya.web.dto.MemberSignUpDTO;
import com.yeogiya.web.dto.member.*;
import com.yeogiya.web.service.MemberService;
import com.yeogiya.web.swagger.MemberSwagger;
import com.yeogiya.web.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController implements MemberSwagger {

    private final MemberService memberService;

    @PostMapping("/public/v1.0.0/members/sign-up")
    public CommonResponse<Void> signUp(@Valid @RequestBody MemberSignUpDTO memberSignUpDto) {
        memberService.signUp(memberSignUpDto);
        return new CommonResponse<>(HttpStatus.OK);
    }

    @GetMapping("/public/v1.0.0/members/id-exists")
    public CommonResponse<CheckDuplicationResponseDTO> checkIdDuplicate(String id) {
        return new CommonResponse<>(HttpStatus.OK, memberService.checkIdDuplication(id));
    }

    @GetMapping("/public/v1.0.0/members/nickname-exists")
    public CommonResponse<CheckDuplicationResponseDTO> checkNicknameDuplicate(String nickname) {
        return new CommonResponse<>(HttpStatus.OK, memberService.checkNicknameDuplication(nickname));
    }

    @GetMapping("/public/v1.0.0/members/email-exists")
    public CommonResponse<CheckDuplicationResponseDTO> checkEmailDuplicate(String email) {
        return new CommonResponse<>(HttpStatus.OK, memberService.checkEmailDuplication(email));
    }

    /**
     * 아이디 찾기
     * @param email 가입 시 사용한 이메일
     */
    @GetMapping("/public/v1.0.0/members/find-id")
    public CommonResponse<FindIdResponseDTO> findId(@RequestParam String email) {
        FindIdResponseDTO response = memberService.getIdByEmail(email);

        return new CommonResponse<>(HttpStatus.OK, response);
    }

    /**
     * 비밀번호 재설정 이메일 발송
     * @param requestDTO 이메일, 아이디
     */
    @PostMapping("/public/v1.0.0/members/send-password-reset-email")
    public CommonResponse<Void> sendPasswordResetEmail(@RequestBody @Valid SendPasswordResetEmailRequestDTO requestDTO) {
        memberService.sendPasswordResetEmail(requestDTO);

        return new CommonResponse<>(HttpStatus.OK);
    }

    /**
     * 비밀번호 재설정
     * @param requestDTO 새 비밀번호, 인증 토큰
     */
    @PostMapping("/public/v1.0.0/members/reset-password")
    public CommonResponse<Void> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO requestDTO) {
        memberService.resetPassword(requestDTO);

        return new CommonResponse<>(HttpStatus.OK);
    }

    @PatchMapping("/auth/v1.0.0/members/change-nickname")
    public CommonResponse<ChangeNicknameResponseDTO> changeNickname(Principal principal,
                                                                    @RequestBody @Valid ChangeNicknameRequestDTO requestDTO) {

        ChangeNicknameResponseDTO responseDTO = memberService.changeNickname(MemberUtil.getMemberId(principal), requestDTO);

        return new CommonResponse<>(HttpStatus.OK, responseDTO);
    }

    @PatchMapping(value = "/auth/v1.0.0/members/change-profile-img", consumes = "multipart/form-data")
    public CommonResponse<ChangeProfileImgResponseDTO> changeProfileImg(Principal principal,
                                                                        @RequestPart MultipartFile profileImg) {

        ChangeProfileImgResponseDTO responseDTO = memberService.changeProfileImg(MemberUtil.getMemberId(principal), profileImg);

        return new CommonResponse<>(HttpStatus.OK, responseDTO);
    }
}
