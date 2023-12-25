package com.yeogiya.web.member.service;

import com.yeogiya.entity.member.LoginType;
import com.yeogiya.entity.member.Member;
import com.yeogiya.entity.member.Role;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.repository.MemberRepository;
import com.yeogiya.web.member.dto.request.*;
import com.yeogiya.web.image.ImageUploadService;
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

        if (member.isWithdrawal()) {
            throw new ClientException.Conflict(EnumErrorCode.INVALID_MEMBER_STATUS);
        }

        passwordResetService.send(member);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDTO requestDTO) {
        Long memberId = passwordResetService.getMemberIdByToken(requestDTO.getToken());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_MEMBER));

        if (member.isWithdrawal()) {
            throw new ClientException.Conflict(EnumErrorCode.INVALID_MEMBER_STATUS);
        }

        // TODO: 기획 쪽에 같은 패스워드 체크 있는지 물어보고 있으면 추가

        member.resetPassword(passwordEncoder, requestDTO.getPassword());
        passwordResetService.confirm(requestDTO.getToken());
    }

    @Transactional
    public ModifyMemberInfoResponseDTO modify(String memberId, String newNickname, MultipartFile profileImg) {
        Member member = getMember(memberId);
        String nickname = member.getNickname();

        if (newNickname != null && !newNickname.equals(nickname) && memberRepository.existsByNickname(newNickname)) {
            throw new ClientException.Conflict(EnumErrorCode.ALREADY_EXISTS_NICKNAME);
        }

        String imageUrl = member.getProfileImg();

        if (profileImg != null) {
            imageUrl = imageUploadService.upload(profileImg);
        }

        member.modify(newNickname, imageUrl);

        return ModifyMemberInfoResponseDTO.builder()
                .nickname(newNickname)
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
    public void withdraw(String id, WithdrawalRequestDTO requestDTO) {
        Member member = getMember(id);
        member.withdraw(requestDTO.getWithdrawalReason(), requestDTO.getWithdrawalReasonDetail());
    }

    public MemberResponseDTO getMemberInfo(String id) {
        Member member = getMember(id);

        return new MemberResponseDTO(member);
    }
}
