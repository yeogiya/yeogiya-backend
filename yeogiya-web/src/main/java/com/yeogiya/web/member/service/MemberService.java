package com.yeogiya.web.member.service;

import com.yeogiya.entity.member.LoginType;
import com.yeogiya.entity.member.Member;
import com.yeogiya.entity.member.Role;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.repository.MemberRepository;
import com.yeogiya.web.member.dto.request.ChangeNicknameRequestDTO;
import com.yeogiya.web.member.dto.request.SignUpRequestDTO;
import com.yeogiya.web.image.ImageUploadService;
import com.yeogiya.web.member.dto.request.ResetPasswordRequestDTO;
import com.yeogiya.web.member.dto.request.SendPasswordResetEmailRequestDTO;
import com.yeogiya.web.member.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetService passwordResetService;
    private final ImageUploadService imageUploadService;

    @Transactional
    public void signUp(SignUpRequestDTO memberSignUpDto) {
        if (memberRepository.existsById(memberSignUpDto.getId())) {
            throw new ClientException.Conflict(EnumErrorCode.ALREADY_EXISTS_ID);
        }
        if (memberRepository.existsByEmail(memberSignUpDto.getEmail())) {
            throw new ClientException.Conflict(EnumErrorCode.ALREADY_EXISTS_EMAIL);
        }
        if (memberRepository.existsByNickname(memberSignUpDto.getNickname())) {
            throw new ClientException.Conflict(EnumErrorCode.ALREADY_EXISTS_NICKNAME);
        }

        Member member = Member.builder()
                .id(memberSignUpDto.getId())
                .email(memberSignUpDto.getEmail())
                .loginType(LoginType.EMAIL)
                .password(memberSignUpDto.getPassword())
                .nickname(memberSignUpDto.getNickname())
                .role(Role.USER)
                .build();

        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);

    }

    @Transactional(readOnly = true)
    public CheckDuplicationResponseDTO checkIdDuplication(String id) {
        return new CheckDuplicationResponseDTO(memberRepository.existsById(id));
    }

    @Transactional(readOnly = true)
    public CheckDuplicationResponseDTO checkNicknameDuplication(String nickname) {
        return new CheckDuplicationResponseDTO(memberRepository.existsByNickname(nickname));
    }

    @Transactional(readOnly = true)
    public CheckDuplicationResponseDTO checkEmailDuplication(String email) {
        return new CheckDuplicationResponseDTO(memberRepository.existsByEmail(email));
    }

    @Transactional(readOnly = true)
    public Optional<Member> getByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public void signUp(Member member) {
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public FindIdResponseDTO getIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_MEMBER));

        String maskedId = member.getId().replaceAll("(?<=.{3})." , "*");

        return FindIdResponseDTO.builder()
                .id(maskedId)
                .build();
    }

    @Transactional
    public void sendPasswordResetEmail(SendPasswordResetEmailRequestDTO requestDTO) {
        Member member = memberRepository.findByIdAndEmail(requestDTO.getId(), requestDTO.getEmail())
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_MEMBER));

        passwordResetService.send(member);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDTO requestDTO) {
        Long memberId = passwordResetService.getMemberIdByToken(requestDTO.getToken());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_MEMBER));

        // TODO: 기획 쪽에 같은 패스워드 체크 있는지 물어보고 있으면 추가

        member.resetPassword(passwordEncoder, requestDTO.getPassword());
        passwordResetService.confirm(requestDTO.getToken());
    }

    @Transactional
    public ChangeNicknameResponseDTO changeNickname(String memberId, ChangeNicknameRequestDTO requestDTO) {
        Member member = getMember(memberId);
        member.changeNickname(requestDTO.getNickname());

        return ChangeNicknameResponseDTO.builder()
                .nickname(member.getNickname())
                .build();
    }

    @Transactional
    public ChangeProfileImgResponseDTO changeProfileImg(String memberId, MultipartFile profileImg) {
        Member member = getMember(memberId);
        String imageUrl = imageUploadService.upload(profileImg);

        member.changeProfileImg(imageUrl);

        return ChangeProfileImgResponseDTO.builder()
                .profileImgUrl(imageUrl)
                .build();
    }

    private Member getMember(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_MEMBER));
    }

    @Transactional
    public void updateRefreshToken(String id, String refreshToken) {
        Member member = getMember(id);

        member.updateRefreshToken(refreshToken);
    }

    @Transactional
    public void logout(String id) {
        Member member = getMember(id);
        member.logout();
    }

    @Transactional
    public void withdraw(String id) {
        Member member = getMember(id);
        member.withdraw();
    }

    public MemberResponseDTO getMemberInfo(String id) {
        Member member = getMember(id);

        return new MemberResponseDTO(member);
    }
}
