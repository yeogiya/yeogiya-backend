package com.yeogiya.web.service.member;

import com.yeogiya.entity.member.Member;
import com.yeogiya.entity.member.PasswordResetEmailToken;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.repository.PasswordResetEmailTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PasswordResetService {

    private final PasswordResetEmailTokenRepository repository;
    private final JavaMailSender javaMailSender;

    // TODO: 프론트팀에서 URL 받아서 세팅하기
    private String resetUrl = "sample-url";

    public void send(Member member) {
        PasswordResetEmailToken token = saveToken(member.getMemberId());

        MimeMessage mailMessage = javaMailSender.createMimeMessage();

        try {
            log.info("member email: {}", member.getEmail());
            mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(member.getEmail()));
            mailMessage.setFrom("yeogiyaTeam");
            mailMessage.setSubject("[여기야] 비밀번호 재설정 안내");
            mailMessage.setContent(createContents(member, token.getId().toString()), "text/html;charset=euc-kr");
        } catch (MessagingException e) {
            log.error("messaging error: {}", e.getMessage());
        }

        javaMailSender.send(mailMessage);
    }

    private PasswordResetEmailToken saveToken(Long memberId) {
        PasswordResetEmailToken token = PasswordResetEmailToken.builder()
                .memberId(memberId)
                .expirationDateTime(LocalDateTime.now().plusMinutes(30))
                .build();

        return repository.save(token);
    }

    /**
     * 재설정 메일 내용 생성
     * @param member 대상 유저 정보
     * @param token 인증 토큰
     * @return 메일 내용
     */
    private String createContents(Member member, String token) {
        return "<html><body>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>" +
                "안녕하세요, 여기야 입니다.<br/>" +
                "회원님의 계정(" + member.getEmail() + ")" +
                " 비밀번호를 재설정하시려면 하단의 '비밀번호 재설정'을 클릭하세요." +
                "<br /><br />" +
                "<a href=\"" +
                resetUrl + "/" + token +
                "\" target=\"_self\">비밀번호 재설정</a>" +
                "<br /><br />" +
                "문의사항은 여기야 담당자 메일(yeogiya2023@gmail.com)로 연락 주시기 바랍니다.<br/>" +
                "감사합니다." +
                "</body></html>";
    }

    public Long getMemberIdByToken(String token) {
        UUID authToken = UUID.fromString(token);

        PasswordResetEmailToken resetToken = repository.findByIdAndExpired(authToken, false)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_PASSWORD_TOKEN));

        if (resetToken.isExpired()) {
            throw new ClientException.Conflict(EnumErrorCode.EXPIRED_PASSWORD_TOKEN);
        }

        return resetToken.getMemberId();
    }

    @Transactional
    public void confirm(String token) {
        UUID authToken = UUID.fromString(token);

        PasswordResetEmailToken resetToken = repository.findByIdAndExpired(authToken, false)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_PASSWORD_TOKEN));

        if (resetToken.isExpired()) {
            throw new ClientException.BadRequest(EnumErrorCode.EXPIRED_PASSWORD_TOKEN);
        }

        resetToken.changeToExpired();
    }
}
