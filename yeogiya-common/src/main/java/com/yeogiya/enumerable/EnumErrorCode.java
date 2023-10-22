package com.yeogiya.enumerable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumErrorCode {

    SUCCESS(1, "Success"),

    ALREADY_EXISTS_ID(100001, "이미 존재하는 아이디입니다."),
    ALREADY_EXISTS_EMAIL(100002, "이미 존재하는 이메일입니다."),
    ALREADY_EXISTS_NICKNAME(100003, "이미 존재하는 닉네임입니다."),
    NOT_FOUND_MEMBER(100004, "존재하지 않는 회원입니다."),
    NOT_FOUND_PASSWORD_TOKEN(100005, "비밀번호 재설정 토큰이 존재하지 않습니다."),
    EXPIRED_PASSWORD_TOKEN(100006, "비밀번호 재설정 토큰이 만료되었습니다."),
    INVALID_MEMBER_STATUS(100007, "유효하지 않은 회원 상태입니다."),
    LOGIN_FAILED(100008, "아이디 또는 비밀번호를 확인해주세요."),

    FAIL_FILE_UPLOAD(200001, "파일 업로드에 실패했습니다."),
    INCORRECTLY_FORMATTED_FILE(200002, "잘못된 형식의 파일입니다."),

    ;

    private final int result;
    private final String message;
}
