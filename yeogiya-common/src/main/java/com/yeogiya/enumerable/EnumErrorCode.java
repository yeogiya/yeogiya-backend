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

    FAIL_FILE_UPLOAD(200001, "파일 업로드에 실패했습니다."),
    INCORRECTLY_FORMATTED_FILE(200002, "잘못된 형식의 파일입니다."),

    ;

    private final int result;
    private final String message;
}
