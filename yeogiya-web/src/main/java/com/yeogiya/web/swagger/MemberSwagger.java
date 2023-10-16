package com.yeogiya.web.swagger;

import com.yeogiya.dto.response.CommonResponse;
import com.yeogiya.web.dto.MemberSignUpDTO;
import com.yeogiya.web.dto.member.CheckDuplicationResponseDTO;
import com.yeogiya.web.dto.member.FindIdResponseDTO;
import com.yeogiya.web.dto.member.ResetPasswordRequestDTO;
import com.yeogiya.web.dto.member.SendPasswordResetEmailRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원")
public interface MemberSwagger {

    @Operation(summary = "이메일 회원가입")
    CommonResponse<Void> signUp(MemberSignUpDTO memberSignUpDTO);

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
}
