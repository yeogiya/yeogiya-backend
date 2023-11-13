package com.yeogiya.web.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum OAuthAttributes {

    GOOGLE("google", (attributes) -> {
        return new UserProfile(
                UUID.randomUUID().toString(),
                (String) attributes.get("email"),
                (String) attributes.get("name"),
                (String) attributes.get("picture")
        );
    }),

    KAKAO("kakao", (attributes) -> {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return new UserProfile(
                UUID.randomUUID().toString(),
                (String) kakaoAccount.get("email"),
                (String) profile.get("nickname"),
                (String) profile.get("profile_image_url")
        );
    });

    private final String registrationId;
    private final Function<Map<String, Object>, UserProfile> of;

    public static UserProfile extract(String loginProvider, Map<String, Object> attributes) {
        return Arrays.stream(values())
                     .filter(provider -> provider.registrationId.equals(loginProvider))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
