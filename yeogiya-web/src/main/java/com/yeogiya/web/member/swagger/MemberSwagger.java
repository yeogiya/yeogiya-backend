package com.yeogiya.web.member.swagger;

import com.yeogiya.dto.response.CommonResponse;
import com.yeogiya.web.member.dto.request.*;
import com.yeogiya.web.member.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Tag(name = "회원")
public interface MemberSwagger {

    @Operation(summary = "이메일 회원가입")
    CommonResponse<Void> signUp(SignUpRequestDTO memberSignUpDTO);

    @Operation(summary = "아이디 중복 체크")
    CommonResponse<CheckDuplicationResponseDTO> checkIdDuplicate(String id);

    @Operation(summary = "닉네임 중복 체크")
    CommonResponse<CheckDuplicationResponseDTO> checkNicknameDuplicate(String nickname);

    @Operation(summary = "이메일 중복 체크")
    CommonResponse<CheckDuplicationResponseDTO> checkEmailDuplicate(String email);

    @Operation(summary = "아이디 찾기")
    CommonResponse<FindIdResponseDTO> findId(String email);

    @Operation(summary = "비밀번호 재설정 이메일 발송")
    CommonResponse<Void> sendPasswordResetEmail(SendPasswordResetEmailRequestDTO requestDTO);

    @Operation(summary = "비밀번호 재설정")
    CommonResponse<Void> resetPassword(ResetPasswordRequestDTO requestDTO);

    @Operation(summary = "비밀번호 재설정 전 현재 비밀번호 확인")
    CommonResponse<Void> checkPassword(@Parameter(hidden = true) Principal principal,
                                       CheckPasswordRequestDTO requestDTO);

    @Operation(summary = "로그인한 회원 비밀번호 재설정")
    CommonResponse<Void> resetPassword(@Parameter(hidden = true) Principal principal,
                                       AuthResetPasswordRequestDTO requestDTO);

    @Operation(summary = "회원 탈퇴", requestBody = @RequestBody(content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WithdrawalRequestDTO.class))
    }))
    CommonResponse<Void> withdraw(Principal principal, @RequestBody WithdrawalRequestDTO requestDTO);

    @Operation(summary = "액세스 토큰으로 회원 정보 조회")
    CommonResponse<MemberResponseDTO> getMemberInfo(Principal principal);

    @Operation(summary = "회원 정보 수정")
    CommonResponse<ModifyMemberInfoResponseDTO> modify(@Parameter(hidden = true) Principal principal,
                                                       String nickname,
                                                       MultipartFile profileImage);
}
