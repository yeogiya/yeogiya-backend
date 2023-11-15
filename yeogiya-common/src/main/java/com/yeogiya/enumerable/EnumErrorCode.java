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

    INVALID_TOKEN(100009, "유효하지 않은 토큰입니다."),

    INVALID_PERMISSION(100010, "인증에 실패했습니다."),


    FAIL_FILE_UPLOAD(200001, "파일 업로드에 실패했습니다."),
    INCORRECTLY_FORMATTED_FILE(200002, "잘못된 형식의 파일입니다."),
    AVAILABLE_AFTER_LOGGING_IN(200003, "로그인 후 이용 가능합니다."),
    NOT_FOUND_PLACE(200004, "장소가 없습니다."),
    NOT_FOUND_DIARY(200005, "해당 공간일기가 존재하지 않습니다."),
    INVALID_ACCESS(200006, "잘못된 접근입니다.")


    ;

    private final int result;
    private final String message;
}
