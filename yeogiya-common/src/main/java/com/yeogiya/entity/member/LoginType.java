package com.yeogiya.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginType {

    EMAIL(false),
    GOOGLE(true),
    KAKAO(true);

    private final boolean requiresRemoteAuthorization;

    public static LoginType getLoginType(String registrationId) {
    	switch(registrationId) {
    		case "google":
    			return GOOGLE;
    		case "kakao":
    			return KAKAO;
    		default:
    			return EMAIL;
    	}
    }
}
