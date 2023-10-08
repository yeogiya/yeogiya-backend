package com.yeogiya.web.service.member;

import com.yeogiya.entity.member.LoginType;
import com.yeogiya.entity.member.Member;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.web.dto.member.OAuthAttributes;
import com.yeogiya.web.dto.member.UserProfile;
import com.yeogiya.web.jwt.JwtService;
import com.yeogiya.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("소셜 로그인 성공");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("OAuth2User = {}", oAuth2User);

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String registrationId = token.getAuthorizedClientRegistrationId();
        LoginType loginType = LoginType.getLoginType(registrationId);

        UserProfile userProfile = getUserProfile(registrationId, oAuth2User);
        log.info("userProfile: {}", userProfile);
        memberService.getByEmail(userProfile.getEmail()).ifPresentOrElse(
                existingUser -> {
                    if (!existingUser.getLoginType().equals(loginType)) {
                        throw new ClientException.Conflict(EnumErrorCode.ALREADY_EXISTS_EMAIL);
                    }
                },
                () -> {
                    memberService.signUp(userProfile.toMember(loginType, generateRandomNickname())); // TODO: 랜덤 닉네임 생성
                }
        );

        Member member = memberService.getByEmail(userProfile.getEmail()).get();

        String accessToken = jwtService.createAccessToken(member.getId());
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
    }

    private String generateRandomNickname() {
        return "랜덤 닉네임" + Math.random();
    }

    private UserProfile getUserProfile(String registrationId, OAuth2User oAuth2User) {
        return OAuthAttributes.extract(registrationId, oAuth2User.getAttributes());
    }
}
